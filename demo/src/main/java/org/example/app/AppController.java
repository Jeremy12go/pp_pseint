package org.example.app;
import Clases.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    Tab pseudocodeTab;
    @FXML
    Label pseudocode;
    @FXML
    Pane panel_menu;
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
    private ImageView figura_procesoE;
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
    private ImageView figura_condiconalE;
    @FXML
    private ImageView figura_condiconal;
    @FXML
    private ImageView figura_documentoE;
    @FXML
    private ImageView figura_documento;
    @FXML
    private ImageView figura_mientrasE;
    @FXML
    private ImageView figura_mientras;
    @FXML
    private ImageView figura_hacer_mientrasE;
    @FXML
    private ImageView figura_hacer_mientras;
    @FXML
    private ImageView figura_paraE;
    @FXML
    private ImageView figura_para;
    @FXML
    private ImageView trash;

    private Inicio_Fin figuraInicio;
    private Inicio_Fin figuraFin;
    private Canvas canvasInicio;
    private Canvas canvasFin;
    private Canvas conector;

    @FXML
    public void initialize() throws IOException {
        fondoCuadriculado(740,1500);
        figurasInicio_fin();

        Pseudocode.initializePseudocodeTab(pseudocodeTab, pseudocode);

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
        Image image7 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("figura_mientras.png")));
        figura_mientras.setImage(image7);
        figura_mientrasE.setImage(image7);
        Image image8 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("figura_hacer_mientras.png")));
        figura_hacer_mientras.setImage(image8);
        figura_hacer_mientrasE.setImage(image8);
        Image image9 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("figura_para.png")));
        figura_paraE.setImage(image9);
        figura_para.setImage(image9);
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
        String contenido = "";
        if (figura_condiconal == sourceDiagram) {
            Vertice p_Fcondicional_direccion = new Vertice(32.5, 25); //no cambiar
            Vertice p_Fcondicional_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y-40);
            Arista dimencion_Fentrada = new Arista(120, 70);
            contenido = " A > B ";
            ArrayList<String> contenidoValidado = new ArrayList<>();
            Condicional condicional = new Condicional(contenido, p_Fcondicional_direccion, p_Fcondicional_conexion, dimencion_Fentrada, contenidoValidado);

            Canvas canvas_Fcondicional = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());
            dibujo_condicional(contenido, x, y, canvas_Fcondicional, condicional);

            panel_Diagrama.getChildren().add(canvas_Fcondicional);
            ultimaFAñadida = condicional;
            ultimaCAñadida = canvas_Fcondicional;
            ins.getList_orden().add(canvas_Fcondicional);
            ins.getList_figuras().add(condicional);

            canvas_Fcondicional.setUserData(condicional);
            // Resto del código para conectar y ajustar la figura...
        } else if (figura_documento == sourceDiagram) {
            Vertice p_Fdocumento_direccion = new Vertice(32.5, 25); //no cambiar
            Vertice p_Fdocumento_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y-40);
            Arista dimencion_Fentrada = new Arista(120, 70);
            contenido= " Documento ";
            Documento documento = new Documento(contenido, p_Fdocumento_direccion, p_Fdocumento_conexion, dimencion_Fentrada);

            Canvas canvas_Fdocumento = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

            dibujo_documento(contenido,x, y, canvas_Fdocumento, documento);

            panel_Diagrama.getChildren().add(canvas_Fdocumento);
            ultimaFAñadida = documento;
            ultimaCAñadida = canvas_Fdocumento;
            ins.getList_orden().add(canvas_Fdocumento);
            ins.getList_figuras().add(documento);

            canvas_Fdocumento.setUserData(documento);
            // Resto del código para conectar y ajustar la figura...
        } else if (figura_entrada == sourceDiagram) {
            Vertice p_Fentrada_direccion = new Vertice(32.5, 25);
            Vertice p_Fentrada_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
            contenido = " Entrada: ";
            Arista dimencion_F = new Arista(153, 50);
            Entrada entrada = new Entrada(contenido, p_Fentrada_direccion, p_Fentrada_conexion, dimencion_F);

            Canvas canvas_Paralelogramo = new Canvas(dimencion_F.getAncho(), dimencion_F.getAlto());
            double diferencia = dimencion_F.getAncho() / 2;
            canvas_Paralelogramo.setLayoutX((panel_Diagrama.getMinWidth() / 2) - diferencia);
            canvas_Paralelogramo.setLayoutY(y);

            dibujo_paralelogramo(canvas_Paralelogramo, entrada, 1);

            panel_Diagrama.getChildren().add(canvas_Paralelogramo);
            ultimaFAñadida = entrada;
            ultimaCAñadida = canvas_Paralelogramo;
            ins.getList_orden().add(canvas_Paralelogramo);
            ins.getList_figuras().add(entrada);

            canvas_Paralelogramo.setUserData(entrada);
            // Resto del código para conectar y ajustar la figura...
        } else if (figura_salida == sourceDiagram) {
            Vertice p_Fsalida_direccion = new Vertice(32.5, 25);
            Vertice p_Fsalida_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
            contenido = " Salida: ";
            Arista dimencion_F = new Arista(153, 50);
            Salida salida = new Salida(contenido, p_Fsalida_direccion, p_Fsalida_conexion, dimencion_F);

            Canvas canvas_Paralelogramo = new Canvas(dimencion_F.getAncho(), dimencion_F.getAlto());
            double diferencia = dimencion_F.getAncho() / 2;
            canvas_Paralelogramo.setLayoutX((panel_Diagrama.getMinWidth() / 2) - diferencia);
            canvas_Paralelogramo.setLayoutY(y);

            dibujo_paralelogramo(canvas_Paralelogramo, salida, 0);

            panel_Diagrama.getChildren().add(canvas_Paralelogramo);
            ultimaFAñadida = salida;
            ultimaCAñadida = canvas_Paralelogramo;
            ins.getList_orden().add(canvas_Paralelogramo);
            ins.getList_figuras().add(salida);

            canvas_Paralelogramo.setUserData(salida);
            // Resto del código para conectar y ajustar la figura...
        } else if (figura_proceso == sourceDiagram) {
            Vertice p_Fproceso_direccion = new Vertice(32.5, 25);
            Vertice p_Fproeso_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
            contenido = " Proceso ";
            Arista dimencion_Fproceso = new Arista(120, 40);
            String contenidoValidado = "";
            ArrayList<String> operaciones = new ArrayList<>();
            ArrayList<String> operandos = new ArrayList<>();
            Proceso proceso = new Proceso(contenido, p_Fproceso_direccion, p_Fproeso_conexion, dimencion_Fproceso, contenidoValidado, operaciones, operandos);

            Canvas canvas_Fproceso = new Canvas(dimencion_Fproceso.getAncho(), dimencion_Fproceso.getAlto());
            double diferencia = dimencion_Fproceso.getAncho() / 2;
            canvas_Fproceso.setLayoutX((panel_Diagrama.getMinWidth() / 2) - diferencia);
            canvas_Fproceso.setLayoutY(y);

            dibujo_rectangulo(contenido, x, y, canvas_Fproceso, proceso);

            panel_Diagrama.getChildren().add(canvas_Fproceso);
            ultimaFAñadida = proceso;
            ultimaCAñadida = canvas_Fproceso;
            ins.getList_orden().add(canvas_Fproceso);
            ins.getList_figuras().add(proceso);

            canvas_Fproceso.setUserData(proceso);
            // Resto del código para conectar y ajustar la figura...
        } else if (figura_hacer_mientras == sourceDiagram) {
            Vertice p_Fcondicional_direccion = new Vertice(32.5, 25);
            Vertice p_Fcondicional_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
            Arista dimencion_Fentrada = new Arista(120, 70);
            contenido = " Hacer Mientras ";
            ArrayList<String> contenidoValidado = new ArrayList<>();
            Hacer_Mientras hacer_mientras = new Hacer_Mientras(contenido, p_Fcondicional_direccion, p_Fcondicional_conexion, dimencion_Fentrada, contenidoValidado);

            Canvas canvas_Fhacer_mientras = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());
            dibujo_hacerMientras(contenido, x, y, canvas_Fhacer_mientras, hacer_mientras);

            panel_Diagrama.getChildren().add(canvas_Fhacer_mientras);
            ultimaFAñadida = hacer_mientras;
            ultimaCAñadida = canvas_Fhacer_mientras;
            ins.getList_orden().add(canvas_Fhacer_mientras);
            ins.getList_figuras().add(hacer_mientras);

            canvas_Fhacer_mientras.setUserData(hacer_mientras);
            // Resto del código para conectar y ajustar la figura...
        } else if (figura_mientras == sourceDiagram) {
            Vertice p_Fcondicional_direccion = new Vertice(32.5, 25);
            Vertice p_Fcondicional_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
            Arista dimencion_Fentrada = new Arista(120, 70);
            contenido = " Mientras ";
            ArrayList<String> contenidoValidado = new ArrayList<>();
            Mientras mientras = new Mientras(contenido, p_Fcondicional_direccion, p_Fcondicional_conexion, dimencion_Fentrada, contenidoValidado);

            Canvas canvas_Fmientras = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());
            dibujo_mientras(contenido, x, y, canvas_Fmientras, mientras);

            panel_Diagrama.getChildren().add(canvas_Fmientras);
            ultimaFAñadida = mientras;
            ultimaCAñadida = canvas_Fmientras;
            ins.getList_orden().add(canvas_Fmientras);
            ins.getList_figuras().add(mientras);

            canvas_Fmientras.setUserData(mientras);
            // Resto del código para conectar y ajustar la figura...
        }  else if (figura_para== sourceDiagram) {
        //Figura Para
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
        Vertice origen = new Vertice((figuraOrigen.getVertice_conexion().getX()), figuraOrigen.getVertice_conexion().getY()-figuraOrigen.getDimenciones().getAlto());
        Vertice destino = new Vertice(figuraDestino.getVertice_conexion().getX(),figuraDestino.getVertice_conexion().getY());
        double diferenciaY = y - figuraDestino.getVertice_conexion().getY()-40;
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
    Font font = Font.font("Arial", FontWeight.BOLD, 11);
    double tamañoTxt = 1;
    double tamaño_Lbordes = 2;
    //colores
    Color colorBordes = Color.web("#fc7c0c");
    Color colorRelleno = Color.web("#242c3c");
    Color colorTexto = Color.web("#ffffff");

    public void figurasInicio_fin() {
        double cx = 32.5;
        double cy = 25;
        // Parámetros figura Inicio
        Vertice p_Finicio_cordenada = new Vertice(cx,cy); //no cambiar
        Vertice p_Finicio_conexion = new Vertice(0,0); //Reajustar
        String contenido = "Algoritmo titulo";
        Arista dimencion_Finicio = new Arista(153, 50);
        figuraInicio = new Inicio_Fin(contenido, p_Finicio_cordenada, p_Finicio_conexion, dimencion_Finicio);

        //considerar no salirse de las dimensiones del canvas
        canvasInicio = new Canvas(dimencion_Finicio.getAncho(), dimencion_Finicio.getAlto());
        //posición de la figura en relación al AnchorPane
        double diferencia = dimencion_Finicio.getAncho()/2;
        canvasInicio.setLayoutX((panel_Diagrama.getMinWidth()/2)-diferencia);
        canvasInicio.setLayoutY(p_Finicio_cordenada.getY());
        p_Finicio_conexion.setX(canvasInicio.getLayoutX());
        p_Finicio_conexion.setY(canvasInicio.getLayoutY()+dimencion_Finicio.getAlto());

        // Dibujo / diseño del Canvas
        dibujo_rect_curvo(canvasInicio, figuraInicio);

        // Parámetros figura Fin
        Vertice p_Ffin_direccion = new Vertice(cx,cy);
        Vertice p_Ffin_conexion = new Vertice(cx,cy);
        contenido="Fin Algoritmo";
        Arista dimencion_Ffin = new Arista(8*contenido.length()+25, 50);
        figuraFin = new Inicio_Fin(contenido, p_Ffin_direccion, p_Ffin_conexion, dimencion_Ffin);
        diferencia += 250; //distancia entre las figuras iniciales
        canvasFin = new Canvas(dimencion_Ffin.getAncho(), dimencion_Ffin.getAlto());
        canvasFin.setLayoutX((panel_Diagrama.getMinWidth()/2)-70);
        canvasFin.setLayoutY(p_Finicio_cordenada.getY() + diferencia);
        p_Ffin_conexion = new Vertice(canvasFin.getLayoutX(), canvasFin.getLayoutY() + dimencion_Ffin.getAlto());
        figuraFin.setVertice_conexion(p_Ffin_conexion);

        // Dibujo / diseño del Canvas
        dibujo_rect_curvo(canvasFin, figuraFin);

        // Conectar
        double distancia = (canvasFin.getLayoutY() - canvasInicio.getLayoutY()) / 2;
        Vertice nueva_conexion = new Vertice((canvasFin.getLayoutX() / 2), canvasInicio.getLayoutY() + distancia);
        Conector conector_inicial = new Conector(p_Finicio_conexion, p_Ffin_conexion);
        conector = crear_canvasConector(diferencia - 35, p_Finicio_conexion, p_Ffin_conexion);
        conector.setLayoutX((panel_Diagrama.getMinWidth()/2)-20);
        conector.setLayoutY(p_Finicio_cordenada.getY() + 50);

        panel_Diagrama.getChildren().addAll(canvasInicio, canvasFin, conector);
        ins.agregarElemento(figuraInicio, 0, 0);
        ins.agregarElemento(figuraFin, 0, 0);
        ins.agregarElemento(conector_inicial, 0, 0);
        ins.agregarElemento(canvasInicio, 0, 0);
        ins.agregarElemento(conector, 0, 0);
        ins.agregarElemento(canvasFin, 0, 0);
    }

    public  void dibujo_rect_curvo(Canvas canvas, Figura figura) {

        ArrayList<Vertice> vertices = calcular_vertices(figura);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Rellenar figura
        gc.setFill(colorRelleno);
        double angulo = -10;
        double longitud = 10;
        double destinoX_L2 = vertices.get(1).getX() + longitud * Math.cos(Math.toRadians(angulo));
        double destinoY_L2 = vertices.get(1).getY() + 2.5 + longitud * -Math.sin(Math.toRadians(angulo));
        double destinoX_L3 = destinoX_L2 - 3 + longitud * Math.cos(Math.toRadians(angulo));
        double destinoY_L3 = destinoY_L2 + 5 + longitud * -Math.sin(Math.toRadians(angulo));
        double destinoX_L4 = destinoX_L3 - 15 + longitud * Math.cos(Math.toRadians(angulo));
        double destinoY_L4 = destinoY_L3 + 7.5 + longitud * -Math.sin(Math.toRadians(angulo));

        double destinoX_L7 = vertices.get(3).getX() - 26 + longitud * Math.cos(Math.toRadians(-angulo));
        double destinoY_L7 = vertices.get(3).getY() - 2 + longitud * -Math.sin(Math.toRadians(-angulo));
        double destinoX_L8 = destinoX_L7 - 14.5 + longitud * Math.cos(Math.toRadians(-angulo));
        double destinoY_L8 = destinoY_L7 - 7 + longitud * -Math.sin(Math.toRadians(-angulo));
        double destinoX_L9 = destinoX_L8 - 3.5 + longitud * Math.cos(Math.toRadians(-angulo));
        double destinoY_L9 = destinoY_L8 - 7 + longitud * -Math.sin(Math.toRadians(-angulo));

        double[] xPoints = {vertices.get(0).getX() + 20, vertices.get(1).getX() - 18.5, destinoX_L2 - 18.5, destinoX_L3 - 18.5,
                destinoX_L4 - 18.5, vertices.get(2).getX() - 18.5, vertices.get(3).getX() + 15, destinoX_L7 + 20, destinoX_L8 + 20,
                destinoX_L9 + 20};

        double[] yPoints = {vertices.get(0).getY(), vertices.get(1).getY(), destinoY_L2, destinoY_L3, destinoY_L4,
                vertices.get(2).getY(), vertices.get(3).getY(), destinoY_L7, destinoY_L8, destinoY_L9};

        gc.fillPolygon(xPoints, yPoints, xPoints.length);

        //lineas Horizontales
        gc.setLineWidth(tamaño_Lbordes);
        gc.setStroke(colorBordes);
        gc.strokeLine(vertices.get(0).getX() + 20, vertices.get(0).getY(), vertices.get(1).getX() - 20, vertices.get(1).getY());
        gc.strokeLine(vertices.get(2).getX() - 20, vertices.get(2).getY(), vertices.get(3).getX() + 20, vertices.get(3).getY());
        //curvas laterales
        gc.strokeArc(vertices.get(0).getX(), vertices.get(0).getY(), vertices.get(3).getX() + 12,
                vertices.get(3).getY() - 25, 90, 180, ArcType.OPEN);
        gc.strokeArc(vertices.get(0).getX() + figura.getDimenciones().getAncho() - 80, vertices.get(0).getY(),
                vertices.get(3).getX() + 12, vertices.get(3).getY() - 25, 270, 180, ArcType.OPEN);

        //contenido
        gc.setLineWidth(tamañoTxt);
        gc.setFont(font);
        gc.setStroke(colorTexto);
        gc.setFill(colorTexto);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(figura.getContenido(), (figura.getDimenciones().getAncho()/ 2)+15, figura.getDimenciones().getAlto()-15);
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
        gc.setLineWidth(tamañoTxt);
        gc.setFont(font);
        gc.setStroke(colorTexto);
        gc.setFill(colorTexto);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(figura.getContenido(), (figura.getDimenciones().getAncho()/ 2)+15, figura.getDimenciones().getAlto()-15);

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

        //ESCRITURA_FIGURA----------------------------------------------------
        canvas.setOnMouseClicked(event -> {
            clickCount++;
            // Si se ha dado doble clic
            if (clickCount == 2) {
                // Restablecer el contador
                clickCount = 0;

                double currentX = canvas.getLayoutX();
                double currentY = canvas.getLayoutY();
                // Habilitar la edición del contenido
                textContenido.setOpacity(1.0);
                textContenido.setDisable(false);
                textContenido.getStyleClass().add("Contenido_edit");
                textContenido.setLayoutX(currentX); // Ajustar según tus necesidades
                textContenido.setLayoutY(currentY); // Ajustar según tus necesidades
                textContenido.setMinWidth(canvas.getWidth()); // Ajustar según tus necesidades
                textContenido.setMinHeight(canvas.getHeight()); // Ajustar según tus necesidades
                textContenido.setText(figura.contenido);
                // Establecer el color del texto como negro

                // Agregar evento de tecla para actualizar el contenido al presionar Enter
                textContenido.setOnKeyPressed(event_2 -> {
                    if (event_2.getCode() == KeyCode.ENTER) {
                        String newText = textContenido.getText();
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Limpiar el canvas
                        gc.setFill(Color.RED);
                        gc.fillPolygon(xPoints, yPoints, 4);
                        gc.setStroke(Color.BLACK);
                        gc.setLineWidth(2);
                        gc.strokePolygon(xPoints, yPoints, 4);
                        gc.setFill(Color.BLACK);
                        gc.setFont(font);
                        gc.setTextAlign(TextAlignment.CENTER);
                        gc.setTextBaseline(VPos.CENTER);
                        gc.fillText(newText, canvas.getWidth() /2, canvas.getHeight()/2);

                        panel_Diagrama.getChildren().remove(canvas);
                        dibujo_paralelogramo(canvas, figura, tipo);
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

    public void dibujo_condicional(String texto, double x, double y, Canvas canvas, Figura figura) {
        // Crear un objeto Text para calcular el ancho del texto
        if (Objects.equals(texto, "") || Objects.equals(texto, " ") || Objects.equals(texto, "  ") || Objects.equals(texto, "   ")) {
            texto = " A > B ";
        }
        String finalTexto = texto;

        javafx.scene.text.Text text = new javafx.scene.text.Text(finalTexto);

        double width = figura.getDimenciones().getAncho() / 2;
        double height = figura.getDimenciones().getAlto() / 2;
        double size = Math.max(width, height); // +40

        // Posición de la figura en relación al AnchorPane
        double diferencia = figura.getDimenciones().getAncho() / 2;
        double centerX = (panel_Diagrama.getMinWidth() / 2) - diferencia + 42.5;
        canvas.setLayoutX(centerX);
        canvas.setLayoutY(y + 24);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Calcular los puntos del rombo
        double[] xPoints = {size / 2, size, size / 2, 0};
        double[] yPoints = {0, size / 2, size, size / 2};
        gc.setFill(colorRelleno);
        gc.fillPolygon(xPoints, yPoints, 4);
        gc.setStroke(colorBordes);
        gc.setLineWidth(tamaño_Lbordes);
        gc.strokePolygon(xPoints, yPoints, 4);

        // Escribir el texto en el centro del rombo
        gc.setFont(font);
        gc.setFill(colorTexto);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(finalTexto, size / 2, (size / 2) + 3); // Ajustar la posición vertical

        double startX = x - size / 2; // Coordenada X del extremo superior izquierdo del rombo
        double startY = y - size / 2; // Coordenada Y del extremo superior izquierdo del rombo





        // Dibujar rectángulos para las acciones
        double rectWidth = 80;
        double rectHeight = 40;
        double rectYOffset = 80;

        Canvas canvasYes = new Canvas(rectWidth, rectHeight);
        Canvas canvasNo = new Canvas(rectWidth, rectHeight);

        // Posicionar los rectángulos
        double rectPosXYes = centerX - (rectWidth / 2) - 100; // Ajuste según tu necesidad
        double rectPosXNo = centerX - (rectWidth / 2) + 150;  // Ajuste según tu necesidad

        // Calcular las coordenadas del centro de la parte inferior de la figura condicional
        double startCX = centerX;
        double startCY = y + size;

        // Calcular las coordenadas de destino para las líneas "Sí" y "No"
        double endXYes = rectPosXYes + (rectWidth / 2); // Centro del rectángulo de "Sí"
        double endXNo = rectPosXNo + (rectWidth / 2);   // Centro del rectángulo de "No"
        double endY = startY + 50; // Punto medio vertical donde las líneas "Sí" y "No" deberían terminar

        // Dibujar líneas de las ramas "Sí" y "No"
        gc.setLineWidth(2);
        gc.setStroke(colorBordes);

        gc.strokeLine(startCX, startCY, endXYes, endY); // Línea "Sí"
        gc.strokeLine(startCX, startCY, endXNo, endY);  // Línea "No"

        gc.strokeLine(startX, startY, endXYes, endY); // Línea de prueba entre la figura condicional y "Sí"



        // Dibujar texto "Sí" y "No"
        gc.fillText("Sí", endXYes, endY - 10);
        gc.fillText("No", endXNo, endY - 10);


        canvasYes.setLayoutX(rectPosXYes);
        canvasYes.setLayoutY(endY + rectYOffset);
        canvasNo.setLayoutX(rectPosXNo);
        canvasNo.setLayoutY(endY + rectYOffset);


        dibujo_rectangulo("Acción Sí", rectPosXYes, endY + rectYOffset, canvasYes, new Figura());
        dibujo_rectangulo("Acción No", rectPosXNo, endY + rectYOffset, canvasNo, new Figura());
        // Agregar los rectángulos al panel
        panel_Diagrama.getChildren().add(canvasYes);
        panel_Diagrama.getChildren().add(canvasNo);


        // MOVIMIENTO_FIGURA----------------------------------------------------
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

        // ESCRITURA_FIGURA----------------------------------------------------
        canvas.setOnMouseClicked(event -> {
            clickCount++;
            // Si se ha dado doble clic
            if (clickCount == 2) {
                // Restablecer el contador
                clickCount = 0;

                double currentX = canvas.getLayoutX();
                double currentY = canvas.getLayoutY();
                // Habilitar la edición del contenido
                textContenido.setOpacity(1.0);
                textContenido.setDisable(false);
                textContenido.getStyleClass().add("Contenido_edit");
                textContenido.setLayoutX(currentX); // Ajustar según tus necesidades
                textContenido.setLayoutY(currentY); // Ajustar según tus necesidades
                textContenido.setMinWidth(size); // Ajustar según tus necesidades
                textContenido.setMinHeight(size); // Ajustar según tus necesidades
                textContenido.setText(finalTexto);

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
                        dibujo_condicional(newText, currentX, currentY, canvas, figura);
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


    public void dibujo_rectangulo(String texto, double x, double y, Canvas canvas, Figura figura) {
        // Verificar y inicializar las dimensiones de la figura si es necesario
        if (figura.getDimenciones() == null) {
            figura.setDimenciones(new Arista(120, 70)); // Valores por defecto
        }

        // Crear un objeto Text para calcular el ancho del texto
        if (Objects.equals(texto, "") || Objects.equals(texto, " ") || Objects.equals(texto, "  ") || Objects.equals(texto, "   ")) {
            texto = "Acción";
        }
        String finalTexto = texto;

        // Obtener las dimensiones de la figura
        double width = figura.getDimenciones().getAncho();
        double height = figura.getDimenciones().getAlto();

        // Posición de la figura en relación al AnchorPane
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dibujar el rectángulo
        gc.setFill(colorRelleno);
        gc.fillRect(0, 0, width, height);
        gc.setStroke(colorBordes);
        gc.setLineWidth(tamaño_Lbordes * 2);
        gc.strokeRect(0, 0, width, height);

        // Contenido
        gc.setLineWidth(tamañoTxt);
        gc.setFont(font);
        gc.setStroke(colorTexto);
        gc.setFill(colorTexto);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(figura.getContenido(), width / 2, height / 2);

        // Movimiento de la figura
        canvas.setOnMousePressed(event -> {
            previousX = event.getSceneX();
            previousY = event.getSceneY();
        });

        canvas.setOnMouseDragged(event -> {
            basurero.setVisible(true);
            double deltaX = event.getSceneX() - previousX;
            double deltaY = event.getSceneY() - previousY;
            double newX = canvas.getLayoutX() + deltaX;
            double newY = canvas.getLayoutY() + deltaY;
            canvas.setLayoutX(newX);
            canvas.setLayoutY(newY);
            previousX = event.getSceneX();
            previousY = event.getSceneY();
        });

        canvas.setOnMouseReleased(event -> {
            double releaseX = event.getSceneX();
            double releaseY = event.getSceneY();
            Bounds basureroBounds = basurero.localToScene(basurero.getBoundsInLocal());
            if (basureroBounds.contains(releaseX, releaseY)) {
                panel_Diagrama.getChildren().remove(canvas);
            }
            basurero.setVisible(false);
        });

        // Escritura de la figura
        canvas.setOnMouseClicked(event -> {
            clickCount++;
            if (clickCount == 2) {
                clickCount = 0;
                double currentX = canvas.getLayoutX();
                double currentY = canvas.getLayoutY();
                textContenido.setOpacity(1.0);
                textContenido.setDisable(false);
                textContenido.getStyleClass().add("Contenido_edit");
                textContenido.setLayoutX(currentX);
                textContenido.setLayoutY(currentY);
                textContenido.setMinWidth(width);
                textContenido.setMinHeight(height);
                textContenido.setText(finalTexto);

                textContenido.setOnKeyPressed(event_2 -> {
                    if (event_2.getCode() == KeyCode.ENTER) {
                        String newText = textContenido.getText();
                        gc.clearRect(0, 0, width, height);
                        gc.setFill(colorRelleno);
                        gc.fillRect(0, 0, width, height);
                        gc.setStroke(colorBordes);
                        gc.setLineWidth(2);
                        gc.strokeRect(0, 0, width, height);
                        gc.setFill(colorBordes);
                        gc.setFont(font);
                        gc.setTextAlign(TextAlignment.CENTER);
                        gc.setTextBaseline(VPos.CENTER);
                        gc.fillText(newText, width / 2, height / 2);

                        panel_Diagrama.getChildren().remove(canvas);
                        dibujo_rectangulo(newText, currentX, currentY, canvas, figura);
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

        double width = figura.getDimenciones().getAncho();
        double height = figura.getDimenciones().getAlto();
        double curveHeight = 20;

        //posicion de la figura en relacion al AnchorPane
        double diferencia = figura.getDimenciones().getAncho() / 2;
        canvas.setLayoutX((panel_Diagrama.getMinWidth() / 2) - diferencia+15);
        canvas.setLayoutY(y+25);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dibujar el cuerpo del documento (rectángulo)
        gc.setFill(colorRelleno);
        gc.setStroke(colorBordes);
        gc.setLineWidth(tamaño_Lbordes);

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
        gc.setStroke(colorTexto);
        gc.setFill(colorTexto);
        gc.setLineWidth(tamañoTxt);
        gc.setFont(font);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(finalTexto, width / 2, (height / 2)-20);



        //MOVIMIENTO_FIGURA----------------------------------------------------
        canvas.setOnMousePressed(event -> {
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
            canvas.setLayoutY(newY+23);

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
        //ESCRITURA_FIGURA----------------------------------------------------
        // Declarar una variable para contar los clics
        canvas.setOnMouseClicked(event -> {
            clickCount++;
            if (clickCount == 2) {
                // Restablecer el contador
                clickCount = 0;

                double currentX = canvas.getLayoutX();
                double currentY = canvas.getLayoutY();

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

                        panel_Diagrama.getChildren().remove(canvas);
                        dibujo_documento(newText, currentX, currentY, canvas,figura);
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

    public void dibujo_mientras(String texto,double x, double y, Canvas canvas, Figura figura){
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
        canvas.setLayoutY(y+24);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Calcular los puntos del rombo
        double[] xPoints = {size / 2, size, size / 2, 0};
        double[] yPoints = {0, size / 2, size, size / 2};
        gc.setFill(colorRelleno);
        gc.fillPolygon(xPoints, yPoints, 4);
        gc.setStroke(colorBordes);
        gc.setLineWidth(tamaño_Lbordes);
        gc.strokePolygon(xPoints, yPoints, 4);

        // Escribir el texto en el centro del rombo
        gc.setFont(font);
        gc.setFill(colorTexto);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(finalTexto, size/2 , (size / 2)+3); // Ajustar la posición vertical

        double startX = x - size / 2; // Coordenada X del extremo superior izquierdo del rombo
        double startY = y - size / 2; // Coordenada Y del extremo superior izquierdo del rombo
        double edgeLength = 50.0; // Longitud de las aristas

        // Calcular los puntos de las aristas
        double rightEdgeX = startX + size; // Extremo derecho del rombo
        double leftEdgeX = startX; // Extremo izquierdo del rombo
        double midY = startY; // Punto medio vertical del rombo

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
        //ESCRITURA_FIGURA----------------------------------------------------
        canvas.setOnMouseClicked(event -> {
            clickCount++;
            // Si se ha dado doble clic
            if (clickCount == 2) {
                // Restablecer el contador
                clickCount = 0;

                double currentX = canvas.getLayoutX();
                double currentY = canvas.getLayoutY();
                // Habilitar la edición del contenido
                textContenido.setOpacity(1.0);
                textContenido.setDisable(false);
                textContenido.getStyleClass().add("Contenido_edit");
                textContenido.setLayoutX(currentX); // Ajustar según tus necesidades
                textContenido.setLayoutY(currentY); // Ajustar según tus necesidades
                textContenido.setMinWidth(size); // Ajustar según tus necesidades
                textContenido.setMinHeight(size); // Ajustar según tus necesidades
                textContenido.setText(finalTexto);

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
                        dibujo_mientras(newText, currentX, currentY,canvas,figura);
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

    public void dibujo_hacerMientras(String texto,double x, double y, Canvas canvas, Figura figura){
        // Crear un objeto Text para calcular el ancho del texto
        if(Objects.equals(texto, "") || Objects.equals(texto, " ") || Objects.equals(texto, "  ") || Objects.equals(texto, "   ")){texto= " Hacer Mientras ";}
        String finalTexto = texto;

        javafx.scene.text.Text text = new javafx.scene.text.Text(finalTexto);

        double width = figura.getDimenciones().getAncho()/2;
        double height = figura.getDimenciones().getAlto()/2;
        double size = Math.max(width, height);//+40

        //posicion de la figura en relacion al AnchorPane
        double diferencia = figura.getDimenciones().getAncho() / 2;
        canvas.setLayoutX((panel_Diagrama.getMinWidth() / 2) - diferencia + 42.5);
        canvas.setLayoutY(y+24);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Calcular los puntos del rombo
        double[] xPoints = {size / 2, size, size / 2, 0};
        double[] yPoints = {0, size / 2, size, size / 2};
        gc.setFill(colorRelleno);
        gc.fillPolygon(xPoints, yPoints, 4);
        gc.setStroke(colorBordes);
        gc.setLineWidth(tamaño_Lbordes);
        gc.strokePolygon(xPoints, yPoints, 4);

        // Escribir el texto en el centro del rombo
        gc.setFont(font);
        gc.setFill(colorTexto);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(finalTexto, size/2 , (size / 2)+3); // Ajustar la posición vertical

        double startX = x - size / 2; // Coordenada X del extremo superior izquierdo del rombo
        double startY = y - size / 2; // Coordenada Y del extremo superior izquierdo del rombo
        double edgeLength = 50.0; // Longitud de las aristas

        // Calcular los puntos de las aristas
        double rightEdgeX = startX + size; // Extremo derecho del rombo
        double leftEdgeX = startX; // Extremo izquierdo del rombo
        double midY = startY; // Punto medio vertical del rombo

        // Dibujar arista derecha
        gc.strokeLine(rightEdgeX, midY, rightEdgeX + edgeLength, midY);

        // Dibujar arista izquierda
        gc.strokeLine(leftEdgeX, midY, leftEdgeX - edgeLength, midY);

        // Calcular el punto de inicio de la flecha en el borde derecho de la figura
        double startArrowRightX = startX + size;
        double startArrowRightY = startY + size / 2;

        // Calcular el punto final de la flecha en la parte inferior
        double endArrowBottomX = startX + size / 2;
        double endArrowBottomY = startY + size;


        // Dibujar la línea principal de la flecha
        gc.strokeLine(startArrowRightX, startArrowRightY, endArrowBottomX, endArrowBottomY);

        // Calcular las coordenadas de la punta de la flecha
        double arrowHeadSize = 10; // Tamaño de la punta de la flecha
        double arrowAngle = Math.atan2(endArrowBottomY - startArrowRightY, endArrowBottomX - startArrowRightX);
        double arrowHeadX1 = endArrowBottomX - arrowHeadSize * Math.cos(arrowAngle - Math.PI / 6);
        double arrowHeadY1 = endArrowBottomY - arrowHeadSize * Math.sin(arrowAngle - Math.PI / 6);
        double arrowHeadX2 = endArrowBottomX - arrowHeadSize * Math.cos(arrowAngle + Math.PI / 6);
        double arrowHeadY2 = endArrowBottomY - arrowHeadSize * Math.sin(arrowAngle + Math.PI / 6);

    // Dibujar la punta de la flecha
        gc.strokeLine(endArrowBottomX, endArrowBottomY, arrowHeadX1, arrowHeadY1);
        gc.strokeLine(endArrowBottomX, endArrowBottomY, arrowHeadX2, arrowHeadY2);

        //MOVIMIENTO_FIGURA----------------------------------------------------
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
        //ESCRITURA_FIGURA----------------------------------------------------
        canvas.setOnMouseClicked(event -> {
            clickCount++;
            // Si se ha dado doble clic
            if (clickCount == 2) {
                // Restablecer el contador
                clickCount = 0;

                double currentX = canvas.getLayoutX();
                double currentY = canvas.getLayoutY();
                // Habilitar la edición del contenido
                textContenido.setOpacity(1.0);
                textContenido.setDisable(false);
                textContenido.getStyleClass().add("Contenido_edit");
                textContenido.setLayoutX(currentX); // Ajustar según tus necesidades
                textContenido.setLayoutY(currentY); // Ajustar según tus necesidades
                textContenido.setMinWidth(size); // Ajustar según tus necesidades
                textContenido.setMinHeight(size); // Ajustar según tus necesidades
                textContenido.setText(finalTexto);

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
                        dibujo_hacerMientras(newText, currentX, currentY,canvas,figura);
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

    @FXML
    private void borrarTodo() {
        // Filtrar las figuras que no deben ser borradas
        List<Node> figurasNoBorrar = List.of(canvasInicio, canvasFin, conector);

        // Limpiar el panel
        panel_Diagrama.getChildren().retainAll(figurasNoBorrar);

        // Limpiar la lista de figuras y conectores en la instancia ins, excepto las figuras que no deben ser borradas
        ins.getList_figuras().removeIf(figura -> figura != figuraInicio && figura != figuraFin);
        ins.getList_conexiones().clear(); // Suponiendo que ins es la instancia de tu clase que almacena las conexiones
        ins.getList_orden().retainAll(figurasNoBorrar);
    }

    @FXML
    private void guardarApseudocode() {
        Pseudocode.generatePseudocode(panel_Diagrama, pseudocode);
    }

}