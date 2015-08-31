import org.junit.*;
import static org.junit.Assert.*;

public class TaskTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_listIsEmptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }
  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame() {
    Task firstTask = new Task("Buy Foods", 1);
    Task secondTask = new Task("Buy Foods", 1);

    assertTrue(firstTask.equals(secondTask));
  }
  @Test
  public void save_returnsTrueIfDescriptionsAreTheSame()  {
    Task myTask = new Task("go to the gym", 1);
     myTask.save();
     System.out.println(Task.all().get(0).getCategoryId());
     System.out.println(myTask.getCategoryId());
     assertTrue(Task.all().get(0).equals(myTask));
  }
  @Test
  public void save_AssignsIdToObject() {
    Task myTask = new Task ("deposit check", 1);
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }
  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("buy vitamins", 1);
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
    }

  @Test
  public void save_savesCategoryIdIntoDB_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Task myTask = new Task("Mow the lawn", myCategory.getId());
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertEquals(savedTask.getCategoryId(), myCategory.getId());
  }
}
