package explainfx.panels;

import explainfx.ExplainFX;
import explainfx.drawables.*;
import explainfx.menus.DrawableMenu;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class CanvasPanel extends Group {


    public enum DrawableState {
        NONE,
        STROKE,
        ERASE,
        TEXT,
        IMAGE,
        SHAPE_SQUARE,
        SHAPE_CIRCLE,
        SHAPE_ARROW
    }

    public ExplainFX explainFX;
    private DrawableMenu drawableMenu;

    private Canvas canvas;

    private double panStartX, panStartY;
    private double anchorX, anchorY;
    private Drawable selectedDrawable;
    private DrawableState drawableState;

    public ArrayList<Drawable> drawables;

    private Drawable copiedDrawable;

    private SquareDrawable activeSquare;
    private CircleDrawable activeCircle;
    private StrokeDrawable activeStroke;
    private TextDrawable activeText;

    private final int canvasSize = 3000;
    public int drawableSize = 5;
    public Color selectedColor = Color.WHITE;

    public CanvasPanel(ExplainFX explainFX) {
        this.explainFX = explainFX;

        drawableMenu = new DrawableMenu(this);
        drawables = new ArrayList<>(20);

        drawableState = DrawableState.NONE;

        this.setOnMousePressed(e -> {

            if (e.isPopupTrigger()) {
                System.out.println("Yeouch");
                drawableMenu.display(e.getScreenX(), e.getSceneY(), e.getX(), e.getY());
                return;
            }

            if (drawableState == DrawableState.NONE) {

                panStartX = e.getSceneX() - this.getTranslateX();
                panStartY = e.getSceneY() - this.getTranslateY();

                return;

            }

            anchorX = e.getX();
            anchorY = e.getY();

            if (drawableState == DrawableState.SHAPE_SQUARE) {
                activeSquare = new SquareDrawable(this, anchorX, anchorY, 0, 0);
                this.getChildren().add(activeSquare);
                addDrawableToList(activeSquare);
            } else if (drawableState == DrawableState.SHAPE_CIRCLE) {
                activeCircle = new CircleDrawable(this, anchorX, anchorY, 0, 0);
                this.getChildren().add(activeCircle);
                addDrawableToList(activeCircle);
            } else if (drawableState == DrawableState.STROKE) {
                activeStroke = new StrokeDrawable(this, anchorX, anchorY);
                this.getChildren().add(activeStroke);
                addDrawableToList(activeStroke);
            } else if (drawableState == DrawableState.TEXT) {
                activeText = new TextDrawable(this, e.getX(), e.getY());
                this.getChildren().add(activeText);
                addDrawableToList(activeText);
            }
        });

        this.setOnMouseDragged(e -> {

            if (e.isPopupTrigger()) return;

            // mouseDragged
            if (drawableState == DrawableState.NONE) {

                this.setTranslateX(e.getSceneX() - panStartX);
                this.setTranslateY(e.getSceneY() - panStartY);
                return;
            }

            if (Objects.requireNonNull(drawableState) == DrawableState.SHAPE_SQUARE) {
                if (activeSquare == null) return;

                double x = Math.min(e.getX(), anchorX);
                double y = Math.min(e.getY(), anchorY);
                double width = Math.abs(e.getX() - anchorX);
                double height = Math.abs(e.getY() - anchorY);

                activeSquare.update(x, y, width, height);
            } else if (drawableState == DrawableState.SHAPE_CIRCLE) {
                if (activeCircle == null) return;

                double x = Math.min(e.getX(), anchorX);
                double y = Math.min(e.getY(), anchorY);
                double height = Math.abs(e.getY() - anchorY);
                double width = Math.abs(e.getX() - anchorX);

                activeCircle.update(width, height);

            } else if (drawableState == DrawableState.STROKE) {

                if (activeStroke == null) return;

                activeStroke.addPoint(e.getX(), e.getY());

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
}
