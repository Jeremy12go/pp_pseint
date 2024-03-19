package Clases;

import java.util.ArrayList;

public class Condicional extends Figura{
    public ArrayList<String> contenido_validado;

    public Condicional(String contenido, Posicion posicion, Punto punto_conexion, ArrayList<String> contenido_validado) {
        super(contenido, posicion, punto_conexion);
        this.contenido_validado = contenido_validado;
    }
    public void validacion(String contenido){

    }
    public void operacion(String contenido_validado){

    }
}
