module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.app to javafx.fxml;
    exports org.example.app;
}