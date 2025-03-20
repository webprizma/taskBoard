package com.example.demo.view;

import com.example.demo.component.CustomEvent;
import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class CreateTaskView extends VerticalLayout {

    private TaskService taskService;
    private TextField taskTitleField;
    private TextArea taskDescriptionField;
    private Button addTaskButton;

    public CreateTaskView(TaskService taskService) {
        this.taskService = taskService;

        taskTitleField = new TextField();
        taskTitleField.setPlaceholder("Название задачи");
        taskTitleField.setWidthFull();

        taskDescriptionField = new TextArea();
        taskDescriptionField.setPlaceholder("Описание задачи");
        taskDescriptionField.setSizeFull();

        addTaskButton = new Button("Добавить задачу", event -> addTask());
        addTaskButton.setDisableOnClick(true);
        addTaskButton.setWidthFull();
        addTaskButton.getStyle().setCursor("pointer");
        addTaskButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(taskTitleField, taskDescriptionField, addTaskButton);
    }

    private void addTask() {
        try {
            var taskTitle = taskTitleField.getValue();
            var taskDescription = taskDescriptionField.getValue();
            if (!taskTitle.isBlank()) {
                taskService.saveTask(new Task(taskTitle, taskDescription, "ykulagin"));
                taskTitleField.clear();
                taskDescriptionField.clear();
            }
        } finally {
            addTaskButton.setEnabled(true);
        }
    }
}
