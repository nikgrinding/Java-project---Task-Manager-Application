import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class ToDoListGUI extends JFrame {
    private JButton removeSubtaskButton; 
    private JButton editTaskButton, deleteTaskButton, editSubtaskButton, deleteSubtaskButton; 
    private ArrayList<task> taskList = new ArrayList<>();
    private task currentTask;
    private subtask currentSubtask;

    // UI Components
    private JList<task> taskJList;
    private DefaultListModel<task> taskListModel;
    private JList<subtask> subtaskJList;
    private DefaultListModel<subtask> subtaskListModel;
    private JTextField taskNameField, taskDescriptionField, taskCategoryField, taskPriorityField;
    private JTextField subtaskNameField, subtaskDescriptionField;
    private JTextArea taskDisplayArea;
    private JButton addTaskButton, markTaskCompleteButton, addSubtaskButton, markSubtaskCompleteButton;
    private JPanel subtaskCheckboxPanel;


    public ToDoListGUI() {
        setTitle("To-Do List Application");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Task List Panel
        taskListModel = new DefaultListModel<>();
        taskJList = new JList<>(taskListModel);
        taskJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskJList.addListSelectionListener(e -> displaySelectedTask());
        add(new JScrollPane(taskJList), BorderLayout.WEST);

        // Task Details Panel
        JPanel taskPanel = new JPanel(new GridLayout(5, 2));
        taskPanel.add(new JLabel("Task Name:"));
        taskNameField = new JTextField();
        taskPanel.add(taskNameField);

        taskPanel.add(new JLabel("Description:"));
        taskDescriptionField = new JTextField();
        taskPanel.add(taskDescriptionField);

        taskPanel.add(new JLabel("Category:"));
        taskCategoryField = new JTextField();
        taskPanel.add(taskCategoryField);

        taskPanel.add(new JLabel("Priority (1-5):"));
        taskPriorityField = new JTextField();
        taskPanel.add(taskPriorityField);

        addTaskButton = new JButton("Add Task");
        markTaskCompleteButton = new JButton("Mark Task Complete");
        editTaskButton = new JButton("Edit Task");
        deleteTaskButton = new JButton("Delete Task");
        taskPanel.add(editTaskButton);
        taskPanel.add(deleteTaskButton);

        editTaskButton.addActionListener(new EditTaskAction());
        deleteTaskButton.addActionListener(new DeleteTaskAction());

        // Subtask Buttons
        editSubtaskButton = new JButton("Edit Subtask");
        deleteSubtaskButton = new JButton("Delete Subtask");
        

        editSubtaskButton.addActionListener(new EditSubtaskAction());
        deleteSubtaskButton.addActionListener(new RemoveSubtaskAction());
        taskPanel.add(addTaskButton);
        taskPanel.add(markTaskCompleteButton);

        add(taskPanel, BorderLayout.NORTH);

        // Task Display Area
        taskDisplayArea = new JTextArea(10, 30);
        taskDisplayArea.setEditable(false);
        add(new JScrollPane(taskDisplayArea), BorderLayout.CENTER);

        // Subtask Panel
        subtaskListModel = new DefaultListModel<>();
        subtaskJList = new JList<>(subtaskListModel);
        subtaskJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subtaskJList.addListSelectionListener(e -> displaySelectedSubtask());
        JPanel subtaskCheckboxPanel = new JPanel(new GridLayout(0, 1));
        JPanel subtaskPanel = new JPanel(new GridLayout(5, 2));

        add(new JScrollPane(subtaskCheckboxPanel), BorderLayout.EAST);
        subtaskPanel.add(new JLabel("Subtask Name:"));
        subtaskNameField = new JTextField();
        subtaskPanel.add(subtaskNameField);

        subtaskPanel.add(new JLabel("Description:"));
        subtaskDescriptionField = new JTextField();
        subtaskPanel.add(subtaskDescriptionField);

        addSubtaskButton = new JButton("Add Subtask");
        markSubtaskCompleteButton = new JButton("Mark Subtask Complete");
        subtaskPanel.add(addSubtaskButton);
        subtaskPanel.add(markSubtaskCompleteButton);

        removeSubtaskButton = new JButton("Remove Subtask");
        subtaskPanel.add(removeSubtaskButton);
        removeSubtaskButton.addActionListener(new RemoveSubtaskAction());
        subtaskPanel.add(editSubtaskButton);
        subtaskPanel.add(deleteSubtaskButton);

        add(new JScrollPane(subtaskJList), BorderLayout.EAST);
        add(subtaskPanel, BorderLayout.SOUTH);

        // Event Listeners
        addTaskButton.addActionListener(new AddTaskAction());
        markTaskCompleteButton.addActionListener(new MarkTaskCompleteAction());
        addSubtaskButton.addActionListener(new AddSubtaskAction());
        markSubtaskCompleteButton.addActionListener(new MarkSubtaskCompleteAction());
    }

    // Action Listeners

    // Add a new Task
    private class AddTaskAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String taskName = taskNameField.getText();
            String taskDescription = taskDescriptionField.getText();
            String taskCategory = taskCategoryField.getText();
            int taskPriority = Integer.parseInt(taskPriorityField.getText());

            task newTask = new task(taskName, taskDescription, LocalDateTime.now().plusDays(5), taskPriority, taskCategory);
            taskListModel.addElement(newTask);
            taskList.add(newTask);
            clearTaskFields();
        }
    }

    // Mark Task as Completed
    private class MarkTaskCompleteAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentTask != null) {
                currentTask.markAsCompleted();
                displaySelectedTask();
                taskJList.repaint(); // Update task list view
            }
        }
    }

    // Add a new Subtask
    private class AddSubtaskAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentTask != null) {
                String subtaskName = subtaskNameField.getText();
                String subtaskDescription = subtaskDescriptionField.getText();
                subtask newSubtask = new subtask(subtaskName, subtaskDescription, LocalDateTime.now().plusDays(2), currentTask);
                currentTask.addsubtask(newSubtask);
                subtaskListModel.addElement(newSubtask);
                clearSubtaskFields();
                checkTaskCompletion();
            } else {
                JOptionPane.showMessageDialog(null, "Please select a task first.");
            }
        }
    }

    // Mark Subtask as Completed
    private class MarkSubtaskCompleteAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentSubtask != null) {
                currentSubtask.markSubtaskAsCompleted();
                subtaskJList.repaint(); // Update subtask list view
                checkTaskCompletion();
            }
        }
    }

    private class RemoveSubtaskAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentSubtask != null && currentTask != null) {
                currentTask.removesubtask(currentSubtask.taskName);
                subtaskListModel.removeElement(currentSubtask);
                currentSubtask = null;
                checkTaskCompletion();
                displaySelectedTask();
            }
        }
    }
    
    private class EditTaskAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentTask != null) {
                currentTask.updateTaskDetails(
                    taskDescriptionField.getText(),
                    Integer.parseInt(taskPriorityField.getText()),
                    taskCategoryField.getText()
                );
                taskJList.repaint();
                displaySelectedTask();
            }
        }
    }
    
    private class DeleteTaskAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentTask != null) {
                taskListModel.removeElement(currentTask);
                taskList.remove(currentTask);
                currentTask = null;
                taskDisplayArea.setText("");
            }
        }
    }
    
    private class EditSubtaskAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentSubtask != null) {
                currentSubtask.updateSubtaskDetails(
                    subtaskDescriptionField.getText(),
                    LocalDateTime.now().plusDays(2) // Set new deadline or use input field
                );
                subtaskJList.repaint();
                displaySelectedTask();
            }
        }
    }
    

    // Helper Methods

    // Display the selected task in the task display area
    private void displaySelectedTask() {
        currentTask = taskJList.getSelectedValue();
        if (currentTask != null) {
            StringBuilder displayText = new StringBuilder();
            displayText.append("Task: ").append(currentTask.taskName).append("\n")
                .append("Description: ").append(currentTask.taskDescription).append("\n")
                .append("Category: ").append(currentTask.taskCategory).append("\n")
                .append("Priority: ").append(currentTask.taskPriority).append("\n")
                .append("Status: ").append(currentTask.completedStatus ? "Completed" : "Pending").append("\n")
                .append("Deadline: ").append(currentTask.deadlineDate).append("\n")
                .append("Progress: ").append(currentTask.getTaskProgress()).append("%\n")
                .append("Subtasks:\n");
            displayText.append("Time Left: ").append(currentTask.getRemainingHours() / 24)
                .append(" days, ").append(currentTask.getRemainingHours() % 24).append(" hours\n");
            ;
    
            for (subtask st : currentTask.subtaskList) {
                displayText.append(" - ").append(st.taskName).append(" [")
                    .append(st.completedStatus ? "Completed" : "Pending").append("]\n");
            }
    
            taskDisplayArea.setText(displayText.toString());
    
            // Load subtasks for the selected task
            // Replace subtaskJList update with subtaskCheckboxPanel in displaySelectedTask()
            subtaskCheckboxPanel.removeAll(); // Clear any existing checkboxes
            for (subtask st : currentTask.subtaskList) {
                JCheckBox subtaskCheckbox = new JCheckBox(st.taskName, st.completedStatus); // Initialize checkbox
                subtaskCheckbox.addActionListener(e -> {
                    st.markSubtaskAsCompleted(); // Mark subtask as completed when checkbox is checked
                    checkTaskCompletion(); // Check if the task is completed after subtask is marked
                    displaySelectedTask(); // Refresh task display to update progress and status
                });
                subtaskCheckboxPanel.add(subtaskCheckbox); // Add checkbox to the panel
            }
            subtaskCheckboxPanel.revalidate(); // Update the panel to show new checkboxes
            subtaskCheckboxPanel.repaint(); // Refresh panel display

        }
    }
    

    // Display the selected subtask in the display area
    private void displaySelectedSubtask() {
        currentSubtask = subtaskJList.getSelectedValue();
    }

    // Clear task input fields
    private void clearTaskFields() {
        taskNameField.setText("");
        taskDescriptionField.setText("");
        taskCategoryField.setText("");
        taskPriorityField.setText("");
    }

    // Clear subtask input fields
    private void clearSubtaskFields() {
        subtaskNameField.setText("");
        subtaskDescriptionField.setText("");
    }

    // Check if all subtasks are completed and update the task status if true
    private void checkTaskCompletion() {
    if (currentTask != null && currentTask.subtaskList.stream().allMatch(subtask::isCompleted)) {
        currentTask.markAsCompleted();
        displaySelectedTask();
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDoListGUI gui = new ToDoListGUI();
            gui.setVisible(true);
        });
    }
}


