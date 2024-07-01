package Clases;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class VG {
    private static Figura ultimaFiguraAñadida;
    private static Canvas ultimoCanvasFigura;
    private static Canvas ultimoCanvasConexion;

    private static ArrayList<Figura> historialFiguras;

    public static void cambiarUltimaFiguraAñadida(Figura f) {
        ultimaFiguraAñadida = f;
    }

    public static void cambiarUltimoCanvasFigura(Canvas c) {
        ultimoCanvasFigura = c;
    }

    public static void cambiarUltimoCanvasConexion(Canvas c) {
        ultimoCanvasConexion = c;
    }

    public static void GuardarHistorialFigura(ArrayList<Figura> figuras) {
        historialFiguras = figuras;
    }

    public static Figura getUltimaFiguraAñadida() {
        return ultimaFiguraAñadida;
    }

    public static Canvas getUltimoCanvasFigura() {
        return ultimoCanvasFigura;
    }

    public static Canvas getUltimoCanvasConexion() {
        return ultimoCanvasConexion;
    }

    public static ArrayList<Figura> getHistorialFiguras() {return historialFiguras;}

    /*
    public void dibujarFigura(double x, double y, ImageView sourceDiagram, String figura) {
        String contenido = "";
        Figura _inicial = (Figura) ins.getList_figuras().get(0);
        Canvas _final = obtenerCanvasDesdeFigura((Figura) ins.getList_figuras().get(ins.getList_figuras().size() - 1));

        //condicional que valide si las cordenadas estan dentro de lo razonable para agregar
        if ((panel_Diagrama.getWidth() / 2) - 150 < x && x < (panel_Diagrama.getWidth() / 2) + 150) {
            //if (_inicial.getVertice_conexion().getY() < y && y < _final.getLayoutY()+80) {

            if (figura_condiconal == sourceDiagram || figura == "Condicional") {
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

                //psudocodigo
                canvas_Fcondicional.setUserData(condicional);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(condicional);

            } else if (figura_documento == sourceDiagram || figura == "Documento") {
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
                moverfiguras(documento);

            } else if (figura_entrada == sourceDiagram || figura == "Entrada") {
                Vertice p_Fentrada_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fentrada_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                contenido = " Entrada ";
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

                //psudocodigo
                canvas_Fentrada.setUserData(entrada);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(entrada);

            } else if (figura_salida == sourceDiagram || figura == "Salida") {

                Vertice p_Fsalida_direccion = new Vertice(32.5, 25); //no cambiar
                Vertice p_Fsalida_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                contenido = " Salida ";
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

                //psudocodigo
                canvas_Fsalida.setUserData(salida);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(salida);

            } else if (figura_proceso == sourceDiagram || figura == "Proceso") {
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

                //psudocodigo
                canvas_Fproceso.setUserData(proceso);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(proceso);

            } else if (figura_hacer_mientras == sourceDiagram || figura == "Hacer Mientras") {
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

                //psudocodigo
                canvas_Fhacer_mientras.setUserData(hacer_mientras);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(hacer_mientras);

            } else if (figura_mientras == sourceDiagram || figura == "Mientras") {
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

                //psudocodigo
                canvas_Fmientras.setUserData(mientras);

                //funcion que mueve las figuras por debajo de la nueva figura
                moverfiguras(mientras);

            } else if (figura_para == sourceDiagram || figura == "Para") {
                Vertice p_Fpara_direccion = new Vertice(32.5, 25);
                Vertice p_Fpara_conexion = new Vertice((panel_Diagrama.getMinWidth() / 2), y - 40);
                Arista dimencion_Fpara = new Arista(120, 70);
                contenido = " Para ";
                ArrayList<String> contenidoValidado = new ArrayList<>();
                Para para = new Para(contenido, p_Fpara_direccion, p_Fpara_conexion, dimencion_Fpara, contenidoValidado);
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
                moverfiguras(para);
            }
        }
    }

     */

}
