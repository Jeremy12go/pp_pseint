package Clases;

import java.util.ArrayList;

public class Para extends Figura{
    public ArrayList<String> contenido_validado;

    public Para(String contenido, Vertice vertice_direccion, Vertice vertice_conexion, Arista dimension, ArrayList<String> contenido_validado,int numero_identificador) {
        super(contenido, vertice_direccion, vertice_conexion, dimension, numero_identificador);
        this.contenido_validado = contenido_validado;
    }
    public static void dibujo(){}

    public static void edición(){}
    public void validacion(String contenido){

    }
    public void operacion(String contenido_validado){

    }
}
