package edu.cnm.deepdive.noted.model.pojo;

import androidx.room.Relation;
import edu.cnm.deepdive.noted.model.entity.Reminder;
import edu.cnm.deepdive.noted.model.entity.User;
import java.util.LinkedList;
import java.util.List;

public class UserWithReminders extends User {

  @Relation(entity = Reminder.class, parentColumn = "user_id", entityColumn = "user_id")
  private List<Reminder> reminders = new LinkedList<>();

  public List<Reminder> getReminders() {
    return reminders;
  }

  public void setReminders(List<Reminder> reminders) {
    this.reminders = reminders;
  }

}
