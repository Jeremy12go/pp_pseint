package org.example.app;
import Clases.*;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

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
    }

    //parametros
    Font font = Font.font("Arial", FontWeight.BOLD, 12);
    double tamañoTxt = 1;
    double tamaño_Lbordes = 2;
    double tamaño_Lfechas = 3.5;
    double tamaño_Lconexiones = 0;
    //colores
    Color colorBordes = Color.web("#fc7c0c");
    Color colorRelleno = Color.web("#242c3c");
    Color colorTexto = Color.web("#ffffff");
    Color colorFlecha = Color.web("#ffffff");

    ArrayList<Conector> conexiones = new ArrayList<Conector>();
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
    @FXML
    private void onMouseReleased(MouseEvent event) {
        // Devolver la imagen a su posición original
        ImageView sourceDiagram = (ImageView) event.getSource();
        sourceDiagram.setLayoutX(originalX);
        sourceDiagram.setLayoutY(originalY);
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

    private double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    private void dibujarFigura(double x, double y, ImageView sourceDiagram) {
        String texto = "";
        if (figura_condiconal == sourceDiagram) {
            texto= " A > B ";
            dibujo_condicional(texto,x, y);
        } else if (figura_documento == sourceDiagram) {
            texto= " Documento ";
            dibujo_documento(texto,x, y);
        } else if (figura_entrada_salida == sourceDiagram) {
            texto= " Dato ";
            //dibujo_paralelogramo();
        } else if (figura_proceso == sourceDiagram) {
            texto= " No Sé :'' ";
            dibujo_rectangulo(texto,x,y);
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
        panel_ventanas.setMinSize(width, height);
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
                vertices.get(0).getY()+figura.getDimenciones().getAlto()/2-10, 190);
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

    public void conectar(){

    }

    public void figurasInicio_fin(){
        double cx = 32.5;
        double cy = 25;
        //Parametros figura Inicio
        Vertice p_Finicio_direccion = new Vertice(cx,cy); //no cambiar
        Vertice p_Finicio_conexion = new Vertice(cx/2,cy/2); //Reajustar
        String contenido = "Algoritmo titulo y algo mas";
        Arista dimencion_Finicio = new Arista(8*contenido.length()+25, 50);
        Inicio_Fin figura_inicio = new Inicio_Fin(contenido, p_Finicio_direccion, p_Finicio_conexion, dimencion_Finicio);

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
        Vertice p_Ffin_direccion = new Vertice(cx,cy);
        Vertice p_Ffin_conexion = new Vertice(cx/2,cy/2); //Reajustar
        contenido="Fin Algoritmo";
        Arista dimencion_Ffin = new Arista(8*contenido.length()+25, 50);
        Inicio_Fin figura_fin = new Inicio_Fin(contenido, p_Ffin_direccion, p_Ffin_conexion, dimencion_Ffin);

        Canvas canvas_Ffin = new Canvas(dimencion_Ffin.getAncho(), dimencion_Ffin.getAlto());
        canvas_Ffin.setLayoutX(p_Ffin_direccion.getX());
        canvas_Ffin.setLayoutY(p_Ffin_direccion.getY());

        // Dibujo / diseño del Canvas
        dibujo_rect_curvo(canvas_Ffin,figura_fin);

        //conectar
        Canvas f_conector = new Canvas(dimencion_Ffin.getAncho(),dimencion_Ffin.getAlto()+150);
        double x = p_Ffin_conexion.getX()-p_Finicio_conexion.getX();
        double y = p_Ffin_conexion.getY()-p_Finicio_conexion.getY();
        System.out.printf("x:"+x+"--y:"+y+"\n");

        double largo = Math.sqrt(Math.pow(x,2)+(Math.pow(y,2)));
        System.out.printf("largo:"+largo+"\n");

        dibujar_flecha(f_conector,cx,0,-90, 150);

        panel_Diagrama.getChildren().addAll(canvas_Finicio,canvas_Ffin,f_conector);

        double recalcular = 10;

        //eliminar sobrecolocación de los canvas
        double offsetY = 7.7;
        double offsetX = 50;
        AnchorPane.setLeftAnchor(canvas_Finicio, offsetX);
        AnchorPane.setTopAnchor(canvas_Finicio, 7.5);

        AnchorPane.setLeftAnchor(f_conector, offsetX+(dimencion_Finicio.getAncho()/2)-20);
        AnchorPane.setTopAnchor(f_conector, offsetY+(dimencion_Finicio.getAlto()/2)+24);

        AnchorPane.setLeftAnchor(canvas_Ffin, offsetX+58);
        AnchorPane.setTopAnchor(canvas_Ffin, offsetY*4.75*recalcular);

    }
    public void dibujo_condicional(String texto,double x, double y){
        // Crear un objeto Text para calcular el ancho del texto
        //if(texto == " "){texto= " A > B ";}

        javafx.scene.text.Text text = new javafx.scene.text.Text(texto);
        text.setFont(font);
        double textWidth = text.getBoundsInLocal().getWidth();
        double textHeight = text.getBoundsInLocal().getHeight();

        // Calcular el tamaño del rombo basado en el texto
        double size = Math.max(textWidth, textHeight) + 40;

        Canvas canvas = new Canvas(size, size);
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Calcular los puntos del rombo
        double[] xPoints = {size / 2, size, size / 2, 0};
        double[] yPoints = {0, size / 2, size, size / 2};
        gc.setFill(Color.RED);
        gc.fillPolygon(xPoints, yPoints, 4);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokePolygon(xPoints, yPoints, 4);

        // Escribir el texto en el centro del rombo
        gc.setFont(font);
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(texto, size / 2, size / 2 + textHeight / 4); // Ajustar la posición vertical
        panel_Diagrama.getChildren().add(canvas);

        canvas.setOnMouseClicked(event -> {
            // Habilitar la edición del contenido
            textContenido.setOpacity(1.0);
            textContenido.setDisable(false);
            textContenido.getStyleClass().add("Contenido_edit");
            textContenido.setLayoutX(x); // Ajustar según tus necesidades
            textContenido.setLayoutY(y); // Ajustar según tus necesidades
            textContenido.setMinWidth(size); // Ajustar según tus necesidades
            textContenido.setMinHeight(size); // Ajustar según tus necesidades
            textContenido.setText(texto);
            // Establecer el color del texto como negro
            textContenido.setStyle("-fx-text-fill: black;");

            // Agregar evento de tecla para actualizar el contenido al presionar Enter
            textContenido.setOnKeyPressed(event_2 -> {
                if (event_2.getCode() == KeyCode.ENTER) {
                    String newText = textContenido.getText();
                    gc.clearRect(0, 0, size, size); // Limpiar el canvas
                    gc.setFill(Color.RED);
                    gc.fillPolygon(xPoints, yPoints, 4);
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(2);
                    gc.strokePolygon(xPoints, yPoints, 4);
                    gc.setFill(Color.BLACK);
                    gc.setFont(font);
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.setTextBaseline(VPos.CENTER);
                    gc.fillText(newText, size / 2, size / 2);

                    panel_Diagrama.getChildren().remove(canvas);
                    dibujo_condicional(newText, x, y);
                    // Deshabilitar la edición del contenido
                    textContenido.clear();
                    textContenido.setOpacity(0.0);
                    textContenido.setDisable(true);
                }
            });
        });

    }

    public void dibujo_rectangulo(String texto, double x, double y) {
        // Crear un objeto Text para calcular el ancho del texto
        javafx.scene.text.Text text = new javafx.scene.text.Text(texto);
        text.setFont(font);
        double textWidth = text.getBoundsInLocal().getWidth();
        double textHeight = text.getBoundsInLocal().getHeight();

        // Calcular el tamaño del rectángulo basado en el texto
        double size = Math.max(textWidth, textHeight) + 40;

        Canvas canvas = new Canvas(size, size);
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dibujar el rectángulo
        gc.setFill(Color.RED);
        gc.fillRect(0, 0, size, size);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, size, size);

        // Escribir el texto en el centro del rectángulo
        gc.setFont(font);
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(texto, size / 2, size / 2 + textHeight / 4); // Ajustar la posición vertical
        panel_Diagrama.getChildren().add(canvas);

        canvas.setOnMouseClicked(event -> {
            // Habilitar la edición del contenido
            textContenido.setOpacity(1.0);
            textContenido.setDisable(false);
            textContenido.getStyleClass().add("Contenido_edit");
            textContenido.setLayoutX(x); // Ajustar según tus necesidades
            textContenido.setLayoutY(y); // Ajustar según tus necesidades
            textContenido.setMinWidth(size); // Ajustar según tus necesidades
            textContenido.setMinHeight(size); // Ajustar según tus necesidades
            textContenido.setText(texto);
            // Establecer el color del texto como negro
            textContenido.setStyle("-fx-text-fill: black;");

            // Agregar evento de tecla para actualizar el contenido al presionar Enter
            textContenido.setOnKeyPressed(event_2 -> {
                if (event_2.getCode() == KeyCode.ENTER) {
                    String newText = textContenido.getText();
                    gc.clearRect(0, 0, size, size); // Limpiar el canvas
                    gc.setFill(Color.RED);
                    gc.fillRect(0, 0, size, size); // Dibujar rectángulo relleno
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(2);
                    gc.strokeRect(0, 0, size, size); // Dibujar borde del rectángulo
                    gc.setFill(Color.BLACK);
                    gc.setFont(font);
                    gc.setTextAlign(TextAlignment.CENTER);
                    gc.setTextBaseline(VPos.CENTER);
                    gc.fillText(newText, size / 2, size / 2);

                    panel_Diagrama.getChildren().remove(canvas);
                    dibujo_condicional(newText, x, y);
                    // Deshabilitar la edición del contenido
                    textContenido.clear();
                    textContenido.setOpacity(0.0);
                    textContenido.setDisable(true);
                }
            });
        });
    }

    public void dibujo_documento(String texto,double x, double y) {
        double width = 150;
        double height = 100;
        double curveHeight = 20;

        Canvas canvas = new Canvas(width, height);
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dibujar el cuerpo del documento (rectángulo)
        gc.setFill(Color.web("#c3c3c3"));
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        // Dibujar las curvas en la parte inferior
        gc.beginPath();
        gc.moveTo(0, height - curveHeight); // Mover el lápiz al punto inicial de la curva
        gc.quadraticCurveTo(width / 4, height, width / 2, height - curveHeight); // Curva cuadrática
        gc.quadraticCurveTo((3 * width) / 4, height - (2 * curveHeight), width, height - curveHeight); // Segunda curva cuadrática
        gc.lineTo(width, 0); // Línea hacia el punto inicial del rectángulo
        gc.lineTo(0, 0); // Línea hacia el punto inicial del rectángulo
        gc.closePath(); // Cerrar el camino

        // Rellenar la figura completa (incluidas las curvas en la parte inferior)
        gc.fill();

        // Dibujar el contorno del documento
        gc.stroke();

        panel_Diagrama.getChildren().add(canvas);
    }
}