package org.example.app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

// IntelliJ IDEA 2024.1.1
// JDK 19.0.1

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("interfaz.fxml"));
        Parent root = fxmlLoader.load();
        AppController appController = fxmlLoader.getController();

        //Dimensiones panel contenedor inicial
        double initialWidth = 740;
        double initialHeight = 654;
        appController.ajustar_Panes(initialWidth, initialHeight);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Icono_PP_white.png")));

        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            appController.setMaximizar(true);
            //reajustar la posicion de las figuras el maximar la ventana
            if (newValue) {
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                double screenWidth = screenBounds.getWidth();
                double screenHeight = screenBounds.getHeight();
                appController.ajustar_Panes(screenWidth, screenHeight);
                //Reajuste de las dimensiones luego de cambiar las dimensiones de la ventana
                appController.fondoCuadriculado(screenWidth-110, screenHeight+500);
            } else {
                appController.ajustar_Panes(initialWidth, initialHeight);
                appController.fondoCuadriculado(initialWidth, initialHeight+500);
            }
        });
        stage.getIcons().add(image);
        stage.setTitle("DiagramFlex");
        stage.setScene(new Scene(root));
        URL estiloURL = getClass().getResource("style.css");
        if (estiloURL != null) {
            root.getStylesheets().add(estiloURL.toExternalForm());
        } else {
            System.err.println("No se pudo encontrar el archivo de estilos 'estilos.css'");
        }
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

