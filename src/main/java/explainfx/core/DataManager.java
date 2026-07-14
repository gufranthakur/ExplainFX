package explainfx.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import explainfx.ExplainFX;
import explainfx.drawables.*;
import explainfx.panels.CanvasPanel;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    private ExplainFX explainFX;
    public DataManager(ExplainFX explainFX) {
        this.explainFX = explainFX;
    }

    public void saveData(ArrayList<Drawable> drawables) {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Canvas");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ExplainFX", "*.efx"));

        chooser.setInitialFileName("canvas.efx");
        File file = chooser.showSaveDialog(explainFX.getStage());


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Map<String, Object>> saveList = new ArrayList<>();

        for (Drawable d : drawables) {
            Map<String, Object> map = new LinkedHashMap<>();

            if (d instanceof SquareDrawable sd) {
                map.put("type", "square");
                map.put("x", sd.getX());
                map.put("y", sd.getY());
                map.put("width", sd.getWidth());
                map.put("height", sd.getHeight());
                map.put("colorHex", sd.getDrawableColorHex());
                map.put("strokeWidth", sd.getStrokeWidth());
            }
            else if (d instanceof CircleDrawable cd) {
                map.put("type", "circle");
                map.put("x", cd.getX());
                map.put("y", cd.getY());
                map.put("width", cd.getWidth());
                map.put("height", cd.getHeight());
                map.put("colorHex", cd.getDrawableColorHex());
                map.put("strokeWidth", cd.getStrokeWidth());
            }
            else if (d instanceof StrokeDrawable strokeD) {
                map.put("type", "stroke");
                map.put("x", strokeD.getX());
                map.put("y", strokeD.getY());
                map.put("colorHex", strokeD.getDrawableColorHex());
                map.put("strokeWidth", strokeD.getStrokeWidth());
                map.put("points", strokeD.getPoints());
            }
            else if (d instanceof TextDrawable td) {
                map.put("type", "text");
                map.put("x", td.getX());
                map.put("y", td.getY());
                map.put("colorHex", td.getDrawableColorHex());
                map.put("textContent", td.getTextContent());
                map.put("fontFamily", td.getFontFamily());
                map.put("fontSize", td.getFontSize());
            }

            saveList.add(map);
        }

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(saveList, writer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void loadData(CanvasPanel canvasPanel) {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Canvas");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ExplainFX", "*.efx"));

        File file = chooser.showOpenDialog(explainFX.getStage());

        ArrayList<Drawable> drawables = new ArrayList<>();

        if (file == null) {
            System.out.println("NULL");
        }

        Gson gson = new Gson();

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
            List<Map<String, Object>> loaded = gson.fromJson(reader, listType);

            for (Map<String, Object> map : loaded) {
                String type = (String) map.get("type");
                double x = (double) (Double) map.get("x");
                double y = (double) (Double) map.get("y");

                switch (type) {
                    case "square" -> {
                        double width = (double) (Double) map.get("width");
                        double height = (double) (Double) map.get("height");

                        SquareDrawable sd = new SquareDrawable(canvasPanel, x, y, width, height);
                        sd.setDrawableColor(Color.web((String) map.get("colorHex")));
                        drawables.add(sd);
                    }

                    case "circle" -> {
                        double width = (double) (Double) map.get("width");
                        double height = (double) (Double) map.get("height");

                        CircleDrawable cd = new CircleDrawable(canvasPanel, x, y, width, height);
                        cd.setDrawableColor(Color.web((String) map.get("colorHex")));
                        drawables.add(cd);
                    }

                    case "stroke" -> {
                        StrokeDrawable strokeD = new StrokeDrawable(canvasPanel, x, y);
                        strokeD.setDrawableColor(Color.web((String) map.get("colorHex")));

                        @SuppressWarnings("unchecked")
                        List<Double> points = (List<Double>) map.get("points");

                        if (points != null) {
                            for (int i = 0; i + 1 < points.size(); i += 2) {
                                strokeD.getPolyline().getPoints().addAll(points.get(i), points.get(i + 1));
                            }
                        }
                        drawables.add(strokeD);
                    }

                    case "text" -> {
                        String textContent = (String) map.get("textContent");

                        TextDrawable td = new TextDrawable(textContent, canvasPanel, x, y);
                        td.setDrawableColor(Color.web((String) map.get("colorHex")));
                        drawables.add(td);
                    }

                    default -> System.out.println("Unknown drawable type in save file: " + type);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        explainFX.getCanvasPanel().placeLoadedDrawables(drawables);
    }

}
