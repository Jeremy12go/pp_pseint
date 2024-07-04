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
import java.util.Objects;

public class Mientras extends Figura{
    public ArrayList<String> contenido_validado;

    public Mientras(String contenido, Vertice vertice_direccion, Vertice vertice_conexion, Arista dimension, ArrayList<String> contenido_validado,int numero_identificador) {
        super(contenido, vertice_direccion, vertice_conexion, dimension, numero_identificador);
        this.contenido_validado = contenido_validado;
    }

    public static void dibujo(String texto, double x, double y, Canvas canvas, Figura figura, AnchorPane panel_Diagrama){
        // Crear un objeto Text para calcular el ancho del texto
        if(Objects.equals(texto, "") || Objects.equals(texto, " ") || Objects.equals(texto, "  ") || Objects.equals(texto, "   ")){texto= " Mientras ";}
        String finalTexto = texto;

        javafx.scene.text.Text text = new javafx.scene.text.Text(finalTexto);

        double width = figura.getDimenciones().getAncho()/2;
        double height = figura.getDimenciones().getAlto()/2;
        double size = Math.max(width, height);//+40

        //posicion de la figura en relacion al AnchorPane
        double diferencia = figura.getDimenciones().getAncho() / 2;
        canvas.setLayoutX((panel_Diagrama.getMinWidth() / 2) - diferencia + 42.5);
        double centerX = (panel_Diagrama.getMinWidth() / 2) - diferencia + 42.5;
        canvas.setLayoutY(y+50);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Calcular los puntos del rombo
        double[] xPoints = {size / 2, size, size / 2, 0};
        double[] yPoints = {0, size / 2, size, size / 2};
        gc.setFill(VG.getColorRelleno());
        gc.fillPolygon(xPoints, yPoints, 4);
        gc.setStroke(VG.getColorBordes());
        gc.setLineWidth(VG.getTamaño_Lbordes());
        gc.strokePolygon(xPoints, yPoints, 4);

        // Escribir el texto en el centro del rombo
        gc.setFont(VG.getFont());
        gc.setFill(VG.getColorTexto());
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(finalTexto, size/2 , (size / 2)+3); // Ajustar la posición vertical

        double startX = x - size / 2; // Coordenada X del extremo superior izquierdo del rombo
        double startY = y - size / 2; // Coordenada Y del extremo superior izquierdo del rombo
        double startXLeft = centerX - size + 10; // Punto de inicio en el lado izquierdo del rombo

        /*
        Canvas conectorIzquierda = crear_canvasLineaIzquierda(startXLeft, startY+110, 50);
        Canvas conectorArriba = crear_canvasLineaArriba(startXLeft,startY+120, 40);
        Canvas conectorDerecha = crear_canvasLineaDerecha(startXLeft,startY+70, 80);

        panel_Diagrama.getChildren().addAll(conectorIzquierda,conectorArriba,conectorDerecha);
        */
        // Calcular el punto de inicio de la flecha en el borde izquierdo de la figura
        double startArrowX = startX;
        double startArrowY = startY + size / 2;

        // Calcular el punto final de la flecha en la parte superior
        double endArrowX = startX + size / 2;
        double endArrowY = startY;

        // Dibujar la línea principal de la flecha
        gc.strokeLine(startArrowX, startArrowY, endArrowX, endArrowY);

        // Calcular las coordenadas de la punta de la flecha
        double arrowHeadSize = 10; // Tamaño de la punta de la flecha
        double arrowAngle = Math.atan2(endArrowY - startArrowY, endArrowX - startArrowX);
        double arrowHeadX1 = endArrowX - arrowHeadSize * Math.cos(arrowAngle - Math.PI / 6);
        double arrowHeadY1 = endArrowY - arrowHeadSize * Math.sin(arrowAngle - Math.PI / 6);
        double arrowHeadX2 = endArrowX - arrowHeadSize * Math.cos(arrowAngle + Math.PI / 6);
        double arrowHeadY2 = endArrowY - arrowHeadSize * Math.sin(arrowAngle + Math.PI / 6);

        // Dibujar la punta de la flecha
        gc.strokeLine(endArrowX, endArrowY, arrowHeadX1, arrowHeadY1);
        gc.strokeLine(endArrowX, endArrowY, arrowHeadX2, arrowHeadY2);

        // Calcular el punto de inicio de la flecha en el borde derecho de la figura
        double startArrowRightX = startX + size;
        double startArrowRightY = startY + size / 2;

        // Calcular el punto final de la flecha en la parte inferior
        double endArrowBottomX = startX + size / 2;
        double endArrowBottomY = startY + size;

        // Dibujar la línea principal de la flecha
        gc.strokeLine(startArrowRightX, startArrowRightY, endArrowBottomX, endArrowBottomY);

        // Calcular las coordenadas de la punta de la flecha
        arrowHeadSize = 10; // Tamaño de la punta de la flecha
        arrowAngle = Math.atan2(endArrowBottomY - startArrowRightY, endArrowBottomX - startArrowRightX);
        arrowHeadX1 = endArrowBottomX - arrowHeadSize * Math.cos(arrowAngle - Math.PI / 6);
        arrowHeadY1 = endArrowBottomY - arrowHeadSize * Math.sin(arrowAngle - Math.PI / 6);
        arrowHeadX2 = endArrowBottomX - arrowHeadSize * Math.cos(arrowAngle + Math.PI / 6);
        arrowHeadY2 = endArrowBottomY - arrowHeadSize * Math.sin(arrowAngle + Math.PI / 6);

        // Dibujar la punta de la flecha
        gc.strokeLine(endArrowBottomX, endArrowBottomY, arrowHeadX1, arrowHeadY1);
        gc.strokeLine(endArrowBottomX, endArrowBottomY, arrowHeadX2, arrowHeadY2);

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
        TextField textContenido = new TextField();
        textContenido.setOpacity(0.0);
        textContenido.setDisable(true);

        canvas.setOnMouseClicked(event -> {
            VG.setClickCount(VG.getClickCount()+1);
            // Si se ha dado doble clic
            if (VG.getClickCount() == 2) {
                // Restablecer el contador
                VG.setClickCount(0);
                edicion(canvas,figura,panel_Diagrama);
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    VG.setClickCount(0);
                }));
                timeline.play();
            }
        });
    }

    public static void edicion(Canvas canvas, Figura figura, AnchorPane panel_Diagrama){
        TextField textContenido = new TextField();
        double currentX = canvas.getLayoutX();
        double currentY = canvas.getLayoutY();

        double width = figura.getDimenciones().getAncho()/2;
        double height = figura.getDimenciones().getAlto()/2;
        double size = Math.max(width, height);//+40

        // Habilitar la edicion del contenido
        textContenido.setOpacity(1.0);
        textContenido.setDisable(false);
        textContenido.getStyleClass().add("Contenido_edit");
        textContenido.setLayoutX(currentX-40); // Ajustar según tus necesidades
        textContenido.setLayoutY(currentY); // Ajustar según tus necesidades
        textContenido.setMinWidth(size); // Ajustar según tus necesidades
        textContenido.setMinHeight(size); // Ajustar según tus necesidades
        textContenido.setText(figura.getContenido());
        textContenido.setStyle("-fx-text-fill: black;");

        panel_Diagrama.getChildren().add(textContenido);

        // Agregar evento de tecla para actualizar el contenido al presionar Enter
        textContenido.setOnKeyPressed(event_2 -> {
            if (event_2.getCode() == KeyCode.ENTER) {
                // Actualiza el nombre de la figura
                figura.setContenido(textContenido.getText());

                // Redibuja la figura con el nuevo nombre en las mismas coordenadas
                dibujo(figura.getContenido(),currentX,currentY-50, canvas, figura,panel_Diagrama);

                // Elimina el campo de texto del panel
                panel_Diagrama.getChildren().remove(textContenido);
                // Deshabilita la edicion del contenido
                textContenido.clear();
                textContenido.setOpacity(0.0);
                textContenido.setDisable(true);
            }
        });
    }
    public void validacion(String contenido){

    }
    public void operacion(String contenido_validado){

    }
}
