package explainfx.menus;

import explainfx.drawables.Drawable;
import explainfx.panels.CanvasPanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class DrawableMenu extends ContextMenu {

    private double x, y;

    private CanvasPanel canvasPanel;
    private MenuItem copyItem, pasteItem, duplicateItem, lockItem, unlockItem, deleteItem;

    public DrawableMenu(CanvasPanel canvasPanel) {
        this.canvasPanel = canvasPanel;

        createUI();
    }

    public void createUI() {
        copyItem = new MenuItem("Copy");
        pasteItem = new MenuItem("Paste");
        duplicateItem = new MenuItem("Duplicate");
        lockItem = new MenuItem("Lock");
        unlockItem = new MenuItem("Unlock");
        deleteItem = new MenuItem("Delete");

        copyItem.setOnAction(e -> canvasPanel.setCopiedDrawable(canvasPanel.getSelectedDrawable()));
        pasteItem.setOnAction(e -> canvasPanel.pasteCopiedDrawable(x, y));

        duplicateItem.setOnAction(e -> {
            canvasPanel.setCopiedDrawable(canvasPanel.getSelectedDrawable());
            canvasPanel.pasteCopiedDrawable(canvasPanel.getCopiedDrawable().getX() + 100, canvasPanel.getCopiedDrawable().getY() + 100);
        });

        deleteItem.setOnAction(e -> deleteDrawableItem());
        lockItem.setOnAction(e -> canvasPanel.getSelectedDrawable().lockDrawable());
        unlockItem.setOnAction(e -> canvasPanel.getSelectedDrawable().unlockDrawable());

        this.getItems().addAll(copyItem, pasteItem, duplicateItem, lockItem, unlockItem, deleteItem);
    }

    public void deleteDrawableItem() {
        Drawable drawable = canvasPanel.getSelectedDrawable();

        canvasPanel.remove(drawable);
    }

    public void display(double screenX, double screenY, double canvasX, double canvasY) {
        this.x = canvasX;
        this.y = canvasY;
        this.show(canvasPanel.getScene().getWindow(), screenX, screenY);
    }

}
