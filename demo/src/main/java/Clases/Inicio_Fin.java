package Clases;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;

public class Inicio_Fin extends Figura{

    public Inicio_Fin(String contenido, Vertice vertice_direccion, Vertice vertice_conexion, Arista dimension,int numero_identificador) {
        super(contenido, vertice_direccion, vertice_conexion, dimension,numero_identificador);
    }

    public static void dibujar(Canvas canvas, Figura figura) {

        ArrayList<Vertice> vertices = calcular_vertices(canvas,1);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Rellenar figura
        gc.setStroke(VG.getColorRelleno());

        double diferencia_restante = canvas.getWidth() - vertices.get(0).getX();

        double[] xPoints = {vertices.get(0).getX() + diferencia_restante - 21.5, vertices.get(0).getX() + diferencia_restante - 5.5,
                vertices.get(1).getX(), vertices.get(2).getX() + diferencia_restante - 5.5, vertices.get(2).getX() + diferencia_restante - 21.5,
                vertices.get(2).getX() - diferencia_restante + 21.5, vertices.get(2).getX() - diferencia_restante + 5.5, vertices.get(3).getX(),
                vertices.get(0).getX() - diferencia_restante + 5.5, vertices.get(0).getX() - diferencia_restante + 21.5};

        double[] yPoints = {vertices.get(0).getY()+2, vertices.get(1).getY()/2, vertices.get(1).getY(),
                vertices.get(1).getY() + vertices.get(1).getY()/2, vertices.get(2).getY()-2, vertices.get(2).getY()-2,
                vertices.get(3).getY() + vertices.get(3).getY()/2, vertices.get(3).getY(), vertices.get(1).getY()/2,
                vertices.get(0).getY()+2};

        gc.fillPolygon(xPoints, yPoints, xPoints.length);

        //lineas Horizontales
        gc.setLineWidth(VG.getTamaño_Lbordes());
        gc.setStroke(VG.getColorBordes());
        gc.strokeLine(vertices.get(0).getX() - diferencia_restante + 28, vertices.get(0).getY()+1, vertices.get(0).getX() + diferencia_restante - 28, vertices.get(0).getY()+1);
        gc.strokeLine(vertices.get(0).getX() - diferencia_restante + 28, vertices.get(2).getY()-1, vertices.get(0).getX() + diferencia_restante - 28, vertices.get(2).getY()-1);

        //curvas laterales
        gc.strokeArc(vertices.get(0).getX() - diferencia_restante + 1.5, vertices.get(0).getY(), vertices.get(0).getX() - diferencia_restante + 70,
                vertices.get(2).getY(), 90, 180, ArcType.OPEN);
        gc.strokeArc(vertices.get(0).getX() + diferencia_restante - 71.5,vertices.get(0).getY(),
                vertices.get(0).getX() - diferencia_restante + 70, vertices.get(2).getY(), 270, 180, ArcType.OPEN);

        //contenido
        gc.setLineWidth(VG.getTamañoTxt());
        gc.setFont(VG.getFont());
        gc.setStroke(VG.getColorTexto());
        gc.setFill(VG.getColorTexto());
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(figura.getContenido(), (figura.getDimenciones().getAncho()/ 2)+15, figura.getDimenciones().getAlto()-15);
    }

    public static void Figuras_Iniciales(AnchorPane panel_Diagrama){
        TextField textContenido = new TextField();
        textContenido.setOpacity(0.0);
        textContenido.setDisable(true);

        double cx = 0;
        double cy = 25;
        //Parametros figura Inicio
        Vertice p_Finicio_cordenada = new Vertice(cx,cy); //no cambiar
        Vertice p_Finicio_conexion = new Vertice(0,0); //Reajustar

        String contenido = "Algoritmo titulo";
        Arista dimencion_Finicio = new Arista(153, 50);
        Inicio_Fin figura_inicio = new Inicio_Fin(contenido, p_Finicio_cordenada, p_Finicio_conexion, dimencion_Finicio,VG.getNumero_figura());

        //considerar no salirse de las dimensiones del canvas
        Canvas canvas_Finicio = new Canvas(dimencion_Finicio.getAncho(), dimencion_Finicio.getAlto());
        //posicion de la figura en relacion al AnchorPane

        double diferencia = dimencion_Finicio.getAncho()/2;
        canvas_Finicio.setLayoutX((panel_Diagrama.getMinWidth()/2)-diferencia);
        canvas_Finicio.setLayoutY(p_Finicio_cordenada.getY());

        figura_inicio.setVertice_conexion(new Vertice (canvas_Finicio.getLayoutX(),(canvas_Finicio.getLayoutY()+dimencion_Finicio.getAlto())));

        // Dibujo / diseño del Canvas
        dibujar(canvas_Finicio,figura_inicio);

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
            dibujar(canvas_Finicio,figura_inicio);

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
                    dibujar(canvas_Finicio,figura_inicio);
                    textContenido.clear();
                    panel_Diagrama.getChildren().remove(textContenido);
                }
            });
        });

        double separacion = 60;

        //Parametros figura Fin
        Vertice p_Ffin_direccion = new Vertice(cx,cy);
        VG.aumentar_numero_figura();

        //distancia entre las figuras iniciales
        Vertice p_Ffin_conexion = new Vertice(cx,cy);
        contenido="Fin Algoritmo";
        Arista dimencion_Ffin = new Arista(8*contenido.length()+25, 50);
        Inicio_Fin figura_fin = new Inicio_Fin(contenido, p_Ffin_direccion, p_Ffin_conexion, dimencion_Ffin, VG.getNumero_figura());

        Canvas canvas_Ffin = new Canvas(dimencion_Ffin.getAncho(), dimencion_Ffin.getAlto());

        diferencia = dimencion_Ffin.getAncho()/2;
        canvas_Ffin.setLayoutX((panel_Diagrama.getMinWidth()/2)-diferencia);
        canvas_Ffin.setLayoutY(figura_inicio.getVertice_conexion().getY()+separacion);

        figura_fin.setVertice_conexion(new Vertice (canvas_Ffin.getLayoutX(),(canvas_Ffin.getLayoutY()+dimencion_Ffin.getAlto())));

        // Dibujo / diseño del Canvas
        dibujar(canvas_Ffin,figura_fin);

        //conectar
        Conector conector_inicial = new Conector(figura_inicio.getVertice_conexion(), figura_fin.getVertice_conexion());
        Canvas f_conector = crear_canvasConector(separacion,figura_inicio.getVertice_conexion(),figura_fin.getVertice_conexion());
        diferencia = f_conector.getWidth()/2;
        f_conector.setLayoutX((panel_Diagrama.getMinWidth()/2)-diferencia);
        f_conector.setLayoutY(p_Finicio_cordenada.getY()+figura_inicio.getDimenciones().getAlto());

        panel_Diagrama.getChildren().addAll(canvas_Finicio,f_conector,canvas_Ffin);
        Diagrama.getIns().agregarElemento(figura_inicio,0,0);
        Diagrama.getIns().agregarElemento(figura_fin,0,0);
        Diagrama.getIns().agregarElemento(conector_inicial,0,0);
        Diagrama.getIns().agregarElemento(canvas_Finicio,0,0);
        Diagrama.getIns().agregarElemento(f_conector,0,0);
        Diagrama.getIns().agregarElemento(canvas_Ffin,0,0);
    }

}
