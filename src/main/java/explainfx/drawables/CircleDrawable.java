package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class CircleDrawable extends Drawable {

    private Ellipse ellipse;
    private CanvasPanel canvasPanel;

    public CircleDrawable(CanvasPanel canvasPanel, double x, double y, double width, double height) {
        super(canvasPanel, x, y, width, height);
        this.canvasPanel = canvasPanel;
        ellipse = new Ellipse(x, y, width, height);
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStroke(drawableColor);
        ellipse.setStrokeWidth(canvasPanel.drawableSize);
        this.getChildren().add(ellipse);

        this.setOnMouseEntered(e -> {
            ellipse.setStroke(hoveredColor);
            canvasPanel.setSelectedDrawable(this);
        });

        this.setOnMouseExited(e -> {
            ellipse.setStroke(Color.WHITE);
            canvasPanel.setSelectedDrawable(null);
        });


    }

    public void update(double width, double height) {
        ellipse.setRadiusX(width);
        ellipse.setRadiusY(height);

        this.setWidth(width);
        this.setHeight(height);
    }

    public CircleDrawable duplicate(double x, double y) {
        return new CircleDrawable(canvasPanel, x, y, getWidth(), getHeight());
    }
}