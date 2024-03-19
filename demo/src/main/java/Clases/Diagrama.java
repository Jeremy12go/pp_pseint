package Clases;

import java.io.Serializable;
import java.util.ArrayList;

public class Diagrama implements Serializable {
    public String titulo;
    public ArrayList<Figura>  list_elementos;

    public Diagrama(String titulo, ArrayList<Figura> list_elementos) {
        this.titulo = titulo;
        this.list_elementos = list_elementos;
    }

    public Diagrama() {}

    public ArrayList<Figura> getEstudiantes() {
        return list_elementos;
    }
    public void setList_elementos(ArrayList<Figura> list_elementos) {
        this.list_elementos = list_elementos;
    }

    public void agregarElementos(){

    }
    public void eliminarElementos(){

    }

    public String getTitulo() {
        return titulo;
    }

    public ArrayList<Figura> getList_elementos() {
        return list_elementos;
    }
}
