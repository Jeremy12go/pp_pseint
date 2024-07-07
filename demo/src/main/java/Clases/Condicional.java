package Clases;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Condicional extends Figura{
    public Diagrama diagrama_false;
    public Diagrama diagrama_true;
    public Vertice vertice_conexion_false;

    public Condicional(String contenido, Vertice vertice_direccion, Vertice vertice_conexion_true,  Vertice vertice_conexion_false,Arista dimension,
                       Diagrama diagrama_true, Diagrama diagrama_false, int numero_identificador) {
        super(contenido, vertice_direccion, vertice_conexion_true, dimension, numero_identificador);
        this.diagrama_true = diagrama_true;
        this.diagrama_false = diagrama_false;
    }

    public static void dibujar(double y, Canvas canvas, Condicional figura, AnchorPane panel_Diagrama, double separacion_figurasFalso){
        String finalTexto = figura.getContenido();

        double width = canvas.getWidth()/2;
        double height = canvas.getHeight()/2;
        double size = Math.max(width, height); // +40

        //posicion de la figura en relacion al AnchorPane

        canvas.setLayoutX((panel_Diagrama.getMinWidth() / 2) - canvas.getWidth()/4);
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
        gc.setLineWidth(VG.getTamaño_Lbordes()-1);
        gc.setStroke(VG.getColorRelleno());
        gc.strokeLine(30, canvas.getHeight()/2+25, 40, canvas.getHeight()/2+25);
        gc.strokeLine(35, canvas.getHeight()/2+25,35, canvas.getHeight()/2+35);

        //Caracter F
        gc.strokeLine(canvas.getWidth()/2-15, canvas.getHeight()/2-30 , canvas.getWidth()/2-8, canvas.getHeight()/2-30);
        gc.strokeLine(canvas.getWidth()/2-15, canvas.getHeight()/2-25 , canvas.getWidth()/2-8, canvas.getHeight()/2-25);
        gc.strokeLine(canvas.getWidth()/2-15, canvas.getHeight()/2-30, canvas.getWidth()/2-15, canvas.getHeight()/2-18);

        //Linea de conexiones false
        gc.setLineWidth(VG.getTamaño_Lconexiones()+3);
        gc.strokeLine(canvas.getWidth()/2-15, canvas.getHeight()/2, canvas.getWidth()-2, canvas.getHeight()/2);
        gc.strokeLine(canvas.getWidth()-2, canvas.getHeight()/2, canvas.getWidth()-2, canvas.getHeight());

        //asignar punto de conexion falso
        //figura.setVertice_conexion_false(new Vertice(x+(canvas.getWidth()/1.5)+28, canvas.getHeight()));


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
                System.out.println("s");
                //todo:revisar
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
                // Deshabilita la edicion del contenido
                textContenido.clear();
                textContenido.setOpacity(0.0);
                textContenido.setDisable(true);
            }
        });
    }

    public static void agregar_conectores_extra(Canvas canvas_Fcondicional, AnchorPane panel_Diagrama,int indice){
        //agregar conector extraInicial
        Canvas conectorCanvas = new Canvas(50,70);
        GraphicsContext gc = conectorCanvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN); // Cambia a tu color preferido
        gc.fillRect(0, 0, conectorCanvas.getWidth(), conectorCanvas.getHeight());

        //posicionamiento del canvas
        double layoutX = canvas_Fcondicional.getLayoutX()+canvas_Fcondicional.getWidth() - conectorCanvas.getWidth()/2-2.5;//(panel_Diagrama.getMinWidth() / 2) - conectorCanvas.getWidth() / 2;
        double layoutY = canvas_Fcondicional.getLayoutY()+canvas_Fcondicional.getHeight()-1; //20 Ajustar la posición Y del conector

        conectorCanvas.setLayoutX(layoutX);
        conectorCanvas.setLayoutY(layoutY);
        Figura.dibujar_flecha(conectorCanvas,conectorCanvas.getWidth()/2,0,-90,conectorCanvas.getWidth()+5);


        //agregar conector extraFinal
        Canvas conector2Canvas = new Canvas(220,20);
        GraphicsContext gc1 = conector2Canvas.getGraphicsContext2D();
        //gc1.setFill(Color.PINK); // Cambia a tu color preferido
        //gc1.fillRect(0, 0, conector2Canvas.getWidth(), conector2Canvas.getHeight());

        //posicionamiento del canvas
        layoutX = canvas_Fcondicional.getLayoutX()+canvas_Fcondicional.getWidth()/3-20;//(panel_Diagrama.getMinWidth() / 2) - conectorCanvas.getWidth() / 2;
        layoutY = 50+canvas_Fcondicional.getLayoutY()+canvas_Fcondicional.getHeight()-1; //20 Ajustar la posición Y del conector

        conector2Canvas.setLayoutX(layoutX);
        conector2Canvas.setLayoutY(layoutY);
        Figura.dibujar_flecha(conector2Canvas,conector2Canvas.getWidth(),conector2Canvas.getHeight()/2,180,conector2Canvas.getWidth()-10);

        //panel_Diagrama.getChildren().add(conectorCanvas);
        //panel_Diagrama.getChildren().add(conector2Canvas);

        //Diagrama.getIns().getList_orden().add(indice, conector2Canvas);
        //Diagrama.getIns().getList_orden().add(indice, conectorCanvas);
    }

    public void setVertice_conexion_false(Vertice vertice_conexion_false) {
        this.vertice_conexion_false = vertice_conexion_false;
    }

    public Vertice getVertice_conexion_false() {return vertice_conexion_false;}

    public Diagrama getDiagrama_true() {
        return diagrama_true;
    }

    public void setDiagrama_true(Diagrama diagrama_true) {
        this.diagrama_true = diagrama_true;
    }

    public Diagrama getDiagrama_false() {
        return diagrama_false;
    }

    public void setDiagrama_false(Diagrama diagrama_false) {
        this.diagrama_false = diagrama_false;
    }

}
