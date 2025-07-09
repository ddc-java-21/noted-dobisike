package edu.cnm.deepdive.noted.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.noted.model.pojo.UserWithTasks;
import edu.cnm.deepdive.noted.service.dao.TaskDao;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TaskRepository {

  private final TaskDao taskDao;

  private final Scheduler scheduler;

  @Inject
  public TaskRepository(TaskDao taskDao) {
    this.taskDao = taskDao;
    scheduler = Schedulers.io();
  }

  public LiveData<UserWithTasks> get(long taskId) {
    return taskDao.select(taskId);
  }

}
