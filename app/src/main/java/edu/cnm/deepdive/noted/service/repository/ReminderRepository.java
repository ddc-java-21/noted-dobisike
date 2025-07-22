package edu.cnm.deepdive.noted.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.noted.model.entity.Reminder;
import edu.cnm.deepdive.noted.model.entity.User;
import edu.cnm.deepdive.noted.model.pojo.UserWithReminders;
import edu.cnm.deepdive.noted.service.dao.ReminderDao;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReminderRepository {

  private final ReminderDao reminderDao;
  private final Scheduler scheduler;

  @Inject
  public ReminderRepository(ReminderDao reminderDao, Scheduler scheduler) {
    this.reminderDao = reminderDao;
    this.scheduler = scheduler;
  }

  public LiveData<Reminder> get(long reminderId) {
    return reminderDao.select(reminderId);
  }

  public Completable remove(Reminder reminder) {
    return reminderDao
        .delete(reminder)
        .subscribeOn(scheduler)
        .ignoreElement();
  }

  public Single<UserWithReminders> save(UserWithReminders user, Reminder reminder) {
    reminder.setUserId(user.getId());
    return (
        (reminder.getId() == 0)
            ? reminderDao.insert(reminder)
            : reminderDao.update(reminder)
    )
        .doOnSuccess((u) -> user.getReminders().forEach((r) -> reminder.setUserId(u.getId())))
        .map((u) -> user.getReminders())
        .doOnSuccess((reminders) -> {
          user.getReminders().clear();
          user.getReminders().addAll(reminders);
        })
        .map((reminders) -> user)
        .subscribeOn(scheduler);
  }

  private LiveData<List<Reminder>> getAll(User user) {
    return reminderDao.selectWhereUserIdOrderByCreatedAsc(user.getId());
  }

  private Single<List<Reminder>> saveReminders(List<Reminder> reminders) {
    List<Single<Reminder>> singles = reminders
        .stream()
        .map((reminder) -> (reminder.getId() == 0)
            ? reminderDao.insert(reminder)
            : reminderDao.update(reminder)
                .map((count) -> reminder))
        .collect(Collectors.toList());
    return Single.just(singles)
        .flatMap((sngls) -> Single.zip(sngls, Arrays::asList))
        .map((reminderObjects) -> reminderObjects
            .stream()
            .map((reminderObject) -> (Reminder) reminderObject)
            .collect(Collectors.toList()));
  }

}
