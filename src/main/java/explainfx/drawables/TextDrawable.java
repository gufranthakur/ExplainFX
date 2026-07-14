package explainfx.drawables;

import explainfx.panels.CanvasPanel;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Optional;

public class TextDrawable extends Drawable {

    private transient Text text;

    private String textContent;
    private String fontFamily;
    private double fontSize;

    public TextDrawable(String textdata, CanvasPanel canvasPanel, double x, double y) {
        super(canvasPanel, x, y, 0, 0);

        this.setLayoutX(x);
        this.setLayoutY(y);

        this.textContent = textdata;
        this.fontFamily = Font.getDefault().getFamily();
        this.fontSize = canvasPanel.drawableSize * 5;

        buildText();

        // Extra behavior only this constructor needs: double-click to edit
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Enter text");
                dialog.setHeaderText(null);
                dialog.setContentText("Text: ");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(updatedText -> {
                    textContent = updatedText;
                    text.setText(updatedText);
                });
            }
        });
    }

    public TextDrawable(CanvasPanel canvasPanel, double x, double y, String copiedText) {
        super(canvasPanel, x, y, 0, 0);

        this.setLayoutX(x);
        this.setLayoutY(y);

        this.textContent = copiedText;
        this.fontFamily = Font.getDefault().getFamily();
        this.fontSize = canvasPanel.drawableSize * 5;

        buildText();
    }

    private void buildText() {
        text = new Text(textContent);
        text.setFill(getDrawableColor());
        text.setFont(Font.font(fontFamily, fontSize));
        this.getChildren().add(text);

        this.setOnMouseEntered(e -> {
            text.setFill(hoveredColor);
            canvasPanel.setSelectedDrawable(this);
        });

        this.setOnMouseExited(e -> {
            text.setFill(getDrawableColor());
            canvasPanel.setSelectedDrawable(null);
        });
    }

    public void update(double width, double height) {
        int factor = (int) (width + height) / 30;
        fontSize = canvasPanel.drawableSize * factor;
        text.setFont(Font.font(fontFamily, fontSize));
    }

    @Override
    public void rebuildVisual(CanvasPanel canvasPanel) {
        wireHandlers(canvasPanel);
        setDrawableColor(hexToColor(getDrawableColorHex()));
        this.getChildren().clear();
        buildText();

        // Re-attach the double-click-to-edit behavior after rebuild
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Enter text");
                dialog.setHeaderText(null);
                dialog.setContentText("Text: ");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(updatedText -> {
                    textContent = updatedText;
                    text.setText(updatedText);
                });
            }
        });
    }

    public String getTextContent() {
        return textContent;
    }

    public TextDrawable duplicate(double x, double y) {
        return new TextDrawable(canvasPanel, x, y, textContent);
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public double getFontSize() {
        return fontSize;
    }
}