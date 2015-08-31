import java.util.List;
import org.sql2o.*;

public class Task {
  private int id;
  private String description;
  private int categoryId;

  public Task(String description, int categoryId) {
    this.description = description;
    this.categoryId = categoryId;
  }
  public String getDescription() {
    return description;
  }

  public int getCategoryId() {
    return categoryId;
  }
  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherTask) {
      if(!(otherTask instanceof Task)) {
        return false;
      } else {
        Task newTask = (Task) otherTask;
        // System.out.println(this.getCategoryId());
        // System.out.println(newTask.getCategoryId());
        return this.getDescription().equals(newTask.getDescription()) &&
               this.getId() == newTask.getId() &&
               this.getCategoryId() == newTask.getCategoryId();
      }
  }
  public static List<Task> all() {
    String sql = "SELECT id, description, categoryId FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks (description, categoryId) VALUES (:description, :categoryId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", description)
        .addParameter("categoryId", categoryId)
        .executeUpdate()
        .getKey();
    }
  }
  public static Task find (int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

}
