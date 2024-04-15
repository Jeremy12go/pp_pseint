package org.example.app;
import Clases.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.TabPane;
import java.util.Objects;
import javafx.scene.shape.ArcType;
import java.math.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

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
    private ImageView figura_entradaE;
    @FXML
    private ImageView figura_entrada;
    @FXML
    private ImageView figura_salidaE;
    @FXML
    private ImageView figura_salida;
    @FXML
    private ImageView figura_condiconal;
    @FXML
    private ImageView figura_documento;
    @FXML
    private ImageView figura_procesoE;
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
        Image image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("figura_entrada.png")));
        figura_entrada.setImage(image2);
        figura_entradaE.setImage(image2);
        Image image6 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("figura_salida.png")));
        figura_salida.setImage(image6);
        figura_salidaE.setImage(image6);
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

    Diagrama ins = Diagrama.getInstance();

    ArrayList<Conector> conexiones = new ArrayList<Conector>();
    //MOUSE_FUNCIONES------------------------------------------------------------------------------
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
        //newY = clamp(newY, 0, paneBounds.getHeight());

        // Limitar las coordenadas dentro del ScrollPane
        Bounds scrollPaneBounds = panel_contenedor.getViewportBounds();
        newX = clamp(newX, 0, scrollPaneBounds.getWidth());
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
        if (sourceDiagram == figura_condiconal || sourceDiagram == figura_documento || sourceDiagram == figura_entrada || sourceDiagram == figura_proceso || sourceDiagram == figura_salida) {
            double releaseX = event.getSceneX();
            double releaseY = event.getSceneY();
            Bounds basureroBounds = basurero.localToScene(basurero.getBoundsInLocal());

            // Verificar si las coordenadas del evento están dentro de los límites del Pane Basurero
            if (basureroBounds.contains(releaseX, releaseY)) {
                panel_Diagrama.getChildren().remove(sourceDiagram);
            } else {
                double[] coordinates = obtenerCoordenadas(event);
                double x = coordinates[0];
                double y = coordinates[1];
                dibujarFigura(x, y,sourceDiagram);
            }

        }

        basurero.setVisible(false);
    }
    //-------------------------------------------------------------------------------------------------------------
    private double[] obtenerCoordenadas(MouseEvent event) {
        double x = event.getSceneX() - panel_Diagrama.getLayoutX() - 210;
        double y = event.getSceneY() - panel_Diagrama.getLayoutY() - 65;
        return new double[]{x, y};
    }

    private double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    private void dibujarFigura(double x, double y, ImageView sourceDiagram) {
        Figura ultimaFAñadida = null;
        Canvas ultimaCAñadida = null;
        String texto = "";
        if (figura_condiconal == sourceDiagram) {
            texto= " A > B ";
            Canvas canvas= new Canvas(0,0);
            dibujo_condicional(texto,x, y,canvas);
        } else if (figura_documento == sourceDiagram) {
            Vertice p_Fentrada_direccion = new Vertice(32.5, 25); //no cambiar
            Vertice p_Fentrada_conexion = new Vertice(65, 50); //Reajustar
            Arista dimencion_Fentrada = new Arista(153, 50);
            texto= " Documento ";
            Figura documento = new Figura(texto, p_Fentrada_direccion, p_Fentrada_conexion, dimencion_Fentrada);

            Canvas canvas= new Canvas(0,0);
            dibujo_documento(texto,x, y, canvas,documento);


        } else if (figura_entrada == sourceDiagram) {
            Vertice p_Fentrada_direccion = new Vertice(32.5, 25); //no cambiar
            Vertice p_Fentrada_conexion = new Vertice(65, 50); //Reajustar
            String contenido = " Entrada: ";
            Arista dimencion_Fentrada = new Arista(153, 50);
            Entrada entrada = new Entrada(contenido, p_Fentrada_direccion, p_Fentrada_conexion, dimencion_Fentrada);

            //considerar no salirse de las dimensiones del canvas
            Canvas canvas_Fentrada = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());
            //posicion de la figura en relacion al AnchorPane
            double diferencia = dimencion_Fentrada.getAncho() / 2;
            canvas_Fentrada.setLayoutX((panel_Diagrama.getMinWidth() / 2) - diferencia);
            canvas_Fentrada.setLayoutY(y);

            //dibujar y agregar canvas al panel
            dibujo_paralelogramo(canvas_Fentrada, entrada, 1);
            //conector preNuevafigura
            Figura figuraOrigen = determinarFiguraOrigen(x, y);
            if (figuraOrigen != null) {
                conectar(figuraOrigen, entrada,x,y);
            } else {
                System.out.println("No se pudo encontrar una figura de origen cercana.");
            }

            // Agregar la nueva figura al panel
            panel_Diagrama.getChildren().add(canvas_Fentrada);
            ultimaFAñadida = entrada;
            ultimaCAñadida = canvas_Fentrada;
            // Agregar el canvas nueva figura a la lista de conectores
            ins.getList_orden().add(entrada);
            // Agregar la nueva figura a la lista de figuras
            ins.getList_figuras().add(entrada);

            //conector posNuevafigura
            Figura figuraDestino = determinarFiguraDestino(entrada, x, y);
            if (figuraDestino != null) {
                conectar(entrada,figuraDestino,x,y);
            } else {
                System.out.println("No se pudo encontrar una figura destino cercana.");
            }

            //eliminar conector anterior
            Conector preconector = obtenerConexionPrevia(figuraOrigen,figuraDestino);
            if (preconector != null) {
                ins.getList_conexiones().remove(preconector);
                ins.getList_orden().remove(preconector);
                panel_Diagrama.getChildren().remove(preconector);
            }
            //reposicionar canvas para la nueva figura
        } else if (figura_salida == sourceDiagram){

            Vertice p_Fsalida_direccion = new Vertice(32.5, 25); //no cambiar
            Vertice p_Fsalida_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y); //Reajustar
            String contenido = " Salida: ";
            Arista dimencion_Fsalida = new Arista(153, 50);
            Salida salida = new Salida(contenido, p_Fsalida_direccion, p_Fsalida_conexion, dimencion_Fsalida);

            Canvas canvas_Fsalida = new Canvas(dimencion_Fsalida.getAncho(), dimencion_Fsalida.getAlto());

            //posicion de la figura en relacion al AnchorPane
            double diferencia = dimencion_Fsalida.getAncho() / 2;
            canvas_Fsalida.setLayoutX((panel_Diagrama.getMinWidth() / 2) - diferencia);
            canvas_Fsalida.setLayoutY(y);

            //dibujar y agregar canvas al panel
            dibujo_paralelogramo(canvas_Fsalida, salida, 0);

            //conector preNuevafigura
            Figura figuraOrigen = determinarFiguraOrigen(x, y);
            if (figuraOrigen != null) {
                conectar(figuraOrigen, salida,x,y);
            } else {
                System.out.println("No se pudo encontrar una figura de origen cercana.");
            }

            // Agregar la nueva figura al panel
            panel_Diagrama.getChildren().add(canvas_Fsalida);
            ultimaFAñadida = salida;
            ultimaCAñadida = canvas_Fsalida;
            // Agregar el canvas nueva figura a la lista de conectores
            ins.getList_orden().add(canvas_Fsalida);
            // Agregar la nueva figura a la lista de figuras
            ins.getList_figuras().add(salida);

            //conector posNuevafigura
            Figura figuraDestino = determinarFiguraDestino(salida, x, y);
            if (figuraDestino != null) {
                conectar(salida,figuraDestino,x,y);
            } else {
                System.out.println("No se pudo encontrar una figura destino cercana.");
            }

            //eliminar conector anterior
            Conector preconector = obtenerConexionPrevia(figuraOrigen,figuraDestino);
            if (preconector != null) {
                ins.getList_conexiones().remove(preconector);
                ins.getList_orden().remove(preconector);
                panel_Diagrama.getChildren().remove(preconector);
            }
            //reposicionar canvas para la nueva figura

        } else if (figura_proceso == sourceDiagram) {
            texto= " Proceso ";
            Canvas canvas= new Canvas(0,0);
            dibujo_rectangulo(texto,x,y, canvas);
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

    public void limpiar_canvas(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public Canvas crear_canvasConector(double dimencion_conector, Vertice origen, Vertice destino){
        //calcular vertice_Nfigura con la distancia/2
        Conector conector_inicial = new Conector(origen, destino);
        Canvas f_conector = new Canvas(50,dimencion_conector+10);
        dibujar_flecha(f_conector,32.5,0,-90, dimencion_conector);//ajustar longitud en relacion a los puntos

        return f_conector;
    }

    public Figura determinarFiguraOrigen(double x, double y) {
        Figura figuraOrigen = null;
        double distanciaMinima = Double.MAX_VALUE;

        for (Object obj : ins.getList_figuras()) {
            if (obj instanceof Figura) { // Comprueba si el objeto es una instancia de Figura
                Figura figura = (Figura) obj; // Convierte el objeto a tipo Figura
                // Calcular la distancia entre el punto (x, y) y el punto de conexión de la figura
                double distancia = calcularDistancia(x, y, figura.getVertice_conexion().getX(), figura.getVertice_conexion().getY());

                // Si la distancia es menor que la distancia mínima actual, actualizar la figura de origen y la distancia mínima
                if (distancia < distanciaMinima) {
                    figuraOrigen = figura;
                    distanciaMinima = distancia;
                }
            }
        }
        return figuraOrigen;
    }

    public Figura determinarFiguraDestino(Figura figuraOrigen, double x, double y) {
        Figura figuraDestino = null;
        double distanciaMinima = Double.MAX_VALUE;

        for (Object obj : ins.getList_figuras()) {
            if (obj instanceof Figura) { // Comprueba si el objeto es una instancia de Figura
                Figura figura = (Figura) obj; // Convierte el objeto a tipo Figura
                // Excluir la figura de origen de la búsqueda
                if (figura != figuraOrigen) {
                    // Calcular la distancia entre el punto (x, y) y el punto de conexión de la figura
                    double distancia = calcularDistancia(x, y, figura.getVertice_conexion().getX(), figura.getVertice_conexion().getY());
                    // Si la distancia es menor que la distancia mínima actual, actualizar la figura destino y la distancia mínima
                    if (distancia < distanciaMinima) {
                        figuraDestino = figura;
                        distanciaMinima = distancia;
                    }
                }
            }
        }
        return figuraDestino;
    }

    public Conector obtenerConexionPrevia(Figura figuraOrigen, Figura figuraDestino) {
        for (Object obj : ins.getList_conexiones()) {
            if(obj instanceof Conector){
                Conector conector = (Conector) obj;
                if (conectorConectaFiguras(conector, figuraOrigen, figuraDestino)) {
                    return conector;
                }
            }
        }
        return null;
    }

    public boolean conectorConectaFiguras(Conector conector, Figura figuraOrigen, Figura figuraDestino) {
        // Comprobar si el conector conecta las figuras origen y destino
        return (conector.getVertice_inicial().equals(figuraOrigen.getVertice_conexion()) && conector.getVertice_final().equals(figuraDestino.getVertice_conexion())) ||
                (conector.getVertice_inicial().equals(figuraDestino.getVertice_conexion()) && conector.getVertice_final().equals(figuraOrigen.getVertice_conexion()));
    }

    public double calcularDistancia(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void conectar(Figura figuraOrigen, Figura figuraDestino, double x, double y) {

        Conector conectorExistente = obtenerConexionPrevia(figuraOrigen, figuraDestino);

        // Si ya existe un conector entre las figuras, no crear uno nuevo
        if (conectorExistente != null) {
            return;
        }

        //canvas conector
        Vertice origen = new Vertice((figuraOrigen.getVertice_conexion().getX()), figuraOrigen.getVertice_conexion().getY()-figuraOrigen.getDimenciones().getAlto());//editado -50
        Vertice destino = (Vertice) figuraDestino.getVertice_conexion();
        double diferenciaY = y - figuraDestino.getVertice_conexion().getY();
        Canvas conectorCanvas = crear_canvasConector(diferenciaY,figuraOrigen.getVertice_conexion(), figuraDestino.getVertice_conexion());


        //posicionamiento del canvas
        double layoutX = (panel_Diagrama.getMinWidth() / 2) - 20;
        double layoutY = y - diferenciaY+15; // Ajustar la posición Y del conector
        conectorCanvas.setLayoutX(layoutX);
        conectorCanvas.setLayoutY(layoutY);

        // Agregar el nuevo canvas conector a la lista visual
        panel_Diagrama.getChildren().add(conectorCanvas);

        // Agregar el canvas conector a la lista de conectores
        ins.getList_orden().add(conectorCanvas);

        // Agregar el conector a la lista de conectores
        Conector conector = new Conector(figuraOrigen.getVertice_conexion(), figuraDestino.getVertice_conexion());
        ins.getList_conexiones().add(conector);
    }

    //FIGURAS-----------------------------------------------------------------------------------------
    private TextField textContenido = new TextField();
    private int clickCount = 0;
    private double previousX = 0;
    private double previousY = 0;
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

    public void figurasInicio_fin(){

        double cx = 32.5;
        double cy = 25;
        //Parametros figura Inicio
        Vertice p_Finicio_cordenada = new Vertice(cx,cy); //no cambiar
        Vertice p_Finicio_conexion = new Vertice(0,0); //Reajustar
        String contenido = "Algoritmo titulo";
        Arista dimencion_Finicio = new Arista(153, 50);
        Inicio_Fin figura_inicio = new Inicio_Fin(contenido, p_Finicio_cordenada, p_Finicio_conexion, dimencion_Finicio);

        //considerar no salirse de las dimensiones del canvas
        Canvas canvas_Finicio = new Canvas(dimencion_Finicio.getAncho(), dimencion_Finicio.getAlto());
        //posicion de la figura en relacion al AnchorPane
        double diferencia = dimencion_Finicio.getAncho()/2;
        canvas_Finicio.setLayoutX((panel_Diagrama.getMinWidth()/2)-diferencia);
        canvas_Finicio.setLayoutY(p_Finicio_cordenada.getY());
        p_Finicio_conexion.setX(canvas_Finicio.getLayoutX());
        p_Finicio_conexion.setY(canvas_Finicio.getLayoutY()+dimencion_Finicio.getAlto());

        // Dibujo / diseño del Canvas
        dibujo_rect_curvo(canvas_Finicio,figura_inicio);

        //editar contenido
        canvas_Finicio.setOnMouseClicked(event -> {
            double _diferencia = dimencion_Finicio.getAncho()/2;
            textContenido.setOpacity(1.0);
            textContenido.setDisable(false);
            textContenido.getStyleClass().add("Contenido_edit");
            textContenido.setLayoutX((panel_Diagrama.getMinWidth()/2)-_diferencia+50);
            textContenido.setLayoutY(p_Finicio_cordenada.getY() + 24);
            textContenido.setMinWidth(canvas_Finicio.getWidth() / 1.5);
            textContenido.setMinHeight(canvas_Finicio.getHeight() / 2);
            textContenido.setText(figura_inicio.getContenido());

            String pre_text = figura_inicio.getContenido();
            figura_inicio.setContenido("");
            limpiar_canvas(canvas_Finicio);
            dibujo_rect_curvo(canvas_Finicio,figura_inicio);

            textContenido.setOnKeyPressed(event_2 -> {
                if (event_2.getCode() == KeyCode.ENTER) {
                    figura_inicio.setContenido(textContenido.getText());
                    String new_text = textContenido.getText();
                    double pre_dimension = dimencion_Finicio.getAncho();

                    //recalculo de la dimensiones de la figura por contenido
                    if(8*new_text.length()+25<=153){
                        dimencion_Finicio.setAncho(153);
                        canvas_Finicio.setWidth(153);
                        textContenido.setMinWidth(153);
                    }else{
                        dimencion_Finicio.setAncho(8*new_text.length()+25);
                        canvas_Finicio.setWidth(8*new_text.length()+25);
                        textContenido.setMinWidth(canvas_Finicio.getWidth()*0.7);
                    }

                    //editar posicion en relacion al largo(mitad del panel)
                    double _diferencia_ = dimencion_Finicio.getAncho()/2;
                    textContenido.setLayoutX((panel_Diagrama.getWidth()/2)-_diferencia_);
                    canvas_Finicio.setLayoutX((panel_Diagrama.getWidth()/2)-_diferencia_);
                    Vertice reajuste_v = new Vertice((panel_Diagrama.getMinWidth()/2)-_diferencia_,cy+dimencion_Finicio.getAlto());
                    figura_inicio.setVertice_conexion(reajuste_v);

                    //redibujo
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
        Vertice p_Ffin_conexion = new Vertice(cx,cy);
        contenido="Fin Algoritmo";
        Arista dimencion_Ffin = new Arista(8*contenido.length()+25, 50);
        Inicio_Fin figura_fin = new Inicio_Fin(contenido, p_Ffin_direccion, p_Ffin_conexion, dimencion_Ffin);
        diferencia+=250;//distancia entre las figuras iniciales
        Canvas canvas_Ffin = new Canvas(dimencion_Ffin.getAncho(), dimencion_Ffin.getAlto());
        canvas_Ffin.setLayoutX((panel_Diagrama.getMinWidth()/2)-70);
        canvas_Ffin.setLayoutY(p_Finicio_cordenada.getY()+diferencia);
        p_Ffin_conexion = new Vertice(canvas_Ffin.getLayoutX(), canvas_Ffin.getLayoutY()+dimencion_Ffin.getAlto());
        figura_fin.setVertice_conexion(p_Ffin_conexion);

        // Dibujo / diseño del Canvas
        dibujo_rect_curvo(canvas_Ffin,figura_fin);

        //conectar
        double distancia = (canvas_Ffin.getLayoutY()-canvas_Finicio.getLayoutY())/2;
        Vertice nueva_conexion = new Vertice((canvas_Ffin.getLayoutX()/2), canvas_Finicio.getLayoutY()+distancia);
        Conector conector_inicial = new Conector(p_Finicio_conexion, p_Ffin_conexion);
        Canvas f_conector = crear_canvasConector(diferencia-35,p_Finicio_conexion,p_Ffin_conexion);
        f_conector.setLayoutX((panel_Diagrama.getMinWidth()/2)-20);
        f_conector.setLayoutY(p_Finicio_cordenada.getY()+50);

        panel_Diagrama.getChildren().addAll(canvas_Finicio,canvas_Ffin,f_conector);
        ins.agregarElemento(figura_inicio,0,0);
        ins.agregarElemento(figura_fin,0,0);
        ins.agregarElemento(conector_inicial,0,0);
        ins.agregarElemento(canvas_Finicio,0,0);
        ins.agregarElemento(f_conector,0,0);
        ins.agregarElemento(canvas_Ffin,0,0);
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
    //FIGURAS_INTERACTIVAS----------------------------------------------------------------------------------------------
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

        canvas.setOnMousePressed(event -> {
            // Registrar las coordenadas del mouse en relación con la esquina superior izquierda de la figura
            previousX = event.getSceneX();
            previousY = event.getSceneY();
        });

        canvas.setOnMouseDragged(event -> {
            basurero.setVisible(true);
            // Calcular el desplazamiento del mouse desde la última posición
            double deltaX = event.getSceneX() - previousX;
            double deltaY = event.getSceneY() - previousY;

            // Calcular las nuevas coordenadas para la figura basadas en el desplazamiento del mouse
            double newX = canvas.getLayoutX() + deltaX;
            double newY = canvas.getLayoutY() + deltaY;

            // Establecer las nuevas coordenadas de la figura
            canvas.setLayoutX(newX);
            canvas.setLayoutY(newY);

            // Actualizar la posición anterior del cursor
            previousX = event.getSceneX();
            previousY = event.getSceneY();
        });

        canvas.setOnMouseReleased(event -> {
            double releaseX = event.getSceneX();
            double releaseY = event.getSceneY();
            Bounds basureroBounds = basurero.localToScene(basurero.getBoundsInLocal());

            // Verificar si las coordenadas del evento están dentro de los límites del Pane Basurero
            if (basureroBounds.contains(releaseX, releaseY)) {
                panel_Diagrama.getChildren().remove(canvas);
            }
            basurero.setVisible(false);
        });

    }

    public void dibujo_condicional(String texto,double x, double y, Canvas canvas){
        // Crear un objeto Text para calcular el ancho del texto
        if(Objects.equals(texto, "") || Objects.equals(texto, " ") || Objects.equals(texto, "  ") || Objects.equals(texto, "   ")){texto= " A > B ";}
        String finalTexto = texto;

        javafx.scene.text.Text text = new javafx.scene.text.Text(finalTexto);
        text.setFont(font);
        double textWidth = text.getBoundsInLocal().getWidth();
        double textHeight = text.getBoundsInLocal().getHeight();

        // Calcular el tamaño del rombo basado en el texto
        double size = Math.max(textWidth, textHeight) + 40;

        canvas = new Canvas(size, size);
        Canvas finalCanvas= canvas;
        finalCanvas.setLayoutX(x);
        finalCanvas.setLayoutY(y);
        GraphicsContext gc = finalCanvas.getGraphicsContext2D();

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
        gc.fillText(finalTexto, size / 2, size / 2 + textHeight / 4); // Ajustar la posición vertical
        panel_Diagrama.getChildren().add(finalCanvas);
        //MOVIMIENTO_FIGURA----------------------------------------------------
        finalCanvas.setOnMousePressed(event -> {
            // Registrar las coordenadas del mouse en relación con la esquina superior izquierda de la figura
            previousX = event.getSceneX();
            previousY = event.getSceneY();
        });

        finalCanvas.setOnMouseDragged(event -> {
            basurero.setVisible(true);
            // Calcular el desplazamiento del mouse desde la última posición
            double deltaX = event.getSceneX() - previousX;
            double deltaY = event.getSceneY() - previousY;

            // Calcular las nuevas coordenadas para la figura basadas en el desplazamiento del mouse
            double newX = finalCanvas.getLayoutX() + deltaX;
            double newY = finalCanvas.getLayoutY() + deltaY;

            // Establecer las nuevas coordenadas de la figura
            finalCanvas.setLayoutX(newX);
            finalCanvas.setLayoutY(newY);

            // Actualizar la posición anterior del cursor
            previousX = event.getSceneX();
            previousY = event.getSceneY();
        });

        finalCanvas.setOnMouseReleased(event -> {
            double releaseX = event.getSceneX();
            double releaseY = event.getSceneY();
            Bounds basureroBounds = basurero.localToScene(basurero.getBoundsInLocal());

            // Verificar si las coordenadas del evento están dentro de los límites del Pane Basurero
            if (basureroBounds.contains(releaseX, releaseY)) {
                panel_Diagrama.getChildren().remove(finalCanvas);
            }
            basurero.setVisible(false);
        });
        //ESCRITURA_FIGURA----------------------------------------------------
        finalCanvas.setOnMouseClicked(event -> {
            clickCount++;
            // Si se ha dado doble clic
            if (clickCount == 2) {
                // Restablecer el contador
                clickCount = 0;

                double currentX = finalCanvas.getLayoutX();
                double currentY = finalCanvas.getLayoutY();
                // Habilitar la edición del contenido
                textContenido.setOpacity(1.0);
                textContenido.setDisable(false);
                textContenido.getStyleClass().add("Contenido_edit");
                textContenido.setLayoutX(currentX); // Ajustar según tus necesidades
                textContenido.setLayoutY(currentY); // Ajustar según tus necesidades
                textContenido.setMinWidth(size); // Ajustar según tus necesidades
                textContenido.setMinHeight(size); // Ajustar según tus necesidades
                textContenido.setText(finalTexto);
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

                        panel_Diagrama.getChildren().remove(finalCanvas);
                        dibujo_condicional(newText, currentX, currentY,finalCanvas);
                        // Deshabilitar la edición del contenido
                        textContenido.clear();
                        textContenido.setOpacity(0.0);
                        textContenido.setDisable(true);
                    }
                });
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    clickCount = 0;
                }));
                timeline.play();
            }
        });
    }

    public void dibujo_rectangulo(String texto, double x, double y,Canvas canvas) {
        // Crear un objeto Text para calcular el ancho del texto
        if(Objects.equals(texto, "") || Objects.equals(texto, " ") || Objects.equals(texto, "  ") || Objects.equals(texto, "   ")){texto= " Proceso ";}
        String finalTexto = texto;

        javafx.scene.text.Text text = new javafx.scene.text.Text(finalTexto);
        text.setFont(font);
        double textWidth = text.getBoundsInLocal().getWidth();
        double textHeight = text.getBoundsInLocal().getHeight();

        // Calcular el tamaño del rectángulo basado en el texto
        double size = Math.max(textWidth, textHeight) + 40;

        canvas = new Canvas(size+(size*0.5), size);
        Canvas finalCanvas= canvas;
        finalCanvas.setLayoutX(x);
        finalCanvas.setLayoutY(y);
        GraphicsContext gc = finalCanvas.getGraphicsContext2D();

        // Dibujar el rectángulo
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, size+(size*0.5), size);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, size+(size*0.5), size);

        // Escribir el texto en el centro del rectángulo
        gc.setFont(font);
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(finalTexto, size / 2, size / 2 + textHeight / 4); // Ajustar la posición vertical
        panel_Diagrama.getChildren().add(finalCanvas);
        //MOVIMIENTO_FIGURA----------------------------------------------------
        finalCanvas.setOnMousePressed(event -> {
            // Registrar las coordenadas del mouse en relación con la esquina superior izquierda de la figura
            previousX = event.getSceneX();
            previousY = event.getSceneY();
        });

        finalCanvas.setOnMouseDragged(event -> {
            basurero.setVisible(true);
            // Calcular el desplazamiento del mouse desde la última posición
            double deltaX = event.getSceneX() - previousX;
            double deltaY = event.getSceneY() - previousY;

            // Calcular las nuevas coordenadas para la figura basadas en el desplazamiento del mouse
            double newX = finalCanvas.getLayoutX() + deltaX;
            double newY = finalCanvas.getLayoutY() + deltaY;

            // Establecer las nuevas coordenadas de la figura
            finalCanvas.setLayoutX(newX);
            finalCanvas.setLayoutY(newY);

            // Actualizar la posición anterior del cursor
            previousX = event.getSceneX();
            previousY = event.getSceneY();
        });

        finalCanvas.setOnMouseReleased(event -> {
            double releaseX = event.getSceneX();
            double releaseY = event.getSceneY();
            Bounds basureroBounds = basurero.localToScene(basurero.getBoundsInLocal());

            // Verificar si las coordenadas del evento están dentro de los límites del Pane Basurero
            if (basureroBounds.contains(releaseX, releaseY)) {
                panel_Diagrama.getChildren().remove(finalCanvas);
            }
            basurero.setVisible(false);
        });
        //ESCRITURA_FIGURA----------------------------------------------------
        finalCanvas.setOnMouseClicked(event -> {
            clickCount++;

            // Si se ha dado doble clic
            if (clickCount == 2) {
                // Restablecer el contador
                clickCount = 0;

                double currentX = finalCanvas.getLayoutX();
                double currentY = finalCanvas.getLayoutY();
                // Habilitar la edición del contenido
                textContenido.setOpacity(1.0);
                textContenido.setDisable(false);
                textContenido.getStyleClass().add("Contenido_edit");
                textContenido.setLayoutX(currentX); // Ajustar según tus necesidades
                textContenido.setLayoutY(currentY); // Ajustar según tus necesidades
                textContenido.setMinWidth(size+(size*0.5)); // Ajustar según tus necesidades
                textContenido.setMinHeight(size); // Ajustar según tus necesidades
                textContenido.setText(finalTexto);
                // Establecer el color del texto como negro
                textContenido.setStyle("-fx-text-fill: black;");

                // Agregar evento de tecla para actualizar el contenido al presionar Enter
                textContenido.setOnKeyPressed(event_2 -> {
                    if (event_2.getCode() == KeyCode.ENTER) {
                        String newText = textContenido.getText();
                        gc.clearRect(0, 0, size+(size*0.5), size); // Limpiar el canvas
                        gc.setFill(Color.BLUE);
                        gc.fillRect(0, 0, size+(size*0.5), size); // Dibujar rectángulo relleno
                        gc.setStroke(Color.BLACK);
                        gc.setLineWidth(2);
                        gc.strokeRect(0, 0, size+(size*0.5), size); // Dibujar borde del rectángulo
                        gc.setFill(Color.BLACK);
                        gc.setFont(font);
                        gc.setTextAlign(TextAlignment.CENTER);
                        gc.setTextBaseline(VPos.CENTER);
                        gc.fillText(newText, size+(size*0.5) / 2, size / 2);

                        panel_Diagrama.getChildren().remove(finalCanvas);
                        dibujo_rectangulo(newText, currentX, currentY, finalCanvas);
                        // Deshabilitar la edición del contenido
                        textContenido.clear();
                        textContenido.setOpacity(0.0);
                        textContenido.setDisable(true);
                    }
                });
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    clickCount = 0;
                }));
                timeline.play();
            }
        });
    }

    public void dibujo_documento(String texto,double x, double y, Canvas canvas, Figura figura) {
        if(Objects.equals(texto, "") || Objects.equals(texto, " ") || Objects.equals(texto, "  ") || Objects.equals(texto, "   ")){texto= " Documento ";}
        String finalTexto = texto;

        javafx.scene.text.Text text = new javafx.scene.text.Text(finalTexto);
        text.setFont(font);
        double textWidth = text.getBoundsInLocal().getWidth();
        double textHeight = text.getBoundsInLocal().getHeight();

        double width = Math.max(textWidth + 40, 60); // Ancho mínimo de 150
        double height = Math.max(textHeight + 40, 85); // Alto mínimo de 100
        double curveHeight = 20;

        canvas = new Canvas(width, height);
        Canvas finalCanvas= canvas;
        /*
        finalCanvas.setLayoutX(x);
        finalCanvas.setLayoutY(y);
         */
        double diferencia = figura.getDimenciones().getAncho() / 2;
        finalCanvas.setLayoutX((panel_Diagrama.getMinWidth() / 2) - diferencia);
        finalCanvas.setLayoutY(y);
        GraphicsContext gc = finalCanvas.getGraphicsContext2D();

        // Dibujar el cuerpo del documento (rectángulo)
        gc.setFill(Color.web("#c3c3c3"));
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

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

        // Escribir el texto en el centro del documento
        gc.setFont(font);
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(finalTexto, width / 2, height / 2);

        panel_Diagrama.getChildren().add(finalCanvas);
        //MOVIMIENTO_FIGURA----------------------------------------------------
        finalCanvas.setOnMousePressed(event -> {
            // Registrar las coordenadas del mouse en relación con la esquina superior izquierda de la figura
            previousX = event.getSceneX();
            previousY = event.getSceneY();
        });

        finalCanvas.setOnMouseDragged(event -> {
            basurero.setVisible(true);
            // Calcular el desplazamiento del mouse desde la última posición
            double deltaX = event.getSceneX() - previousX;
            double deltaY = event.getSceneY() - previousY;

            // Calcular las nuevas coordenadas para la figura basadas en el desplazamiento del mouse
            double newX = finalCanvas.getLayoutX() + deltaX;
            double newY = finalCanvas.getLayoutY() + deltaY;

            // Establecer las nuevas coordenadas de la figura
            finalCanvas.setLayoutX(newX);
            finalCanvas.setLayoutY(newY);

            // Actualizar la posición anterior del cursor
            previousX = event.getSceneX();
            previousY = event.getSceneY();
        });

        finalCanvas.setOnMouseReleased(event -> {
            double releaseX = event.getSceneX();
            double releaseY = event.getSceneY();
            Bounds basureroBounds = basurero.localToScene(basurero.getBoundsInLocal());

            // Verificar si las coordenadas del evento están dentro de los límites del Pane Basurero
            if (basureroBounds.contains(releaseX, releaseY)) {
                panel_Diagrama.getChildren().remove(finalCanvas);
            }
            basurero.setVisible(false);
        });
        //ESCRITURA_FIGURA----------------------------------------------------
        // Declarar una variable para contar los clics
        finalCanvas.setOnMouseClicked(event -> {
            clickCount++;

            // Si se ha dado doble clic
            if (clickCount == 2) {
                // Restablecer el contador
                clickCount = 0;

                double currentX = finalCanvas.getLayoutX();
                double currentY = finalCanvas.getLayoutY();

                // Tu código para habilitar la edición del contenido
                textContenido.setOpacity(1.0);
                textContenido.setDisable(false);
                textContenido.getStyleClass().add("Contenido_edit");
                textContenido.setLayoutX(currentX); // Ajustar según tus necesidades
                textContenido.setLayoutY(currentY); // Ajustar según tus necesidades
                textContenido.setMinWidth(width-100); // Ajustar según tus necesidades
                textContenido.setMinHeight(height); // Ajustar según tus necesidades
                textContenido.setText(finalTexto);
                // Establecer el color del texto como negro
                textContenido.setStyle("-fx-text-fill: black;");

                // Agregar evento de tecla para actualizar el contenido al presionar Enter
                textContenido.setOnKeyPressed(event_2 -> {
                    if (event_2.getCode() == KeyCode.ENTER) {
                        String newText = textContenido.getText();
                        gc.clearRect(0, 0, width, height); // Limpiar el canvas
                        gc.setFill(Color.web("#c3c3c3"));
                        gc.fillRect(0, 0, width, height); // Dibujar rectángulo relleno
                        gc.setStroke(Color.BLACK);
                        gc.setLineWidth(2);
                        gc.strokeRect(0, 0, width, height); // Dibujar borde del rectángulo
                        gc.setFill(Color.BLACK);
                        gc.setFont(font);
                        gc.setTextAlign(TextAlignment.CENTER);
                        gc.setTextBaseline(VPos.CENTER);
                        gc.fillText(newText, width / 2, height / 2);

                        panel_Diagrama.getChildren().remove(finalCanvas);
                        dibujo_documento(newText, currentX, currentY, finalCanvas,figura);
                        // Deshabilitar la edición del contenido
                        textContenido.clear();
                        textContenido.setOpacity(0.0);
                        textContenido.setDisable(true);
                    }
                });
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    clickCount = 0;
                }));
                timeline.play();
            }
        });
    }
    // Método para centrar el Pane basurero y el ícono del basurero
    private void centrarPane() {
        double basureroX = (panel_Diagrama.getWidth() - basurero.getWidth()) / 2;
        double basureroY = (panel_Diagrama.getHeight() - basurero.getHeight()) / 2;
        basurero.setLayoutX(basureroX);
        basurero.setLayoutY(basureroY);

        // Centrar el ícono del basurero dentro del Pane basurero
        double trashX = (basurero.getWidth() - trash.getFitWidth()) / 2;
        double trashY = (basurero.getHeight() - trash.getFitHeight()) / 2;
        trash.setLayoutX(trashX);
        trash.setLayoutY(trashY);
    }
}