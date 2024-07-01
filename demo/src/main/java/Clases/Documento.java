package Clases;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Documento extends Figura {

    public Documento(String contenido, Vertice vertice_cordenada, Vertice vertice_conexion, Arista dimenciones,int numero_identificador) {
        super(contenido, vertice_cordenada, vertice_conexion, dimenciones,numero_identificador);
    }

    public void dibujo_documento(double posY, Canvas canvas, Figura figura, AnchorPane panel_Diagrama) {
        String finalTexto = figura.getContenido();
        javafx.scene.text.Text text = new javafx.scene.text.Text(figura.getContenido());

        double width = figura.getDimenciones().getAncho();
        double height = figura.getDimenciones().getAlto();
        double curveHeight = 20;

        //posicion de la figura en relacion al AnchorPane
        double diferencia = figura.getDimenciones().getAncho() / 2;
        canvas.setLayoutX((panel_Diagrama.getMinWidth() / 2) - diferencia+15);
        canvas.setLayoutY(posY + 58);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dibujar el cuerpo del documento (rectángulo)
        gc.setFill(VG.getColorRelleno());
        gc.setStroke(VG.getColorBordes());
        gc.setLineWidth(VG.getTamaño_Lbordes());

        // Dibujar las curvas en la parte inferior
        gc.beginPath();
        gc.moveTo(0, height - curveHeight);
        gc.quadraticCurveTo(width / 4, height, width / 2, height - curveHeight);
        gc.quadraticCurveTo((3 * width) / 4, height - (2 * curveHeight), width, height - curveHeight);
        gc.lineTo(width, 0);
        gc.lineTo(0, 0);
        gc.closePath();

        // Rellenar la figura completa
        gc.fill();

        // Dibujar el contorno del documento
        gc.stroke();

        //contenido
        gc.setStroke(VG.getColorTexto());
        gc.setFill(VG.getColorTexto());
        gc.setLineWidth(VG.getTamañoTxt());
        gc.setFont(VG.getFont());
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(finalTexto, width / 2, (height / 2)-20);

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
        // Declarar una variable para contar los clics
        canvas.setOnMouseClicked(event -> {
            VG.setClickCount(VG.getClickCount() + 1);
            if (VG.getClickCount() == 2) {
                // Restablecer el contador
                VG.setClickCount(0);
                edición_Documento(canvas,figura, panel_Diagrama);
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    VG.setClickCount(0);
                }));
                timeline.play();
            }
        });
    }

    public void edición_Documento(Canvas canvas, Figura figura, AnchorPane panel_Diagrama){
        TextField textContenido = new TextField();
        textContenido.setOpacity(0.0);
        textContenido.setDisable(true);

        double currentX = canvas.getLayoutX();
        double currentY = canvas.getLayoutY();

        // Tu código para habilitar la edición del contenido
        textContenido.setOpacity(1.0);
        textContenido.setDisable(false);
        panel_Diagrama.getChildren().add(textContenido);
        textContenido.getStyleClass().add("Contenido_edit");
        textContenido.setLayoutX(currentX+22.5); // Ajustar según tus necesidades
        textContenido.setLayoutY(currentY+4.5); // Ajustar según tus necesidades
        textContenido.setMinWidth(figura.getDimenciones().getAncho()-100); // Ajustar según tus necesidades
        textContenido.setMinHeight(figura.getDimenciones().getAlto()-20); // Ajustar según tus necesidades
        textContenido.setText(figura.getContenido());

        String pre_text = figura.getContenido();
        figura.setContenido("");
        limpiar_canvas(canvas);
        dibujo_documento(currentY-60, canvas,figura, panel_Diagrama);

        // Agregar evento de tecla para actualizar el contenido al presionar Enter
        textContenido.setOnKeyPressed(event_2 -> {
            if (event_2.getCode() == KeyCode.ENTER) {

                figura.setContenido(textContenido.getText());
                String new_text = textContenido.getText();
                double pre_dimension = figura.getDimenciones().getAncho();

                pre_dimension = figura.getDimenciones().getAncho();

                //recalculo de la dimensiones de la figura por contenido
                if(8*new_text.length()+25<=120){
                    figura.getDimenciones().setAncho(120);
                    canvas.setWidth(120);
                    textContenido.setMinWidth(120);
                }else{
                    figura.getDimenciones().setAncho(6*new_text.length()+25);
                    canvas.setWidth(6*new_text.length()+25);
                    textContenido.setMinWidth(canvas.getWidth()*0.7);
                }

                //editar posicion en relacion al largo(mitad del panel)
                double _diferencia_ = figura.getDimenciones().getAncho()/2;
                Vertice reajuste_v = new Vertice((panel_Diagrama.getMinWidth()/2)-_diferencia_,figura.getDimenciones().getAlto());
                figura.setVertice_conexion(reajuste_v);
                //figura.setContenido(textContenido.getText());

                //redibujo
                limpiar_canvas(canvas);
                dibujo_documento(currentY-60, canvas, figura, panel_Diagrama);
                textContenido.clear();
                panel_Diagrama.getChildren().remove(textContenido);
            }
        });
    }
}

