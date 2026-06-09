package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import explainfx.panels.ControlPanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareDrawable extends Drawable{

    private CanvasPanel canvasPanel;
    private Rectangle rectangle;



    public SquareDrawable(CanvasPanel canvasPanel, double x, double y, double width, double height) {
        super(canvasPanel, x, y, width, height);
        this.canvasPanel = canvasPanel;
        rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(drawableColor);
        rectangle.setStrokeWidth(canvasPanel.drawableSize);
        this.getChildren().add(rectangle);

        this.setOnMouseEntered(e -> {
            rectangle.setStroke(hoveredColor);
            canvasPanel.setSelectedDrawable(this);
        });

        this.setOnMouseExited(e -> {
            rectangle.setStroke(drawableColor);
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


    public SquareDrawable duplicate(double x, double y) {
        return new SquareDrawable(canvasPanel, x, y, getWidth(), getHeight());
    }
}
