package org.example.app;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.io.IOException;
import javafx.scene.input.MouseEvent;

public class AppController {
    @FXML
    AnchorPane panel_Diagrama;
    @FXML
    ScrollPane panel_contenedor;
    @FXML
    private double initialX;
    @FXML
    private double initialY;

    @FXML
    public void initialize() throws IOException {
        fondoCuadriculado();
        Image image1 = new Image("C:\\Users\\Renatto\\IdeaProjects\\pp_pseint\\demo\\src\\main\\resources\\org\\example\\app\\diagrama_1.png");
        diagrama_1.setImage(image1);
        Image image2 = new Image("C:\\Users\\Renatto\\IdeaProjects\\pp_pseint\\demo\\src\\main\\resources\\org\\example\\app\\diagrama_2.png");
        diagrama_2.setImage(image2);
        Image image3 = new Image("C:\\Users\\Renatto\\IdeaProjects\\pp_pseint\\demo\\src\\main\\resources\\org\\example\\app\\diagrama_3.png");
        diagrama_3.setImage(image3);
        Image image4 = new Image("C:\\Users\\Renatto\\IdeaProjects\\pp_pseint\\demo\\src\\main\\resources\\org\\example\\app\\diagrama_4.png");
        diagrama_4.setImage(image4);
    }

    //--------------------------------------------------
    @FXML
    private ImageView diagrama_1;
    @FXML
    private ImageView diagrama_2;
    @FXML
    private ImageView diagrama_3;
    @FXML
    private ImageView diagrama_4;

    @FXML
    private void onMousePressed(MouseEvent event) {
        initialX = event.getSceneX();
        initialY = event.getSceneY();

    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        double deltaX = event.getSceneX() - initialX;
        double deltaY = event.getSceneY() - initialY;

        ImageView sourceDiagram = (ImageView) event.getSource();
        //ImageView copyDiagram = new ImageView(sourceDiagram.getImage());
        sourceDiagram.setLayoutX(sourceDiagram.getLayoutX() + deltaX);
        sourceDiagram.setLayoutY(sourceDiagram.getLayoutY() + deltaY);

        initialX = event.getSceneX();
        initialY = event.getSceneY();
    }

    @FXML
    private void onMouseReleased(MouseEvent event) {
        // Agregar accciones adicionales
    }

    //--------------------------------------------------

    @FXML
    protected void fondoCuadriculado() {
        Image image = new Image("C:\\Users\\Renatto\\IdeaProjects\\pp_pseint\\demo\\src\\main\\resources\\org\\example\\app\\fondoCuadriculado.jpg");
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