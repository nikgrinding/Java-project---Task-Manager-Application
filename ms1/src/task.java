import java.time.LocalDateTime;
import java.util.List;

public class task{

    String taskName;
    String taskDescription;
    LocalDateTime createdDate;
    LocalDateTime deadlineDate;
    LocalDateTime completionDate;
    int taskPriority;
    String taskCategory;
    Boolean completedStatus;
    List <subtask> subtaskList;

    public task(String taskName, String taskDescription, LocalDateTime deadlineDate, int taskPriority, String taskCategory){
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        createdDate = LocalDateTime.now();
        this.deadlineDate = deadlineDate;
        this.taskPriority = taskPriority;
        this.taskCategory = taskCategory;
        completedStatus = false;
    }

}