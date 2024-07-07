package Clases;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Figura {
    public String contenido;
    public Vertice vertice_cordenada;
    public Vertice vertice_conexion;
    public Arista dimenciones;
    public int numero_identificador;

    public Figura(String contenido, Vertice vertice_cordenada, Vertice vertice_conexion, Arista dimenciones, int numero_identificador) {
        this.contenido = contenido;
        this.vertice_cordenada = vertice_cordenada;
        this.vertice_conexion = vertice_conexion;
        this.dimenciones = dimenciones;
        this.numero_identificador = numero_identificador;
    }

    public Figura(){

    }

    protected static ArrayList<Vertice> calcular_vertices(Canvas canvas, int tipo){
        ArrayList<Vertice> vertices = new ArrayList<Vertice>();

        if(tipo == 0){
            Vertice up_left = new Vertice(0, 0);
            Vertice up_right = new Vertice(canvas.getWidth(), 0);
            Vertice down_right = new Vertice(canvas.getWidth(), canvas.getHeight());
            Vertice down_left = new Vertice(0,canvas.getHeight());
            vertices.add(up_left);
            vertices.add(up_right);
            vertices.add(down_right);
            vertices.add(down_left);
        }else{
            Vertice up = new Vertice(canvas.getWidth()/2, 0);
            Vertice right = new Vertice(canvas.getWidth(), canvas.getHeight()/2);
            Vertice down = new Vertice(canvas.getWidth()/2, canvas.getHeight());
            Vertice left = new Vertice(0,canvas.getHeight()/2);
            vertices.add(up);
            vertices.add(right);
            vertices.add(down);
            vertices.add(left);
        }
        return vertices;
    }

    protected static void limpiar_canvas(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public static void dibujar_flecha(Canvas canvas, double origenX, double origenY, double angulo, double longitud){

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

    public static Canvas crear_canvasConector(double dimencion_conector, Vertice origen, Vertice destino){
        //calcular vertice_Nfigura con la distancia/2
        Conector conector_inicial = new Conector(origen, destino);
        Canvas f_conector = new Canvas(50,dimencion_conector);

        GraphicsContext gc = f_conector.getGraphicsContext2D();
        gc.setFill(Color.RED); // Cambia a tu color preferido
        gc.fillRect(0, 0, f_conector.getWidth(), f_conector.getHeight());
        dibujar_flecha(f_conector,f_conector.getWidth()/2,0,-90, dimencion_conector-10);//ajustar longitud en relacion a los puntos

        return f_conector;
    }

    //Getter and Setter
    public void asignar_conexion(Vertice vertice_conexion){
        this.vertice_conexion=vertice_conexion;
    }

    public void mover(Vertice vertice_direccion){

    }

    public void dibujar(Vertice vertice_direccion){

    }

    public void editar(String contenido){

    }

    public void setVertice_cordenada(Vertice vertice_cordenada) {
        this.vertice_cordenada = vertice_cordenada;
    }

    public void setVertice_conexion(Vertice vertice_conexion){
        this.vertice_conexion = vertice_conexion;
    }

    public void setContenido(String contenido){
        this.contenido = contenido;
    }

    public Arista setDimenciones(Arista dimenciones) {
        return this.dimenciones = dimenciones;
    }

    public void setNumero_identificador(int numero_identificador){this.numero_identificador = numero_identificador;}

    public String getContenido() {
        return contenido;
    }

    public Vertice getVertice_cordenada() {
        return vertice_cordenada;
    }

    public Vertice getVertice_conexion() {
        return vertice_conexion;
    }

    public Arista getDimenciones() {
        return dimenciones;
    }

   public int getNumero_identificador() {
        return numero_identificador;
    }

}
