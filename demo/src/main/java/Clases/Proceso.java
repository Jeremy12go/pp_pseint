package Clases;

import java.util.ArrayList;

public class Proceso extends Figura{
    public String contenido_valido;
    public ArrayList<String> operaciones;
    public ArrayList<String> operandos;

    public Proceso(String contenido, Vertice vertice_direccion, Vertice vertice_conexion, Arista dimension, String contenido_valido, ArrayList<String> operaciones, ArrayList<String> operandos) {
        super(contenido, vertice_direccion, vertice_conexion, dimension);
        this.contenido_valido = contenido_valido;
        this.operaciones = operaciones;
        this.operandos = operandos;
    }

    public void validacion(String contenido){

    }
}
