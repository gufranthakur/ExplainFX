package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class StrokeDrawable extends Drawable{

    private Polyline polyline;

    public StrokeDrawable(CanvasPanel canvasPanel, double x, double y) {
        super(canvasPanel, x, y, 0, 0);
        polyline = new Polyline();
        polyline.setStroke(Color.WHITE);
        polyline.setStrokeWidth(5);
        polyline.setSmooth(true);
        polyline.setStrokeLineCap(StrokeLineCap.ROUND);
        polyline.setStrokeLineJoin(StrokeLineJoin.ROUND);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.getChildren().add(polyline);
    }

    public void addPoint(double x, double y) {
        polyline.getPoints().addAll(x - this.getLayoutX(), y - this.getLayoutY());
    }


}
