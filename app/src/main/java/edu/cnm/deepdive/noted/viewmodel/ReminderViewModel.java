package edu.cnm.deepdive.noted.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.noted.model.entity.Reminder;
import edu.cnm.deepdive.noted.model.entity.User;
import edu.cnm.deepdive.noted.model.pojo.UserWithReminders;
import edu.cnm.deepdive.noted.service.repository.ReminderRepository;
import edu.cnm.deepdive.noted.service.repository.UserRepository;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.time.Instant;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class ReminderViewModel extends ViewModel implements DefaultLifecycleObserver {

  private final Context context;
  private final ReminderRepository reminderRepository;
  private final UserRepository userRepository;
  private final MutableLiveData<Long> reminderId;
  private final MutableLiveData<User> user;
  private final LiveData<List<Reminder>> reminders;
  private final MutableLiveData<Boolean> editing;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  private Instant reminderModified;

  @Inject
  public ReminderViewModel(@ApplicationContext Context context, @NonNull ReminderRepository reminderRepository,
      @NonNull UserRepository userRepository, LiveData<UserWithReminders> reminder) {
    this.context = context;
    this.reminderRepository = reminderRepository;
    this.userRepository = userRepository;
    reminderId = new MutableLiveData<>();
    user = new MutableLiveData<>();
    reminders = Transformations.switchMap(user, reminderRepository::getAll);
    editing = new MutableLiveData<>(false);
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }

  public LiveData<Long> getReminderId() {
    return reminderId;
  }

  public void setReminderId(Long reminderId) {
    this.reminderId.setValue(reminderId);
  }

  public LiveData<List<Reminder>> getReminders() {
    return reminders;
  }

  LiveData<Boolean> getEditing() {
    return editing;
  }

  public void setEditing(boolean editing) {
    this.editing.setValue(editing);
  }

  public void save(UserWithReminders reminder) {
    throwable.setValue(null);
    //noinspection DataFlowIssue
    Single.just(reminder)
        .doOnSuccess((r) -> r.getReminders().clear())
        .doOnSuccess((r) -> r.getReminders().addAll(reminders.getValue()))
        .subscribe(
            (r) -> reminderId.postValue(r.getId()),
            this::postThrowable,
            pending
        );
  }

  public void remove(Reminder reminder) {
    throwable.setValue(null);
    reminderRepository
        .remove(reminder)
        .subscribe(
            () -> {},
            this::postThrowable,
            pending
        );
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    pending.clear();
    DefaultLifecycleObserver.super.onStop(owner);
  }

  public void fetchUser() {
    throwable.setValue(null);
    userRepository
        .getCurrentUser()
        .subscribe(
            user::postValue,
            this::postThrowable,
            pending
        );
  }

  private void postThrowable(Throwable throwable) {
    Log.e(NoteViewModel.class.getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

}
