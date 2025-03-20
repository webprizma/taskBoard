package com.example.demo.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;

public class CustomEvent extends ComponentEvent<TextField> {
    public CustomEvent(TextField source, boolean fromClient) {
        super(source, fromClient);
    }
}