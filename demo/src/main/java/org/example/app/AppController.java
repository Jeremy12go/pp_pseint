package org.example.app;
import Clases.Arista;
import Clases.Figura;
import Clases.Vertice;
import javafx.fxml.FXML;
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

public class AppController {
    @FXML
    AnchorPane panel_Diagrama;
    @FXML
    ScrollPane panel_contenedor;
    @FXML
    Button btn_entrada;

    @FXML
    private double initialX;
    @FXML
    private double initialY;

    @FXML
    public void initialize() throws IOException {
        fondoCuadriculado(740,1500);
        figurasInicio_fin();
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

    @FXML
    private void onDragDetected() {
        Dragboard dragboard = btn_entrada.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        //Agregar figura que se arrastrara
        content.putString("Figura");
        dragboard.setContent(content);
    }
    @FXML
    private void onDragDropped(javafx.scene.input.DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasString()) {
            String figura = db.getString();
            if ("Figura".equals(figura)) {
                //espacio para generar la figura

                //panel_Diagrama.getChildren().add(circle);
                success = true;
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

}