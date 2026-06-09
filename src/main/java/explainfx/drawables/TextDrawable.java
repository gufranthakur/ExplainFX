package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Optional;

public class TextDrawable extends Drawable {

    private Text text;
    private CanvasPanel canvasPanel;

    public TextDrawable(CanvasPanel canvasPanel, double x, double y) {
        super(canvasPanel, x, y, 0, 0);

        this.canvasPanel = canvasPanel;

        this.setLayoutX(x);
        this.setLayoutY(y);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter text");
        dialog.setHeaderText(null);
        dialog.setContentText("Text: ");

        Optional<String> input = dialog.showAndWait();
        String userInput = input.get();

        text = new Text(userInput);
        this.getChildren().add(text);

        this.setOnMouseEntered(e -> {
            text.setStroke(hoveredColor);
            canvasPanel.setSelectedDrawable(this);
        });

        this.setOnMouseExited(e -> {
            text.setStroke(drawableColor);
            canvasPanel.setSelectedDrawable(null);
        });
    }

    public TextDrawable(CanvasPanel canvasPanel, double x, double y, String copiedText) {
        super(canvasPanel, x, y, 0, 0);
        this.canvasPanel = canvasPanel;

        this.setLayoutX(x);
        this.setLayoutY(y);


        text = new Text(copiedText);
        this.getChildren().add(text);
    }

    public TextDrawable duplicate(double x, double y) {
        return new TextDrawable(canvasPanel, x, y, text.getText());
    }



}