package Clases;

public class Figura {
    public String contenido;
    public Vertice vertice_cordenada;
    public Vertice vertice_conexion;
    public  Arista dimenciones;

    public Figura(String contenido, Vertice vertice_cordenada, Vertice vertice_conexion, Arista dimenciones) {
        this.contenido = contenido;
        this.vertice_cordenada = vertice_cordenada;
        this.vertice_conexion = vertice_conexion;
        this.dimenciones = dimenciones;
    }
    public void asignar_conexion(Vertice vertice_conexion){

    }
    public void mover(Vertice vertice_direccion){

    }
    public void dibujar(Vertice vertice_direccion){

    }
    public void editar(String contenido){

    }

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
}
