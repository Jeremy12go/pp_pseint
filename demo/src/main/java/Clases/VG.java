package Clases;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

public class VG {
    private static Figura ultimaFiguraAñadida;
    private static Canvas ultimoCanvasFigura;
    private static Canvas ultimoCanvasConexion;
    private static int numero_figura;
    private static Diagrama historial;
    private static int ultimoIndiceConexion;

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

    public static void setUltimoIndiceConexion(int ultimo){
        ultimoIndiceConexion = ultimo;
    }

    public static void setHistorial(Diagrama hist){
        historial = hist;
    }

    public static void setNumero_figura(int n){
        numero_figura = n;
    }

    public static void aumentar_numero_figura() {
        numero_figura++;
    }

    public static void disminuir_numero_figura() {
        numero_figura--;
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

    public static int getNumero_figura() {return numero_figura;}

    public static Diagrama getHistorial() {return historial;}

    public static int getUltimoIndiceConexion() {return ultimoIndiceConexion;}
}
