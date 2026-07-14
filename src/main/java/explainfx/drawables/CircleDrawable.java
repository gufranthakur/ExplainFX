package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class CircleDrawable extends Drawable {

    private transient Ellipse ellipse;
    private double strokeWidth;

    public CircleDrawable(CanvasPanel canvasPanel, double x, double y, double width, double height) {
        super(canvasPanel, x, y, width, height);
        this.strokeWidth = canvasPanel.drawableSize;
        buildEllipse();
    }

    private void buildEllipse() {
        ellipse = new Ellipse(getX(), getY(), getWidth(), getHeight());
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStroke(getDrawableColor());
        ellipse.setStrokeWidth(strokeWidth);
        this.getChildren().add(ellipse);

        this.setOnMouseEntered(e -> {
            ellipse.setStroke(hoveredColor);
            canvasPanel.setSelectedDrawable(this);
        });

        this.setOnMouseExited(e -> {
            ellipse.setStroke(getDrawableColor());
            canvasPanel.setSelectedDrawable(null);
        });
    }

    public void update(double width, double height) {
        ellipse.setRadiusX(width);
        ellipse.setRadiusY(height);

        this.setWidth(width);
        this.setHeight(height);
    }

    @Override
    public void rebuildVisual(CanvasPanel canvasPanel) {
        wireHandlers(canvasPanel);
        setDrawableColor(hexToColor(getDrawableColorHex()));
        this.getChildren().clear();
        buildEllipse();
    }

    public CircleDrawable duplicate(double x, double y) {
        return new CircleDrawable(canvasPanel, x, y, getWidth(), getHeight());
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }
}