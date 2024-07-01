package Clases;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.util.ArrayList;

public class Condicional extends Figura{
    public ArrayList<Diagrama> diagrama_false;
    public Vertice vertice_conexion_false;

    public Condicional(String contenido, Vertice vertice_direccion, Vertice vertice_conexion_true,  Vertice vertice_conexion_false,Arista dimension,
                       ArrayList<Diagrama> diagrama_false, int numero_identificador) {
        super(contenido, vertice_direccion, vertice_conexion_true, dimension, numero_identificador);
        this.diagrama_false = diagrama_false;
    }

    public static void dibujar(double y, Canvas canvas, Condicional figura, AnchorPane panel_Diagrama, double separacion_figurasFalso){
        String finalTexto = figura.getContenido();

        double width = canvas.getWidth()/2;
        double height = canvas.getHeight()/2;
        double size = Math.max(width, height); // +40

        //posicion de la figura en relacion al AnchorPane
        double centerX = (panel_Diagrama.getMinWidth() / 2) - canvas.getWidth()/4;

        canvas.setLayoutX(centerX);
        canvas.setLayoutY(y);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // puntos del rombo
        double[] xPoints = {canvas.getWidth()/4, canvas.getWidth()/2-15, canvas.getWidth()/4, 15};
        double[] yPoints = {0, canvas.getHeight()/2, canvas.getHeight(), canvas.getHeight()/2};
        gc.setFill(VG.getColorRelleno());
        gc.fillPolygon(xPoints, yPoints, xPoints.length);
        gc.setStroke(VG.getColorBordes());
        gc.setLineWidth(VG.getTamaño_Lbordes());
        gc.strokePolygon(xPoints, yPoints, 4);

        // Escribir el texto en el centro del rombo
        gc.setFont(VG.getFont());
        gc.setFill(VG.getColorTexto());
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(finalTexto, canvas.getWidth()/4 , canvas.getHeight()/2);

        //Caracter T
        gc.setLineWidth(VG.getTamaño_Lbordes());
        gc.setStroke(VG.getColorRelleno());
        gc.strokeLine(30, canvas.getHeight()/2+25, 40, canvas.getHeight()/2+25);
        gc.strokeLine(35, canvas.getHeight()/2+25,35, canvas.getHeight()/2+35);

        //Caracter F
        gc.strokeLine(canvas.getWidth()/2-15, canvas.getHeight()/2-30 , canvas.getWidth()/2-8, canvas.getHeight()/2-30);
        gc.strokeLine(canvas.getWidth()/2-15, canvas.getHeight()/2-25 , canvas.getWidth()/2-8, canvas.getHeight()/2-25);
        gc.strokeLine(canvas.getWidth()/2-15, canvas.getHeight()/2-30, canvas.getWidth()/2-15, canvas.getHeight()/2-18);

        //Linea de conexiones false
        gc.setLineWidth(VG.getTamaño_Lconexiones()+3);
        gc.strokeLine(canvas.getWidth()/2-15, canvas.getHeight()/2, canvas.getWidth()/2-15+separacion_figurasFalso, canvas.getHeight()/2);
        gc.strokeLine(canvas.getWidth()/2-15+separacion_figurasFalso, canvas.getHeight()/2, canvas.getWidth()/2-15+separacion_figurasFalso, canvas.getHeight());

        //asignar punto de conexion falso
        figura.setVertice_conexion_false(new Vertice(canvas.getWidth()/2-15+separacion_figurasFalso, canvas.getHeight()));

        //MOVIMIENTO_FIGURA----------------------------------------------------
        canvas.setOnMousePressed(event -> {
            // Registrar las coordenadas del mouse en relación con la esquina superior izquierda de la figura
            VG.setPreviousX(event.getSceneX());
            VG.setPreviousY(event.getSceneY());
        });

        canvas.setOnMouseDragged(event -> {
            VG.getBasurero().setVisible(true);
            // Calcular el desplazamiento del mouse desde la última posición
            double deltaX = event.getSceneX() - VG.getPreviousX();
            double deltaY = event.getSceneY() - VG.getPreviousY();

            // Calcular las nuevas coordenadas para la figura basadas en el desplazamiento del mouse
            double newX = canvas.getLayoutX() + deltaX;
            double newY = canvas.getLayoutY() + deltaY;

            // Establecer las nuevas coordenadas de la figura
            canvas.setLayoutX(newX);
            canvas.setLayoutY(newY);

            // Actualizar la posición anterior del cursor
            VG.setPreviousX(event.getSceneX());
            VG.setPreviousY(event.getSceneY());
        });

        canvas.setOnMouseReleased(event -> {
            double releaseX = event.getSceneX();
            double releaseY = event.getSceneY();
            Bounds basureroBounds = VG.getBasurero().localToScene(VG.getBasurero().getBoundsInLocal());

            // Verificar si las coordenadas del evento están dentro de los límites del Pane Basurero
            if (basureroBounds.contains(releaseX, releaseY)) {
                panel_Diagrama.getChildren().remove(canvas);
            }
            VG.getBasurero().setVisible(false);
        });
        //ESCRITURA_FIGURA----------------------------------------------------
        canvas.setOnMouseClicked(event -> {
            VG.setClickCount(VG.getClickCount() + 1);
            // Si se ha dado doble clic
            if (VG.getClickCount() == 2) {
                // Restablecer el contador
                VG.setClickCount(0);
                editar(canvas, figura,panel_Diagrama,separacion_figurasFalso);
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    VG.setClickCount(0);
                }));
                timeline.play();
            }
        });
    }

    public static void editar(Canvas canvas, Condicional figura, AnchorPane panel_Diagrama, double separacion_figurasFalso) {
        // Crea el campo de texto para editar el nombre
        TextField textContenido = new TextField();
        textContenido.setOpacity(0.0);
        textContenido.setDisable(true);

        // Configura el campo de texto
        double size = Math.max(figura.getDimenciones().getAncho() / 2, figura.getDimenciones().getAlto() / 2);
        double currentX = canvas.getLayoutX();
        double currentY = canvas.getLayoutY();
        textContenido.setOpacity(1.0);
        textContenido.setDisable(false);
        textContenido.getStyleClass().add("Contenido_edit");
        textContenido.setLayoutX(currentX-40);
        textContenido.setLayoutY(currentY);
        textContenido.setMinWidth(size);
        textContenido.setMinHeight(size);
        textContenido.setText(figura.getContenido());
        textContenido.setStyle("-fx-text-fill: black;");

        // Agrega el campo de texto al panel
        panel_Diagrama.getChildren().add(textContenido);

        // Agrega evento de tecla para actualizar el contenido al presionar Enter
        textContenido.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                // Actualiza el nombre de la figura
                figura.setContenido(textContenido.getText());

                // Redibuja la figura con el nuevo nombre en las mismas coordenadas
                dibujar(currentY, canvas, figura, panel_Diagrama,separacion_figurasFalso);

                // Elimina el campo de texto del panel
                panel_Diagrama.getChildren().remove(textContenido);
                // Deshabilita la edición del contenido
                textContenido.clear();
                textContenido.setOpacity(0.0);
                textContenido.setDisable(true);
            }
        });
    }

    public Vertice getVertice_conexion_false() {
        return vertice_conexion_false;
    }

    public void setVertice_conexion_false(Vertice vertice_conexion_false) {
        this.vertice_conexion_false = vertice_conexion_false;
    }
}
