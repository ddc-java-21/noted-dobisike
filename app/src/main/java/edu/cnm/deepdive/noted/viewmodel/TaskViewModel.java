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
import edu.cnm.deepdive.noted.model.entity.Task;
import edu.cnm.deepdive.noted.model.entity.User;
import edu.cnm.deepdive.noted.model.pojo.UserWithTasks;
import edu.cnm.deepdive.noted.service.repository.TaskRepository;
import edu.cnm.deepdive.noted.service.repository.UserRepository;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.time.Instant;
import java.util.List;
import javax.inject.Inject;

@HiltViewModel
public class TaskViewModel extends ViewModel implements DefaultLifecycleObserver {

  private final Context context;
  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final MutableLiveData<Long> taskId;
  private final MutableLiveData<User> user;
  private final LiveData<List<Task>> tasks;
  private final MutableLiveData<Boolean> editing;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  private Instant taskModified;

  @Inject
  public TaskViewModel(@ApplicationContext Context context, @NonNull TaskRepository taskRepository,
      @NonNull UserRepository userRepository) {
    this.context = context;
    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
    taskId = new MutableLiveData<>();
    user = new MutableLiveData<>();
    tasks = Transformations.switchMap(user, taskRepository::getAll);
    editing = new MutableLiveData<>(false);
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }

  public LiveData<Long> getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId.setValue(taskId);
  }

  public LiveData<List<Task>> getTasks() {
    return tasks;
  }

  LiveData<Boolean> getEditing() {
    return editing;
  }

  public void setEditing(boolean editing) {
    this.editing.setValue(editing);
  }

  public void save(UserWithTasks task) {
    throwable.setValue(null);
    //noinspection DataFlowIssue
    Single.just(task)
        .doOnSuccess((t) -> t.getTasks().clear())
        .doOnSuccess((t) -> t.getTasks().addAll(tasks.getValue()))
        .subscribe(
            (t) -> taskId.postValue(t.getId()),
            this::postThrowable,
            pending
        );
  }

  public void remove(Task task) {
    throwable.setValue(null);
    taskRepository
        .remove(task)
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
