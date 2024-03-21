package org.example.app;
import Clases.Diagrama;
import Clases.Persistencia;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("interfaz.fxml"));
        Parent root = fxmlLoader.load();
        AppController appController = fxmlLoader.getController();

        //Dimensiones panel inicial
        double initialWidth = 740;
        double initialHeight = 1500;
        appController.ajustar_ScrollPane(initialWidth, initialHeight);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Icono_PP_white.png")));

        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                double screenWidth = screenBounds.getWidth();
                double screenHeight = screenBounds.getHeight();
                appController.ajustar_ScrollPane(screenWidth, screenHeight);
                appController.rellenarImagen();
            } else {
                appController.ajustar_ScrollPane(740, 1500);
            }
        });

        stage.getIcons().add(image);
        stage.setTitle("DiagramFlex");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private static Parent loadFXMl(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        Persistencia persi = new Persistencia();
        Diagrama ins = new Diagrama();
        ins.setList_elementos(persi.deserializar(ins.getList_elementos()));
        launch();
    }
}