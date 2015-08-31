import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Task {
  private int id;
  private String description;

  public Task(String description) {
    this.description = description;
  }
  public String getDescription() {
    return description;
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

        return this.getDescription().equals(newTask.getDescription()) &&
               this.getId() == newTask.getId();
      }
  }
  public static List<Task> all() {
    String sql = "SELECT * FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks (description) VALUES (:description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", description)
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
  public void addCategory(Category category) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO categories_tasks (categories_id, tasks_id) VALUES (:categories_id, :tasks_id)";
    con.createQuery(sql)
    .addParameter("categories_id", category.getId())
    .addParameter("tasks_id", this.getId())
    .executeUpdate();
    }
  }
  public ArrayList<Category> getCategories() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT categories_id FROM categories_tasks WHERE tasks_id = :tasks_id";
      List<Integer> categoryIds = con.createQuery(sql)
      .addParameter("tasks_id", this.getId())
      .executeAndFetch(Integer.class);

    ArrayList<Category> categories = new ArrayList<Category>();

    for (Integer categoryId : categoryIds) {
        String taskQuery = "Select * From categories WHERE id = :categories_id";
        Category category = con.createQuery(taskQuery)
        .addParameter("categories_id", categoryId)
        .executeAndFetchFirst(Category.class);
      categories.add(category);
    }
    return categories;
    }
  }
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM tasks WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();

    String joinDeleteQuery = "DELETE FROM categories_tasks WHERE tasks_id = :tasks_id";
    con.createQuery(joinDeleteQuery)
    .addParameter("tasks_id", this.getId())
    .executeUpdate();
    }
  }

  // public void update(String description) {
  //   try(Connection con= DB.sql2o.open()) {
  //     String sql = "UPDATE tasks SET description = :description WHERE id = :id";
  //     con.createQuery(sql)
  //       .addParameter("description", desciption)
  //       .addParameter("id", id)
  //       .executeUpdate();
  //   }
  // }

}
