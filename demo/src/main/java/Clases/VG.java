package Clases;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.app.AppController;

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


    private static ImageView figura_proceso;
    private static ImageView figura_entrada;
    private static ImageView figura_salida;
    private static ImageView figura_condiconal;
    private static ImageView figura_documento;
    private static ImageView figura_mientras;
    private static ImageView figura_hacer_mientras;
    private static ImageView figura_para;

    private static int count_deshacer;

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

    public static ImageView getFigura_proceso() {
        return figura_proceso;
    }

    public static void setFigura_proceso(ImageView figura_proceso_) {
        figura_proceso = figura_proceso_;
    }

    public static ImageView getFigura_entrada() {
        return figura_entrada;
    }

    public static void setFigura_entrada(ImageView figura_entrada_) {
        figura_entrada = figura_entrada_;
    }

    public static ImageView getFigura_salida() {
        return figura_salida;
    }

    public static void setFigura_salida(ImageView figura_salida_) {figura_salida = figura_salida_;
    }

    public static ImageView getFigura_condiconal() {
        return figura_condiconal;
    }

    public static void setFigura_condiconal(ImageView figura_condiconal_) {
        figura_condiconal = figura_condiconal_;
    }

    public static ImageView getFigura_documento() {
        return figura_documento;
    }

    public static void setFigura_documento(ImageView figura_documento_) {
        figura_documento = figura_documento_;
    }

    public static ImageView getFigura_mientras() {
        return figura_mientras;
    }

    public static void setFigura_mientras(ImageView figura_mientras_) {
        figura_mientras = figura_mientras_;
    }

    public static ImageView getFigura_hacer_mientras() {
        return figura_hacer_mientras;
    }

    public static void setFigura_hacer_mientras(ImageView figura_hacer_mientras_) {
        figura_hacer_mientras = figura_hacer_mientras_;
    }

    public static ImageView getFigura_para() {
        return figura_para;
    }

    public static void setFigura_para(ImageView figura_para_) {
        figura_para = figura_para_;
    }

    public static int getCount_deshacer() {
        return count_deshacer;
    }

    public static void setCount_deshacer(int count_deshacer) {
        VG.count_deshacer = count_deshacer;
    }
}
