import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.ArrayList;

public class TaskTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_listIsEmptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }
  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame() {
    Task firstTask = new Task("Buy Foods");
    Task secondTask = new Task("Buy Foods");

    assertTrue(firstTask.equals(secondTask));
  }
  @Test
  public void save_returnsTrueIfDescriptionsAreTheSame()  {
    Task myTask = new Task("go to the gym");
     myTask.save();
     assertTrue(Task.all().get(0).equals(myTask));
  }
  @Test
  public void save_AssignsIdToObject() {
    Task myTask = new Task ("deposit check");
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }
  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("buy vitamins");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
    }
  @Test
  public void addCategory_addsCategoryToTask() {
    Category myCategory = new Category("Household Chores");
    myCategory.save();

    Task myTask = new Task("walk the dog");
    myTask.save();

    myTask.addCategory(myCategory);
    Category savedCategory = myTask.getCategories().get(0);
    assertTrue(myCategory.equals(savedCategory));
  }

  @Test
  public void getCategories_returnAllCategories_ArrayList() {
    Category myCategory = new Category("Household Chores");
    myCategory.save();

    Task myTask = new Task("walk the dog");
    myTask.save();

    myTask.addCategory(myCategory);
    ArrayList savedCategories = myTask.getCategories();
    assertEquals(savedCategories.size(), 1);
  }
  @Test
  public void delete_deletesAllTasksAndListsAssoicationes() {
    Category myCategory = new Category("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();

    myTask.addCategory(myCategory);
    myTask.delete();
    assertEquals(myCategory.getTasks().size(), 0);
  } 



  // @Test
  // public void delete_deletesfromTasks() {
  //   Task myTask = new Task("grocery shopping");
  //   myTask.save();
  //   myTask.delete();
  //   assertEquals(myTask.eq);
  // }

  //
  // @Test
  // public void save_savesCategoryIdIntoDB_true() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //   Task myTask = new Task("Mow the lawn", myCategory.getId());
  //   myTask.save();
  //   Task savedTask = Task.find(myTask.getId());
  //   assertEquals(savedTask.getCategoryId(), myCategory.getId());
  // }
}
