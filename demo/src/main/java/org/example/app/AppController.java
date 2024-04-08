package org.example.app;
import Clases.*;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.shape.ArcType;

import java.math.*;
import javafx.scene.image.ImageView;

public class AppController {
    @FXML
    AnchorPane panel_Diagrama;
    @FXML
    ScrollPane panel_contenedor;
    @FXML
    TabPane panel_ventanas;
    @FXML
    Pane panel_menu;
    @FXML
    Button btn_entrada;
    @FXML
    Pane basurero;
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
    private ImageView trash;

    private TextField textContenido = new TextField();
    Diagrama ins = Diagrama.getInstance();

    //parametros
    double tamañoTxt = 1;
    double tamaño_Lbordes = 2;
    double tamaño_Lfechas = 3.5;
    double tamaño_Lconexiones = 0;
    //colores
    Color colorBordes = Color.web("#fc7c0c");
    Color colorRelleno = Color.web("#242c3c");
    Color colorTexto = Color.web("#ffffff");
    Color colorFlecha = Color.web("#ffffff");

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
        Image image5 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("trash.png")));
        trash.setImage(image5);

        textContenido.setOpacity(0.0);
        textContenido.setDisable(true);
        panel_Diagrama.getChildren().add(textContenido);
    }

    @FXML
    private void onMousePressed(MouseEvent event) {
        initialX = event.getSceneX();
        initialY = event.getSceneY();
        ImageView sourceDiagram = (ImageView) event.getSource();
        originalX = sourceDiagram.getLayoutX();
        originalY = sourceDiagram.getLayoutY();
        basurero.setVisible(true);
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

        basurero.setVisible(false);
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

    public void ajustar_Panes(double width, double height){
        panel_contenedor.setMinSize(width, height);
        panel_ventanas.setMinSize(width,height);
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

    public void dibujar_flecha(Canvas canvas, double origenX, double origenY, double angulo, double longitud){

        GraphicsContext gc = canvas.getGraphicsContext2D();

        double pendiente = Math.tan(Math.toRadians(angulo));
        double destinoX1_L1 = origenX + longitud * Math.cos(Math.toRadians(angulo));
        double destinoY1_L1 = origenY + longitud * -Math.sin(Math.toRadians(angulo));

        // Dibujar recta
        gc.setLineWidth(3);
        gc.strokeLine(origenX, origenY, destinoX1_L1, destinoY1_L1);

        // Dibujar punta
        //puntos
        double destinoX1_L2 = destinoX1_L1 + 5 * Math.cos(Math.toRadians(angulo+90));
        double destinoY1_L2 = destinoY1_L1 + 10 * -Math.sin(Math.toRadians(angulo+90));
        double destinoX2_L1 = destinoX1_L1 + 10 * Math.cos(Math.toRadians(angulo));
        double destinoY2_L1 = destinoY1_L1 + 10 * -Math.sin(Math.toRadians(angulo));
        double destinoX2_L2 = destinoX1_L1 + 5 * Math.cos(Math.toRadians(angulo-90));
        double destinoY2_L2 = destinoY1_L1 + 10 * -Math.sin(Math.toRadians(angulo-90));

        //rectas
        gc.setLineWidth(1);
        gc.strokeLine(destinoX1_L1, destinoY1_L1, destinoX1_L2, destinoY1_L2);
        gc.strokeLine(destinoX1_L2, destinoY1_L2, destinoX2_L1, destinoY2_L1);
        gc.strokeLine(destinoX2_L1, destinoY2_L1, destinoX2_L2, destinoY2_L2);
        gc.strokeLine(destinoX2_L2, destinoY2_L2, destinoX1_L1, destinoY1_L1);
        //relleno
        double[] xPoints = {destinoX1_L2, destinoX2_L1, destinoX2_L2};
        double[] yPoints = {destinoY1_L2, destinoY2_L1, destinoY2_L2};
        gc.fillPolygon(xPoints, yPoints, 3);
    }

    public  void dibujo_rect_curvo(Canvas canvas, Figura figura){

        ArrayList<Vertice> vertices = calcular_vertices(figura);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Rellenar figura
        gc.setFill(colorRelleno);
        double angulo = -10;
        double longitud = 10;
        double destinoX_L2 = vertices.get(1).getX() + longitud * Math.cos(Math.toRadians(angulo));
        double destinoY_L2 = vertices.get(1).getY()+2.5 + longitud * -Math.sin(Math.toRadians(angulo));
        double destinoX_L3 = destinoX_L2-3 + longitud * Math.cos(Math.toRadians(angulo));
        double destinoY_L3 = destinoY_L2+5 + longitud * -Math.sin(Math.toRadians(angulo));
        double destinoX_L4 = destinoX_L3-15 + longitud * Math.cos(Math.toRadians(angulo));
        double destinoY_L4 = destinoY_L3+7.5 + longitud * -Math.sin(Math.toRadians(angulo));

        double destinoX_L7 = vertices.get(3).getX()-26 + longitud * Math.cos(Math.toRadians(-angulo));
        double destinoY_L7 = vertices.get(3).getY()-2 + longitud * -Math.sin(Math.toRadians(-angulo));
        double destinoX_L8 = destinoX_L7-14.5 + longitud * Math.cos(Math.toRadians(-angulo));
        double destinoY_L8 = destinoY_L7-7 + longitud * -Math.sin(Math.toRadians(-angulo));
        double destinoX_L9 = destinoX_L8-3.5 + longitud * Math.cos(Math.toRadians(-angulo));
        double destinoY_L9 = destinoY_L8-7 + longitud * -Math.sin(Math.toRadians(-angulo));

        double[] xPoints = {vertices.get(0).getX()+20,vertices.get(1).getX()-18.5,destinoX_L2-18.5,destinoX_L3-18.5,
                destinoX_L4-18.5,vertices.get(2).getX()-18.5,vertices.get(3).getX()+15,destinoX_L7+20,destinoX_L8+20,
                destinoX_L9+20};

        double[] yPoints = {vertices.get(0).getY(), vertices.get(1).getY(), destinoY_L2, destinoY_L3, destinoY_L4,
                vertices.get(2).getY(), vertices.get(3).getY(),destinoY_L7,destinoY_L8,destinoY_L9};

        gc.fillPolygon(xPoints, yPoints, xPoints.length);

        //lineas Horizontales
        gc.setLineWidth(tamaño_Lbordes);
        gc.setStroke(colorBordes);
        gc.strokeLine(vertices.get(0).getX()+20, vertices.get(0).getY(), vertices.get(1).getX()-20, vertices.get(1).getY());
        gc.strokeLine(vertices.get(2).getX()-20, vertices.get(2).getY(), vertices.get(3).getX()+20, vertices.get(3).getY());
        //curvas laterales
        gc.strokeArc(vertices.get(0).getX(), vertices.get(0).getY(), vertices.get(3).getX()+12,
                vertices.get(3).getY()-25, 90,180, ArcType.OPEN);
        gc.strokeArc(vertices.get(0).getX()+figura.getDimenciones().getAncho()-80, vertices.get(0).getY(),
                vertices.get(3).getX()+12, vertices.get(3).getY()-25, 270,180, ArcType.OPEN);

        //contenido
        gc.setStroke(colorTexto);
        gc.setLineWidth(tamañoTxt);
        gc.strokeText(figura.getContenido(),vertices.get(0).getX()+figura.getContenido().length(),
            vertices.get(0).getY()+figura.getDimenciones().getAlto()/2-10, 80*figura.getContenido().length());
    }

    public void dibujo_paralelogramo(Canvas canvas, Figura figura, int tipo){
        // Calcular los otros vértices
        ArrayList<Vertice> vertices = calcular_vertices(figura);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        int delta = 14; //inclinacion

        // Dibujar el paralelogramo
        gc.setLineWidth(tamaño_Lbordes);
        gc.setStroke(colorBordes);

        // Rellenar figura
        gc.setFill(colorRelleno);
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
        gc.setStroke(colorBordes);
        gc.setFill(colorBordes);
        //1=entrada -- 0=salida
        if(tipo == 1){
            dibujar_flecha(canvas, vertices.get(1).getX()-10, vertices.get(1).getY()/3, -135,10);
        }else{
            dibujar_flecha(canvas, vertices.get(1).getX()-30, vertices.get(1).getY()*2-vertices.get(1).getY(), 45,10);
        }

        //contenido
        gc.setStroke(colorTexto);
        gc.setLineWidth(tamañoTxt);
        gc.strokeText(figura.getContenido(),vertices.get(0).getX()+figura.getContenido().length(),
                vertices.get(0).getY()+figura.getDimenciones().getAlto()/2-10, 25*figura.getContenido().length());
     }

    public void limpiar_canvas(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    public void conectar(){

    }

    public void figurasInicio_fin(){

        double cx = 32.5;
        double cy = 25;
        //Parametros figura Inicio
        Vertice p_Finicio_direccion = new Vertice(cx,cy); //no cambiar
        Vertice p_Finicio_conexion = new Vertice(cx*2,cy*2); //Reajustar
        String contenido = "Algoritmo titulo y algo mas";
        Arista dimencion_Finicio = new Arista(8*contenido.length()+25, 50);
        Inicio_Fin figura_inicio = new Inicio_Fin(contenido, p_Finicio_direccion, p_Finicio_conexion, dimencion_Finicio);

        //considerar no salirse de las dimensiones del canvas
        Canvas canvas_Finicio = new Canvas(dimencion_Finicio.getAncho(), dimencion_Finicio.getAlto());
        //posicion de la figura en relacion al AnchorPane
        canvas_Finicio.setLayoutX(p_Finicio_direccion.getX()+150);
        canvas_Finicio.setLayoutY(p_Finicio_direccion.getY());

        // Dibujo / diseño del Canvas
        dibujo_rect_curvo(canvas_Finicio,figura_inicio);

        //editar contenido
        canvas_Finicio.setOnMouseClicked(event -> {
            textContenido.setOpacity(1.0);
            textContenido.setDisable(false);
            textContenido.getStyleClass().add("Contenido_edit");
            textContenido.setLayoutX(p_Finicio_direccion.getX() + 200);
            textContenido.setLayoutY(p_Finicio_direccion.getY() + 24);
            textContenido.setMinWidth(canvas_Finicio.getWidth() / 1.5);
            textContenido.setMinHeight(canvas_Finicio.getHeight() / 2);
            textContenido.setText(figura_inicio.getContenido());

            String text_previo = figura_inicio.getContenido();
            figura_inicio.setContenido("");
            limpiar_canvas(canvas_Finicio);
            dibujo_rect_curvo(canvas_Finicio,figura_inicio);
            double pre_largo = text_previo.length();

            textContenido.setOnKeyPressed(event_2 -> {
                if (event_2.getCode() == KeyCode.ENTER) {
                    figura_inicio.setContenido(textContenido.getText());
                    String new_text = textContenido.getText();
                    dimencion_Finicio.setAncho(8*new_text.length()+25);
                    canvas_Finicio.setWidth(8*new_text.length()+25);
                    //editar posicion en relacion al largo
                    /*double diferencia = (new_text.length()+25);
                    System.out.printf("Largo:"+diferencia);
                    canvas_Finicio.setLayoutX(p_Finicio_direccion.getX()+150/diferencia/2);*/
                    //redibujo
                    textContenido.setMinWidth(canvas_Finicio.getWidth()*0.5);
                    limpiar_canvas(canvas_Finicio);
                    dibujo_rect_curvo(canvas_Finicio,figura_inicio);
                    textContenido.clear();
                    textContenido.setOpacity(0.0);
                    textContenido.setDisable(true);
                }
            });
        });

        //Parametros figura Fin
        Vertice p_Ffin_direccion = new Vertice(cx,cy);
        //distancia entre las figuras iniciales
        double diferencia = 150;
        Vertice p_Ffin_conexion = new Vertice(cx*2,cy*2+diferencia);
        contenido="Fin Algoritmo";
        Arista dimencion_Ffin = new Arista(8*contenido.length()+25, 50);
        Inicio_Fin figura_fin = new Inicio_Fin(contenido, p_Ffin_direccion, p_Ffin_conexion, dimencion_Ffin);

        Canvas canvas_Ffin = new Canvas(dimencion_Ffin.getAncho(), dimencion_Ffin.getAlto());
        canvas_Ffin.setLayoutX(p_Finicio_direccion.getX()*3+140);
        canvas_Ffin.setLayoutY(p_Finicio_direccion.getY()+diferencia);

        // Dibujo / diseño del Canvas
        dibujo_paralelogramo(canvas_Ffin,figura_fin,0);

        //conectar
        Vertice vertice_Nfigura = new Vertice(0,0); //relacionar
        Conector conector_inicial = new Conector(p_Finicio_conexion,vertice_Nfigura, p_Ffin_conexion);

        Canvas f_conector = new Canvas(dimencion_Ffin.getAncho(),diferencia-25);
        dibujar_flecha(f_conector,cx,0,-90, diferencia-35);//ajustar longitud en relacion a los puntos

        f_conector.setLayoutX(p_Finicio_direccion.getX()*4+155);
        f_conector.setLayoutY(p_Finicio_direccion.getY()+50);

        panel_Diagrama.getChildren().addAll(canvas_Finicio,canvas_Ffin,f_conector);
        ins.agregarElemento(figura_inicio,0,0);
        ins.agregarElemento(figura_fin,0,0);
        ins.agregarElemento(conector_inicial,0,0);
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