import java.time.LocalDateTime;
import java.util.ArrayList;

public class task{

    String taskName;
    String taskDescription;
    LocalDateTime createdDate;
    LocalDateTime deadlineDate;
    LocalDateTime completionDate;
    int taskPriority;
    String taskCategory;
    Boolean completedStatus;
    ArrayList <subtask> subtaskList;

    public task(String taskName, String taskDescription, LocalDateTime deadlineDate, int taskPriority, String taskCategory){
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        createdDate = LocalDateTime.now();
        this.deadlineDate = deadlineDate;
        this.taskPriority = taskPriority;
        this.taskCategory = taskCategory;
        completedStatus = false;
        subtaskList = new ArrayList<subtask>();
    }

    public void addsubtask(String taskName, String taskDescription, LocalDateTime deadlineDate, task parentTask){
        subtaskList.add(new subtask(taskName, taskDescription, deadlineDate, parentTask));
    }

    // 1. Mark Task as Completed
    public void markAsCompleted() {
        this.completedStatus = true;
        this.completionDate = LocalDateTime.now();
    }

    // 2. Calculate Remaining Time until the deadline (in hours)
    public long getRemainingHours() {
        return java.time.Duration.between(LocalDateTime.now(), this.deadlineDate).toHours();
    }

    // 3. Update Task Details
    public void updateTaskDetails(String newDescription, int newPriority, String newCategory) {
        this.taskDescription = newDescription;
        this.taskPriority = newPriority;
        this.taskCategory = newCategory;
    }

    // 4. View All subtasks
    public void viewAllsubtasks() {
        for (subtask st : subtaskList) {
            System.out.println("subtask: " + st.taskName + ", Status: " + (st.completedStatus ? "Completed" : "Pending"));
        }
    }

    // 5. Remove a subtask by Name
    public void removesubtask(String subtaskName) {
        subtaskList.removeIf(st -> st.taskName.equals(subtaskName));
    }

    // 6. Check if Task is Overdue
    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(this.deadlineDate) && !this.completedStatus;
    }

    // 7. List Completed and Pending subtasks Separately
    public void listsubtasksByStatus() {
        System.out.println("Completed subtasks:");
        for (subtask st : subtaskList) {
            if (st.completedStatus) {
                System.out.println(st.taskName);
            }
        }
        System.out.println("Pending subtasks:");
        for (subtask st : subtaskList) {
            if (!st.completedStatus) {
                System.out.println(st.taskName);
            }
        }
    }

    // 8. Get Task Progress Percentage
    public double getTaskProgress() {
        if (completedStatus) return 100;
        int completedCount = (int) subtaskList.stream().filter(st -> st.completedStatus).count();
        return subtaskList.size() > 0 ? (completedCount / (double) subtaskList.size()) * 100 : 0;
    }

    

    // 9. Extend Deadline by Specified Days
    public void extendDeadline(int days) {
        this.deadlineDate = this.deadlineDate.plusDays(days);
    }

    // 10. Display Task Summary
    public void displaySummary() {
        System.out.println("Task: " + taskName + ", Priority: " + taskPriority + ", Due: " + deadlineDate);
        System.out.println("Category: " + taskCategory + ", Status: " + (completedStatus ? "Completed" : "Pending"));
    }

    // Method to add subtask
    public void addsubtask(String taskName, String taskDescription, LocalDateTime deadlineDate) {
        subtaskList.add(new subtask(taskName, taskDescription, deadlineDate, this));
    }

    public void addsubtask(subtask newSubtask) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addsubtask'");
    }

    
}
