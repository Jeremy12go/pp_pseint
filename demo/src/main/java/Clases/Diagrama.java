package Clases;
import javafx.scene.canvas.Canvas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Diagrama<T> implements Serializable {
    public String titulo;

    public ArrayList<Figura>  list_figuras = new ArrayList<Figura>();

    public ArrayList<Conector> list_conexiones = new ArrayList<Conector>();

    public ArrayList<Canvas> list_orden = new ArrayList<Canvas>();

    static Diagrama ins;

    public Diagrama(String titulo, ArrayList<Figura> list_elementos, ArrayList<Conector> list_conexiones, ArrayList<Canvas> list_orden) {
        this.titulo = titulo;
        this.list_figuras = list_elementos;
        this.list_conexiones = list_conexiones;
        this.list_orden = list_orden;
    }

    public Diagrama() {}

    public void eliminarElementos(int metodo, int indice){
        if(metodo==0){
            list_figuras.remove(indice);
        }else if(metodo==1){
            list_conexiones.remove(indice);
        }else{
            list_orden.remove(indice);
        }

    }

    public void agregarElemento(T data, int metodo, int indice){

        ArrayList<String> tag_figuras = new ArrayList<String>(Arrays.asList("Inicio_Fin",
                "Entrada","Figura","Condicional","Proceso"));

        for (String elemento : tag_figuras) {
            if (elemento.equals(data.getClass().getSimpleName())) {
                if(metodo==0){
                    list_figuras.add((Figura) data);
                }else{
                    list_figuras.add(indice,(Figura) data);
                }
            }
        }
        String tipo = "Conector";
        if (tipo.equals(data.getClass().getSimpleName())) {
            if(metodo==0){
                list_conexiones.add((Conector) data);
            }else{
                list_conexiones.add(indice,(Conector) data);
            }
        }
        tipo = "Canvas";
        if (tipo.equals(data.getClass().getSimpleName())) {
            if(metodo==0){
                list_orden.add((Canvas) data);
            }else{
                list_orden.add(indice,(Canvas) data);
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

    public  ArrayList<Canvas> getList_orden(){
        return list_orden;
    }

    public void setList_elementos(ArrayList<Figura> list_figuras, ArrayList<Conector> list_conexiones) {
        this.list_figuras = list_figuras;
        this.list_conexiones = list_conexiones;
    }

    public void setList_conexiones(ArrayList<Conector> list_conexiones) {
        this.list_conexiones = list_conexiones;
    }

    public void setList_orden(ArrayList<Canvas> list_orden) {
        this.list_orden = list_orden;
    }

    public void setList_figuras(ArrayList<Figura> list_figuras) {
        this.list_figuras = list_figuras;
    }

    public static Diagrama getInstance(){
        if(ins == null){
            return new Diagrama();
        }
        return ins;
    }
}
