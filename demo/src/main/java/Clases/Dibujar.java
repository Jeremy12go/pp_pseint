package Clases;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import org.example.app.AppController;

import java.util.ArrayList;
import java.util.List;


public class Dibujar {

    public static void dibujarFigura(double x, double y, ImageView sourceDiagram,String contenido,String figura, AnchorPane panel_Diagrama) {
        Figura _inicial = (Figura) Diagrama.getIns().getList_figuras().get(0);
        Canvas _final = obtenerCanvasDesdeFigura((Figura) Diagrama.getIns().getList_figuras().get(Diagrama.getIns().getList_figuras().size() - 1));
        VG.aumentar_numero_figura();

        if ((panel_Diagrama.getWidth() / 2) - 150 < x && x < (panel_Diagrama.getWidth() / 2) + 150) {
            if (VG.getFigura_condiconal() == sourceDiagram || figura == "condicional") {
                if (contenido.equals("")) {
                    dibujarCondicional(x, y, contenido, panel_Diagrama);
                } else if (VG.getFigura_documento() == sourceDiagram || figura == "documento") {
                    if (contenido.equals("")) {
                        contenido = "Documento";
                    }
                    dibujarDocumento(x, y, contenido, panel_Diagrama);
                } else if (VG.getFigura_entrada() == sourceDiagram || figura == "entrada") {
                    if (contenido.equals("")) {
                        contenido = "Entrada";
                    }
                    dibujarEntrada(x, y, contenido, panel_Diagrama);
                } else if (VG.getFigura_salida() == sourceDiagram || figura == "salida") {
                    if (contenido.equals("")) {
                        contenido = "Salida";
                    }
                    dibujarSalida(x, y, contenido, panel_Diagrama);
                } else if (VG.getFigura_proceso() == sourceDiagram || figura == "proceso") {
                    if (contenido.equals("")) {
                        contenido = "Proceso";
                    }
                    dibujarProceso(x, y, contenido, panel_Diagrama);
                } else if (VG.getFigura_hacer_mientras() == sourceDiagram || figura == "hacer mientras") {
                    if (contenido.equals("")) {
                        contenido = "Hacer Mientras";
                    }
                    dibujarHacerMientras(x, y, contenido, panel_Diagrama);
                } else if (VG.getFigura_mientras() == sourceDiagram || figura == "mientras") {
                    if (contenido.equals("")) {
                        contenido = "Mientras";
                    }
                    dibujarMientras(x, y, contenido, panel_Diagrama);
                } else if (VG.getFigura_para() == sourceDiagram || figura == "para") {
                    if (contenido.equals("")) {
                        contenido = "Para";
                    }
                    dibujarPara(x, y, contenido, panel_Diagrama);
                }
            }
            VG.setHistorial(Diagrama.getIns());
            System.out.println("largoHistorial:" + VG.getHistorial().getList_orden().size() + " -- largo:" + Diagrama.getIns().getList_orden().size());
            VG.setCount_deshacer(0);
        }
    }

