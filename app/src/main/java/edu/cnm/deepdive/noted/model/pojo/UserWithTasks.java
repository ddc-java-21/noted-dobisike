package edu.cnm.deepdive.noted.model.pojo;

import androidx.room.Relation;
import edu.cnm.deepdive.noted.model.entity.Task;
import edu.cnm.deepdive.noted.model.entity.User;
import java.util.LinkedList;
import java.util.List;

public class UserWithTasks extends User {

  @Relation(entity = Task.class, parentColumn = "user_id", entityColumn = "user_id")
  private  List<Task> tasks = new LinkedList<>();

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

}
