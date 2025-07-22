package edu.cnm.deepdive.noted.viewmodel;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.noted.model.entity.Image;
import edu.cnm.deepdive.noted.model.entity.Task;
import edu.cnm.deepdive.noted.model.entity.User;
import edu.cnm.deepdive.noted.model.pojo.NoteWithImages;
import edu.cnm.deepdive.noted.model.pojo.UserWithReminders;
import edu.cnm.deepdive.noted.model.pojo.UserWithTasks;
import edu.cnm.deepdive.noted.service.repository.TaskRepository;
import edu.cnm.deepdive.noted.service.repository.TaskRepository;
import edu.cnm.deepdive.noted.service.repository.UserRepository;
import edu.cnm.deepdive.noted.viewmodel.NoteViewModel.VisibilityFlags;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends ViewModel implements DefaultLifecycleObserver {

  private final Context context;
  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final MutableLiveData<Long> taskId;
  private final LiveData<UserWithTasks> task;
  private final MutableLiveData<User> user;
  private final LiveData<List<Task>> tasks;
  private final MutableLiveData<Uri> captureUri;
  private final MutableLiveData<Boolean> editing;
  private final MediatorLiveData<VisibilityFlags> visibilityFlags;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  private Uri pendingCaptureUri;
  private Instant taskModified;


  public TaskViewModel(@ApplicationContext Context context, @NonNull TaskRepository taskRepository,
      @NonNull UserRepository userRepository) {
    this.context = context;
    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
    taskId = new MutableLiveData<>();
    task = setupUserWithTasks();
    user = new MutableLiveData<>();
    tasks = Transformations.switchMap(user, taskRepository::getAll);
    captureUri = new MutableLiveData<>();
    editing = new MutableLiveData<>(false);
    visibilityFlags = setupVisibilityFlags();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }

  public LiveData<Long> getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId.setValue(taskId);
  }



  private LiveData<UserWithTasks> setupUserWithTasks() {
    return null;
  }

  private MediatorLiveData<VisibilityFlags> setupVisibilityFlags() {
    return null;
  }
}
