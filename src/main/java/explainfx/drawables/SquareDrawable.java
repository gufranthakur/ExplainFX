package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import explainfx.panels.ControlPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareDrawable extends Drawable{

    private Rectangle rectangle;



    public SquareDrawable(CanvasPanel canvasPanel, double x, double y, float width, float height) {
        super(canvasPanel, x, y, width, height);
        rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(borderColor);
        rectangle.setStrokeWidth(1.5);
        this.getChildren().add(rectangle);

        this.setOnMouseEntered(e -> {
            rectangle.setStroke(Color.WHEAT);

            canvasPanel.setSelectedDrawable(this);
        });

        this.setOnMouseExited(e -> {
            rectangle.setStroke(Color.WHITE);

            canvasPanel.setSelectedDrawable(null);
        });


    }

    public void update(double x, double y, double width, double height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);

        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setX(x);
        rectangle.setY(y);
    }
}
