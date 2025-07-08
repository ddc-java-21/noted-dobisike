package edu.cnm.deepdive.noted.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity(
    tableName = "task",
    foreignKeys = {
        @ForeignKey(
            entity = User.class,
            parentColumns = "user_id",
            childColumns = "user_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class Task {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "task_id")
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


  @ColumnInfo(name = "due_date", index = true)
  private LocalDateTime dueDate;

  @ColumnInfo(name = "completed_date", index = true)
  private boolean completed;

  @ColumnInfo(name = "user_id", index = true)
  private long userId;

  public long getId() {return id;}

  public Task setId(long id) {
    this.id = id;
    return this;
  }

  @NonNull
  public String getTitle() {return title;}

  public Task setTitle(@NonNull String title) {
    this.title = title;
    return this;
  }

  public String getDescription() {return description;}

  public Task setDescription(String description) {
    this.description = description;
    return this;
  }

  @NonNull
  public Instant getCreated() {return created;}

  public Task setCreated(@NonNull Instant created) {
    this.created = created;
    return this;
  }

  @NonNull
  public Instant getModified() {return modified;}

  public Task setModified(@NonNull Instant modified) {
    this.modified = modified;
    return this;
  }

  public LocalDateTime getDueDate() {return dueDate;}

  public Task setDueDate(LocalDateTime dueDate) {
    this.dueDate = dueDate;
    return this;
  }

  public boolean isCompleted() {return completed;}

  public Task setCompleted(boolean completed) {
    this.completed = completed;
    return this;
  }

  public long getUserId() {return userId;}

  public Task setUserId(long userId) {
    this.userId = userId;
    return this;
  }
}
