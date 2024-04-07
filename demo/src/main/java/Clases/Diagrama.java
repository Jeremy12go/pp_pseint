package Clases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Diagrama<T> implements Serializable {
    public String titulo;
    public ArrayList<Figura>  list_figuras = new ArrayList<Figura>();

    public ArrayList<Conector> list_conexiones = new ArrayList<Conector>();

    private static Diagrama ins;

    public Diagrama(String titulo, ArrayList<Figura> list_elementos) {
        this.titulo = titulo;
        this.list_figuras = list_elementos;
    }

    public Diagrama() {}

    public void eliminarElementos(int metodo, int indice){
        if(metodo==0){
            list_figuras.remove(indice);
        }else{
            list_conexiones.remove(indice);
        }

    }
    public void agregarElemento(T data, int metodo, int indice){
        boolean condicion = false;
        ArrayList<String> tag_figuras = new ArrayList<String>(Arrays.asList("Inicio_Fin",
                "Entrada","Figura","Condicional","Proceso"));

        for (String elemento : tag_figuras) {
            if (elemento.equals(data.getClass().getSimpleName())) {
                condicion = true;
                if(metodo==0){
                    list_figuras.add((Figura) data);
                }else{
                    list_figuras.add(indice,(Figura) data);
                }
            }
        }

        if(!condicion){
            if(metodo==0){
                list_conexiones.add((Conector) data);
            }else{
                list_conexiones.add(indice,(Conector) data);
            }
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public ArrayList<Figura> getList_figuras() {
        return list_figuras;
    }

    public ArrayList<Conector> getList_conexiones(){
        return list_conexiones;
    }

    public void setList_elementos(ArrayList<Figura> list_figuras, ArrayList<Conector> list_conexiones) {
        this.list_figuras = list_figuras;
        this.list_conexiones = list_conexiones;
    }

    public static Diagrama getInstance(){
        if(ins == null){
            return new Diagrama();
        }
        return ins;
    }
}
