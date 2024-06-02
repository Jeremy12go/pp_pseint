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
import javafx.scene.shape.Line;
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
    private Button borrarTodoButton;

    @FXML
    public void initialize() throws IOException {
        fondoCuadriculado(740,1500);
        figurasInicio_fin();

        Pseudocode.initializePseudocodeTab(pseudocodeTab,pseudocode);

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
        VG.cambiarUltimaFiguraAñadida((Figura) ins.getList_figuras().get(0));
        VG.cambiarUltimoCanvasFigura((Canvas)ins.getList_orden().get(0));
        VG.cambiarUltimoCanvasConexion((Canvas)ins.getList_orden().get(1));

    }

    Diagrama ins = Diagrama.getInstance();


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
        if (sourceDiagram == figura_condiconal || sourceDiagram == figura_documento || sourceDiagram == figura_entrada
                || sourceDiagram == figura_proceso || sourceDiagram == figura_salida || sourceDiagram == figura_mientras
                || sourceDiagram == figura_hacer_mientras || sourceDiagram == figura_para) {
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
        String contenido = "";
        Figura _inicial = (Figura) ins.getList_figuras().get(0);
        Canvas _final = obtenerCanvasDesdeFigura((Figura) ins.getList_figuras().get(ins.getList_figuras().size() - 1));

        //condicional que valide si las cordenadas estan dentro de lo razonable para agregar
        if ((panel_Diagrama.getWidth() / 2) - 150 < x && x < (panel_Diagrama.getWidth() / 2) + 150) {
            //if (_inicial.getVertice_conexion().getY() < y && y < _final.getLayoutY()+80) {

            if (figura_condiconal == sourceDiagram) {
                Vertice p_Fcondicional_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fcondicional_conexion = new Vertice(0, 0);
                Arista dimencion_Fentrada = new Arista(120, 70);
                contenido = " A > B ";
                ArrayList<String> contenidoValidado = new ArrayList<String>(); //Ajustar
                Condicional condicional = new Condicional(contenido, p_Fcondicional_direccion, p_Fcondicional_conexion, dimencion_Fentrada, contenidoValidado);
                Canvas canvas_Fcondicional = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY() + 25;
                condicional.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                dibujo_condicional(nuevaPosY, canvas_Fcondicional, condicional);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                ins.agregarElemento(condicional, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fcondicional) - 1;
                ins.agregarElemento(canvas_Fcondicional, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), condicional));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fcondicional);
                VG.cambiarUltimaFiguraAñadida(condicional);
                VG.cambiarUltimoCanvasFigura(canvas_Fcondicional);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(condicional);

            } else if (figura_documento == sourceDiagram) {
                Vertice p_Fdocumento_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fdocumento_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                Arista dimencion_Fentrada = new Arista(120, 70);
                contenido = " Documento ";
                Documento documento = new Documento(contenido, p_Fdocumento_direccion, p_Fdocumento_conexion, dimencion_Fentrada);
                Canvas canvas_Fdocumento = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY() + 16;
                documento.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                dibujo_documento(nuevaPosY, canvas_Fdocumento, documento);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                ins.agregarElemento(documento, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fdocumento) - 1;
                ins.agregarElemento(canvas_Fdocumento, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), documento));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fdocumento);
                VG.cambiarUltimaFiguraAñadida(documento);
                VG.cambiarUltimoCanvasFigura(canvas_Fdocumento);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(documento);

            } else if (figura_entrada == sourceDiagram) {
                Vertice p_Fentrada_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fentrada_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                contenido = " Entrada: ";
                Arista dimencion_Fentrada = new Arista(153, 50);
                Entrada entrada = new Entrada(contenido, p_Fentrada_direccion, p_Fentrada_conexion, dimencion_Fentrada);
                Canvas canvas_Fentrada = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY();
                entrada.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                dibujo_paralelogramo(canvas_Fentrada, entrada, 1);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                ins.agregarElemento(entrada, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fentrada) - 1;
                ins.agregarElemento(canvas_Fentrada, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), entrada));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fentrada);
                VG.cambiarUltimaFiguraAñadida(entrada);
                VG.cambiarUltimoCanvasFigura(canvas_Fentrada);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(entrada);

            } else if (figura_salida == sourceDiagram) {

                Vertice p_Fsalida_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fsalida_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                contenido = " Salida: ";
                Arista dimencion_Fsalida = new Arista(153, 50);
                Salida salida = new Salida(contenido, p_Fsalida_direccion, p_Fsalida_conexion, dimencion_Fsalida);
                Canvas canvas_Fsalida = new Canvas(dimencion_Fsalida.getAncho(), dimencion_Fsalida.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY();
                salida.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                dibujo_paralelogramo(canvas_Fsalida, salida, 0);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                ins.agregarElemento(salida, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fsalida) - 1;
                ins.agregarElemento(canvas_Fsalida, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), salida));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fsalida);
                VG.cambiarUltimaFiguraAñadida(salida);
                VG.cambiarUltimoCanvasFigura(canvas_Fsalida);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(salida);

            } else if (figura_proceso == sourceDiagram) {
                Vertice p_Fproceso_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fproeso_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                contenido = " Proceso ";
                Arista dimencion_Fproceso = new Arista(120, 40);
                String contenidoValidado = ""; //Ajustar
                ArrayList<String> operaciones = new ArrayList<String>(); //Ajustar
                ArrayList<String> operandos = new ArrayList<String>(); //Ajustar
                Proceso proceso = new Proceso(contenido, p_Fproceso_direccion, p_Fproeso_conexion, dimencion_Fproceso, contenidoValidado, operaciones, operandos);
                Canvas canvas_Fproceso = new Canvas(dimencion_Fproceso.getAncho(), dimencion_Fproceso.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY() + 25;
                proceso.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                dibujo_rectangulo(canvas_Fproceso, proceso);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                ins.agregarElemento(proceso, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fproceso) - 1;
                ins.agregarElemento(canvas_Fproceso, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), proceso));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fproceso);
                VG.cambiarUltimaFiguraAñadida(proceso);
                VG.cambiarUltimoCanvasFigura(canvas_Fproceso);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(proceso);

            } else if (figura_hacer_mientras == sourceDiagram) {
                Vertice p_Fhacer_mientras_direccion = new Vertice(32.5, 25);
                Vertice p_Fhacer_mientras_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                Arista dimencion_Fentrada = new Arista(120, 70);
                contenido = " Hacer Mientras ";
                ArrayList<String> contenidoValidado = new ArrayList<>();
                Hacer_Mientras hacer_mientras = new Hacer_Mientras(contenido, p_Fhacer_mientras_direccion, p_Fhacer_mientras_conexion, dimencion_Fentrada, contenidoValidado);
                Canvas canvas_Fhacer_mientras = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY() + 25;
                hacer_mientras.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                dibujo_hacerMientras(contenido, x, nuevaPosY , canvas_Fhacer_mientras, hacer_mientras);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                ins.agregarElemento(hacer_mientras, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fhacer_mientras) - 1;
                ins.agregarElemento(canvas_Fhacer_mientras, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), hacer_mientras));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fhacer_mientras);
                VG.cambiarUltimaFiguraAñadida(hacer_mientras);
                VG.cambiarUltimoCanvasFigura(canvas_Fhacer_mientras);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(hacer_mientras);

            } else if (figura_mientras == sourceDiagram) {
                Vertice p_Fcondicional_direccion = new Vertice(32.5, 25);
                Vertice p_Fcondicional_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                Arista dimencion_Fentrada = new Arista(120, 70);
                contenido = " Mientras ";
                ArrayList<String> contenidoValidado = new ArrayList<>();
                Mientras mientras = new Mientras(contenido, p_Fcondicional_direccion, p_Fcondicional_conexion, dimencion_Fentrada, contenidoValidado);
                Canvas canvas_Fmientras = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY() + 25;
                mientras.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                dibujo_mientras(contenido, x, nuevaPosY, canvas_Fmientras, mientras);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                ins.agregarElemento(mientras, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fmientras) - 1;
                ins.agregarElemento(canvas_Fmientras, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), mientras));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fmientras);
                VG.cambiarUltimaFiguraAñadida(mientras);
                VG.cambiarUltimoCanvasFigura(canvas_Fmientras);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(mientras);

            } else if (figura_para == sourceDiagram) {
                //Figura Para
            }
        }
    }

    public void moverfiguras(Figura figura){
        boolean condicion = false;
        Figura pre_figura = null;
        Canvas pre_canvas = null;
        int largo = ins.getList_orden().size();
        ArrayList<Canvas> arr_list_canvas = ins.getList_orden();
        for (int i = 0; i < largo; i++ ) {
            Figura figura_InList = obtenerFiguraDesdeCanvas(arr_list_canvas.get(i));
            if(figura_InList != null){
                if(figura == figura_InList){
                    condicion = true;
                    pre_figura = figura_InList;
                    pre_canvas = obtenerCanvasDesdeFigura(figura_InList);
                }
                if (condicion && figura_InList != figura){
                    Figura figura_final = obtenerFiguraDesdeCanvas(arr_list_canvas.get(largo-1));
                    if(figura_InList.getContenido() != figura_final.getContenido() ){
                        /*
                        //asignar nueva posicion a la figura
                        double nuevaPosY = pre_figura.getVertice_conexion().getY() + 100;
                        System.out.println("Coordenada_NuevaPosicion:"+nuevaPosY);
                        figura_InList.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));
                        System.out.printf("Prefigura:"+pre_figura.getContenido()+"\n");
                        System.out.printf("figura:"+figura_InList.getContenido()+"\n");
                        */
                        //mover el canvas en relacion a la figura previa
                        double cordenadas = pre_figura.getVertice_conexion().getY()+pre_figura.getDimenciones().getAlto()+106.5;
                        System.out.println("ConexionY_pF:"+pre_figura.getVertice_conexion().getY()+"  --  Dimension_pF:"+pre_figura.getDimenciones().getAlto()+"  --  Constante: 106.5"+"  --  total:"+cordenadas);
                        arr_list_canvas.get(i).setLayoutY(cordenadas);
                    }else{
                        arr_list_canvas.get(largo-1).setLayoutY(arr_list_canvas.get(i).getLayoutY()+arr_list_canvas.get(i).getHeight()-5);
                    }
                }
            }
            pre_figura = figura_InList;
            pre_canvas = arr_list_canvas.get(i);
        }
    }

    public void prueba(){
        int aux = 0;
        for(Object obj : ins.getList_orden()){
            if(obj instanceof Canvas){
                if(aux%2==1){
                    ((Canvas) obj).setLayoutX(((Canvas)obj).getLayoutX()-40);
                }
            }
            aux++;
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

    public int determinarIndiceFigura_InList_figuras(Figura figuraOrigen, double x, double y) {
        List<Figura> listaFiguras = ins.getList_figuras(); // Obtener la lista de figuras

        if (listaFiguras.isEmpty()) {
            // Si la lista está vacía, no hay figuras, devolver -1
            return -1;
        }

        int indiceFiguraDestino = -1; // Inicializar el índice de la figura destino como -1
        double distanciaMinima = Double.MAX_VALUE;

        for (int i = 0; i < listaFiguras.size(); i++) {
            Figura figura = listaFiguras.get(i);

            // Excluir la figura de origen de la búsqueda
            if (figura != figuraOrigen) {
                // Calcular la distancia entre el punto (x, y) y el punto de conexión de la figura
                double distancia = calcularDistancia(x, y, figura.getVertice_conexion().getX(), figura.getVertice_conexion().getY());

                // Si la distancia es menor que la distancia mínima actual, actualizar el índice y la distancia mínima
                if (distancia < distanciaMinima) {
                    indiceFiguraDestino = i;
                    distanciaMinima = distancia;
                }
            }
        }

        return indiceFiguraDestino;
    }

    public int determinarIndiceCanvas_InList_orden(Canvas canvas){
        int indiceCanvasDestino = 0;
        for(Object obj : ins.getList_orden()){
            if(obj instanceof Canvas){
                if(obj == canvas){
                    return indiceCanvasDestino;
                }
            }
            indiceCanvasDestino++;
        }
        return indiceCanvasDestino;
    }

    public double calcularDistancia(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public Canvas conectar(Figura figuraOrigen, Figura figuraDestino) {
        //canvas conector
        Vertice origen = new Vertice((figuraOrigen.getVertice_conexion().getX()), figuraOrigen.getVertice_conexion().getY()-figuraOrigen.getDimenciones().getAlto());
        Vertice destino = new Vertice(figuraDestino.getVertice_conexion().getX(),figuraDestino.getVertice_conexion().getY());

        double diferenciaY = 65;
        Canvas conectorCanvas = crear_canvasConector(diferenciaY,figuraOrigen.getVertice_conexion(), figuraDestino.getVertice_conexion());

        //posicionamiento del canvas
        double layoutX = (panel_Diagrama.getMinWidth() / 2) - 20;
        double layoutY = destino.getY()+figuraDestino.getDimenciones().getAlto()+40; //20 Ajustar la posición Y del conector

        conectorCanvas.setLayoutX(layoutX);
        conectorCanvas.setLayoutY(layoutY);

        // Agregar el nuevo canvas conector a la lista visual
        panel_Diagrama.getChildren().add(conectorCanvas);

        // Agregar el canvas conector a la lista de conectores
        Canvas canvasF = obtenerCanvasDesdeFigura(figuraDestino);
        int indice_C = determinarIndiceCanvas_InList_orden(canvasF);//+1
        ins.agregarElemento(conectorCanvas,1,indice_C);

        // Agregar el conector a la lista de conectores
        Conector conector = new Conector(figuraOrigen.getVertice_conexion(), figuraDestino.getVertice_conexion());
        ins.getList_conexiones().add(conector);
        return conectorCanvas;
    }

    private Figura obtenerFiguraDesdeCanvas(Canvas canvas) {
        ArrayList<Canvas> canvasList = ins.getList_orden();
        ArrayList<Figura> figuraList = ins.getList_figuras();
        int largo = ins.getList_figuras().size();

        int index = canvasList.indexOf(canvas);
        if (index != -1 && index < figuraList.size()) {
            return figuraList.get(index);
        }
        return (Figura)ins.getList_figuras().get(largo-1);
    }

    private Canvas obtenerCanvasDesdeFigura(Figura figura) {
        ArrayList<Canvas> list_orden = ins.getList_orden();
        ArrayList<Figura> list_figuras = ins.getList_figuras();
        ArrayList<Conector> list_conexiones = ins.getList_conexiones();
        int i = 0;
        int indexFigura = 0;
        for(Figura fig : list_figuras){
            if(figura == fig){
                indexFigura = i;
            }
            i++;
        }

        int indexCanvas = indexFigura * 2;
        // Verifica si el índice esta en la lista
        if (indexCanvas >= list_orden.size() || indexCanvas < 0) {
            return null;
        }
        return list_orden.get(indexCanvas);
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
    double tamaño_Lfechas = 3.5;
    double tamaño_Lconexiones = 0;
    //colores
    Color colorBordes = Color.web("#fc7c0c");
    Color colorRelleno = Color.web("#242c3c");
    Color colorTexto = Color.web("#ffffff");
    Color colorFlecha = Color.web("#ffffff");

    public void figurasInicio_fin(){
        TextField textContenido = new TextField();
        textContenido.setOpacity(0.0);
        textContenido.setDisable(true);

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
            panel_Diagrama.getChildren().add(textContenido);
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
                    panel_Diagrama.getChildren().remove(textContenido);
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
        diferencia=100;//distancia entre las figuras iniciales
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

        canvas.setLayoutX(figura.getVertice_conexion().getX() - 80);
        canvas.setLayoutY(figura.getVertice_conexion().getY() + 50);

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

        //editar contenido
        canvas.setOnMouseClicked(event -> {
            clickCount++;
            // Si se ha dado doble clic
            if (clickCount == 2) {
                // Restablecer el contador
                clickCount = 0;
                edición_paralelogramo(canvas,figura,tipo);
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    clickCount = 0;
                }));
                timeline.play();
            }
        });

    }

    public void edición_paralelogramo(Canvas canvas, Figura figura, int tipo){
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
        dibujo_paralelogramo(canvas,figura,tipo);

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

                //editar posicion en relacion al largo(mitad del panel)
                double _diferencia_ = figura.getDimenciones().getAncho()/2;
                textContenido.setLayoutX((panel_Diagrama.getWidth()/2)-_diferencia_);
                canvas.setLayoutX((panel_Diagrama.getWidth()/2)-_diferencia_);
                Vertice reajuste_v = new Vertice((panel_Diagrama.getMinWidth()/2)-_diferencia_,figura.getDimenciones().getAlto());
                figura.setVertice_conexion(reajuste_v);

                //redibujo
                limpiar_canvas(canvas);
                dibujo_paralelogramo(canvas,figura,tipo);
                textContenido.clear();
                panel_Diagrama.getChildren().remove(textContenido);
            }
        });
    }

    public void dibujo_condicional(double y, Canvas canvas, Figura figura){
        String finalTexto = figura.getContenido();
        javafx.scene.text.Text text = new javafx.scene.text.Text(figura.getContenido());

        double width = figura.getDimenciones().getAncho() / 2;
        double height = figura.getDimenciones().getAlto() / 2;
        double size = Math.max(width, height); // +40

        //posicion de la figura en relacion al AnchorPane
        double diferencia = figura.getDimenciones().getAncho() / 2;
        double centerX = (panel_Diagrama.getMinWidth() / 2) - diferencia + 42.5;
        canvas.setLayoutX(centerX);
        canvas.setLayoutY(y+50);
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
        gc.fillText(finalTexto, size/2 , (size / 2)+3);

        // Calcular coordenadas de los puntos de inicio y fin
        double startXLeft = centerX - size + 10; // Punto de inicio en el lado izquierdo del rombo

        double startY = y + size - 5; // Punto medio vertical del rombo

        double startXRight = centerX + size; // Punto de inicio en el lado derecho del rombo

        // Crear canvas conectores para las líneas hacia la izquierda y la derecha
        Canvas conectorIzquierda = crear_canvasConector2(startXLeft, startY, true);
        Canvas conectorDerecha = crear_canvasConector2(startXRight, startY, false);

        // Calcular las coordenadas para la línea horizontal entre las líneas verticales
        double startXHorizontal = startXLeft;
        double endXHorizontal = startXRight;
        double startYHorizontal = startY + 90; // Desplazamiento hacia abajo desde el punto medio vertical del rombo
        double endYHorizontal = startY + 90; // Desplazamiento hacia abajo desde el punto medio vertical del rombo

        // Crear la línea horizontal
        Line conectorHorizontal = new Line(startXHorizontal, startYHorizontal, endXHorizontal+50, endYHorizontal);
        conectorHorizontal.setStroke(Color.BLACK);

        // Calcular las coordenadas para la línea central
        double startXCentral = (startXHorizontal + endXHorizontal + 50) / 2;
        double endXCentral = (startXHorizontal + endXHorizontal + 50) / 2;
        double startYCentral = startYHorizontal;
        double endYCentral = startYHorizontal + 50;

        // Crear la línea central vertical
        Line conectorCentral = new Line(startXCentral, startYCentral, endXCentral, endYCentral);
        conectorCentral.setStroke(Color.BLACK);

        // Agregar los conectores al panel
        panel_Diagrama.getChildren().addAll(conectorIzquierda, conectorDerecha, conectorHorizontal, conectorCentral);


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
                edición_condicional(canvas, figura);
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    clickCount = 0;
                }));
                timeline.play();
            }
        });
    }

    public void edición_condicional(Canvas canvas, Figura figura){
        TextField textContenido = new TextField();
        textContenido.setOpacity(0.0);
        textContenido.setDisable(true);

        double size = Math.max(figura.getDimenciones().getAncho()/2, figura.getDimenciones().getAlto()/2);//+40
        double currentX = canvas.getLayoutX();
        double currentY = canvas.getLayoutY();

        // Habilitar la edición del contenido
        textContenido.setOpacity(1.0);
        textContenido.setDisable(false);
        panel_Diagrama.getChildren().add(textContenido);
        textContenido.getStyleClass().add("Contenido_edit");
        textContenido.setLayoutX(currentX+10); // Ajustar según tus necesidades
        textContenido.setLayoutY(currentY+15); // Ajustar según tus necesidades
        textContenido.setMinWidth(size); // Ajustar según tus necesidades
        textContenido.setMinHeight(size); // Ajustar según tus necesidades
        textContenido.setText(figura.getContenido());

        String pre_text = figura.getContenido();
        figura.setContenido("");
        limpiar_canvas(canvas);
        dibujo_condicional(currentY-25, canvas,figura);

        String newText = textContenido.getText();

        // Agregar evento de tecla para actualizar el contenido al presionar Enter
        textContenido.setOnKeyPressed(event_2 -> {
            if (event_2.getCode() == KeyCode.ENTER) {

                figura.setContenido(textContenido.getText());
                String new_text = textContenido.getText();
                double pre_dimension = figura.getDimenciones().getAncho();

                pre_dimension = figura.getDimenciones().getAncho();

                //recalculo de la dimensiones de la figura por contenido
                if (8 * new_text.length() + 25 <= 120) {
                    figura.getDimenciones().setAncho(120);
                    canvas.setWidth(120);
                    canvas.setHeight(120);
                    textContenido.setMinWidth(120);
                } else {
                    figura.getDimenciones().setAncho(6 * new_text.length() + 25);
                    canvas.setWidth(6 * new_text.length() + 25);
                    canvas.setHeight(6 * new_text.length());
                    textContenido.setMinWidth(canvas.getWidth() * 0.7);
                }

                //editar posicion en relacion al largo(mitad del panel)
                double _diferencia_ = figura.getDimenciones().getAncho() / 2;
                Vertice reajuste_v = new Vertice((panel_Diagrama.getMinWidth() / 2) - _diferencia_, figura.getDimenciones().getAlto());
                figura.setVertice_conexion(reajuste_v);
                //figura.setContenido(textContenido.getText());

                //redibujo
                limpiar_canvas(canvas);
                dibujo_condicional(currentY - 25, canvas, figura);
                textContenido.clear();
                panel_Diagrama.getChildren().remove(textContenido);
                panel_Diagrama.getChildren().remove(canvas);
                dibujo_condicional(currentY, canvas, figura);
                // Deshabilitar la edición del contenido
                textContenido.clear();
                textContenido.setOpacity(0.0);
                textContenido.setDisable(true);
            }
        });
    }

    public void dibujo_rectangulo(Canvas canvas, Figura figura) {
        String finalTexto = figura.getContenido();
        javafx.scene.text.Text text = new javafx.scene.text.Text(figura.getContenido());

        double width = figura.getDimenciones().getAncho()/2;
        double height = figura.getDimenciones().getAlto()/2;
        double size = Math.max(width, height)+20;

        //posicion de la figura en relacion al AnchorPane
        double diferencia = figura.getDimenciones().getAncho() / 2;
        canvas.setLayoutX((panel_Diagrama.getMinWidth() / 2) - diferencia+15);
        canvas.setLayoutY(figura.getVertice_conexion().getY() + 50);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dibujar el rectángulo
        gc.setFill(colorRelleno);
        gc.fillRect(0, 0, size+(size*0.5), size);
        gc.setStroke(colorBordes);
        gc.setLineWidth(tamaño_Lbordes*2);
        gc.strokeRect(0, 0, size+(size*0.5), size);

        //contenido
        gc.setLineWidth(tamañoTxt);
        gc.setFont(font);
        gc.setStroke(colorTexto);
        gc.setFill(colorTexto);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(figura.getContenido(), figura.getDimenciones().getAncho()/2, figura.getDimenciones().getAlto()/2);

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
                edición_rectangulo(canvas,figura);
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    clickCount = 0;
                }));
                timeline.play();
            }
        });
    }

    public void edición_rectangulo(Canvas canvas, Figura figura){
        TextField textContenido = new TextField();
        textContenido.setOpacity(0.0);
        textContenido.setDisable(true);

        double currentX = canvas.getLayoutX();
        double currentY = canvas.getLayoutY();
        double size = Math.max(figura.getDimenciones().getAncho()/2, figura.getDimenciones().getAlto()/2)+20;

        // Habilitar la edición del contenido
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
        dibujo_rectangulo(canvas,figura);

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

                //editar posicion en relacion al largo(mitad del panel)
                double _diferencia_ = figura.getDimenciones().getAncho()/2;
                Vertice reajuste_v = new Vertice((panel_Diagrama.getMinWidth()/2)-_diferencia_,figura.getDimenciones().getAlto());
                figura.setVertice_conexion(reajuste_v);
                //figura.setContenido(textContenido.getText());

                //redibujo
                limpiar_canvas(canvas);
                dibujo_rectangulo(canvas, figura);
                textContenido.clear();
                panel_Diagrama.getChildren().remove(textContenido);
            }
        });
    }

    public void dibujo_documento(double posY, Canvas canvas, Figura figura) {
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
        // Declarar una variable para contar los clics
        canvas.setOnMouseClicked(event -> {
            clickCount++;
            if (clickCount == 2) {
                // Restablecer el contador
                clickCount = 0;
                edición_rectangulo(canvas,figura);
            } else {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                    clickCount = 0;
                }));
                timeline.play();
            }
        });
    }

    public void edición_Documento(Canvas canvas, Figura figura){
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
        dibujo_documento(currentY-25, canvas,figura);

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
                dibujo_documento(currentY-25, canvas, figura);
                textContenido.clear();
                panel_Diagrama.getChildren().remove(textContenido);
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
        canvas.setLayoutY(y+50);
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
        //todo: separar funcionalidad a otro metodo
        TextField textContenido = new TextField();
        textContenido.setOpacity(0.0);
        textContenido.setDisable(true);
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
        canvas.setLayoutY(y+50);
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
        //todo: separar funcionalidad
        TextField textContenido = new TextField();
        textContenido.setOpacity(0.0);
        textContenido.setDisable(true);
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

    // MÉTODOS ASOCIADOS A CONDICIONAL
    public Canvas crear_canvasConector2(double startX, double startY, boolean isLeft) {
        Canvas conector = new Canvas();

        conector.setWidth(100);
        conector.setHeight(100);

        // Establecer la posición del canvas
        conector.setLayoutX(startX);
        conector.setLayoutY(startY - 5);

        GraphicsContext gc = conector.getGraphicsContext2D();

        // Dibujar la línea horizontal del conector
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        if (isLeft) {
            gc.strokeLine(0, 5, 50, 5);
            // Dibujar la línea vertical izquierda
            gc.strokeLine(0, 5, 0, 95);
        } else {
            gc.strokeLine(0, 5, 50, 5);
            // Dibujar la línea vertical derecha
            gc.strokeLine(50, 5, 50, 95);
        }

        return conector;
        //----------------------------------------------------------------------------------
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