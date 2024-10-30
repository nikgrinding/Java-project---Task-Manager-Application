import java.time.LocalDateTime;

public class subtask extends task{

    public subtask(String taskName, String taskDescription, LocalDateTime deadlineDate, task parentTask){
        super(taskName, taskDescription, deadlineDate, parentTask.taskPriority, parentTask.taskCategory);
    }

}