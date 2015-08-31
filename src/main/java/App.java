import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import static java.lang.System.out;
import java.lang.*;

import java.util.Map;
import java.util.HashMap;

import spark.ModelAndView;

import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("categories", Category.all());
      model.put("template", "templates/index.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasks",(request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String description = request.queryParams("description");
      int category_id = Integer.parseInt(request.queryParams("category_id"));
      Task newTask = new Task(description);

      newTask.save();

      Category.find(category_id).addTask(newTask);
      model.put("task", newTask);
      model.put("tasks", Task.all());
      model.put("template", "templates/index.vtl");
      response.redirect("/");
      return null;
    });

    post("/categories", (request,response)-> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String name = request.queryParams("name");
      Category newCategory = new Category(name);
      newCategory.save();

      model.put("category", newCategory);
      model.put("tasks", Task.all());
      model.put("template", "templates/index.vtl");
      response.redirect("/");
      return null;
    });

    get("/categories/:id", (request, response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Category category = Category.find(id);

      model.put("category", category);
      model.put("tasks", category.getTasks());
      model.put("template", "templates/categories.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/categories/:category_id/tasks/:task_id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      int catId = Integer.parseInt(request.params(":category_id"));
      Category myCategory = Category.find(catId);

      int taskId = Integer.parseInt(request.params(":task_id"));
      Task taskToDelete = Task.find(taskId);

      taskToDelete.delete();

      response.redirect("/categories/" + request.params(":category_id"));
      return null;
    });

    post("/categories/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Category category = Category.find(Integer.parseInt(request.params(":id")));

      category.delete();
      response.redirect("/");
      return null;
    });


  }
}
