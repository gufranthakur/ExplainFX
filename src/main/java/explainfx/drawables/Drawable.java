package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public abstract class Drawable extends Group {

    protected transient CanvasPanel canvasPanel;
    private double x, y;
    private double width, height;

    private boolean isLocked = false;

    protected transient double dragOffSetX, dragOffSetY;
    protected transient Color borderColor = Color.WHITE;
    protected transient Color hoveredColor = Color.rgb(237, 113, 104);

    // Color is a JavaFX Paint object -> not Gson-safe. Store as hex, rebuild the Color at runtime.
    private String drawableColorHex;
    protected transient Color drawableColor;

    public Drawable(CanvasPanel canvasPanel, double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.canvasPanel = canvasPanel;

        this.drawableColor = canvasPanel.selectedColor;
        this.drawableColorHex = colorToHex(drawableColor);

        wireHandlers(canvasPanel);
    }

    // Shared mouse handler wiring, callable again after deserialization
    protected void wireHandlers(CanvasPanel canvasPanel) {
        this.canvasPanel = canvasPanel;

        this.setOnMouseEntered(e -> {
            canvasPanel.setSelectedDrawable(this);
            borderColor = Color.RED;
        });

        this.setOnMouseExited(e -> {
            borderColor = Color.WHITE;
        });

        this.setOnMousePressed(e -> {
            dragOffSetX = e.getX();
            dragOffSetY = e.getY();
        });

        this.setOnMouseDragged(e -> {
            if (isLocked) return;
            if (canvasPanel.getDrawableState() != CanvasPanel.DrawableState.VIEW_MODE) return;
            this.setLayoutX(this.getLayoutX() + e.getX() - dragOffSetX);
            this.setLayoutY(this.getLayoutY() + e.getY() - dragOffSetY);
            e.consume();
        });
    }

    // Every subclass rebuilds its own JavaFX shape + re-wires handlers after Gson load
    public abstract void rebuildVisual(CanvasPanel canvasPanel);

    protected static String colorToHex(Color color) {
        return color.toString(); // e.g. "0x336699ff" — Color.web() can parse this back
    }

    protected static Color hexToColor(String hex) {
        return Color.web(hex);
    }

    //----------Getters and setters-------------//

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public String getDrawableColorHex() { return drawableColorHex; }

    public Color getDrawableColor() { return drawableColor; }

    public void setDrawableColor(Color color) {
        this.drawableColor = color;
        this.drawableColorHex = colorToHex(color);
    }

    public void lockDrawable() { isLocked = true; }
    public void unlockDrawable() { isLocked = false; }
}