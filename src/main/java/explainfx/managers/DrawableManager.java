package explainfx.managers;

import explainfx.drawables.Drawable;

import java.util.ArrayList;

public class DrawableManager {

    public ArrayList<Drawable> drawables;

    public DrawableManager() {
        drawables = new ArrayList<>(20);
    }

    public void add(Drawable drawable) {
        drawables.add(drawable);
    }

    public void remove(Drawable drawable) {
        drawables.remove(drawable);
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }


}
