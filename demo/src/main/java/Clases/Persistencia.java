package Clases;

import java.io.*;
import java.util.ArrayList;

public class Persistencia {
    public void serializar_figuras(ArrayList<Figura> figuras){
        try{
            FileOutputStream archivo = new FileOutputStream("Figuras.txt");
            ObjectOutputStream objSalida = new ObjectOutputStream(archivo);
            objSalida.writeObject(figuras);
            objSalida.close();
            archivo.close();
            System.out.println("Guardando...");
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }
    public void serializar_conexiones(ArrayList<Conector> conectors){
        try{
            FileOutputStream archivo = new FileOutputStream("Conexiones.txt");
            ObjectOutputStream objSalida = new ObjectOutputStream(archivo);
            objSalida.writeObject(conectors);
            objSalida.close();
            archivo.close();
            System.out.println("Guardando...");
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }
    public ArrayList<Conector> deserializar_conexiones(ArrayList<Conector> conexiones){
        try{
            FileInputStream archivo = new FileInputStream("Conexiones.txt");
            ObjectInputStream objEntrada = new ObjectInputStream(archivo);
            conexiones = (ArrayList<Conector>) objEntrada.readObject();
            objEntrada.close();
            archivo.close();
            return conexiones;

        }catch (IOException ex){
            System.out.println("Archivo no existente\ncreando...");
            serializar_conexiones(conexiones);
        }catch (ClassNotFoundException cnf){
            System.out.println("Clase no existe...");
        }
        return conexiones;
    }
    public ArrayList<Figura> deserializar_figuras(ArrayList<Figura> figuras){
        try{
            FileInputStream archivo = new FileInputStream("Figuras.txt");
            ObjectInputStream objEntrada = new ObjectInputStream(archivo);
            figuras = (ArrayList<Figura>) objEntrada.readObject();
            objEntrada.close();
            archivo.close();
            return figuras;

        }catch (IOException ex){
            System.out.println("Archivo no existente\ncreando...");
            serializar_figuras(figuras);
        }catch (ClassNotFoundException cnf){
            System.out.println("Clase no existe...");
        }
        return figuras;
    }
}
