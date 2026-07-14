package explainfx;


import atlantafx.base.theme.CupertinoDark;
import explainfx.core.DataManager;
import explainfx.panels.CanvasPanel;
import explainfx.panels.ControlPanel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

public class ExplainFX extends Application {

    private Stage stage;
    private StackPane rootPane;

    private ControlPanel controlPanel;
    private CanvasPanel canvasPanel;
    private DataManager dataManager;

    public static void main(String[] args) {
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());

        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();
            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                taskbar.setIconImage(new ImageIcon(ExplainFX.class.getResource("/installer/explainFX_icon.png")).getImage());
            }
        }

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        createComponents();
        createWindow(stage);
    }

    public void createComponents() {
        dataManager = new DataManager(this);

        rootPane = new StackPane();

        canvasPanel = new CanvasPanel(this);

        controlPanel = new ControlPanel(this);
        controlPanel.setPrefSize(400, 100);
        controlPanel.setMaxHeight(100);

        rootPane.getChildren().add(canvasPanel);
        rootPane.getChildren().add(controlPanel);
        rootPane.setPadding(new Insets(0, 0, 20, 0));
        StackPane.setAlignment(controlPanel, Pos.BOTTOM_CENTER);

    }

    public void createWindow(Stage stage) {
        this.stage = stage;
        Scene rootScene = new Scene(rootPane, 1280, 720);
        stage = new Stage();
        stage.setScene(rootScene);
        stage.setTitle("ExplainFX");
        stage.show();
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public CanvasPanel getCanvasPanel() {
        return canvasPanel;
    }

    public DataManager getDataManager() {return dataManager;}

    public Stage getStage() {
        return stage;
    }

}
