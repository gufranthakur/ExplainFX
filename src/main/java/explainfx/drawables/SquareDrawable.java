package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareDrawable extends Drawable {

    private transient Rectangle rectangle;
    private double strokeWidth;

    public SquareDrawable(CanvasPanel canvasPanel, double x, double y, double width, double height) {
        super(canvasPanel, x, y, width, height);
        this.strokeWidth = canvasPanel.drawableSize;
        buildRectangle();
    }

    private void buildRectangle() {
        rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(getDrawableColor());
        rectangle.setStrokeWidth(strokeWidth);
        this.getChildren().add(rectangle);

        this.setOnMouseEntered(e -> {
            rectangle.setStroke(hoveredColor);
            canvasPanel.setSelectedDrawable(this);
        });

        this.setOnMouseExited(e -> {
            rectangle.setStroke(getDrawableColor());
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

    @Override
    public void rebuildVisual(CanvasPanel canvasPanel) {
        wireHandlers(canvasPanel);
        setDrawableColor(hexToColor(getDrawableColorHex()));
        this.getChildren().clear();
        buildRectangle();
    }

    public SquareDrawable duplicate(double x, double y) {
        return new SquareDrawable(canvasPanel, x, y, getWidth(), getHeight());
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }
}