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
  private Instant selectedDate;

  @ColumnInfo(name = "completed_date", index = true)
  private boolean completed;

  @ColumnInfo(name = "user_id", index = true)
  private long userId;

  public long getId() {return id;}

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public String getTitle() {return title;}

  public void setTitle(@NonNull String title) {
    this.title = title;
  }

  public String getDescription() {return description;}

  public void setDescription(String description) {
    this.description = description;
  }

  @NonNull
  public Instant getCreated() {return created;}

  public void setCreated(@NonNull Instant created) {
    this.created = created;
  }

  @NonNull
  public Instant getModified() {return modified;}

  public void setModified(@NonNull Instant modified) {
    this.modified = modified;
  }

  public Instant getSelectedDate() {return selectedDate;}

  public void setSelectedDate(Instant selectedDate) {
    this.selectedDate = selectedDate;
  }

  public boolean isCompleted() {return completed;}

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public long getUserId() {return userId;}

  public void setUserId(long userId) {
    this.userId = userId;
  }
}
