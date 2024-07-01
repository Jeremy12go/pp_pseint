package org.example.app;
import Clases.*;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Scale;

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
    @FXML
    private Button borrarTodoButton;
    @FXML
    private boolean maximizar;

    private Inicio_Fin figuraInicio;
    private Inicio_Fin figuraFin;
    private Canvas canvasInicio;
    private Canvas canvasFin;
    private Canvas conector;

    @FXML
    public void initialize() throws IOException {
        Diagrama.setIns(new Diagrama<>());
        fondoCuadriculado(740,1500);
        VG.setNumero_figura(-2);
        Inicio_Fin.Figuras_Iniciales(panel_Diagrama);
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

        Scale escalaTransformacion = new Scale(1,1,0,0);
        Scale escalaReset = new Scale(1,1,0,0);
        panel_Diagrama.getTransforms().addAll(escalaTransformacion,escalaReset);

        /*
        VG.setTextContenido(new TextField());
        VG.getTextContenido().setOpacity(0.0);
        VG.getTextContenido().setDisable(true);
        panel_Diagrama.getChildren().add(VG.getTextContenido());*/
        VG.cambiarUltimaFiguraAñadida((Figura) Diagrama.getIns().getList_figuras().get(0));
        VG.cambiarUltimoCanvasFigura((Canvas)Diagrama.getIns().getList_orden().get(0));
        VG.cambiarUltimoCanvasConexion((Canvas)Diagrama.getIns().getList_orden().get(1));
        VG.setUltimoIndiceConexion(0);

        VG.setBasurero(basurero);

        //parametros
        VG.setClickCount(0);
        VG.setPreviousX(0);
        VG.setPreviousY(0);
        VG.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        VG.setTamañoTxt(1);
        VG.setTamaño_Lbordes(2);
        VG.setTamaño_Lfechas(3.5);
        VG.setTamaño_Lconexiones(0);

        //colores
        VG.setColorBordes(Color.web("#fc7c0c"));
        VG.setColorRelleno(Color.web("#242c3c"));
        VG.setColorTexto(Color.web("#ffffff"));
        VG.setColorFlecha(Color.web("#ffffff"));
        borrarTodo();
    }

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

    //FUNCIONES------------------------------------------------------------------------------
    @FXML
    private void borrarTodo() {
        try{
            // Limpiar listas de elementos
            panel_Diagrama.getChildren().clear();
            Diagrama.getIns().getList_orden().clear();
            Diagrama.getIns().getList_figuras().clear();
            Diagrama.getIns().getList_conexiones().clear();

            //colocar elementos iniciales
            VG.setNumero_figura(-2);
            Inicio_Fin.Figuras_Iniciales(panel_Diagrama);

            VG.cambiarUltimaFiguraAñadida((Figura)Diagrama.getIns().getList_figuras().get(0));
            VG.cambiarUltimoCanvasFigura((Canvas)Diagrama.getIns().getList_orden().get(0));
            VG.cambiarUltimoCanvasConexion((Canvas)Diagrama.getIns().getList_orden().get(1));
            VG.setUltimoIndiceConexion(0);

        }catch (NullPointerException e){
            System.out.println("Ups... DLC \'borrar todo\' debe adquirirse por separado :)");
        }
    }
    @FXML
    private void guardarApseudocode() {
        Pseudocode.generatePseudocode(panel_Diagrama, pseudocode);
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

    //-------------------------------------------------------------------------------------------------------------
    private double[] obtenerCoordenadas(MouseEvent event) {
        double x = event.getSceneX() - panel_Diagrama.getLayoutX() - 210;
        double y = event.getSceneY() - panel_Diagrama.getLayoutY() - 65;
        return new double[]{x, y};
    }

    private double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    public double ubicacionY_newFigura(double y) {
        ArrayList<Canvas> list_canvas = Diagrama.getIns().getList_orden();
        int i = 1;
        boolean condicion = false;

        //añadir figura posterior a la ultima
        if (VG.getUltimoCanvasConexion().getLayoutY() < y && y < (VG.getUltimoCanvasConexion().getLayoutY() + VG.getUltimoCanvasConexion().getHeight())) {
            //System.out.println(VG.getUltimoCanvasConexion().getLayoutY()+" < "+ y + " < "+(VG.getUltimoCanvasConexion().getLayoutY() + VG.getUltimoCanvasConexion().getHeight())+"\n");
            return VG.getUltimoCanvasConexion().getLayoutY() + VG.getUltimoCanvasConexion().getHeight();

        } else {
            //System.out.println("else");
            for (; i < list_canvas.size()-1; i+=2) {
                //actual_canvas = list_canvas.get(i);
                try {
                    //System.out.println(list_canvas.get(i).getLayoutY()+" <= "+ y + " <= "+list_canvas.get(i).getLayoutY() + list_canvas.get(i).getHeight()+"  o\n");

                    if (list_canvas.get(i).getLayoutY() <= y) {
                        if( y <= list_canvas.get(i).getLayoutY() + list_canvas.get(i).getHeight()){

                            //System.out.println(list_canvas.get(i).getLayoutY()+" < "+ y + " < "+list_canvas.get(i).getLayoutY() + list_canvas.get(i).getHeight()+"  i\n");
                            //System.out.println("AC_Y:" + list_canvas.get(i).getLayoutY() + " -- AC_H:" + list_canvas.get(i).getHeight());
                            //System.out.println("normal2");
                            return list_canvas.get(i).getLayoutY() + list_canvas.get(i).getHeight();
                        }
                    }

                } catch (NullPointerException e) {
                    //System.out.println("error");
                }
            }
            //System.out.println("final");
            return list_canvas.get(i-2).getLayoutY() + list_canvas.get(i-2).getHeight();
        }
    }

    public double ubicacionX_newFigura(){

        //-(panel_Diagrama.getMinWidth()/2)>x ? : ;
        return 0;
    }

    public void dibujarFigura(double x, double y, ImageView sourceDiagram) {
        String contenido = "";
        Figura _inicial = (Figura) Diagrama.getIns().getList_figuras().get(0);
        Canvas _final = obtenerCanvasDesdeFigura((Figura) Diagrama.getIns().getList_figuras().get(Diagrama.getIns().getList_figuras().size() - 1));
        VG.aumentar_numero_figura();

        //condicional que valide si las cordenadas estan dentro de lo razonable para agregar
        //todo:ajustar
        if ((panel_Diagrama.getWidth() / 2) - 150 < x && x < (panel_Diagrama.getWidth() / 2) + 150) {
            //if (_inicial.getVertice_conexion().getY() < y && y < _final.getLayoutY()+80) {

            if (figura_condiconal == sourceDiagram) {
                Vertice p_Fcondicional_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fcondicional_conexion = new Vertice(0, 0);
                Arista dimencion_Fentrada = new Arista(160, 80);
                contenido = " A > B ";

                ArrayList<Diagrama> figuras_contenidas = new ArrayList<Diagrama>(); //Ajustar

                Condicional condicional = new Condicional(contenido, p_Fcondicional_direccion, p_Fcondicional_conexion, p_Fcondicional_conexion,dimencion_Fentrada, figuras_contenidas,VG.getNumero_figura());
                double separacion_condicional = 150;
                Canvas canvas_Fcondicional = new Canvas(dimencion_Fentrada.getAncho()+separacion_condicional, dimencion_Fentrada.getAlto());

                // Obtener la posición ajustada para la nueva figura
                double nuevaPosY = ubicacionY_newFigura(y);

                //todo:cambio
                condicional.setVertice_conexion(new Vertice (VG.getUltimoCanvasFigura().getLayoutX(),(VG.getUltimoCanvasConexion().getLayoutY()+VG.getUltimoCanvasConexion().getHeight()-40)));
                Condicional.dibujar(nuevaPosY, canvas_Fcondicional, condicional,panel_Diagrama,separacion_condicional);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                Diagrama.getIns().agregarElemento(condicional, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fcondicional,VG.getUltimoIndiceConexion()) - 1;
                Diagrama.getIns().agregarElemento(canvas_Fcondicional, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), condicional));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fcondicional);
                VG.cambiarUltimaFiguraAñadida(condicional);
                VG.cambiarUltimoCanvasFigura(canvas_Fcondicional);

                //psudocodigo
                canvas_Fcondicional.setUserData(condicional);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(condicional.getNumero_identificador());//por ahora solo mueve la figuraFinal

                //prueba();

            }/*else if (figura_documento == sourceDiagram) {
                Vertice p_Fdocumento_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fdocumento_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                Arista dimencion_Fentrada = new Arista(120, 70);
                contenido = " Documento ";
                Documento documento = new Documento(contenido, p_Fdocumento_direccion, p_Fdocumento_conexion, dimencion_Fentrada,VG.getNumero_figura());
                Canvas canvas_Fdocumento = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY() + 16;
                documento.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                dibujo_documento(nuevaPosY, canvas_Fdocumento, documento);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                ins.agregarElemento(documento, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fdocumento)-1;
                ins.agregarElemento(canvas_Fdocumento, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), documento));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fdocumento);
                VG.cambiarUltimaFiguraAñadida(documento);
                VG.cambiarUltimoCanvasFigura(canvas_Fdocumento);

                //psudocodigo
                canvas_Fdocumento.setUserData(documento);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(documento, 100, 100);

            } else if (figura_entrada == sourceDiagram) {
                Vertice p_Fentrada_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fentrada_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                contenido = " Entrada: ";
                Arista dimencion_Fentrada = new Arista(153, 50);
                Entrada entrada = new Entrada(contenido, p_Fentrada_direccion, p_Fentrada_conexion, dimencion_Fentrada,VG.getNumero_figura());
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

                //psudocodigo
                canvas_Fentrada.setUserData(entrada);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(entrada, 100, 100);

            } else if (figura_salida == sourceDiagram) {

                Vertice p_Fsalida_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fsalida_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                contenido = " Salida: ";
                Arista dimencion_Fsalida = new Arista(153, 50);
                Salida salida = new Salida(contenido, p_Fsalida_direccion, p_Fsalida_conexion, dimencion_Fsalida,VG.getNumero_figura());
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

                //psudocodigo
                canvas_Fsalida.setUserData(salida);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(salida, 100, 100);

            } else if (figura_proceso == sourceDiagram) {
                Vertice p_Fproceso_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fproeso_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                contenido = " Proceso ";
                Arista dimencion_Fproceso = new Arista(120, 40);
                String contenidoValidado = ""; //Ajustar
                ArrayList<String> operaciones = new ArrayList<String>(); //Ajustar
                ArrayList<String> operandos = new ArrayList<String>(); //Ajustar
                Proceso proceso = new Proceso(contenido, p_Fproceso_direccion, p_Fproeso_conexion, dimencion_Fproceso, contenidoValidado, operaciones, operandos,VG.getNumero_figura());
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

                //psudocodigo
                canvas_Fproceso.setUserData(proceso);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(proceso, 100, 100);

            } else if (figura_hacer_mientras == sourceDiagram) {
                Vertice p_Fhacer_mientras_direccion = new Vertice(32.5, 25);
                Vertice p_Fhacer_mientras_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                Arista dimencion_Fentrada = new Arista(120, 70);
                contenido = " Hacer Mientras ";
                ArrayList<String> contenidoValidado = new ArrayList<>();
                Hacer_Mientras hacer_mientras = new Hacer_Mientras(contenido, p_Fhacer_mientras_direccion, p_Fhacer_mientras_conexion, dimencion_Fentrada, contenidoValidado,VG.getNumero_figura());
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

                //psudocodigo
                canvas_Fhacer_mientras.setUserData(hacer_mientras);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(hacer_mientras, 100, 100);

            } else if (figura_mientras == sourceDiagram) {
                Vertice p_Fcondicional_direccion = new Vertice(32.5, 25);
                Vertice p_Fcondicional_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                Arista dimencion_Fentrada = new Arista(120, 70);
                contenido = " Mientras ";
                ArrayList<String> contenidoValidado = new ArrayList<>();
                Mientras mientras = new Mientras(contenido, p_Fcondicional_direccion, p_Fcondicional_conexion, dimencion_Fentrada, contenidoValidado,VG.getNumero_figura());
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

                //psudocodigo
                canvas_Fmientras.setUserData(mientras);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(mientras, 100, 100);

            } else if (figura_para == sourceDiagram) {
                Vertice p_Fpara_direccion = new Vertice(32.5, 25);
                Vertice p_Fpara_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                Arista dimencion_Fpara = new Arista(120, 70);
                contenido = " Para ";
                ArrayList<String> contenidoValidado = new ArrayList<>();
                Para para = new Para(contenido, p_Fpara_direccion, p_Fpara_conexion, dimencion_Fpara, contenidoValidado,VG.getNumero_figura());
                Canvas canvas_Fmientras = new Canvas(dimencion_Fpara.getAncho(), dimencion_Fpara.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY() + 25;
                para.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                dibujo_para();

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                ins.agregarElemento(para, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fmientras) - 1;
                ins.agregarElemento(canvas_Fmientras, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), para));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fmientras);
                VG.cambiarUltimaFiguraAñadida(para);
                VG.cambiarUltimoCanvasFigura(canvas_Fmientras);

                //psudocodigo
                canvas_Fmientras.setUserData(para);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(para, 100, 100);
            }*/
        }
        VG.setHistorial(Diagrama.getIns());
    }

    public void moverfiguras_agregando(int numero_identificador){

        ArrayList<Canvas> list_canvas = Diagrama.getIns().getList_orden();
        ArrayList<Canvas> list_figuras = Diagrama.getIns().getList_orden();
        boolean cond_identificado = false;
        boolean cond_salto_vuelta = false;

        int i = 0;
        for (; i < list_canvas.size()-1; i+=2) {

            System.out.println("i:"+i+"  numeroFrecorriendo:"+obtenerFiguraDesdeCanvas(list_canvas.get(i)).getNumero_identificador()+"  numero_buscado:"+ numero_identificador+"\n");

            if(cond_identificado){
                System.out.println("M2");

                list_canvas.get(i+1).setLayoutY(list_canvas.get(i+1).getLayoutY()+list_canvas.get(i+1).getHeight()*2+45);
                cond_salto_vuelta = true;
                i--;
            }
            if (obtenerFiguraDesdeCanvas(list_canvas.get(i)).getNumero_identificador() == numero_identificador && !cond_identificado) {
                System.out.println("M1");
                cond_identificado = true;
                i--;
            }
        }
    }

    public boolean determinar_NImaximo(Figura figura){
        ArrayList<Figura> list_figuras = Diagrama.getIns().getList_figuras();
        int max = figura.getNumero_identificador();
        for(Figura obj : list_figuras){
            if(obj != null){
                if(obj.getNumero_identificador() > max){
                    return false;
                }
            }
        }
        return true;
    }

    public void  moverfiguras_eliminando(){

    }

    public void prueba(){
        int aux = 0;
        int cns = 100;
        for(Object obj : Diagrama.getIns().getList_orden()){
            if(obj instanceof Canvas){
                if(aux%2==1){
                    ((Canvas) obj).setLayoutX(100);
                }else{
                    ((Canvas) obj).setLayoutX(250);
                }
            }
            aux++;
        }
    }

    public void deshacer(){
        //todo:pendiente
        Diagrama historial = VG.getHistorial();
        try {
            ArrayList<Canvas> list_orden_ = new ArrayList<>(historial.getList_orden());
            ArrayList<Conector> list_conexiones_ = new ArrayList<>(historial.getList_conexiones());
            ArrayList<Figura> list_figuras_ = new ArrayList<>(historial.getList_figuras());

            // Asegurarse de que no se borren los elementos iniciales
            if (list_orden_.size() <= 5 || list_figuras_.size() <= 3) {
                System.out.println("No se puede deshacer más, elementos iniciales.");
                return;
            }

            // Remover la última figura nueva y el último conector
            panel_Diagrama.getChildren().remove(list_orden_.get(list_orden_.size() - 2));
            list_orden_.remove(list_orden_.get(list_orden_.size() - 2)); // Eliminar conector2

            panel_Diagrama.getChildren().remove(list_orden_.get(list_orden_.size() - 2));
            list_orden_.remove(list_orden_.get(list_orden_.size() - 2)); // Eliminar figuraNueva
            /*
            list_conexiones_.remove(list_conexiones_.size() - 1); // Eliminar conector2
            list_figuras_.remove(list_figuras_.size() - 2); // Eliminar figuraNueva

            try {
                for (Canvas canvas : list_orden_) {
                    panel_Diagrama.getChildren().add(canvas);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("CSM: " + e.getMessage());
            }

            //panel_Diagrama.getChildren().addAll(list_orden_);


            ins.setList_orden(list_orden_);
            ins.setList_conexiones(list_conexiones_);
            ins.setList_figuras(list_figuras_);

            if (!list_orden_.isEmpty()) {
                VG.cambiarUltimoCanvasFigura(list_orden_.get(list_orden_.size() - 1));
                if (list_orden_.size() > 1) {
                    VG.cambiarUltimoCanvasConexion(list_orden_.get(list_orden_.size() - 2));
                } else {
                    VG.cambiarUltimoCanvasConexion(null);
                }
            } else {
                VG.cambiarUltimoCanvasFigura(null);
                VG.cambiarUltimoCanvasConexion(null);
            }*/

        }catch (NullPointerException e){
            System.out.println("Ups...");
        }


    }

    public void rehacer(){

    }

    public void zoom_in() {

        // Obtener la transformación de escala actual
        Scale escalaTransformacion = (Scale) panel_Diagrama.getTransforms().get(0);

        // Aplicar el factor de escala
        escalaTransformacion.setX(escalaTransformacion.getX() * 1.1);
        escalaTransformacion.setY(escalaTransformacion.getY() * 1.1);

        // Ajustar dimensiones del panel
        ajustar_Panes(panel_Diagrama.getWidth(),
                panel_Diagrama.getHeight());
        fondoCuadriculado(panel_Diagrama.getWidth()+120*(1.2),
                panel_Diagrama.getHeight()+120*(1.2));
    }

    public void zoom_out() {
        // Obtener la transformación de escala actual
        Scale escalaTransformacion = (Scale) panel_Diagrama.getTransforms().get(0);

        // Aplicar el factor de escala
        escalaTransformacion.setX(escalaTransformacion.getX() * 0.9);
        escalaTransformacion.setY(escalaTransformacion.getY() * 0.9);

        // Ajustar dimensiones del panel
        ajustar_Panes(panel_Diagrama.getWidth(),
                panel_Diagrama.getHeight());
        fondoCuadriculado(panel_Diagrama.getWidth()+120*(0.9),
                panel_Diagrama.getHeight()+120*(0.9));
    }

    public void reset_zoom(){
        Scale escalaTransformada = (Scale) panel_Diagrama.getTransforms().get(0);
        Scale escalaReset = (Scale) panel_Diagrama.getTransforms().get(1);
        escalaTransformada.setX(escalaReset.getX());
        escalaTransformada.setY(escalaReset.getY());

        //reajustar la posicion de las figuras el maximar la ventana
        if (getMaximizar()) { // ventana Maximizada
            ajustar_Panes(1920, 1080);
            fondoCuadriculado(1920, 1080 + 500);
        } else { // ventana Minimizada
            ajustar_Panes(740, 654);
            fondoCuadriculado(740, 645 + 500);
        }
    }

    public void ajustar_Panes(double width, double height){
        panel_contenedor.setMinSize(width, height);
        panel_ventanas.setMinSize(width, height);
    }

    public int determinarIndiceFigura_InList_figuras(Figura figuraOrigen, double x, double y) {
        List<Figura> listaFiguras = Diagrama.getIns().getList_figuras(); // Obtener la lista de figuras

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

    public int determinarIndiceCanvas_InList_orden(Canvas canvas, int ultimadato){
        int indiceCanvasDestino = 0;
        for(Object obj : Diagrama.getIns().getList_orden()){
            if(obj instanceof Canvas){
                if(obj == canvas){
                    if(indiceCanvasDestino > ultimadato){
                        VG.setUltimoIndiceConexion(indiceCanvasDestino);
                        return indiceCanvasDestino;
                    }else{
                        VG.setUltimoIndiceConexion(ultimadato+2);
                        return ultimadato+2;
                    }
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
        Canvas conectorCanvas = Figura.crear_canvasConector(diferenciaY,figuraOrigen.getVertice_conexion(), figuraDestino.getVertice_conexion());

        //posicionamiento del canvas
        double layoutX = (panel_Diagrama.getMinWidth() / 2) - conectorCanvas.getWidth() / 2;
        double layoutY = destino.getY()+figuraDestino.getDimenciones().getAlto()+40; //20 Ajustar la posición Y del conector

        conectorCanvas.setLayoutX(layoutX);
        conectorCanvas.setLayoutY(layoutY);

        // Agregar el nuevo canvas conector a la lista visual
        panel_Diagrama.getChildren().add(conectorCanvas);

        // Agregar el canvas conector a la lista de conectores
        Canvas canvasF = obtenerCanvasDesdeFigura(figuraDestino);
        int indice_C = determinarIndiceCanvas_InList_orden(canvasF,VG.getUltimoIndiceConexion())+1;
        //System.out.println("Indice:"+indice_C);
        Diagrama.getIns().agregarElemento(conectorCanvas,1,indice_C);

        // Agregar el conector a la lista de conectores
        Conector conector = new Conector(figuraOrigen.getVertice_conexion(), figuraDestino.getVertice_conexion());
        Diagrama.getIns().getList_conexiones().add(conector);
        return conectorCanvas;
    }

    private Figura obtenerFiguraDesdeCanvas(Canvas canvas) {
        ArrayList<Canvas> list_canvas = Diagrama.getIns().getList_orden();
        ArrayList<Figura> list_figura = Diagrama.getIns().getList_figuras();

        int index = list_canvas.indexOf(canvas);
        int ind_canvas = 0;
        int ind_figuras = 0;
        if (index != -1) {
            for(; ind_canvas < list_canvas.size();){
                if(ind_canvas==index){
                    return list_figura.get(ind_figuras);
                }
                ind_canvas+=2;
                ind_figuras++;
            }
        }
        return list_figura.get(list_figura.size()-1);
    }

    private Canvas obtenerCanvasDesdeFigura(Figura figura) {
        ArrayList<Canvas> list_orden = Diagrama.getIns().getList_orden();
        ArrayList<Figura> list_figuras = Diagrama.getIns().getList_figuras();

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

    /*
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
        double centerX = (panel_Diagrama.getMinWidth() / 2) - diferencia + 42.5;
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
        double startXLeft = centerX - size + 10; // Punto de inicio en el lado izquierdo del rombo

        Canvas conectorIzquierda = crear_canvasLineaIzquierda(startXLeft, startY+110, 50);
        Canvas conectorArriba = crear_canvasLineaArriba(startXLeft,startY+120, 40);
        Canvas conectorDerecha = crear_canvasLineaDerecha(startXLeft,startY+70, 80);

        panel_Diagrama.getChildren().addAll(conectorIzquierda,conectorArriba,conectorDerecha);

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
                textContenido.setLayoutX(currentX-40); // Ajustar según tus necesidades
                textContenido.setLayoutY(currentY); // Ajustar según tus necesidades
                textContenido.setMinWidth(size); // Ajustar según tus necesidades
                textContenido.setMinHeight(size); // Ajustar según tus necesidades
                textContenido.setText(finalTexto);
                textContenido.setStyle("-fx-text-fill: black;");

                panel_Diagrama.getChildren().add(textContenido);

                // Agregar evento de tecla para actualizar el contenido al presionar Enter
                textContenido.setOnKeyPressed(event_2 -> {
                    if (event_2.getCode() == KeyCode.ENTER) {
                        // Actualiza el nombre de la figura
                        figura.setContenido(textContenido.getText());

                        // Redibuja la figura con el nuevo nombre en las mismas coordenadas
                        dibujo_mientras(figura.getContenido(),currentX,currentY-50, canvas, figura);

                        // Elimina el campo de texto del panel
                        panel_Diagrama.getChildren().remove(textContenido);
                        // Deshabilita la edición del contenido
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
        double centerX = (panel_Diagrama.getMinWidth() / 2) - diferencia + 42.5;
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
        double startXLeft = centerX - size + 10; // Punto de inicio en el lado izquierdo del rombo

        Canvas conectorAbajo1 = crear_canvasLineaAbajo(startXLeft+80, startY+130, 80);
        Canvas conectorIzquierda1 = crear_canvasLineaIzquierda(startXLeft+10,startY+160, 70);
        Canvas conectorArriba = crear_canvasLineaArriba(startXLeft+10,startY+110, 100);
        Canvas conectorDerecha1 = crear_canvasLineaDerecha(startXLeft+10,startY+60, 70);
        Canvas conectorDerecha2 = crear_canvasLineaDerecha(startXLeft+100,startY+110, 50);
        Canvas conectorAbajo2 = crear_canvasLineaAbajo(startXLeft+150,startY+110, 75);
        Canvas conectorIzquierda2 = crear_canvasLineaIzquierda(startXLeft+80,startY+185, 70);



        panel_Diagrama.getChildren().addAll(conectorAbajo1,conectorIzquierda1,conectorArriba,conectorDerecha1,
                conectorDerecha2,conectorAbajo2,conectorIzquierda2);


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
                textContenido.setLayoutX(currentX-40); // Ajustar según tus necesidades
                textContenido.setLayoutY(currentY); // Ajustar según tus necesidades
                textContenido.setMinWidth(size); // Ajustar según tus necesidades
                textContenido.setMinHeight(size); // Ajustar según tus necesidades
                textContenido.setText(finalTexto);
                textContenido.setStyle("-fx-text-fill: black;");
                panel_Diagrama.getChildren().add(textContenido);

                // Agregar evento de tecla para actualizar el contenido al presionar Enter
                textContenido.setOnKeyPressed(event_2 -> {
                    if (event_2.getCode() == KeyCode.ENTER) {
                        // Actualiza el nombre de la figura
                        figura.setContenido(textContenido.getText());

                        // Redibuja la figura con el nuevo nombre en las mismas coordenadas
                        dibujo_hacerMientras(figura.getContenido(),currentX,currentY-50, canvas, figura);

                        // Elimina el campo de texto del panel
                        panel_Diagrama.getChildren().remove(textContenido);
                        // Deshabilita la edición del contenido
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

    // MÉTODOS ASOCIADOS A CREACIÓN DE LÍNEAS
    public void dibujo_para(){}

    public void edición_para(){}

    // MÉTODOS ASOCIADOS A CONDICIONAL

    public Canvas crear_canvasConector2(double startX, double startY, boolean isLeft) {
        Canvas conector = new Canvas();

        conector.setWidth(120);
        conector.setHeight(120);

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
    }

    public Canvas crear_canvasLineaIzquierda(double startX, double startY, double length) {
        Canvas conector = new Canvas();

        // Ajustar las dimensiones del canvas según sea necesario
        conector.setWidth(100);
        conector.setHeight(100);

        // Establecer la posición del canvas
        conector.setLayoutX(startX);
        conector.setLayoutY(startY - 5);

        GraphicsContext gc = conector.getGraphicsContext2D();

        // Dibujar la línea horizontal del conector hacia la izquierda
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(0, 5, length, 5);

        return conector;
    }

    public Canvas crear_canvasLineaArriba(double startX, double startY, double length) {
        Canvas conector = new Canvas();

        // Ajustar las dimensiones del canvas según sea necesario
        conector.setWidth(120);
        conector.setHeight(120);

        // Establecer la posición del canvas
        conector.setLayoutX(startX - 5); // Para centrar la línea vertical en startX
        conector.setLayoutY(startY - 50); // La línea se dibuja hacia arriba

        GraphicsContext gc = conector.getGraphicsContext2D();

        // Dibujar la línea vertical del conector hacia arriba
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(5, 0, 5, length);

        return conector;
    }

    public Canvas crear_canvasLineaDerecha(double startX, double startY, double length) {
        Canvas conector = new Canvas();

        // Ajustar las dimensiones del canvas según sea necesario
        conector.setWidth(100);
        conector.setHeight(100);

        // Establecer la posición del canvas
        conector.setLayoutX(startX);
        conector.setLayoutY(startY - 5); // Para centrar la línea horizontal en startY

        GraphicsContext gc = conector.getGraphicsContext2D();

        // Dibujar la línea horizontal del conector hacia la derecha
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(0, 5, length, 5);

        return conector;
    }

    public Canvas crear_canvasLineaAbajo(double startX, double startY, double length) {
        Canvas conector = new Canvas();

        // Ajustar las dimensiones del canvas según sea necesario
        conector.setWidth(120);
        conector.setHeight(300);

        // Establecer la posición del canvas
        conector.setLayoutX(startX - 5); // Para centrar la línea vertical en startX
        conector.setLayoutY(startY);

        GraphicsContext gc = conector.getGraphicsContext2D();



        // Dibujar la línea vertical del conector hacia abajo
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(5, 0, 5, length);

        return conector;
    }*/

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

    public void setMaximizar(boolean maximizar){
        this.maximizar = maximizar;
    }

    public boolean getMaximizar(){
        return maximizar;
    }
}