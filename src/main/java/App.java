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
      model.put("template", "templates/index.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

 //    post("/tasks",(request response) -> {
 //      HashMap<String, Object> model = new HashMap<String, Object>();
 //
 //      String description = request.queryParams("description");
 //      int categories_id = Integer.parseInt(request.queryParams("categories_id"));
 //      Task newTask = new Task(description);
 //
 //      newTask.save();
 //      model.put("task", newTask);
 //      model.put("template", "templates/index.vtl");
 //      response.redirect("/");
 //      return null;
 //    });
 //
 //    post("/categories", (request,response)-> {
 //      HashMap<String, Object> model = new HashMap<String, Object>();
 //
 //      String name = request.queryParams("name");
 //      Category newCategory = new Category(name);
 //      new Category.save();
 //
 //      model.put("category", newCategory);
 //      model.put("template", "templates/index.vtl");
 //      response.redirect("/");
 //      return null;
 //    });
 //    // do I need this??
 //    get("/tasks/:id", request,response) -> {
 //      HashMap<String, Object> model = new HashMap<String, Object>();
 //      int id = Integer.parseInt(request.params("id"));
 //      Task task = Task.find(id);
 //      model.put("task", task);
 //      model.put("allCategories", Category.all());
 //      model.put("template", "templates/index.vtl");
 //      return new ModelAndView(model, layout);
 //    }, new VelocityTemplateEngine());
 // //
 //
 //    get("/categories/:id", (request, response) ->{
 //      HashMap<String, Object> model = new HashMap<String, Object>();
 //      int id = Integer.parseInt(request.params("id"));
 //      Category category = Category.find(id);
 //
 //      model.put("category", category);
 //      model.put("allTasks", Task.all());
 //      model.put("template", "templates/categories.vtl");
 //      return new ModelAndView(model, layout);
 //    }, new VelocityTemplateEngine());

    // get("/categories/:categories_id/delete", (request, response) -> {
    //   HashMap<String, Object> model new HashMap<String, Object>();
    //
    //   Category category = Category.find(Integer.parseInt(request.params(":categories_id")));
    //
    //   category.delete();
    //   response.redirect("/");
    //   return null;
    // });
    // get("/categories/:categories_id/tasks/:tasks_id/delete", (request, response) ->{
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Category category = Category.find(Integer.parseInt(request.params(":categories_id")));
    //   model.put("category", category);
    //   String categories_id = request.params(":categories_id");
    // })
  }


}
