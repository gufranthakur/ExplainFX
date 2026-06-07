package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public abstract class Drawable extends Group {

    private CanvasPanel canvasPanel;
    private double x, y;
    private double width, height;


    protected double dragOffSetX, dragOffSetY;
    protected Color borderColor = Color.WHITE;


    public Drawable(CanvasPanel canvasPanel, double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.canvasPanel = canvasPanel;

        this.setOnMouseEntered(e -> {
            canvasPanel.setSelectedDrawable(this);
            System.out.println("Entered");
            borderColor = Color.RED;
        });

        this.setOnMouseExited(e -> {
            borderColor = Color.WHITE;
            System.out.println("Exited");
        });

        this.setOnMousePressed(e -> {
            dragOffSetX = e.getX();
            dragOffSetY = e.getY();

        });

        this.setOnMouseDragged(e -> {
            if (canvasPanel.getDrawableState() != CanvasPanel.DrawableState.NONE) return;
            this.setLayoutX(this.getLayoutX() + e.getX() - dragOffSetX);
            this.setLayoutY(this.getLayoutY() + e.getY() - dragOffSetY);
            e.consume();
        });
    }

    //----------Getters and setters-------------//

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}
