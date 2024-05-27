package Clases;

import java.util.ArrayList;

public class Mientras extends Figura{
    public ArrayList<String> contenido_validado;

    public Mientras(String contenido, Vertice vertice_direccion, Vertice vertice_conexion, Arista dimension, ArrayList<String> contenido_validado) {
        super(contenido, vertice_direccion, vertice_conexion, dimension);
        this.contenido_validado = contenido_validado;
    }
    public void validacion(String contenido){

    }
    public void operacion(String contenido_validado){

    }
}
