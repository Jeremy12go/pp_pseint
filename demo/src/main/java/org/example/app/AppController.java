package org.example.app;
import Clases.Arista;
import Clases.Figura;
import Clases.Vertice;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.io.IOException;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.shape.ArcType;
import javafx.scene.input.ClipboardContent;
import java.math.*;
import javafx.scene.image.ImageView;

public class AppController {
    @FXML
    AnchorPane panel_Diagrama;
    @FXML
    ScrollPane panel_contenedor;
    @FXML
    Pane panel_menu;
    @FXML
    Button btn_entrada;

    @FXML
    private double initialX;
    @FXML
    private double initialY;
    @FXML
    private double originalX;
    @FXML
    private double originalY;
    @FXML
    private ImageView figura_proceso;
    @FXML
    private ImageView figura_entrada_salida;
    @FXML
    private ImageView figura_condiconal;
    @FXML
    private ImageView figura_documento;
    @FXML
    private ImageView figura_procesoE;
    @FXML
    private ImageView figura_entrada_salidaE;
    @FXML
    private ImageView figura_condiconalE;
    @FXML
    private ImageView figura_documentoE;

    @FXML
    public void initialize() throws IOException {
        fondoCuadriculado(740,1500);
        figurasInicio_fin();

        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("figura_proceso.png")));
        figura_proceso.setImage(image1);
        figura_procesoE.setImage(image1);
        Image image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("figura_entrada_salida.png")));
        figura_entrada_salida.setImage(image2);
        figura_entrada_salidaE.setImage(image2);
        Image image3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("figura_condiconal.png")));
        figura_condiconal.setImage(image3);
        figura_condiconalE.setImage(image3);
        Image image4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("figura_documento.png")));
        figura_documento.setImage(image4);
        figura_documentoE.setImage(image4);
    }

    @FXML
    private void onMousePressed(MouseEvent event) {
        initialX = event.getSceneX();
        initialY = event.getSceneY();
        ImageView sourceDiagram = (ImageView) event.getSource();
        originalX = sourceDiagram.getLayoutX();
        originalY = sourceDiagram.getLayoutY();

    }
    @FXML
    private void onMouseDragged(MouseEvent event) {
        ImageView sourceDiagram = (ImageView) event.getSource();

        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();

        // Calcular la distancia que se ha movido el ratón desde la posición inicial de la imagen
        double deltaX = mouseX - initialX;
        double deltaY = mouseY - initialY;

        // Calcular las nuevas coordenadas de la imagen
        double newX = sourceDiagram.getLayoutX() + deltaX;
        double newY = sourceDiagram.getLayoutY() + deltaY;

        // Limitar las coordenadas dentro del Pane
        Bounds paneBounds = panel_menu.getBoundsInLocal();
        newY = clamp(newY, 0, paneBounds.getHeight());

        // Limitar las coordenadas dentro del ScrollPane
        Bounds scrollPaneBounds = panel_contenedor.getViewportBounds();
        newX = clamp(newX, 0, scrollPaneBounds.getWidth() );
        newY = clamp(newY, 0, scrollPaneBounds.getHeight());

        // Establecer las nuevas coordenadas de la imagen
        sourceDiagram.setLayoutX(newX);
        sourceDiagram.setLayoutY(newY);

        // Actualizar las coordenadas iniciales del ratón
        initialX = mouseX;
        initialY = mouseY;
    }

    private double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    @FXML
    private void onMouseReleased(MouseEvent event) {
        // Devolver la imagen a su posición original
        ImageView sourceDiagram = (ImageView) event.getSource();
        sourceDiagram.setLayoutX(originalX);
        sourceDiagram.setLayoutY(originalY);
        // Verificar si la imagen que se soltó es figura_condicional
        /*
        if (sourceDiagram == figura_condiconal) {
            // Obtener las coordenadas donde se soltó la imagen
            // Obtener las coordenadas del evento con respecto al panel_Diagrama
            double x = event.getSceneX() - panel_Diagrama.getLayoutX() - 210;
            double y = event.getSceneY() - panel_Diagrama.getLayoutY() - 65;

            // Dibujar el rombo en las coordenadas obtenidas
            dibujo_condicional(x, y);
        } else if (sourceDiagram == figura_documento) {
            // Obtener las coordenadas donde se soltó la imagen
            // Obtener las coordenadas del evento con respecto al panel_Diagrama
            double x = event.getSceneX() - panel_Diagrama.getLayoutX() - 210;
            double y = event.getSceneY() - panel_Diagrama.getLayoutY() - 65;

            // Dibujar
        }else if (sourceDiagram == figura_entrada_salida) {
            // Obtener las coordenadas donde se soltó la imagen
            // Obtener las coordenadas del evento con respecto al panel_Diagrama
            double x = event.getSceneX() - panel_Diagrama.getLayoutX() - 210;
            double y = event.getSceneY() - panel_Diagrama.getLayoutY() - 65;

            // Dibujar
        }else if (sourceDiagram == figura_proceso) {
            // Obtener las coordenadas donde se soltó la imagen
            // Obtener las coordenadas del evento con respecto al panel_Diagrama
            double x = event.getSceneX() - panel_Diagrama.getLayoutX() - 210;
            double y = event.getSceneY() - panel_Diagrama.getLayoutY() - 65;

            // Dibujar
        }
        */
        // Verificar la imagen soltada
        if (sourceDiagram == figura_condiconal || sourceDiagram == figura_documento || sourceDiagram == figura_entrada_salida || sourceDiagram == figura_proceso) {
            double[] coordinates = obtenerCoordenadas(event);
            double x = coordinates[0];
            double y = coordinates[1];
            dibujarFigura(x, y,sourceDiagram);
        }
    }

    private double[] obtenerCoordenadas(MouseEvent event) {
        double x = event.getSceneX() - panel_Diagrama.getLayoutX() - 210;
        double y = event.getSceneY() - panel_Diagrama.getLayoutY() - 65;
        return new double[]{x, y};
    }

    private void dibujarFigura(double x, double y, ImageView sourceDiagram) {
        if (figura_condiconal == sourceDiagram) {
            dibujo_condicional(x, y);
        } else if (figura_documento == sourceDiagram) {
            // Dibujar la imagen de documento
        } else if (figura_entrada_salida == sourceDiagram) {
            // Dibujar la imagen de entrada/salida
        } else if (figura_proceso == sourceDiagram) {
            // Dibujar la imagen de proceso
        }
    }

    @FXML
    protected void fondoCuadriculado(double width, double height) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("fondoCuadriculado.jpg")));

        //no tocar parametros backgroundSize
        BackgroundSize backgroundSize = new BackgroundSize(800, 500, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);
        panel_Diagrama.setBackground(background);
        panel_Diagrama.setMinSize(width, height);
    }

    public void ajustar_ScrollPane(double width, double height){
        panel_contenedor.setMinSize(width, height);
    }

    public ArrayList<Vertice> calcular_vertices(Figura figura){
        ArrayList<Vertice> vertices = new ArrayList<Vertice>();
        Vertice p1 = figura.getVertice_cordenada();
        Vertice p2 = new Vertice(p1.getX() + figura.getDimenciones().getAncho()-33, p1.getY());
        Vertice p3 = new Vertice(p2.getX(), p1.getY() + figura.getDimenciones().getAlto()-26);
        Vertice p4 = new Vertice(p1.getX(), p3.getY());
        vertices.add(p1);
        vertices.add(p2);
        vertices.add(p3);
        vertices.add(p4);
        return vertices;
    }

    public  void dibujo_rect_curvo(Canvas canvas, Figura figura){

        ArrayList<Vertice> vertices = calcular_vertices(figura);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeText(figura.getContenido(),vertices.get(0).getX()+figura.getDimenciones().getAncho()/2-60,
                vertices.get(0).getY()+figura.getDimenciones().getAlto()/2-10, 190);

        gc.setLineWidth(2.5);
        gc.setStroke(Color.BLUE);
        //lineas Horizontales
        gc.strokeLine(vertices.get(0).getX()+45, vertices.get(0).getY(), vertices.get(1).getX()-45, vertices.get(1).getY());
        gc.strokeLine(vertices.get(2).getX()-45, vertices.get(2).getY(), vertices.get(3).getX()+45, vertices.get(3).getY());

        //curvas laterales
        gc.strokeArc(vertices.get(0).getX(), vertices.get(0).getY(), vertices.get(3).getX()+75,
                 vertices.get(3).getY()-25, 90,180, ArcType.OPEN);
        gc.strokeArc(vertices.get(0).getX()+figura.getDimenciones().getAncho()-141, vertices.get(0).getY(),
                vertices.get(3).getX()+75, vertices.get(3).getY()-25, 270,180, ArcType.OPEN);

        //lineas Verticales
        //gc.strokeLine(vertices.get(1).getX(), vertices.get(1).getY(), vertices.get(2).getX(), vertices.get(2).getY());
        //gc.strokeLine(vertices.get(3).getX(), vertices.get(3).getY(), vertices.get(0).getX(), vertices.get(0).getY());
    }

    public void dibujo_paralelogramo(Canvas canvas, Figura figura){

        // Calcular los otros vértices
        ArrayList<Vertice> vertices = calcular_vertices(figura);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dibujar el paralelogramo
        gc.setStroke(Color.BLACK);
        // Línea p1-p2
        gc.strokeLine(vertices.get(0).getX(), vertices.get(0).getY(), vertices.get(1).getX(), vertices.get(1).getY());
        // Línea p2-p3
        gc.strokeLine(vertices.get(1).getX(), vertices.get(1).getY(), vertices.get(2).getX(), vertices.get(2).getY());
        // Línea p3-p4
        gc.strokeLine(vertices.get(2).getX(), vertices.get(2).getY(), vertices.get(3).getX(), vertices.get(3).getY());
        // Línea p4-p1
        gc.strokeLine(vertices.get(3).getX(), vertices.get(3).getY(), vertices.get(0).getX(), vertices.get(0).getY());

    }

    public void figurasInicio_fin(){
        //Parametros figura Inicio
        //establecer valores adecuados y centralizados en relacion a la proporcion 32.5x25.0
        Vertice p_Finicio_direccion = new Vertice(32.5,25.0); //no cambiar
        Vertice p_Finicio_conexion = new Vertice(1,1); //Reajustar
        Arista dimencion_Finicio = new Arista(500, 75);
        Figura figura_inicio = new Figura("Algoritmo titulo", p_Finicio_direccion, p_Finicio_conexion, dimencion_Finicio);

        //considerar no salirse de las dimensiones del canvas
        Canvas canvas_Finicio = new Canvas(dimencion_Finicio.getAncho(), dimencion_Finicio.getAlto());
        canvas_Finicio.setLayoutX(p_Finicio_direccion.getX());
        canvas_Finicio.setLayoutY(p_Finicio_direccion.getY());

        // Dibujo / diseño del Canvas
        dibujo_rect_curvo(canvas_Finicio,figura_inicio);

        //funcionalidad (En proceso)
        canvas_Finicio.setOnMouseClicked(event ->{
            //editar titulo
                System.out.println("CLICK!");
        });
        //Parametros figura Fin
        //establecer valores adecuados y centralizados
        Vertice p_Ffin_direccion = new Vertice(32.5,25.0);
        Vertice p_Ffin_conexion = new Vertice(1,1); //Reajustar
        Arista dimencion_Ffin = new Arista(500, 75);
        Figura figura_fin = new Figura("Fin Algoritmo", p_Ffin_direccion, p_Ffin_conexion, dimencion_Ffin);

        Canvas canvas_Ffin = new Canvas(dimencion_Ffin.getAncho(), dimencion_Ffin.getAlto());
        canvas_Ffin.setLayoutX(p_Ffin_direccion.getX());
        canvas_Ffin.setLayoutY(p_Ffin_direccion.getY());

        // Dibujo / diseño del Canvas
        dibujo_rect_curvo(canvas_Ffin,figura_fin);

        panel_Diagrama.getChildren().addAll(canvas_Finicio,canvas_Ffin);

        AnchorPane.setTopAnchor(canvas_Finicio, p_Finicio_direccion.getY());
        AnchorPane.setLeftAnchor(canvas_Finicio, p_Finicio_direccion.getX());
        //eliminar sobrecolocación de los canvas
        double offset = 100;
        AnchorPane.setTopAnchor(canvas_Ffin, p_Ffin_direccion.getY() + offset);
        AnchorPane.setLeftAnchor(canvas_Ffin, p_Ffin_direccion.getX());
    }
    public void dibujo_condicional(double x, double y){
        double size = 110; // Tamaño del rombo

        Canvas canvas = new Canvas(size, size);
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Calcular los puntos del rombo
        double[] xPoints = { size / 2, size, size / 2, 0 };
        double[] yPoints = { 0, size / 2, size, size / 2 };

        gc.setFill(Color.RED);
        gc.fillPolygon(xPoints, yPoints, 4);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokePolygon(xPoints, yPoints, 4);
        panel_Diagrama.getChildren().add(canvas);
    }
}