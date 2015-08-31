import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Category {
  private int id;
  private String name;

  public Category(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }
  @Override
  public boolean equals(Object otherCategory) {
      if(!(otherCategory instanceof Category)) {
        return false;
      } else {
        Category newCategory = (Category) otherCategory;

        return this.getName().equals(newCategory.getName()) &&
               this.getId() == newCategory.getId();
      }
  }
  public static List<Category> all() {
    String sql = "SELECT * FROM categories";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey();
    }
  }
  public static Category find (int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM categories where id=:id";
      Category Category = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Category.class);
      return Category;
    }
  }
  public void addTask(Task task) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories_tasks (categories_id, tasks_id) VALUES (:categories_id, :tasks_id)";
      con.createQuery(sql)
        .addParameter("categories_id", this.getId())
        .addParameter("tasks_id", task.getId())
        .executeUpdate();
    }
  }
  public ArrayList<Task> getTasks() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT tasks_id FROM categories_tasks WHERE categories_id = :categories_id";
    List<Integer> taskIds = con.createQuery(sql)
      .addParameter("categories_id", this.getId())
      .executeAndFetch(Integer.class);

    ArrayList<Task> tasks = new ArrayList<Task>();

    for (Integer taskId : taskIds) {
      String taskQuery = "SELECT * FROM tasks WHERE id = :tasks_id";
      Task task = con.createQuery(taskQuery)
        .addParameter("tasks_id", taskId)
        .executeAndFetchFirst(Task.class);
      tasks.add(task);
    }
    return tasks;
    }
  }
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String deleteQuery = "DELETE FROM categories WHERE id = :id;";
    con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();

    String joinDeleteQuery = "DELETE FROM categories_tasks WHERE categories_id = :categories_id";
    con.createQuery(joinDeleteQuery)
    .addParameter("categories_id", this.getId())
    .executeUpdate();
    }
  }
  // public void update(String name) {
  //   try(Connection con= DB.sql2o.open()) {
  //     String sql = "UPDATE categories SET name = :description WHERE id = :id";
  //     con.createQuery(sql)
  //       .addParameter("name", name)
  //       .addParameter("id", id)
  //       .executeUpdate();
  //   }
  // }

}
