package Clases;

import javafx.scene.canvas.Canvas;

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
}
