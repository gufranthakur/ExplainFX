package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class CircleDrawable extends Drawable {

    private Ellipse ellipse;

    public CircleDrawable(CanvasPanel canvasPanel, double x, double y, double width, double height) {
        super(canvasPanel, x, y, width, height);
        ellipse = new Ellipse(x, y, width, height);
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStroke(Color.WHITE);
        ellipse.setStrokeWidth(2);
        this.getChildren().add(ellipse);

        this.setOnMouseEntered(e -> {
            ellipse.setStroke(Color.WHEAT);
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
    }
}