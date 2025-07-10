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
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TaskRepository {
//
//  private final TaskDao taskDao;
//
//  private final Scheduler scheduler;
//
//  @Inject
//  public TaskRepository(TaskDao taskDao) {
//    this.taskDao = taskDao;
//    scheduler = Schedulers.io();
//  }
//
//  public LiveData<UserWithTasks> get(long taskId) {
//    return taskDao.select(taskId);
//  }
//
//  public Completable remove(Task task) {
//    return taskDao
//        .delete(task)
//        .subscribeOn(scheduler)
//        .ignoreElement();
//  }
//
//  public Single<UserWithTasks> save(UserWithTasks user, Task task) {
//    task.setUserId(user.getId());
//    return (
//        (task.getId() == 0) ? taskDao.insert(task) : taskDao.update(task)
//        )
//        .doOnSuccess((u) -> user.getTasks().forEach((t) -> task.setUserId(u.getId())))
//        .map((u) -> user.getTasks())
//        .
//  }
//
//  private Single<List<Task>> saveTasks(List<Task> tasks) {
//
//  }

}
