package edu.cnm.deepdive.noted.model.pojo;

import androidx.room.Relation;
import edu.cnm.deepdive.noted.model.entity.Note;
import edu.cnm.deepdive.noted.model.entity.User;
import java.util.LinkedList;
import java.util.List;

public class UserWithNotes extends User {

  @Relation(entity = Note.class, parentColumn = "user_id", entityColumn = "user_id")
  private final List<Note> notes = new LinkedList<>();


  public List<Note> getNotes() {
    return notes;
  }
}
