package com.example.demo;

import javafx.scene.input.MouseEvent;

public interface Switch {

    void switchToProject(MouseEvent event) ;

    void switchToTask(MouseEvent event);

    void switchToTeam(MouseEvent event);

    void loadFXML(String fxmlFileName);
}
