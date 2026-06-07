package explainfx.panels;

import explainfx.ExplainFX;
import explainfx.drawables.CircleDrawable;
import explainfx.drawables.Drawable;
import explainfx.drawables.SquareDrawable;
import explainfx.managers.DrawableManager;
import explainfx.menus.DrawableMenu;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
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
    private DrawableManager drawableManager;
    private DrawableMenu drawableMenu;

    private Canvas canvas;

    private double mouseX, mouseY;
    private double anchorX, anchorY;
    private Drawable selectedDrawable;
    private DrawableState drawableState;

    public ArrayList<Drawable> drawables;

    private SquareDrawable activeSquare;
    private CircleDrawable activeCircle;

    private final int canvasSize = 3000;

    public CanvasPanel(ExplainFX explainFX, DrawableManager drawableManager) {
        this.explainFX = explainFX;
        this.drawableManager = drawableManager;

        drawableMenu = new DrawableMenu(this);
        drawables = new ArrayList<>(20);

        drawableState = DrawableState.NONE;

        this.setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });

        this.setOnMousePressed(e -> {

            if (e.isPopupTrigger()) {
                System.out.println("Yeouch");
                drawableMenu.display(e.getScreenX(), e.getSceneY());
                return;
            }

            if (drawableState == DrawableState.SHAPE_SQUARE) {
                anchorX = e.getX();
                anchorY = e.getY();
                activeSquare = new SquareDrawable(this, anchorX, anchorY, 0, 0);
                this.getChildren().add(activeSquare);
                addDrawableToList(activeSquare);
            } else if (drawableState == DrawableState.SHAPE_CIRCLE) {

                anchorX = e.getX();
                anchorY = e.getY();

                activeCircle = new CircleDrawable(this, anchorX, anchorY, 0, 0);
                this.getChildren().add(activeCircle);
                addDrawableToList(activeCircle);
            }
        });

        this.setOnMouseDragged(e -> {

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

            }

        });

        this.setOnMouseReleased(e -> {
            activeSquare = null;
            activeCircle = null;
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

    public DrawableState getDrawableState() {
        return drawableState;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
