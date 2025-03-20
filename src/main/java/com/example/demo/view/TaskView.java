package com.example.demo.view;

import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.Optional;

@Route(value = "task")
public class TaskView extends VerticalLayout implements HasUrlParameter<Long> {

    private Long taskId;
    private Task task;
    private final TextField taskIdInput;
    private final TextField taskTitleInput;
    private final TextArea taskDescriptionInput;
    private final Button saveTaskButton;
    private final Button returnButton;
    private final TaskService taskService;

    public TaskView(TaskService taskService) {
        this.taskService = taskService;
        setSizeFull();
        getThemeList().add(Lumo.DARK);

        taskIdInput = new TextField();
        taskIdInput.setLabel("Номер задачи");
        taskIdInput.setWidthFull();
        taskIdInput.setReadOnly(true);
        add(taskIdInput);

        taskTitleInput = new TextField();
        taskTitleInput.setLabel("Название задачи");
        taskTitleInput.setWidthFull();
        add(taskTitleInput);

        taskDescriptionInput = new TextArea();
        taskDescriptionInput.setLabel("Описание задачи");
        taskDescriptionInput.setSizeFull();
        add(taskDescriptionInput);

        //buttons
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setWidthFull();
        buttons.setJustifyContentMode(JustifyContentMode.END);

        saveTaskButton = new Button("Сохранить", event -> {
            task.setTitle(taskTitleInput.getValue());
            task.setDescription(taskDescriptionInput.getValue());
            taskService.updateTask(task, taskId);
            getUI().orElseThrow().getPage().setLocation("/");
        });
        saveTaskButton.setDisableOnClick(true);
        saveTaskButton.getStyle().setCursor("pointer");
        saveTaskButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        returnButton = new Button("Вернуться", event -> getUI().orElseThrow().getPage().setLocation("/"));
        returnButton.getStyle().setCursor("pointer");
        returnButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        buttons.add(saveTaskButton, returnButton);
        //end buttons

        add(buttons);
    }

    @Override
    public void setParameter(BeforeEvent event, Long taskId) {
        Optional<Task> optionalTask = taskService.fetchTaskById(taskId);

        if (optionalTask.isEmpty()) {
            throw new IllegalArgumentException("Invalid task ID");
        }
        this.taskId = taskId;
        this.task = optionalTask.get();
        this.taskIdInput.setValue(task.getId().toString());
        this.taskTitleInput.setValue(task.getTitle());
        this.taskDescriptionInput.setValue(task.getDescription());
    }
}
