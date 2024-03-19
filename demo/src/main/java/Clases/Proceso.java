package Clases;

import java.util.ArrayList;

public class Proceso extends Figura{
    public String contenido_valido;
    public ArrayList<String> operaciones;
    public ArrayList<String> operandos;

    public Proceso(String contenido, Posicion posicion, Punto punto_conexion, String contenido_valido, ArrayList<String> operaciones, ArrayList<String> operandos) {
        super(contenido, posicion, punto_conexion);
        this.contenido_valido = contenido_valido;
        this.operaciones = operaciones;
        this.operandos = operandos;
    }

    public void validacion(String contenido){

    }
}
