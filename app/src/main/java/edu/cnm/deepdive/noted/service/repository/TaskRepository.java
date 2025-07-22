package edu.cnm.deepdive.noted.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.noted.model.entity.Task;
import edu.cnm.deepdive.noted.model.entity.User;
import edu.cnm.deepdive.noted.model.pojo.UserWithTasks;
import edu.cnm.deepdive.noted.service.dao.TaskDao;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TaskRepository {

  private final TaskDao taskDao;

  private final Scheduler scheduler;

  @Inject
  TaskRepository(TaskDao taskDao) {
    this.taskDao = taskDao;
    scheduler = Schedulers.io();
  }

  public LiveData<Task> get(long taskId) {
    return taskDao.select(taskId);
  }

  public Completable remove(Task task) {
    return taskDao
        .delete(task)
        .subscribeOn(scheduler)
        .ignoreElement();
  }

  public Single<UserWithTasks> save(UserWithTasks user, Task task) {
    task.setUserId(user.getId());
    return (
        (task.getId() == 0)
            ? taskDao.insert(task)
            : taskDao.update(task)
    )
        .doOnSuccess((u) -> user.getTasks().forEach((t) -> task.setUserId(u.getId())))
        .map((u) -> user.getTasks())
//        .flatMap(this::saveTasks)
        .doOnSuccess((tasks) -> {
          user.getTasks().clear();
          user.getTasks().addAll(tasks);
        })
        .map((tasks) -> user)
        .subscribeOn(scheduler);
  }

  public LiveData<List<Task>> getAll(User user) {
    return taskDao.selectWhereUserIdOrderByCreatedDesc(user.getId());
  }

  private Single<List<Task>> saveTasks(List<Task> tasks) {
    List<Single<Task>> singles = tasks
        .stream()
        .map((task) -> (task.getId() == 0)
            ? taskDao.insert(task)
            : taskDao.update(task)
                .map((count) -> task))
        .collect(Collectors.toList());
    return Single.just(singles)
        .flatMap((sngls) -> Single.zip(sngls, Arrays::asList))
        .map((taskObjects) -> taskObjects
            .stream()
            .map((taskObject) -> (Task) taskObject)
            .collect(Collectors.toList()));
  }

}
