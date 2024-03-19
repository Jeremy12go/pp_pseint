package org.example.app;
import Clases.Diagrama;
import Clases.Persistencia;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("interfaz.fxml"));
        scene = new Scene(fxmlLoader.load(), 900, 600);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Icono_PP_white.png")));
        stage.getIcons().add(image);
        stage.setTitle("DiagramFlex");
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXMl(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    static void setRoot(String fxml) throws IOException{
        scene.setRoot(loadFXMl(fxml));
    }
    public static void main(String[] args) {
        Persistencia persi = new Persistencia();
        Diagrama ins = new Diagrama();
        ins.setList_elementos(persi.deserializar(ins.getList_elementos()));
        launch();
    }
}