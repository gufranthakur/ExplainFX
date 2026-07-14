package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class StrokeDrawable extends Drawable {

    private transient Polyline polyline;

    private List<Double> points = new ArrayList<>();
    private double strokeWidth;

    private transient double lastX = Double.NaN;
    private transient double lastY = Double.NaN;
    private static final double SMOOTHING = 0.3;

    public StrokeDrawable(CanvasPanel canvasPanel, double x, double y) {
        super(canvasPanel, x, y, 0, 0);
        this.strokeWidth = canvasPanel.drawableSize;
        this.setLayoutX(x);
        this.setLayoutY(y);
        buildPolyline();
    }

    // Used by duplicate() — takes an existing Polyline's points directly
    public StrokeDrawable(CanvasPanel canvasPanel, double x, double y, Polyline sourcePolyline) {
        super(canvasPanel, x, y, 0, 0);
        this.strokeWidth = sourcePolyline.getStrokeWidth();
        setDrawableColor((Color) sourcePolyline.getStroke());

        for (Double p : sourcePolyline.getPoints()) {
            points.add(p);
        }

        this.setLayoutX(x);
        this.setLayoutY(y);
        buildPolyline();
    }

    private void buildPolyline() {
        polyline = new Polyline();
        polyline.setStroke(getDrawableColor());
        polyline.setStrokeWidth(strokeWidth);
        polyline.setSmooth(true);
        polyline.setStrokeLineCap(StrokeLineCap.ROUND);
        polyline.setStrokeLineJoin(StrokeLineJoin.ROUND);
        polyline.getPoints().addAll(points); // restores points if loaded, no-op if empty

        this.getChildren().add(polyline);

        this.setOnMouseEntered(e -> {
            polyline.setStroke(hoveredColor);
            canvasPanel.setSelectedDrawable(this);
        });

        this.setOnMouseExited(e -> {
            polyline.setStroke(getDrawableColor());
            canvasPanel.setSelectedDrawable(null);
        });
    }

    public void addPoint(double x, double y) {
        double localX = x - this.getLayoutX();
        double localY = y - this.getLayoutY();

        if (Double.isNaN(lastX)) {
            lastX = localX;
            lastY = localY;
        } else {
            lastX = lastX + SMOOTHING * (localX - lastX);
            lastY = lastY + SMOOTHING * (localY - lastY);
        }

        polyline.getPoints().addAll(lastX, lastY);
        points.add(lastX);
        points.add(lastY);
    }

    @Override
    public void rebuildVisual(CanvasPanel canvasPanel) {
        wireHandlers(canvasPanel);
        setDrawableColor(hexToColor(getDrawableColorHex()));
        this.getChildren().clear();
        buildPolyline();
    }

    public StrokeDrawable duplicate(double x, double y, Polyline polyline) {
        return new StrokeDrawable(canvasPanel, x, y, polyline);
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public List<Double> getPoints() {
        return points;
    }
}