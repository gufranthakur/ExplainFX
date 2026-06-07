package explainfx.menus;

import explainfx.drawables.Drawable;
import explainfx.panels.CanvasPanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class DrawableMenu extends ContextMenu {

    private CanvasPanel canvasPanel;
    private MenuItem copyItem, duplicateItem, lockItem, unlockItem, deleteItem;

    public DrawableMenu(CanvasPanel canvasPanel) {
        this.canvasPanel = canvasPanel;

        createUI();
    }

    public void createUI() {
        copyItem = new MenuItem("Copy");
        duplicateItem = new MenuItem("Duplicate");
        lockItem = new MenuItem("Lock");
        unlockItem = new MenuItem("Unlock");
        deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> deleteDrawableItem());

        this.getItems().addAll(copyItem, duplicateItem, lockItem, unlockItem, deleteItem);
    }

    public void deleteDrawableItem() {
        Drawable drawable = canvasPanel.getSelectedDrawable();

        canvasPanel.remove(drawable);
    }

    public void display(double x, double y) {
        this.show(canvasPanel.getScene().getWindow(), x, y);
    }

}
