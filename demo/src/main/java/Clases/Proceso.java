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
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.util.ArrayList;

public class Proceso extends Figura{
    public String contenido_valido;
    public ArrayList<String> operaciones;
    public ArrayList<String> operandos;

    public Proceso(String contenido, Vertice vertice_direccion, Vertice vertice_conexion, Arista dimension, String contenido_valido, ArrayList<String> operaciones, ArrayList<String> operandos,int numero_identificador) {
        super(contenido, vertice_direccion, vertice_conexion, dimension, numero_identificador);
        this.contenido_valido = contenido_valido;
        this.operaciones = operaciones;
        this.operandos = operandos;
    }

    public static void dibujo(double posY,Canvas canvas, Figura figura, AnchorPane panel_Diagrama) {
        String finalTexto = figura.getContenido();
        javafx.scene.text.Text text = new javafx.scene.text.Text(figura.getContenido());

        // Posicion de la figura en relación al AnchorPane
        canvas.setLayoutX((panel_Diagrama.getMinWidth() / 2) - canvas.getWidth()/2);
        canvas.setLayoutY(posY);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(VG.getTamaño_Lbordes());
        gc.setStroke(VG.getColorBordes());

        // Rellenar figura
        ArrayList<Vertice> vertices = Figura.calcular_vertices(canvas,0);
        gc.setFill(VG.getColorRelleno());
        double[] xPoints = {vertices.get(0).getX(), vertices.get(1).getX(),
                vertices.get(2).getX(), vertices.get(3).getX()};
        double[] yPoints = {vertices.get(0).getY(), vertices.get(1).getY(), vertices.get(2).getY(), vertices.get(3).getY()};

        gc.fillPolygon(xPoints, yPoints, vertices.size());

        gc.setFill(VG.getColorBordes());
        // Línea p1-p2
        gc.strokeLine(vertices.get(0).getX(), vertices.get(0).getY()+1, vertices.get(1).getX(), vertices.get(1).getY()+1);

        // Línea p2-p3
        gc.strokeLine(vertices.get(1).getX()-1, vertices.get(1).getY(), vertices.get(2).getX()-1, vertices.get(2).getY());

        // Línea p3-p4
        gc.strokeLine(vertices.get(2).getX(), vertices.get(2).getY()-1, vertices.get(3).getX(), vertices.get(3).getY()-1);

        // Línea p4-p1
        gc.strokeLine(vertices.get(3).getX()+1, vertices.get(3).getY(), vertices.get(0).getX()+1, vertices.get(0).getY());

        // Contenido
        gc.setLineWidth(VG.getTamañoTxt());
        gc.setFont(VG.getFont());
        gc.setStroke(VG.getColorTexto());
        gc.setFill(VG.getColorTexto());
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(figura.getContenido(), figura.getDimenciones().getAncho() / 2, figura.getDimenciones().getAlto() / 2);

        // MOVIMIENTO_FIGURA----------------------------------------------------
        canvas.setOnMousePressed(event -> {
            // Registrar las coordenadas del mouse en relación con la esquina superior izquierda de la figura
            VG.setPreviousX(event.getSceneX());
            VG.setPreviousY(event.getSceneY());
        });

        canvas.setOnMouseDragged(event -> {
            VG.getBasurero().setVisible(true);

            // Verificar si el arrastre del mouse está ocurriendo dentro del área del campo de texto
            if (!text.getBoundsInParent().contains(event.getX(), event.getY())) {
                // Calcular el desplazamiento del mouse desde la última posición
                double deltaX = event.getSceneX() - VG.getPreviousY();
                double deltaY = event.getSceneY() - VG.getPreviousY();

                // Calcular las nuevas coordenadas para la figura basadas en el desplazamiento del mouse
                double newX = canvas.getLayoutX() + deltaX;
                double newY = canvas.getLayoutY() + deltaY;

                // Establecer las nuevas coordenadas de la figura
                canvas.setLayoutX(newX);
                canvas.setLayoutY(newY);
            }

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
        // ESCRITURA_FIGURA----------------------------------------------------
        canvas.setOnMouseClicked(event -> {
            VG.setClickCount(VG.getClickCount() + 1);
            // Si se ha dado doble clic
            if (VG.getClickCount() == 2) {
                // Restablecer el contador
                VG.setClickCount(0);
                edicion(posY, canvas, figura, panel_Diagrama);
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    VG.setClickCount(0);
                }));
                timeline.play();
            }
        });
    }

    public static void edicion(double posY, Canvas canvas, Figura figura, AnchorPane panel_Diagrama){
        TextField textContenido = new TextField();
        textContenido.setOpacity(0.0);
        textContenido.setDisable(true);

        double currentX = canvas.getLayoutX();
        double currentY = canvas.getLayoutY();
        double size = Math.max(figura.getDimenciones().getAncho()/2, figura.getDimenciones().getAlto()/2)+20;

        // Habilitar la edicion del contenido
        textContenido.setOpacity(1.0);
        textContenido.setDisable(false);
        panel_Diagrama.getChildren().add(textContenido);
        textContenido.getStyleClass().add("Contenido_edit");
        textContenido.setLayoutX(currentX); // Ajustar según tus necesidades
        textContenido.setLayoutY(currentY+10); // Ajustar según tus necesidades
        textContenido.setMinWidth(size+(size*0.5)); // Ajustar según tus necesidades
        textContenido.setMinHeight(size); // Ajustar según tus necesidades
        textContenido.setText(figura.getContenido());

        String pre_text = figura.getContenido();
        figura.setContenido("");
        limpiar_canvas(canvas);
        dibujo(posY,canvas,figura,panel_Diagrama);

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
                    textContenido.setMinWidth(canvas.getWidth()*0.6);
                }

                //redibujo
                limpiar_canvas(canvas);
                dibujo(posY, canvas, figura, panel_Diagrama);
                textContenido.clear();
                panel_Diagrama.getChildren().remove(textContenido);
            }
        });
    }
}
