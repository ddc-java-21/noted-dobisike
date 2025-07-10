package edu.cnm.deepdive.noted.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import edu.cnm.deepdive.noted.model.entity.Task;
import edu.cnm.deepdive.noted.model.pojo.UserWithTasks;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Dao
public interface TaskDao {

  @Insert
  Single<Long> _insert(Task task);

  default Single<Task> insert(Task task) {
    return Single
        .just(task)
        .doOnSuccess((t) -> {
          Instant now = Instant.now();
          Instant dueDate = Instant.ofEpochMilli(now.toEpochMilli());
          t.setCreated(now);
          t.setModified(now);
          t.setDueDate(dueDate);
          t.setCompleted(false);
        })
        .flatMap(this::_insert)
        .doOnSuccess(task::setId)
        .map((id) -> task);
  }

  @Insert
  Single<List<Long>> _insert(List<Task> tasks);

  default Single<List<Task>> insert(List<Task> tasks) {
    return Single
        .just(tasks)
        .doOnSuccess((ts) -> {
          Instant now = Instant.now();
          Instant dueDate = Instant.ofEpochMilli(now.toEpochMilli());
          ts.forEach((t) -> {
            t.setCreated(now);
            t.setModified(now);
            t.setDueDate(dueDate);
            t.setCompleted(false);
          });
        })
        .flatMap(this::_insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<Task> taskIterator = tasks.iterator();
          while (idIterator.hasNext() && taskIterator.hasNext()) {
            taskIterator.next().setId(idIterator.next());
          }
        })
        .map((ids) -> tasks);
  }

  @Update
  Single<Integer> _update(Task task);

  default Single<Task> update(Task task) {
    return Single
        .just(task)
        .doOnSuccess((t) -> {
          t.setModified(Instant.now());
          t.setDueDate(Instant.now());
          t.setCompleted(true);
        })
        .flatMap(this::_update)
        .map((count) -> task);
  }

  @Delete
  Single<Integer> delete(Task task);

  @Delete
  Single<Integer> delete(List<? extends Task> tasks);

  @Delete
  Single<Integer> delete(Task... tasks);

  @Transaction
  @Query("SELECT * FROM task WHERE user_id = :userId")
  LiveData<Task> select(long userId);

  @Transaction
  @Query("SELECT * FROM task WHERE user_id = :userId ORDER BY created ASC")
  LiveData<List<Task>>  selectWhereUserIdOrderByCreatedAsc(long userId);

  // TODO: 7/8/25 Add more queries when appropriate

}
