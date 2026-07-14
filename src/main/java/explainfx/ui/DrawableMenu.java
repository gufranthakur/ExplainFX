package explainfx.ui;

import explainfx.drawables.Drawable;
import explainfx.panels.CanvasPanel;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class DrawableMenu extends ContextMenu {

    private double x, y;

    private CanvasPanel canvasPanel;
    private MenuItem copyItem, pasteItem, duplicateItem, lockItem, unlockItem, saveItem, loadItem, deleteItem, backItem;

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
        saveItem = new MenuItem("Save");
        loadItem = new MenuItem("Load");
        deleteItem = new  MenuItem("Delete");
        deleteItem = new MenuItem("Delete");
        backItem = new MenuItem("Back");

        copyItem.setOnAction(e -> canvasPanel.setCopiedDrawable(canvasPanel.getSelectedDrawable()));
        pasteItem.setOnAction(e -> canvasPanel.pasteCopiedDrawable(x, y));

        duplicateItem.setOnAction(e -> {
            canvasPanel.setCopiedDrawable(canvasPanel.getSelectedDrawable());
            if (canvasPanel.getCopiedDrawable() == null) return;
            canvasPanel.pasteCopiedDrawable(canvasPanel.getCopiedDrawable().getX() + 100, canvasPanel.getCopiedDrawable().getY() + 100);
        });

        saveItem.setOnAction(e -> canvasPanel.getExplainFX().getDataManager().saveData(canvasPanel.getDrawables()));
        loadItem.setOnAction(e -> canvasPanel.explainFX.getDataManager().loadData(canvasPanel));

        deleteItem.setOnAction(e -> deleteDrawableItem());
        lockItem.setOnAction(e -> canvasPanel.getSelectedDrawable().lockDrawable());
        unlockItem.setOnAction(e -> canvasPanel.getSelectedDrawable().unlockDrawable());

        this.getItems().addAll(copyItem, pasteItem, duplicateItem,
                new SeparatorMenuItem(),
                lockItem, unlockItem,
                new  SeparatorMenuItem(),
                saveItem, loadItem,
                new SeparatorMenuItem(),
                deleteItem,
                new SeparatorMenuItem(),
                backItem);

        this.showingProperty().addListener((obs, wasShowing, isShowing) -> {
            if (isShowing) {
                this.getScene().setOnMouseExited(e -> {
                    canvasPanel.requestFocus();
                    this.hide();
                });
            }
        });


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
