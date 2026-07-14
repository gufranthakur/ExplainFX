module explainfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.desktop;
    requires com.google.gson;


    opens explainfx.drawables to com.google.gson;
    exports explainfx.drawables;
    exports explainfx;
}