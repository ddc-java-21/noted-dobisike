package edu.cnm.deepdive.noted.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import edu.cnm.deepdive.noted.model.entity.Reminder;
import edu.cnm.deepdive.noted.model.pojo.UserWithReminders;
import io.reactivex.rxjava3.core.Single;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;

@Dao
public interface ReminderDao {

  @Insert
  Single<Long> _insert(Reminder reminder);

  default Single<Reminder> insert(Reminder reminder) {
    return Single
        .just(reminder)
        .doOnSuccess((t) -> {
          Instant now = Instant.now();
          Instant selectedDate = Instant.ofEpochMilli(now.toEpochMilli());
          t.setCreated(now);
          t.setModified(now);
          t.setSelectedDate(selectedDate);
          t.setCompleted(false);
        })
        .flatMap(this::_insert)
        .doOnSuccess(reminder::setId)
        .map((id) -> reminder);
  }

  @Insert
  Single<List<Long>> _insert(List<Reminder> reminders);

  default Single<List<Reminder>> insert(List<Reminder> reminders) {
    return Single
        .just(reminders)
        .doOnSuccess((ts) -> {
          Instant now = Instant.now();
          Instant selectedDate = Instant.ofEpochMilli(now.toEpochMilli());
          ts.forEach((t) -> {
            t.setCreated(now);
            t.setModified(now);
            t.setSelectedDate(selectedDate);
            t.setCompleted(false);
          });
        })
        .flatMap(this::_insert)
        .doOnSuccess((ids) -> {
          Iterator<Long> idIterator = ids.iterator();
          Iterator<Reminder> taskIterator = reminders.iterator();
          while (idIterator.hasNext() && taskIterator.hasNext()) {
            taskIterator.next().setId(idIterator.next());
          }
        })
        .map((ids) -> reminders);
  }

  @Update
  Single<Integer> _update(Reminder reminder);

  default Single<Reminder> update(Reminder reminder) {
    return Single
        .just(reminder)
        .doOnSuccess((r) -> {
          r.setModified(Instant.now());
          r.setSelectedDate(Instant.now());
          r.setCompleted(true);
        })
        .flatMap(this::_update)
        .map((count) -> reminder);
  }

  @Delete
  Single<Integer> delete(Reminder reminder);

  @Delete
  Single<Integer> delete(List<? extends Reminder> reminders);

  @Delete
  Single<Integer> delete(Reminder... reminders);

  @Transaction
  @Query("SELECT * FROM reminder WHERE reminder_id = :reminderId")
  LiveData<UserWithReminders> select(long reminderId);

  @Transaction
  @Query("SELECT * FROM reminder WHERE user_id = :userId ORDER BY created ASC")
  LiveData<List<UserWithReminders>>  selectWhereUserIdOrderByCreatedAsc(long userId);

  // TODO: 7/8/25 Add more queries when appropriate

}
