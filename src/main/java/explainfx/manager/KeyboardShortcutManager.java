package explainfx.manager;

import explainfx.ExplainFX;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class KeyboardShortcutManager {

    private ExplainFX explainFX;

    private KeyCombination saveCombination, loadCombination, undoCombination, redoCombination;

    public KeyboardShortcutManager(ExplainFX explainFX) {
        this.explainFX = explainFX;

        setupShortcuts();
        addShortcuts();
    }

    private void setupShortcuts() {
        saveCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
        loadCombination = new  KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN);
        undoCombination = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);
        redoCombination = new  KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN);
    }

    private void addShortcuts() {
        Scene rootScene = explainFX.getRootScene();


        rootScene.getAccelerators().put(saveCombination, () -> explainFX.getDataManager().saveData(explainFX.getCanvasPanel().getDrawables()));
        rootScene.getAccelerators().put(loadCombination, () -> explainFX.getDataManager().loadData(explainFX.getCanvasPanel()));
        rootScene.getAccelerators().put(undoCombination, () -> System.out.println("UNDO"));
        rootScene.getAccelerators().put(redoCombination, () -> System.out.println("REDO"));
    }

}
