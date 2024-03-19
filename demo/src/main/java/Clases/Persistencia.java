package Clases;

import java.io.*;
import java.util.ArrayList;

public class Persistencia {
    public void serializar(ArrayList<Figura> inicio){
        try{
            FileOutputStream archivo = new FileOutputStream("Diagrama.txt");
            ObjectOutputStream objSalida = new ObjectOutputStream(archivo);
            objSalida.writeObject(inicio);
            objSalida.close();
            archivo.close();
            System.out.println("Guardando...");
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }
    public ArrayList<Figura> deserializar(ArrayList<Figura> inicio){
        try{
            FileInputStream archivo = new FileInputStream("Diagrama.txt");
            ObjectInputStream objEntrada = new ObjectInputStream(archivo);
            inicio = (ArrayList<Figura>) objEntrada.readObject();
            objEntrada.close();
            archivo.close();
            return inicio;

        }catch (IOException ex){
            System.out.println("Archivo no existente\ncreando...");
            serializar(inicio);
        }catch (ClassNotFoundException cnf){
            System.out.println("Clase no existe...");
        }
        return inicio;
    }
}
