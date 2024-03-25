package org.example.app;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.io.IOException;

public class AppController {
    @FXML
    AnchorPane panel_Diagrama;
    @FXML
    ScrollPane panel_contenedor;
    @FXML
    public void initialize() throws IOException {
        fondoCuadriculado();
    }


    @FXML
    protected void fondoCuadriculado() {
        Image image = new Image("C:\\Users\\diego\\IdeaProjects\\pp_pseint\\demo\\src\\main\\resources\\org\\example\\app\\fondoCuadriculado.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        panel_Diagrama.setBackground(background);
        panel_Diagrama.setMinSize(740, 1500);
    }

    public void ajustar_ScrollPane(double ancho, double largo){
        panel_contenedor.setMinSize(ancho,largo);
    }
    public void rellenarImagen() {
        double scrollPaneWidth = panel_contenedor.getWidth();
        double scrollPaneHeight = panel_contenedor.getHeight();

        // Configura la imagen para mantener su ascpecto luego del ajuste al tamaño(En proceso...)
        if (scrollPaneWidth > scrollPaneHeight) {
            panel_Diagrama.getChildren().forEach(node -> {
                if (node instanceof ImageView) {
                    ImageView imageView = (ImageView) node;
                    imageView.setFitWidth(scrollPaneWidth);
                    imageView.setFitHeight(-1);
                }
            });
        } else {
            panel_Diagrama.getChildren().forEach(node -> {
                if (node instanceof ImageView) {
                    ImageView imageView = (ImageView) node;
                    imageView.setFitWidth(-1);
                    imageView.setFitHeight(scrollPaneHeight);
                }
            });
        }
    }
}