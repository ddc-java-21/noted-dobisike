package edu.cnm.deepdive.noted.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity(
    tableName = "reminder",
    foreignKeys = {
        @ForeignKey(
            entity = User.class,
            parentColumns = "user_id",
            childColumns = "user_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class Reminder {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "reminder_id")
  private long id;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE, index = true)
  private String title = "";

  private String description;

  @NonNull
  @ColumnInfo(index = true)
  private Instant created = Instant.now();

  @NonNull
  @ColumnInfo(index = true)
  private Instant modified = Instant.now();


  @ColumnInfo(name = "selected_date", index = true)
  private LocalDateTime selectedDate;

  @ColumnInfo(name = "completed_date", index = true)
  private boolean completed;

  @ColumnInfo(name = "user_id", index = true)
  private long userId;

  public long getId() {return id;}

  public Reminder setId(long id) {
    this.id = id;
    return this;
  }

  @NonNull
  public String getTitle() {return title;}

  public Reminder setTitle(@NonNull String title) {
    this.title = title;
    return this;
  }

  public String getDescription() {return description;}

  public Reminder setDescription(String description) {
    this.description = description;
    return this;
  }

  @NonNull
  public Instant getCreated() {return created;}

  public Reminder setCreated(@NonNull Instant created) {
    this.created = created;
    return this;
  }

  @NonNull
  public Instant getModified() {return modified;}

  public Reminder setModified(@NonNull Instant modified) {
    this.modified = modified;
    return this;
  }

  public LocalDateTime getSelectedDate() {return selectedDate;}

  public Reminder setSelectedDate(LocalDateTime selectedDate) {
    this.selectedDate = selectedDate;
    return this;
  }

  public boolean isCompleted() {return completed;}

  public Reminder setCompleted(boolean completed) {
    this.completed = completed;
    return this;
  }

  public long getUserId() {return userId;}

  public Reminder setUserId(long userId) {
    this.userId = userId;
    return this;
  }
}
