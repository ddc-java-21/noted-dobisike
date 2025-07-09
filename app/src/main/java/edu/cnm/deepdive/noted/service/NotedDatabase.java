package edu.cnm.deepdive.noted.service;

import android.net.Uri;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.noted.model.entity.Image;
import edu.cnm.deepdive.noted.model.entity.Note;
import edu.cnm.deepdive.noted.model.entity.Reminder;
import edu.cnm.deepdive.noted.model.entity.Task;
import edu.cnm.deepdive.noted.model.entity.User;
import edu.cnm.deepdive.noted.service.NotedDatabase.Converters;
import edu.cnm.deepdive.noted.service.dao.ImageDao;
import edu.cnm.deepdive.noted.service.dao.NoteDao;
import edu.cnm.deepdive.noted.service.dao.ReminderDao;
import edu.cnm.deepdive.noted.service.dao.TaskDao;
import edu.cnm.deepdive.noted.service.dao.UserDao;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Database(
    entities = {User.class, Task.class, Reminder.class, Note.class, Image.class},
    version = NotedDatabase.VERSION
)
@TypeConverters(Converters.class)
public abstract class NotedDatabase extends RoomDatabase {

  static final int VERSION = 1;
  private static final String NAME = "notesd-db";

  public static String getName() {
    return NAME;
  }

  public abstract UserDao getUserDao();

  public abstract TaskDao getTaskDao();

  public abstract ReminderDao getReminderDao();

  public abstract NoteDao getNoteDao();

  public abstract ImageDao getImageDao();

  public static class Converters {

    @TypeConverter
    public static Long fromInstant(Instant value) {
      return (value != null) ? value.toEpochMilli() : null;
    }

    @TypeConverter
    public static Instant fromLong(Long value) {
      return (value != null) ? Instant.ofEpochMilli(value) : null;
    }

    @TypeConverter
    public static String fromUri(Uri value) {
      return (value != null) ? value.toString() : null;
    }

    @TypeConverter
    public static Uri fromString(String value) {
      return (value != null) ? Uri.parse(value) : null;
    }

    @TypeConverter
    public static Instant fromLocalDate(LocalDate value) {
      return (value!= null) ? Instant.ofEpochMilli(Instant.now().toEpochMilli()) : null;
    }

  }

}
