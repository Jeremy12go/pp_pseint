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

import java.util.ArrayList;

public class Salida extends Figura{

    public Salida(String contenido, Vertice vertice_direccion, Vertice vertice_conexion, Arista dimension,int numero_identificador) {
        super(contenido, vertice_direccion, vertice_conexion, dimension, numero_identificador);
    }
    public static void dibujo(Canvas canvas, Figura figura, AnchorPane panel_Diagrama){
        // Calcular los otros vértices
        ArrayList<Vertice> vertices = Figura.calcular_vertices(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        int delta = 14; //inclinacion

        // Dibujar el paralelogramo
        gc.setLineWidth(VG.getTamaño_Lbordes());
        gc.setStroke(VG.getColorBordes());

        canvas.setLayoutX(figura.getVertice_conexion().getX() - 80);
        canvas.setLayoutY(figura.getVertice_conexion().getY() + 50);

        // Rellenar figura
        gc.setFill(VG.getColorRelleno());
        double[] xPoints = {vertices.get(0).getX() + figura.getDimenciones().getAncho()/delta, vertices.get(1).getX(),
                vertices.get(2).getX() - figura.getDimenciones().getAncho()/delta, vertices.get(3).getX()};
        double[] yPoints = {vertices.get(0).getY(), vertices.get(1).getY(), vertices.get(2).getY(), vertices.get(3).getY()};

        gc.fillPolygon(xPoints, yPoints, vertices.size());

        // Línea p1-p2
        gc.strokeLine(vertices.get(0).getX() + figura.getDimenciones().getAncho()/delta, vertices.get(0).getY(),
                vertices.get(1).getX(), vertices.get(1).getY());
        // Línea p2-p3
        gc.strokeLine(vertices.get(1).getX(), vertices.get(1).getY(),
                vertices.get(2).getX() - figura.getDimenciones().getAncho()/delta, vertices.get(2).getY());
        // Línea p3-p4
        gc.strokeLine(vertices.get(2).getX() - figura.getDimenciones().getAncho()/delta,
                vertices.get(2).getY(), vertices.get(3).getX(), vertices.get(3).getY());
        // Línea p4-p1
        gc.strokeLine(vertices.get(3).getX(), vertices.get(3).getY(),
                vertices.get(0).getX() + figura.getDimenciones().getAncho()/delta, vertices.get(0).getY());

        //dibujo flecha
        gc.setStroke(VG.getColorBordes());
        gc.setFill(VG.getColorBordes());

        dibujar_flecha(canvas, vertices.get(1).getX()-30, vertices.get(1).getY()*2-vertices.get(1).getY(), 45,10);

        //contenido
        gc.setLineWidth(VG.getTamañoTxt());
        gc.setFont(VG.getFont());
        gc.setStroke(VG.getColorTexto());
        gc.setFill(VG.getColorTexto());
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(figura.getContenido(), (figura.getDimenciones().getAncho()/ 2)+15, figura.getDimenciones().getAlto()-15);

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

        //editar contenido
        canvas.setOnMouseClicked(event -> {
            VG.setClickCount(VG.getClickCount() + 1);
            // Si se ha dado doble clic
            if (VG.getClickCount() == 2) {
                // Restablecer el contador
                VG.setClickCount(0);
                edición(canvas,figura,panel_Diagrama);
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    VG.setClickCount(0);
                }));
                timeline.play();
            }
        });

    }

    public static void edición(Canvas canvas, Figura figura, AnchorPane panel_Diagrama){
        TextField textContenido = new TextField();

        double _diferencia = figura.getDimenciones().getAncho()/2;
        textContenido.setOpacity(1.0);
        textContenido.setDisable(false);
        panel_Diagrama.getChildren().add(textContenido);
        textContenido.getStyleClass().add("Contenido_edit");
        textContenido.setLayoutX((panel_Diagrama.getMinWidth()/2)-_diferencia+60);
        textContenido.setLayoutY(canvas.getLayoutY()+25);//+24
        textContenido.setMinWidth(canvas.getWidth() / 1.5);
        textContenido.setMinHeight(canvas.getHeight() / 2);
        textContenido.setText(figura.getContenido());

        String pre_text = figura.getContenido();
        figura.setContenido("");
        limpiar_canvas(canvas);
        dibujo(canvas,figura,panel_Diagrama);

        textContenido.setOnKeyPressed(event_2 -> {
            if (event_2.getCode() == KeyCode.ENTER) {
                figura.setContenido(textContenido.getText());
                String new_text = textContenido.getText();
                double pre_dimension = figura.getDimenciones().getAncho();

                //recalculo de la dimensiones de la figura por contenido
                if(8*new_text.length()+25<=153){
                    figura.getDimenciones().setAncho(153);
                    canvas.setWidth(153);
                    textContenido.setMinWidth(153);
                }else{
                    figura.getDimenciones().setAncho(8*new_text.length()+25);
                    canvas.setWidth(8*new_text.length()+25);
                    textContenido.setMinWidth(canvas.getWidth()*0.7);
                }

                //TUVE QUE COMENTAR ESTO PARA QUE SE MANTENGA EN LA MISMA POS
                //editar posicion en relacion al largo(mitad del panel)
                //double _diferencia_ = figura.getDimenciones().getAncho()/2;
                //textContenido.setLayoutX((panel_Diagrama.getWidth()/2)-_diferencia_);
                //canvas.setLayoutX((panel_Diagrama.getWidth()/2)-_diferencia_);
                //Vertice reajuste_v = new Vertice((panel_Diagrama.getMinWidth()/2)-_diferencia_,figura.getDimenciones().getAlto());
                //figura.setVertice_conexion(reajuste_v);

                //redibujo
                limpiar_canvas(canvas);
                dibujo(canvas,figura,panel_Diagrama);
                textContenido.clear();
                panel_Diagrama.getChildren().remove(textContenido);
            }
        });
    }
}
