package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class StrokeDrawable extends Drawable{

    private CanvasPanel canvasPanel;
    private Polyline polyline;

    private double lastX = Double.NaN;
    private double lastY = Double.NaN;
    private static final double SMOOTHING = 0.3;

    public StrokeDrawable(CanvasPanel canvasPanel, double x, double y) {
        super(canvasPanel, x, y, 0, 0);
        this.canvasPanel = canvasPanel;
        polyline = new Polyline();
        polyline.setStroke(drawableColor);
        polyline.setStrokeWidth(canvasPanel.drawableSize);
        polyline.setSmooth(true);
        polyline.setStrokeLineCap(StrokeLineCap.ROUND);
        polyline.setStrokeLineJoin(StrokeLineJoin.ROUND);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.getChildren().add(polyline);

        this.setOnMouseEntered(e -> {
            polyline.setStroke(hoveredColor);
            canvasPanel.setSelectedDrawable(this);
        });

        this.setOnMouseExited(e -> {
            polyline.setStroke(drawableColor);
            canvasPanel.setSelectedDrawable(null);
        });

    }
    public StrokeDrawable(CanvasPanel canvasPanel, double x, double y, Polyline polyline) {
        super(canvasPanel, x, y, 0, 0);
        this.canvasPanel = canvasPanel;

        this.setLayoutX(x);
        this.setLayoutY(y);
        this.getChildren().add(polyline);

        this.setOnMouseEntered(e -> {
            polyline.setStroke(hoveredColor);
            canvasPanel.setSelectedDrawable(this);
        });

        this.setOnMouseExited(e -> {
            polyline.setStroke(drawableColor);
            canvasPanel.setSelectedDrawable(null);
        });
    }

    public void addPoint(double x, double y) {
        double localX = x - this.getLayoutX();
        double localY = y - this.getLayoutY();

        if (Double.isNaN(lastX)) {
            // First point — no smoothing yet
            lastX = localX;
            lastY = localY;
        } else {
            // Exponential moving average
            lastX = lastX + SMOOTHING * (localX - lastX);
            lastY = lastY + SMOOTHING * (localY - lastY);
        }

        polyline.getPoints().addAll(lastX, lastY);
    }

    public StrokeDrawable duplicate(double x, double y, Polyline polyline) {
        Polyline newPolyline = new Polyline();
        newPolyline.setStroke(getPolyline().getStroke());
        newPolyline.setStrokeWidth(getPolyline().getStrokeWidth());
        newPolyline.setSmooth(true);
        newPolyline.setStrokeLineCap(StrokeLineCap.ROUND);
        newPolyline.setStrokeLineJoin(StrokeLineJoin.ROUND);
        newPolyline.getPoints().addAll(getPolyline().getPoints());
        return new StrokeDrawable(canvasPanel, x, y, newPolyline);
    }

    public Polyline getPolyline() {
        return polyline;
    }

}
