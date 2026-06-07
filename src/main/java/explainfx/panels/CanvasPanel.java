package explainfx.panels;

import explainfx.ExplainFX;
import explainfx.drawables.Drawable;
import explainfx.drawables.SquareDrawable;
import explainfx.managers.DrawableManager;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class CanvasPanel extends Group {


    public enum DrawableState {

        STROKE,
        ERASE,
        TEXT,
        IMAGE,
        SHAPE_SQUARE,
        SHAPE_CIRCLE,
        SHAPE_ARROW
    }

    private ExplainFX explainFX;
    private DrawableManager drawableManager;

    private Canvas canvas;

    private double mouseX, mouseY;
    private double anchorX, anchorY;
    private Drawable selectedDrawable;
    private DrawableState drawableState;

    private SquareDrawable activeSquare;

    private final int canvasSize = 600;

    public CanvasPanel(ExplainFX explainFX, DrawableManager drawableManager) {
        this.explainFX = explainFX;
        this.drawableManager = drawableManager;

        drawableState = DrawableState.SHAPE_SQUARE;

        this.setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();


        });

        this.setOnMousePressed(e -> {
            if (drawableState == DrawableState.SHAPE_SQUARE) {
                anchorX = e.getX();
                anchorY = e.getY();
                activeSquare = new SquareDrawable(this, anchorX, anchorY, 0, 0);
                this.getChildren().add(activeSquare);
                drawableManager.add(activeSquare);
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
            }
        });

        this.setOnMouseReleased(e -> {


            activeSquare = null;
        });

        createUI();
        addComponent();
    }

    public void createUI() {
        canvas = new Canvas(canvasSize, canvasSize);
        this.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.ROYALBLUE);
        gc.fillRect(0, 0, 700, 700);

    }

    private void addComponent() {
        //this.getChildren().add(canvas);
    }

    public void setSelectedDrawable(Drawable drawable) {
        this.selectedDrawable = drawable;
    }

    public void setDrawableState(DrawableState state) {
        this.drawableState = state;
    }

}