    // Métodos de dibujo específicos para cada tipo de figura
    private static void dibujarCondicional(double x, double y,String contenido, AnchorPane panel_Diagrama) {
        Vertice p_Fcondicional_direccion = new Vertice(32.5, 25); //no cambiar
        Arista dimencion_Fentrada = new Arista(160, 80);
        Vertice p_Fcondicional_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2) - dimencion_Fentrada.getAncho()/4, 0);
        contenido = " A > B ";

        Diagrama figuras_true = new Diagrama();
        Diagrama figuras_false = new Diagrama();

        Condicional condicional = new Condicional(contenido, p_Fcondicional_direccion, p_Fcondicional_conexion, p_Fcondicional_conexion,dimencion_Fentrada, figuras_true, figuras_false,VG.getNumero_figura());
        double separacion_condicional = 150;
        Canvas canvas_Fcondicional = new Canvas(dimencion_Fentrada.getAncho()+separacion_condicional, dimencion_Fentrada.getAlto());

        // Obtener la posición ajustada para la nueva figura
        double nuevaPosY = ubicacionY_newFigura(y).getKey();

        condicional.setVertice_conexion(new Vertice (VG.getUltimoCanvasFigura().getLayoutX(),(VG.getUltimoCanvasConexion().getLayoutY()+VG.getUltimoCanvasConexion().getHeight()-40)));
        Condicional.dibujar(nuevaPosY, canvas_Fcondicional, condicional,panel_Diagrama,separacion_condicional);


        // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
        int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
        Diagrama.getIns().agregarElemento(condicional, 1, indice_Fposterior);


        // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
        int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fcondicional,VG.getUltimoIndiceConexion()) - 1;
        Diagrama.getIns().agregarElemento(canvas_Fcondicional, 1, indice_Cposterior);
        VG.cambiarUltimoCanvasConexion(conectar(condicional.getVertice_conexion().getX()+55,VG.getUltimaFiguraAñadida(), condicional, panel_Diagrama,figuras_false));


        // Agregar la nueva figura al panel
        panel_Diagrama.getChildren().add(canvas_Fcondicional);
        VG.cambiarUltimaFiguraAñadida(condicional);
        VG.cambiarUltimoCanvasFigura(canvas_Fcondicional);

        //psudocodigo
        canvas_Fcondicional.setUserData(condicional);

        //funcion que mueve las figuras por debajo de la nueva figura
        moverfiguras_agregando(condicional.getNumero_identificador());
    }

    private static void dibujarDocumento(double x, double y,String contenido, AnchorPane panel_Diagrama) {
        Vertice p_Fdocumento_direccion = new Vertice(32.5, 25); //no cambiar
        Vertice p_Fdocumento_conexion = new Vertice(0, 0);
        Arista dimencion_Fentrada = new Arista(120, 70);
        contenido = " Documento ";
        Documento documento = new Documento(contenido, p_Fdocumento_direccion, p_Fdocumento_conexion, dimencion_Fentrada,VG.getNumero_figura());
        Canvas canvas_Fdocumento = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

        // Obtener la posición Y ajustada para la nueva figura
        double nuevaPosY = ubicacionY_newFigura(y).getKey();;
        documento.setVertice_conexion(new Vertice (VG.getUltimoCanvasFigura().getLayoutX(),(VG.getUltimoCanvasConexion().getLayoutY()+VG.getUltimoCanvasConexion().getHeight()-40)));

        Documento.dibujo(nuevaPosY, canvas_Fdocumento, documento, panel_Diagrama);

        // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
        int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
        Diagrama.getIns().agregarElemento(documento, 1, indice_Fposterior);

        // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
        int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fdocumento,VG.getUltimoIndiceConexion())-1;
        Diagrama.getIns().agregarElemento(canvas_Fdocumento, 1, indice_Cposterior);
        VG.cambiarUltimoCanvasConexion(conectar(documento.getVertice_conexion().getX(), VG.getUltimaFiguraAñadida(), documento, panel_Diagrama,Diagrama.getIns()));

        // Agregar la nueva figura al panel
        panel_Diagrama.getChildren().add(canvas_Fdocumento);
        VG.cambiarUltimaFiguraAñadida(documento);
        VG.cambiarUltimoCanvasFigura(canvas_Fdocumento);

        //psudocodigo
        canvas_Fdocumento.setUserData(documento);

        //funcion que mueve las figuras por debajo de la nueva figura
        moverfiguras_agregando(documento.getNumero_identificador());
    }

    private static void dibujarEntrada(double x, double y,String contenido, AnchorPane panel_Diagrama) {
        Vertice p_Fentrada_direccion = new Vertice(32.5, 25); //no cambiar
        Vertice p_Fentrada_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
        contenido = " Entrada ";
        Arista dimencion_Fentrada = new Arista(153, 50);
        Entrada entrada = new Entrada(contenido, p_Fentrada_direccion, p_Fentrada_conexion, dimencion_Fentrada,VG.getNumero_figura());
        Canvas canvas_Fentrada = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

        // Obtener la posición Y ajustada para la nueva figura
        double nuevaPosY = ubicacionY_newFigura(y).getKey();;
        entrada.setVertice_conexion(new Vertice (VG.getUltimoCanvasFigura().getLayoutX(),(VG.getUltimoCanvasConexion().getLayoutY()+VG.getUltimoCanvasConexion().getHeight()-40)));

        Entrada.dibujo(nuevaPosY ,canvas_Fentrada, entrada,panel_Diagrama);

        // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
        int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
        Diagrama.getIns().agregarElemento(entrada, 1, indice_Fposterior);

        // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
        int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fentrada,VG.getUltimoIndiceConexion()) - 1;
        Diagrama.getIns().agregarElemento(canvas_Fentrada, 1, indice_Cposterior);
        VG.cambiarUltimoCanvasConexion(conectar(entrada.getVertice_conexion().getX(),VG.getUltimaFiguraAñadida(), entrada, panel_Diagrama,Diagrama.getIns()));

        // Agregar la nueva figura al panel
        panel_Diagrama.getChildren().add(canvas_Fentrada);
        VG.cambiarUltimaFiguraAñadida(entrada);
        VG.cambiarUltimoCanvasFigura(canvas_Fentrada);

        //psudocodigo
        canvas_Fentrada.setUserData(entrada);

        //funcion que mueve las figuras por debajo de la nueva figura
        moverfiguras_agregando(entrada.getNumero_identificador());
    }

    private static void dibujarSalida(double x, double y,String contenido, AnchorPane panel_Diagrama) {
        Vertice p_Fsalida_direccion = new Vertice(32.5, 25); //no cambiar
        Vertice p_Fsalida_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
        contenido = " Salida ";
        Arista dimencion_Fsalida = new Arista(153, 50);
        Salida salida = new Salida(contenido, p_Fsalida_direccion, p_Fsalida_conexion, dimencion_Fsalida,VG.getNumero_figura());
        Canvas canvas_Fsalida = new Canvas(dimencion_Fsalida.getAncho(), dimencion_Fsalida.getAlto());

        // Obtener la posición Y ajustada para la nueva figura
        double nuevaPosY = ubicacionY_newFigura(y).getKey();;
        salida.setVertice_conexion(new Vertice (VG.getUltimoCanvasFigura().getLayoutX(),(VG.getUltimoCanvasConexion().getLayoutY()+VG.getUltimoCanvasConexion().getHeight()-40)));

        Salida.dibujo(nuevaPosY,canvas_Fsalida, salida, panel_Diagrama);

        // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
        int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
        Diagrama.getIns().agregarElemento(salida, 1, indice_Fposterior);

        // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
        int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fsalida,VG.getUltimoIndiceConexion()) - 1;
        Diagrama.getIns().agregarElemento(canvas_Fsalida, 1, indice_Cposterior);
        VG.cambiarUltimoCanvasConexion(conectar(salida.getVertice_conexion().getX(),VG.getUltimaFiguraAñadida(), salida, panel_Diagrama,Diagrama.getIns()));

        // Agregar la nueva figura al panel
        panel_Diagrama.getChildren().add(canvas_Fsalida);
        VG.cambiarUltimaFiguraAñadida(salida);
        VG.cambiarUltimoCanvasFigura(canvas_Fsalida);

        //psudocodigo
        canvas_Fsalida.setUserData(salida);

        //funcion que mueve las figuras por debajo de la nueva figura
        moverfiguras_agregando(salida.getNumero_identificador());
    }

    private static void dibujarProceso(double x, double y,String contenido, AnchorPane panel_Diagrama) {
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
        double nuevaPosY = ubicacionY_newFigura(y).getKey();;
        proceso.setVertice_conexion(new Vertice (VG.getUltimoCanvasFigura().getLayoutX(),(VG.getUltimoCanvasConexion().getLayoutY()+VG.getUltimoCanvasConexion().getHeight()-40)));

        Proceso.dibujo(nuevaPosY,canvas_Fproceso, proceso, panel_Diagrama);

        // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
        int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
        Diagrama.getIns().agregarElemento(proceso, 1, indice_Fposterior);

        // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
        int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fproceso,VG.getUltimoIndiceConexion()) - 1;
        Diagrama.getIns().agregarElemento(canvas_Fproceso, 1, indice_Cposterior);
        VG.cambiarUltimoCanvasConexion(conectar(proceso.getVertice_conexion().getX(),VG.getUltimaFiguraAñadida(), proceso, panel_Diagrama,Diagrama.getIns()));

        // Agregar la nueva figura al panel
        panel_Diagrama.getChildren().add(canvas_Fproceso);
        VG.cambiarUltimaFiguraAñadida(proceso);
        VG.cambiarUltimoCanvasFigura(canvas_Fproceso);

        //psudocodigo
        canvas_Fproceso.setUserData(proceso);

        //funcion que mueve las figuras por debajo de la nueva figura
        moverfiguras_agregando(proceso.getNumero_identificador());
    }

    private static void dibujarHacerMientras(double x, double y,String contenido, AnchorPane panel_Diagrama) {
        Vertice p_Fhacer_mientras_direccion = new Vertice(32.5, 25);
        Vertice p_Fhacer_mientras_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
        Arista dimencion_Fentrada = new Arista(120, 70);
        contenido = " Hacer Mientras ";
        ArrayList<String> contenidoValidado = new ArrayList<>();
        Hacer_Mientras hacer_mientras = new Hacer_Mientras(contenido, p_Fhacer_mientras_direccion, p_Fhacer_mientras_conexion, dimencion_Fentrada, contenidoValidado,VG.getNumero_figura());
        Canvas canvas_Fhacer_mientras = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

        // Obtener la posición Y ajustada para la nueva figura
        double nuevaPosY = ubicacionY_newFigura(y).getKey();;
        hacer_mientras.setVertice_conexion(new Vertice (VG.getUltimoCanvasFigura().getLayoutX(),(VG.getUltimoCanvasConexion().getLayoutY()+VG.getUltimoCanvasConexion().getHeight()-40)));

        Hacer_Mientras.dibujo(contenido, x, nuevaPosY , canvas_Fhacer_mientras, hacer_mientras,panel_Diagrama);

        // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
        int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
        Diagrama.getIns().agregarElemento(hacer_mientras, 1, indice_Fposterior);

        // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
        int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fhacer_mientras,VG.getUltimoIndiceConexion()) - 1;
        Diagrama.getIns().agregarElemento(canvas_Fhacer_mientras, 1, indice_Cposterior);
        VG.cambiarUltimoCanvasConexion(conectar(hacer_mientras.getVertice_conexion().getX(),VG.getUltimaFiguraAñadida(), hacer_mientras, panel_Diagrama,Diagrama.getIns()));

        // Agregar la nueva figura al panel
        panel_Diagrama.getChildren().add(canvas_Fhacer_mientras);
        VG.cambiarUltimaFiguraAñadida(hacer_mientras);
        VG.cambiarUltimoCanvasFigura(canvas_Fhacer_mientras);

        //psudocodigo
        canvas_Fhacer_mientras.setUserData(hacer_mientras);

        //funcion que mueve las figuras por debajo de la nueva figura
        moverfiguras_agregando(hacer_mientras.getNumero_identificador());
    }

    private static void dibujarMientras(double x, double y,String contenido, AnchorPane panel_Diagrama) {
        Vertice p_Fcondicional_direccion = new Vertice(32.5, 25);
        Vertice p_Fcondicional_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
        Arista dimencion_Fentrada = new Arista(120, 70);
        contenido = " Mientras ";
        ArrayList<String> contenidoValidado = new ArrayList<>();
        Mientras mientras = new Mientras(contenido, p_Fcondicional_direccion, p_Fcondicional_conexion, dimencion_Fentrada, contenidoValidado,VG.getNumero_figura());
        Canvas canvas_Fmientras = new Canvas(dimencion_Fentrada.getAncho(), dimencion_Fentrada.getAlto());

        // Obtener la posición Y ajustada para la nueva figura
        double nuevaPosY = ubicacionY_newFigura(y).getKey();;
        mientras.setVertice_conexion(new Vertice (VG.getUltimoCanvasFigura().getLayoutX(),(VG.getUltimoCanvasConexion().getLayoutY()+VG.getUltimoCanvasConexion().getHeight()-40)));

        Mientras.dibujo(contenido, x, nuevaPosY, canvas_Fmientras, mientras,panel_Diagrama);

        // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
        int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
        Diagrama.getIns().agregarElemento(mientras, 1, indice_Fposterior);

        // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
        int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fmientras,VG.getUltimoIndiceConexion()) - 1;
        Diagrama.getIns().agregarElemento(canvas_Fmientras, 1, indice_Cposterior);
        VG.cambiarUltimoCanvasConexion(conectar(mientras.getVertice_conexion().getX(),VG.getUltimaFiguraAñadida(), mientras, panel_Diagrama, Diagrama.getIns()));

        // Agregar la nueva figura al panel
        panel_Diagrama.getChildren().add(canvas_Fmientras);
        VG.cambiarUltimaFiguraAñadida(mientras);
        VG.cambiarUltimoCanvasFigura(canvas_Fmientras);

        //psudocodigo
        canvas_Fmientras.setUserData(mientras);

        //funcion que mueve las figuras por debajo de la nueva figura
        moverfiguras_agregando(mientras.getNumero_identificador());
    }

    private static void dibujarPara(double x, double y,String contenido, AnchorPane panel_Diagrama) {
        Vertice p_Fpara_direccion = new Vertice(32.5, 25);
        Vertice p_Fpara_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
        Arista dimencion_Fpara = new Arista(120, 70);
        contenido = " Para ";
        ArrayList<String> contenidoValidado = new ArrayList<>();
        Para para = new Para(contenido, p_Fpara_direccion, p_Fpara_conexion, dimencion_Fpara, contenidoValidado,VG.getNumero_figura());
        Canvas canvas_Fpara = new Canvas(dimencion_Fpara.getAncho(), dimencion_Fpara.getAlto());

        // Obtener la posición Y ajustada para la nueva figura
        double nuevaPosY = ubicacionY_newFigura(y).getKey();;
        para.setVertice_conexion(new Vertice (VG.getUltimoCanvasFigura().getLayoutX(),(VG.getUltimoCanvasConexion().getLayoutY()+VG.getUltimoCanvasConexion().getHeight()-40)));

        Para.dibujo();

        // Agregar la nueva figura a la lista de figuras, antes de figura siguiente
        int indice_Fposterior = determinarIndiceFigura_InList_figuras(VG.getUltimaFiguraAñadida(), x, y);
        Diagrama.getIns().agregarElemento(para, 1, indice_Fposterior);

        // Agregar la nueva figura a la lista de canvas, antes de canvas conexión-figura
        int indice_Cposterior = determinarIndiceCanvas_InList_orden(canvas_Fpara,VG.getUltimoIndiceConexion()) - 1;
        Diagrama.getIns().agregarElemento(canvas_Fpara, 1, indice_Cposterior);
        VG.cambiarUltimoCanvasConexion(conectar(para.getVertice_conexion().getX(),VG.getUltimaFiguraAñadida(), para, panel_Diagrama,Diagrama.getIns()));

        // Agregar la nueva figura al panel
        panel_Diagrama.getChildren().add(canvas_Fpara);
        VG.cambiarUltimaFiguraAñadida(para);
        VG.cambiarUltimoCanvasFigura(canvas_Fpara);

        //psudocodigo
        canvas_Fpara.setUserData(para);

        //funcion que mueve las figuras por debajo de la nueva figura
        moverfiguras_agregando(para.getNumero_identificador());
    }

    // Métodos auxiliares----------------------------------------------------------------

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

    private static Figura obtenerFiguraDesdeCanvas(Canvas canvas) {
        ArrayList<Canvas> list_canvas = Diagrama.getIns().getList_orden();
        ArrayList<Figura> list_figura = Diagrama.getIns().getList_figuras();

        int index = list_canvas.indexOf(canvas);
        int ind_canvas = 0;
        int ind_figuras = 0;
        if (index != -1) {
            for(; ind_canvas < list_canvas.size();){
                if(ind_canvas==index){
                    if(ind_figuras>=list_figura.size()){
                        return list_figura.get(list_figura.size()-1);
                    }else{
                        return list_figura.get(ind_figuras);
                    }
                }
                ind_canvas+=2;
                ind_figuras++;
            }
        }
        return list_figura.get(list_figura.size()-1);
    }

    public static Pair<Double,Figura> ubicacionY_newFigura(double y) {
        ArrayList<Canvas> list_canvas = Diagrama.getIns().getList_orden();
        int i = 1;
        boolean condicion = false;

        //añadir figura posterior a la ultima
        if (VG.getUltimoCanvasConexion().getLayoutY() < y && y < (VG.getUltimoCanvasConexion().getLayoutY() + VG.getUltimoCanvasConexion().getHeight())) {
            //System.out.println(VG.getUltimoCanvasConexion().getLayoutY()+" < "+ y + " < "+(VG.getUltimoCanvasConexion().getLayoutY() + VG.getUltimoCanvasConexion().getHeight())+"\n");
            double v1 = VG.getUltimoCanvasConexion().getLayoutY() + VG.getUltimoCanvasConexion().getHeight();
            Figura v2 = VG.getUltimaFiguraAñadida();
            return new Pair<>(v1, v2);

        } else {
            for (; i < list_canvas.size()-1; i+=2) {
                try {

                    if (list_canvas.get(i).getLayoutY() <= y) {
                        if( y <= list_canvas.get(i).getLayoutY() + list_canvas.get(i).getHeight()){
                            double v1 = list_canvas.get(i).getLayoutY() + list_canvas.get(i).getHeight();;
                            Figura v2 = obtenerFiguraDesdeCanvas(list_canvas.get(i));
                            return new Pair<>(v1, v2);
                        }
                    }

                } catch (NullPointerException e) {
                    System.out.println("Ups...");
                }
            }
            //System.out.println("final");
            double v1 = list_canvas.get(i-2).getLayoutY() + list_canvas.get(i-2).getHeight();
            Figura v2 = obtenerFiguraDesdeCanvas(list_canvas.get(i-2));
            return new Pair<>(v1, v2);
        }
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

    public static Canvas conectar(double x,Figura figuraOrigen, Figura figuraDestino, AnchorPane panel_Diagrama,Diagrama diagrama) {
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
}
