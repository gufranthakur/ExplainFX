package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

public class CircleDrawable extends Drawable {

    private Ellipse circle;

    public CircleDrawable(CanvasPanel canvasPanel, double x, double y, double width, double height) {
        super(canvasPanel, x, y, width, height);
        circle = new Ellipse(x, y, width, height);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(2);
        this.getChildren().add(circle);
    }

    public void update(double width, double height) {
        circle.setRadiusX(width);
        circle.setRadiusY(height);
    }
}