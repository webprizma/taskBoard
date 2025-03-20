package com.example.demo.view;

import com.example.demo.entity.Task;
import com.example.demo.layout.MainLayout;
import com.example.demo.service.TaskService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class MainView extends Grid<Task> {
    private final TaskService taskService;

    public MainView(TaskService taskService) {
        this.taskService = taskService;
        configureBeanType(Task.class, false);

        //styling
        addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        //listeners
        addItemClickListener(event -> getUI().orElseThrow().getPage().setLocation("/task/%d".formatted(event.getItem().getId())));

        //columns
        addComponentColumn(task -> createStatusIcon(task.getActive())).setAutoWidth(true).setFlexGrow(0);
        addColumn(Task::getTitle).setHeader("Название задачи").setSortable(true).setResizable(true).setAutoWidth(true).setFlexGrow(0);
        addColumn(Task::getDescription).setHeader("Описание задачи");
        addColumn(Task::getAuthor).setHeader("Автор задачи").setSortable(true).setAutoWidth(true).setFlexGrow(0);
        addComponentColumn(this::createItemMenu).setAutoWidth(true).setFlexGrow(0);
    }

    private void refreshTasks() {
        setItems(taskService.fetchTaskList());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        refreshTasks();
    }

    private Icon createStatusIcon(Boolean isActive) {
        Icon icon;
        if (isActive != null && isActive.equals(true)) {
            icon = createIcon(VaadinIcon.CHECK_CIRCLE_O, "Выполнена");
            icon.getElement().getThemeList().add("badge success");
        } else if (isActive != null && isActive.equals(false)) {
            icon = createIcon(VaadinIcon.CLOSE_CIRCLE_O, "Отменена");
            icon.getElement().getThemeList().add("badge error");
        } else {
            icon = createIcon(VaadinIcon.CIRCLE, "Новая");
            icon.getElement().getThemeList().add("badge warning");
        }
        return icon;
    }

    private Icon createIcon(VaadinIcon vaadinIcon, String label) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        // Accessible label
        icon.getElement().setAttribute("aria-label", label);
        // Tooltip
        icon.getElement().setAttribute("title", label);
        return icon;
    }

    private HorizontalLayout createItemMenu(Task task) {
        HorizontalLayout buttonsBlock = new HorizontalLayout();

        Button editButton = new Button("Редактировать", event -> {
            getUI().orElseThrow().getPage().setLocation("/task/%d".formatted(task.getId()));
        });
        buttonsBlock.add(editButton);

        Button finishButton = new Button("Завершить", event -> {
            task.setActive(true);
            taskService.updateTask(task, task.getId());
            refreshTasks();
        });
        buttonsBlock.add(finishButton);

        Button cancelButton = new Button("Отменить", event -> {
            task.setActive(false);
            taskService.updateTask(task, task.getId());
            refreshTasks();
        });
        buttonsBlock.add(cancelButton);

        Button recoverButton = new Button("Восстановить", event -> {
            task.setActive(null);
            taskService.updateTask(task, task.getId());
            refreshTasks();
        });
        buttonsBlock.add(recoverButton);

        Button deleteButton = new Button("Удалить", event -> {
            taskService.deleteTaskById(task.getId());
            refreshTasks();
        });
        buttonsBlock.add(deleteButton);

        return buttonsBlock;
    }
}
