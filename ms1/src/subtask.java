import java.time.LocalDateTime;


public class subtask extends task{

    public subtask(String taskName, String taskDescription, LocalDateTime deadlineDate, task parentTask){
        super(taskName, taskDescription, deadlineDate, parentTask.taskPriority, parentTask.taskCategory);
    }
    // Subtask-specific Methods

    // 1. Mark Subtask as Completed
    public void markSubtaskAsCompleted() {
        this.completedStatus = true;
        this.completionDate = LocalDateTime.now();
        syncCompletionWithParent();
    }

    // 2. Get Parent Task Details
    public String getParentTaskDetails() {
        return "Parent Task: " + super.taskName + ", Due Date: " + super.deadlineDate;
    }

    // 3. Update Subtask Details
    public void updateSubtaskDetails(String newDescription, LocalDateTime newDeadline) {
        this.taskDescription = newDescription;
        this.deadlineDate = newDeadline;
    }

    // 4. Check if Subtask is Overdue
    public boolean isSubtaskOverdue() {
        return LocalDateTime.now().isAfter(this.deadlineDate) && !this.completedStatus;
    }

    // 5. Display Subtask Summary
    public void displaySubtaskSummary() {
        System.out.println("Subtask: " + taskName + ", Status: " + (completedStatus ? "Completed" : "Pending"));
        System.out.println("Due Date: " + deadlineDate + ", Parent Task: " + super.taskName);
    }

    // 6. Check if Subtask is Linked to a Specific Parent Task
    public boolean isLinkedToParent(task parentTask) {
        return super.taskName.equals(parentTask.taskName);
    }

    // 7. Set Subtask Priority Based on Parent Task Priority
    public void setPriorityFromParent() {
        this.taskPriority = super.taskPriority;
    }

    // 8. Get Subtask Remaining Time (in hours)
    public long getSubtaskRemainingHours() {
        return java.time.Duration.between(LocalDateTime.now(), this.deadlineDate).toHours();
    }

    // 9. Extend Subtask Deadline by Specified Days
    public void extendSubtaskDeadline(int days) {
        this.deadlineDate = this.deadlineDate.plusDays(days);
    }

    // 10. Sync Subtask Completion with Parent Task Completion Status
    public void syncCompletionWithParent() {
        if (super.subtaskList.stream().allMatch(sub -> sub.completedStatus)) {
            super.markAsCompleted();
        }
    }

    public boolean isCompleted() {
        return completedStatus;
    }
}
