package Clases;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;

public class VG {
    private static Figura ultimaFiguraAñadida;
    private static Canvas ultimoCanvasFigura;
    private static Canvas ultimoCanvasConexion;
    private static int numero_figura;
    private static Diagrama historial;
    private static int ultimoIndiceConexion;
    private static ArrayList<Figura> historialFiguras;
    private static Pane basurero;

    private static int clickCount;
    private static double previousX;
    private static double previousY;
    //parametros
    private static Font font;
    private static double tamañoTxt;
    private static double tamaño_Lbordes;
    private static double tamaño_Lfechas;
    private static double tamaño_Lconexiones;
    //colores
    private static Color colorBordes;
    private static Color colorRelleno;
    private static Color colorTexto;
    private static Color colorFlecha;

    //Getter and Setter
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

    public static int getClickCount() {
        return clickCount;
    }

    public static void setClickCount(int clickCount) {
        VG.clickCount = clickCount;
    }

    public static double getPreviousX() {
        return previousX;
    }

    public static void setPreviousX(double previousX) {
        VG.previousX = previousX;
    }

    public static double getPreviousY() {
        return previousY;
    }

    public static void setPreviousY(double previousY) {
        VG.previousY = previousY;
    }

    public static Font getFont() {
        return font;
    }

    public static void setFont(Font font) {
        VG.font = font;
    }

    public static double getTamañoTxt() {
        return tamañoTxt;
    }

    public static void setTamañoTxt(double tamañoTxt) {
        VG.tamañoTxt = tamañoTxt;
    }

    public static double getTamaño_Lbordes() {
        return tamaño_Lbordes;
    }

    public static void setTamaño_Lbordes(double tamaño_Lbordes) {
        VG.tamaño_Lbordes = tamaño_Lbordes;
    }

    public static double getTamaño_Lfechas() {
        return tamaño_Lfechas;
    }

    public static void setTamaño_Lfechas(double tamaño_Lfechas) {
        VG.tamaño_Lfechas = tamaño_Lfechas;
    }

    public static double getTamaño_Lconexiones() {
        return tamaño_Lconexiones;
    }

    public static void setTamaño_Lconexiones(double tamaño_Lconexiones) {
        VG.tamaño_Lconexiones = tamaño_Lconexiones;
    }

    public static Color getColorBordes() {
        return colorBordes;
    }

    public static void setColorBordes(Color colorBordes) {
        VG.colorBordes = colorBordes;
    }

    public static Color getColorRelleno() {
        return colorRelleno;
    }

    public static void setColorRelleno(Color colorRelleno) {
        VG.colorRelleno = colorRelleno;
    }

    public static Color getColorTexto() {
        return colorTexto;
    }

    public static void setColorTexto(Color colorTexto) {
        VG.colorTexto = colorTexto;
    }

    public static Color getColorFlecha() {
        return colorFlecha;
    }

    public static void setColorFlecha(Color colorFlecha) {
        VG.colorFlecha = colorFlecha;
    }

    public static Pane getBasurero() {
        return basurero;
    }

    public static void setBasurero(Pane basurero) {
        VG.basurero = basurero;
    }
}
