package explainfx;


import atlantafx.base.theme.CupertinoDark;
import explainfx.managers.DrawableManager;
import explainfx.panels.CanvasPanel;
import explainfx.panels.ControlPanel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

public class ExplainFX extends Application {

    private StackPane rootPane;

    private ControlPanel controlPanel;
    private CanvasPanel canvasPanel;

    private DrawableManager drawableManager;

    public static void main(String[] args) {
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());

        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();
            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                taskbar.setIconImage(new ImageIcon(ExplainFX.class.getResource("/icon.png")).getImage());
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
        rootPane = new StackPane();

        drawableManager = new DrawableManager();

        controlPanel = new ControlPanel(this);
        controlPanel.setPrefSize(400, 100);
        controlPanel.setMaxHeight(100);

        canvasPanel = new CanvasPanel(this, drawableManager);

        rootPane.getChildren().add(canvasPanel);
        rootPane.getChildren().add(controlPanel);
        rootPane.setPadding(new Insets(0, 0, 20, 0));
        StackPane.setAlignment(controlPanel, Pos.BOTTOM_CENTER);

    }

    public void createWindow(Stage stage) {
        Scene rootScene = new Scene(rootPane, 1280, 720);
        stage = new Stage();
        stage.setScene(rootScene);
        stage.setTitle("ExplainFX");
        stage.show();
    }

    public CanvasPanel getCanvasPanel() {
        return canvasPanel;
    }

    public DrawableManager getDrawableManager() {
        return drawableManager;
    }

}
