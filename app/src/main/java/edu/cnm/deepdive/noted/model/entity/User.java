package edu.cnm.deepdive.noted.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.time.Instant;
@Entity(
    tableName = "user",
    indices = {
        @Index(value = {"oauth_key"}, unique = true),
        @Index(value = {"display_name"}, unique = true)
    }
)
public class User {
  /*
  C - Create account with oauth/server side
  R - Retrieve local information for date/time formatting for user
  U - Update display name
  D - Deletion: Not Applicable
   */

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "user_id")
  private long id;

  @NonNull
  @ColumnInfo(name = "oauth_key")
  private String oauthKey = "";

  @NonNull
  @ColumnInfo(name = "display_name", collate = ColumnInfo.NOCASE)
  private String displayName = "";

  @NonNull
  private Instant created = Instant.now();

  @NonNull
  private Instant modified = Instant.now();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;

  }

  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  public void setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;

  }

  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(@NonNull String displayName) {
    this.displayName = displayName;

  }

  @NonNull
  public Instant getCreated() {
    return created;
  }

  public void setCreated(@NonNull Instant created) {
    this.created = created;

  }

  @NonNull
  public Instant getModified() {
    return modified;
  }

  public void setModified(@NonNull Instant modified) {
    this.modified = modified;

  }
}