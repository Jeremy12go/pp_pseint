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
    private boolean maximizar;
    @FXML
    private Button editButton;

    private String pseudocodeContent;
    private PseudocodeInterpreter interpreter;
    private String originalText;
    private int count_deshacer;

    @FXML
    public void initialize(){
        interpreter = new PseudocodeInterpreter();
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

        VG.setCount_deshacer(0);

        VG.setFigura_proceso(figura_proceso);
        VG.setFigura_entrada(figura_entrada);
        VG.setFigura_salida(figura_salida);
        VG.setFigura_condiconal(figura_condiconal);
        VG.setFigura_documento(figura_documento);
        VG.setFigura_mientras(figura_mientras);
        VG.setFigura_hacer_mientras(figura_hacer_mientras);
        VG.setFigura_para(figura_para);

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
                dibujarFigura(x, y,sourceDiagram,panel_Diagrama);
            }
        }
        basurero.setVisible(false);
    }
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

            count_deshacer = 0;

        }catch (NullPointerException e){
            System.out.println("Ups... DLC \'borrar todo\' debe adquirirse por separado :)");
        }
    }
    @FXML
    private void guardarApseudocode() {
        Pseudocode.generatePseudocode(panel_Diagrama, pseudocode);
        // Generar pseudocódigo
        pseudocodeContent = Pseudocode.generatePseudocode(panel_Diagrama, pseudocode);

        // Validar pseudocódigo
        String validationErrors = Validar.validarPseudocodigo(pseudocodeContent);

        // Mostrar errores si existen
        if (!validationErrors.equals("No se encontraron errores.")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errores de Validación");
            alert.setHeaderText(null);
            alert.setContentText(validationErrors);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validación Exitosa");
            alert.setHeaderText(null);
            alert.setContentText("El pseudocódigo se generó y validó correctamente.");
            alert.showAndWait();
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
    //-------------------------------------------------------------------------------------------------------------
    private double[] obtenerCoordenadas(MouseEvent event) {
        double x = event.getSceneX() - panel_Diagrama.getLayoutX() - 210;
        double y = event.getSceneY() - panel_Diagrama.getLayoutY() - 65;
        return new double[]{x, y};
    }

    private double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    public static double ubicacionY_newFigura(double y) {
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

    public static void dibujarFigura(double x, double y, ImageView sourceDiagram, AnchorPane panel_Diagrama) {
        String contenido = "";
        Figura _inicial = (Figura) Diagrama.getIns().getList_figuras().get(0);
        Canvas _final = obtenerCanvasDesdeFigura((Figura) Diagrama.getIns().getList_figuras().get(Diagrama.getIns().getList_figuras().size() - 1));
        VG.aumentar_numero_figura();

        //condicional que valide si las cordenadas estan dentro de lo razonable para agregar
        if ((panel_Diagrama.getWidth() / 2) - 150 < x && x < (panel_Diagrama.getWidth() / 2) + 150) {
            //if (_inicial.getVertice_conexion().getY() < y && y < _final.getLayoutY()+80) {

            if (VG.getFigura_condiconal() == sourceDiagram) {
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
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), condicional, panel_Diagrama));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fcondicional);
                VG.cambiarUltimaFiguraAñadida(condicional);
                VG.cambiarUltimoCanvasFigura(canvas_Fcondicional);

                //psudocodigo
                canvas_Fcondicional.setUserData(condicional);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(condicional.getNumero_identificador());//por ahora solo mueve la figuraFinal

                //prueba();

            }else if (VG.getFigura_documento() == sourceDiagram) {
                Vertice p_Fdocumento_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fdocumento_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                Arista dimencion_Fentrada = new Arista(120, 70);
                contenido = " Documento ";
                Documento documento = new Documento(contenido, p_Fdocumento_direccion, p_Fdocumento_conexion, dimencion_Fentrada,VG.getNumero_figura());
                Canvas canvas_Fdocumento = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY() + 16;
                documento.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                Documento.dibujo(nuevaPosY, canvas_Fdocumento, documento, panel_Diagrama);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                Diagrama.getIns().agregarElemento(documento, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fdocumento,VG.getUltimoIndiceConexion())-1;
                Diagrama.getIns().agregarElemento(canvas_Fdocumento, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), documento, panel_Diagrama));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fdocumento);
                VG.cambiarUltimaFiguraAñadida(documento);
                VG.cambiarUltimoCanvasFigura(canvas_Fdocumento);

                //psudocodigo
                canvas_Fdocumento.setUserData(documento);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(documento.getNumero_identificador());

            } else if (VG.getFigura_entrada() == sourceDiagram) {
                Vertice p_Fentrada_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fentrada_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                contenido = " Entrada ";
                Arista dimencion_Fentrada = new Arista(153, 50);
                Entrada entrada = new Entrada(contenido, p_Fentrada_direccion, p_Fentrada_conexion, dimencion_Fentrada,VG.getNumero_figura());
                Canvas canvas_Fentrada = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY();
                entrada.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                Entrada.dibujo(canvas_Fentrada, entrada,panel_Diagrama);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                Diagrama.getIns().agregarElemento(entrada, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fentrada,VG.getUltimoIndiceConexion()) - 1;
                Diagrama.getIns().agregarElemento(canvas_Fentrada, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), entrada, panel_Diagrama));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fentrada);
                VG.cambiarUltimaFiguraAñadida(entrada);
                VG.cambiarUltimoCanvasFigura(canvas_Fentrada);

                //psudocodigo
                canvas_Fentrada.setUserData(entrada);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(entrada.getNumero_identificador());

            } else if (VG.getFigura_salida() == sourceDiagram) {

                Vertice p_Fsalida_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fsalida_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                contenido = " Salida ";
                Arista dimencion_Fsalida = new Arista(153, 50);
                Salida salida = new Salida(contenido, p_Fsalida_direccion, p_Fsalida_conexion, dimencion_Fsalida,VG.getNumero_figura());
                Canvas canvas_Fsalida = new Canvas(dimencion_Fsalida.getAncho(), dimencion_Fsalida.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY();
                salida.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                Salida.dibujo(canvas_Fsalida, salida, panel_Diagrama);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                Diagrama.getIns().agregarElemento(salida, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fsalida,VG.getUltimoIndiceConexion()) - 1;
                Diagrama.getIns().agregarElemento(canvas_Fsalida, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), salida, panel_Diagrama));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fsalida);
                VG.cambiarUltimaFiguraAñadida(salida);
                VG.cambiarUltimoCanvasFigura(canvas_Fsalida);

                //psudocodigo
                canvas_Fsalida.setUserData(salida);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(salida.getNumero_identificador());

            } else if (VG.getFigura_proceso() == sourceDiagram) {
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

                Proceso.dibujo(canvas_Fproceso, proceso, panel_Diagrama);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                Diagrama.getIns().agregarElemento(proceso, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fproceso,VG.getUltimoIndiceConexion()) - 1;
                Diagrama.getIns().agregarElemento(canvas_Fproceso, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), proceso, panel_Diagrama));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fproceso);
                VG.cambiarUltimaFiguraAñadida(proceso);
                VG.cambiarUltimoCanvasFigura(canvas_Fproceso);

                //psudocodigo
                canvas_Fproceso.setUserData(proceso);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(proceso.getNumero_identificador());

            } else if (VG.getFigura_hacer_mientras() == sourceDiagram) {
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

                Hacer_Mientras.dibujo(contenido, x, nuevaPosY , canvas_Fhacer_mientras, hacer_mientras,panel_Diagrama);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                Diagrama.getIns().agregarElemento(hacer_mientras, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fhacer_mientras,VG.getUltimoIndiceConexion()) - 1;
                Diagrama.getIns().agregarElemento(canvas_Fhacer_mientras, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), hacer_mientras, panel_Diagrama));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fhacer_mientras);
                VG.cambiarUltimaFiguraAñadida(hacer_mientras);
                VG.cambiarUltimoCanvasFigura(canvas_Fhacer_mientras);

                //psudocodigo
                canvas_Fhacer_mientras.setUserData(hacer_mientras);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(hacer_mientras.getNumero_identificador());

            } else if (VG.getFigura_mientras() == sourceDiagram) {
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

                Mientras.dibujo(contenido, x, nuevaPosY, canvas_Fmientras, mientras,panel_Diagrama);

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                Diagrama.getIns().agregarElemento(mientras, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fmientras,VG.getUltimoIndiceConexion()) - 1;
                Diagrama.getIns().agregarElemento(canvas_Fmientras, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), mientras, panel_Diagrama));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fmientras);
                VG.cambiarUltimaFiguraAñadida(mientras);
                VG.cambiarUltimoCanvasFigura(canvas_Fmientras);

                //psudocodigo
                canvas_Fmientras.setUserData(mientras);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(mientras.getNumero_identificador());

            } else if (VG.getFigura_para() == sourceDiagram) {
                Vertice p_Fpara_direccion = new Vertice(32.5, 25);
                Vertice p_Fpara_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                Arista dimencion_Fpara = new Arista(120, 70);
                contenido = " Para ";
                ArrayList<String> contenidoValidado = new ArrayList<>();
                Para para = new Para(contenido, p_Fpara_direccion, p_Fpara_conexion, dimencion_Fpara, contenidoValidado,VG.getNumero_figura());
                Canvas canvas_Fpara = new Canvas(dimencion_Fpara.getAncho(), dimencion_Fpara.getAlto());

                // Obtener la posición Y ajustada para la nueva figura
                double nuevaPosY = VG.getUltimoCanvasConexion().getLayoutY() + 25;
                para.setVertice_conexion(new Vertice((panel_Diagrama.getMinWidth() / 2), nuevaPosY));

                Para.dibujo();

                // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
                int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
                Diagrama.getIns().agregarElemento(para, 1, indice_Fposterior);

                // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
                int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fpara,VG.getUltimoIndiceConexion()) - 1;
                Diagrama.getIns().agregarElemento(canvas_Fpara, 1, indice_Cposterior);
                VG.cambiarUltimoCanvasConexion(conectar(VG.getUltimaFiguraAñadida(), para, panel_Diagrama));

                // Agregar la nueva figura al panel
                panel_Diagrama.getChildren().add(canvas_Fpara);
                VG.cambiarUltimaFiguraAñadida(para);
                VG.cambiarUltimoCanvasFigura(canvas_Fpara);

                //psudocodigo
                canvas_Fpara.setUserData(para);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras_agregando(para.getNumero_identificador());
            }
        }
        VG.setHistorial(Diagrama.getIns());
        //todo: ver posible error no se queda el historial
        System.out.println("largoHistorial:"+VG.getHistorial().getList_orden().size()+" -- largo:"+Diagrama.getIns().getList_orden().size());
        VG.setCount_deshacer(0);
    }

    public static void moverfiguras_agregando(int numero_identificador){

        ArrayList<Canvas> list_canvas = Diagrama.getIns().getList_orden();
        ArrayList<Canvas> list_figuras = Diagrama.getIns().getList_orden();
        boolean cond_identificado = false;

        int i = 0;
        for (; i < list_canvas.size()-1; i+=2) {
            //System.out.println("i:"+i+"  numeroFrecorriendo:"+obtenerFiguraDesdeCanvas(list_canvas.get(i)).getNumero_identificador()+"  numero_buscado:"+ numero_identificador+"\n");
            if(cond_identificado){
                //System.out.println("M2");
                list_canvas.get(i+1).setLayoutY(list_canvas.get(i).getLayoutY()+list_canvas.get(i).getHeight());
                i--;
            }
            if (obtenerFiguraDesdeCanvas(list_canvas.get(i)).getNumero_identificador() == numero_identificador && !cond_identificado) {
                //System.out.println("M1");
                cond_identificado = true;
                i--;
            }
        }
    }

    public void  moverfiguras_eliminando(){
        ArrayList<Canvas> list_canvas = Diagrama.getIns().getList_orden();
        list_canvas.get(list_canvas.size()-1).setLayoutY(list_canvas.get(list_canvas.size()-2).getLayoutY()
                +list_canvas.get(list_canvas.size()-2).getHeight());
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

    public Diagrama clonarHistorial() {
        Diagrama clon = new Diagrama();
        clon.setList_orden(new ArrayList<>(Diagrama.getIns().getList_orden()));
        clon.setList_conexiones(new ArrayList<>(Diagrama.getIns().getList_conexiones()));
        clon.setList_figuras(new ArrayList<>(Diagrama.getIns().getList_figuras()));
        return clon;
    }

    public void deshacer(){
        VG.setCount_deshacer(VG.getCount_deshacer()+1);

        Diagrama clon = clonarHistorial();
        Diagrama historial = VG.getHistorial();

        try {
            ArrayList<Canvas> list_orden_ = new ArrayList<>(historial.getList_orden());
            ArrayList<Conector> list_conexiones_ = new ArrayList<>(historial.getList_conexiones());
            ArrayList<Figura> list_figuras_ = new ArrayList<>(historial.getList_figuras());

            // Asegurarse de que no se borren los elementos iniciales
            if (list_orden_.size() == 3) {
                System.out.println("No se puede deshacer más, elementos iniciales.");
                return;
            }

            //Eliminar Ultimo conector
            panel_Diagrama.getChildren().remove(list_orden_.get(list_orden_.size() - 2));
            Diagrama.getIns().getList_orden().remove(list_orden_.get(list_orden_.size() - 2));

            //Eliminar ultima figura
            panel_Diagrama.getChildren().remove(list_orden_.get(list_orden_.size() - 3));
            Diagrama.getIns().getList_orden().remove(list_orden_.get(list_orden_.size() - 3));

            VG.setHistorial(clon);

            moverfiguras_eliminando();
        }catch (NullPointerException e){
            System.out.println("Ups...");
        }
        System.out.println("largoHistorial:"+historial.getList_orden().size()+" -- largo:"+Diagrama.getIns().getList_orden().size());
    }

    public void rehacer(){
        Diagrama historial = VG.getHistorial();
        try {
            ArrayList<Canvas> list_orden_ = new ArrayList<>(historial.getList_orden());
            ArrayList<Conector> list_conexiones_ = new ArrayList<>(historial.getList_conexiones());
            ArrayList<Figura> list_figuras_ = new ArrayList<>(historial.getList_figuras());

            // Asegurarse de que no se borren los elementos iniciales
            System.out.println("count_deshacer:"+VG.getCount_deshacer());
            if (VG.getCount_deshacer() < 1) {
                System.out.println("No se puede rehacer más, elementos iniciales.");
                return;
            }
            System.out.println("largoHistorial:"+VG.getHistorial().getList_orden().size()+" -- largo:"+Diagrama.getIns().getList_orden().size());

            //agregar Ultimo conector
            panel_Diagrama.getChildren().add(list_orden_.get(list_orden_.size() - 2));
            Diagrama.getIns().getList_orden().add(list_orden_.get(list_orden_.size() - 2));

            //agregar ultima figura
            panel_Diagrama.getChildren().add(list_orden_.get(list_orden_.size() - 3));
            Diagrama.getIns().getList_orden().add(list_orden_.get(list_orden_.size() - 3));

            //moverfiguras_eliminando();
        }catch (IllegalArgumentException e){
            System.out.println("Ups...");
        }
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

    public static int determinarIndiceFigura_InList_figuras(Figura figuraOrigen, double x, double y) {
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

    public static int determinarIndiceCanvas_InList_orden(Canvas canvas, int ultimadato){
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

    public static double calcularDistancia(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static Canvas conectar(Figura figuraOrigen, Figura figuraDestino, AnchorPane panel_Diagrama) {
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

    private static Figura obtenerFiguraDesdeCanvas(Canvas canvas) {
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

    private static Canvas obtenerCanvasDesdeFigura(Figura figura) {
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

    @FXML
    private void ejecutar() {
        // Generar pseudocódigo
        String pseudocodeContent = Pseudocode.generarPseudo(panel_Diagrama, pseudocode);

        // Validar pseudocódigo
        String validationErrors = Validar.validarPseudocodigo(pseudocodeContent);

        // Mostrar errores si existen
        if (!validationErrors.equals("No se encontraron errores.")) {
            mostrarAlertaError("Errores de Validación", validationErrors);
        } else {
            // Ejecutar el pseudocódigo
            interpreter.ejecutarPseudocodigo(pseudocodeContent);
            mostrarAlertaInformacion("Ejecución Exitosa", "El pseudocódigo se ejecutó correctamente.");
            interpreter.imprimirVariables();
        }
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}