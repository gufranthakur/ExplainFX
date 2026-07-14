package explainfx.panels;

import explainfx.ExplainFX;
import explainfx.drawables.*;
import explainfx.handlers.KeyboardEventHandler;
import explainfx.handlers.MouseEventHandler;
import explainfx.ui.DrawableMenu;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;

public class CanvasPanel extends Group {


    public enum DrawableState {
        VIEW_MODE, DRAW_MODE, TEXT_MODE, SQUARE_MODE, CIRCLE_MODE,
    }

    // Core stuff

    public ExplainFX explainFX;
    public DrawableMenu drawableMenu;
    private Canvas canvas;
    private Drawable selectedDrawable;
    private DrawableState drawableState;
    public ArrayList<Drawable> drawables;
    private Drawable copiedDrawable;
    // Active Elements, used for keeping track of what the user is drawing right now. //
    public SquareDrawable activeSquare;
    public CircleDrawable activeCircle;
    public StrokeDrawable activeStroke;
    public TextDrawable activeText;
    public String inputTextData;

    private final int canvasSize = 3000;
    public int drawableSize = 5;
    public Circle strokeSizePreviewCircle;
    public Color selectedColor = Color.WHITE;

    private MouseEventHandler mouseEventHandler;
    private KeyboardEventHandler keyboardEventHandler;

    public CanvasPanel(ExplainFX explainFX) {
        this.explainFX = explainFX;

        mouseEventHandler = new MouseEventHandler(this);
        keyboardEventHandler = new KeyboardEventHandler(this);

        drawableMenu = new DrawableMenu(this);
        drawables = new ArrayList<>(20);

        drawableState = DrawableState.VIEW_MODE;

        this.setOnMousePressed(e -> mouseEventHandler.handleMousePressed(e));
        this.setOnMouseDragged(e -> mouseEventHandler.handleMouseDrag(e));

        // In CanvasPanel constructor, after scene is available
        this.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(e -> keyboardEventHandler.handleKeyPressed(e));
            }
        });

        this.setOnMouseReleased(e -> {
            activeSquare = null;
            activeCircle = null;
            activeStroke = null;
        });

        createUI();

    }

    public void createUI() {
        strokeSizePreviewCircle = new Circle(1500, 1500, drawableSize);
        strokeSizePreviewCircle.setFill(Color.WHITE);

        canvas = new Canvas(canvasSize, canvasSize);
        this.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(30, 30, 30));
        gc.fillRect(0, 0, canvasSize, canvasSize);

        int gridSpacing = 40;
        gc.setStroke(Color.rgb(60, 60, 60));
        gc.setLineWidth(0.5);

        for (int x = 0; x < canvasSize; x += gridSpacing) {
            gc.strokeLine(x, 0, x, canvasSize); // vertical
        }
        for (int y = 0; y < canvasSize; y += gridSpacing) {
            gc.strokeLine(0, y, canvasSize, y); // horizontal
        }
    }

    public void addDrawableToList(Drawable drawable) {
        drawables.add(drawable);
    }

    public void remove(Drawable drawable) {
        drawables.remove(drawable);
        this.getChildren().remove(drawable);
    }

    public void clearAllDrawables() {
        this.getChildren().removeAll(drawables);
        drawables.clear();
    }

    public void pasteCopiedDrawable(double x, double y) {

        Drawable duplicatedDrawable = copiedDrawable;

        try {
            switch (duplicatedDrawable) {
                case SquareDrawable ignored -> duplicatedDrawable = ((SquareDrawable) copiedDrawable).duplicate(x, y);
                case CircleDrawable ignored -> duplicatedDrawable = ((CircleDrawable) copiedDrawable).duplicate(x, y);
                case TextDrawable ignored -> duplicatedDrawable = ((TextDrawable) copiedDrawable).duplicate(x, y);
                case StrokeDrawable strokeDrawable -> duplicatedDrawable = ((StrokeDrawable) copiedDrawable).duplicate(x, y, strokeDrawable.getPolyline());
                default -> throw new IllegalStateException("Unexpected value: " + duplicatedDrawable);
            }

            drawables.add(duplicatedDrawable);
            this.getChildren().add(duplicatedDrawable);
        } catch (NullPointerException e) {
            System.out.println("User did not copy anything. Ignoring paste command.");
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }

    public void placeLoadedDrawables(ArrayList<Drawable> loadedDrawables) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear canvas");
        alert.setHeaderText(null);
        alert.setContentText("You must clear the current canvas, before loading a new one");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) explainFX.getCanvasPanel().clearAllDrawables();

            for (Drawable d : loadedDrawables) {
                drawables.add(d);
                this.getChildren().add(d);
            }
        });

    }

    public void setInputTextData(String data) {
        this.inputTextData = data;
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }

    public void setSelectedDrawable(Drawable drawable) {
        this.selectedDrawable = drawable;
    }

    public Drawable getSelectedDrawable() {
        try {
            return selectedDrawable;
        } catch (NullPointerException e) {
            System.out.println("awefawefawefaw");
        }

        return this.selectedDrawable;
    }

    public void setDrawableState(DrawableState state) {
        this.drawableState = state;
    }

    public void setDrawableSize(int size) {
        this.drawableSize = size;

        strokeSizePreviewCircle.setRadius((double) size / 2 );

    }

    public void setDrawableColor(Color color) {
        this.selectedColor = color;
    }

    public void setCopiedDrawable(Drawable drawable) {
        this.copiedDrawable = drawable;
    }

    public Drawable getCopiedDrawable() {
        return copiedDrawable;
    }

    public DrawableState getDrawableState() {
        return drawableState;
    }

    public Canvas getCanvas() {
        return canvas;
    }
    public ExplainFX getExplainFX() {return explainFX; }
}
