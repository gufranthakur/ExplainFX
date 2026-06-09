package explainfx.panels;

import explainfx.ExplainFX;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ControlPanel extends VBox {

    private ExplainFX explainFX;

    private HBox toolbox;
    private HBox propertyBox;

    private Button normalButton, markerButton , textButton, imageButton, squareButton, circleButton, arrowButton;
    private Slider sizeSlider;
    private Label sliderLabel;
    private Button colorButton;

    public ControlPanel(ExplainFX explainFX) {
        this.explainFX = explainFX;

        this.setStyle("-fx-background-color: #141414;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;");
        this.setMaxWidth(500);

        createUI();
        createListeners();
        addComponents();
    }

    public void createUI() {
        toolbox = new HBox(10);
        propertyBox = new HBox(5);

        normalButton = createIconButton("/cursor.png");
        markerButton = createIconButton("/marker.png");
        textButton = createIconButton("/text.png");
        imageButton = createIconButton("/image.png");
        squareButton = createIconButton("/square.png");
        circleButton = createIconButton("/circles.png");
        colorButton = createIconButton("/color.png");

        sizeSlider = new Slider(2, 15, 2);
        sizeSlider.setShowTickLabels(true);
        sizeSlider.setShowTickMarks(true);
        sizeSlider.setMajorTickUnit(2);
        sliderLabel = new Label("Size");
    }

    private void createListeners() {

        normalButton.setOnAction(e -> explainFX.getCanvasPanel().setDrawableState(CanvasPanel.DrawableState.NONE));

        markerButton.setOnAction(e -> {
            explainFX.getCanvasPanel().setDrawableState(CanvasPanel.DrawableState.STROKE);
        });


        textButton.setOnAction(e -> {
            explainFX.getCanvasPanel().setDrawableState(CanvasPanel.DrawableState.TEXT);
        });

        imageButton.setOnAction(e -> {
            explainFX.getCanvasPanel().setDrawableState(CanvasPanel.DrawableState.IMAGE);
        });

        squareButton.setOnAction(e -> {
            explainFX.getCanvasPanel().setDrawableState(CanvasPanel.DrawableState.SHAPE_SQUARE);
        });

        circleButton.setOnAction(e -> {
            explainFX.getCanvasPanel().setDrawableState(CanvasPanel.DrawableState.SHAPE_CIRCLE);
        });

        sizeSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
            explainFX.getCanvasPanel().setDrawableSize(newValue.intValue());
        }));

        colorButton.setOnAction(e -> {
            ColorPicker picker = new ColorPicker(Color.WHITE);

            Dialog<Color> dialog = new Dialog<>();
            dialog.setTitle("Pick a Color");
            dialog.getDialogPane().setContent(picker);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.setResultConverter(btn -> {
                if (btn == ButtonType.OK) return picker.getValue();
                return null;
            });

            dialog.showAndWait().ifPresent(color -> {
                explainFX.getCanvasPanel().setDrawableColor(color);
            });
        });
    }

    public void addComponents() {
        toolbox.setAlignment(Pos.CENTER);
        toolbox.getChildren().add(normalButton);
        toolbox.getChildren().add(markerButton);
        toolbox.getChildren().add(textButton);
        toolbox.getChildren().add(imageButton);
        toolbox.getChildren().add(squareButton);
        toolbox.getChildren().add(circleButton);

        propertyBox.getChildren().add(sliderLabel);
        propertyBox.getChildren().add(sizeSlider);
        propertyBox.setAlignment(Pos.CENTER);

        propertyBox.getChildren().add(new Label("Color: "));
        propertyBox.getChildren().add(colorButton);

        this.setPadding(new Insets(20, 20, 20, 20));
        this.getChildren().add(toolbox);
        this.getChildren().add(propertyBox);
    }

    public Button createIconButton(String iconPath) {
        Button button = new Button();
        button.setMaxWidth(50);
        button.setMaxHeight(50);

        ImageView icon = new ImageView(new Image(String.valueOf(getClass().getResource(iconPath))));
        icon.setFitWidth(35);
        icon.setFitHeight(35);

        button.setGraphic(icon);

        return button;
    }

}
