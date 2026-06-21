package explainfx.handlers;

import explainfx.drawables.CircleDrawable;
import explainfx.drawables.SquareDrawable;
import explainfx.drawables.StrokeDrawable;
import explainfx.drawables.TextDrawable;
import explainfx.panels.CanvasPanel;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class MouseEventHandler {

    public CanvasPanel canvasPanel;

    private double panStartX, panStartY;
    private double anchorX, anchorY;

    public MouseEventHandler(CanvasPanel canvasPanel) {
        this.canvasPanel = canvasPanel;
    }

    public void handleMousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            canvasPanel.drawableMenu.display(e.getScreenX(), e.getSceneY(), e.getX(), e.getY());
            return;
        }

        if (canvasPanel.getDrawableState() == CanvasPanel.DrawableState.NONE) {

            panStartX = e.getSceneX() - canvasPanel.getTranslateX();
            panStartY = e.getSceneY() - canvasPanel.getTranslateY();

            return;

        }

        anchorX = e.getX();
        anchorY = e.getY();

        if (canvasPanel.getDrawableState() == CanvasPanel.DrawableState.SHAPE_SQUARE) {
            canvasPanel.activeSquare = new SquareDrawable(canvasPanel, anchorX, anchorY, 0, 0);
            canvasPanel.getChildren().add(canvasPanel.activeSquare);
            canvasPanel.addDrawableToList(canvasPanel.activeSquare);

        } else if (canvasPanel.getDrawableState() == CanvasPanel.DrawableState.SHAPE_CIRCLE) {
            canvasPanel.activeCircle = new CircleDrawable(canvasPanel, anchorX, anchorY, 0, 0);
            canvasPanel.getChildren().add(canvasPanel.activeCircle);
            canvasPanel.addDrawableToList(canvasPanel.activeCircle);

        } else if (canvasPanel.getDrawableState() == CanvasPanel.DrawableState.STROKE) {
            canvasPanel.activeStroke = new StrokeDrawable(canvasPanel, anchorX, anchorY);
            canvasPanel.getChildren().add(canvasPanel.activeStroke);
            canvasPanel.addDrawableToList(canvasPanel.activeStroke);
        } else if (canvasPanel.getDrawableState() == CanvasPanel.DrawableState.TEXT) {
            canvasPanel.activeText = new TextDrawable(canvasPanel.inputTextData, canvasPanel, e.getX(), e.getY());
            canvasPanel.getChildren().add(canvasPanel.activeText);
            canvasPanel.addDrawableToList(canvasPanel.activeText);
        }
    }

    public void handleMouseDrag(MouseEvent e) {
        if (e.isPopupTrigger()) return;

        if (canvasPanel.getDrawableState() == CanvasPanel.DrawableState.NONE) {

            canvasPanel.setTranslateX(e.getSceneX() - panStartX);
            canvasPanel.setTranslateY(e.getSceneY() - panStartY);
            return;
        }

        if (Objects.requireNonNull(canvasPanel.getDrawableState()) == CanvasPanel.DrawableState.SHAPE_SQUARE) {
            if (canvasPanel.activeSquare == null) return;

            double x = Math.min(e.getX(), anchorX);
            double y = Math.min(e.getY(), anchorY);
            double width = Math.abs(e.getX() - anchorX);
            double height = Math.abs(e.getY() - anchorY);

            canvasPanel.activeSquare.update(x, y, width, height);
        } else if (canvasPanel.getDrawableState() == CanvasPanel.DrawableState.SHAPE_CIRCLE) {
            if (canvasPanel.activeCircle == null) return;

            double height = Math.abs(e.getY() - anchorY);
            double width = Math.abs(e.getX() - anchorX);

            canvasPanel.activeCircle.update(width, height);

        } else if (canvasPanel.getDrawableState() == CanvasPanel.DrawableState.STROKE) {

            if (canvasPanel.activeStroke == null) return;

            canvasPanel.activeStroke.addPoint(e.getX(), e.getY());

        } else if (canvasPanel.getDrawableState() == CanvasPanel.DrawableState.TEXT) {
            double width = Math.abs(e.getX() - anchorX);
            double height = Math.abs(e.getY() - anchorY);

            canvasPanel.activeText.update(width, height);
        }
    }

}
