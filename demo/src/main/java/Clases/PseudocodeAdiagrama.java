package Clases;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.example.app.AppController;

import java.util.ArrayList;
import java.util.Objects;

public class PseudocodeAdiagrama {

    public static void generateFlowDiagram(Label pseudocodeLabel, AnchorPane panel_Diagrama) {

        String pseudocode = pseudocodeLabel.getText();
        String[] lineas = pseudocode.split("\n");

        double yPosition = 20.0; // Posición inicial

        for (String line : lineas) {
            int x=20;
            if (line.startsWith("Entrada:")) {
                System.out.println("Entra 1... \n");
                String contenido = (line.replace("Entrada: ", "").trim());
                Dibujar.dibujarFigura(x, yPosition,contenido,"entrada",panel_Diagrama);
            } else if (line.startsWith("Salida:")) {
                Dibujar.dibujarFigura(x, yPosition,line.replace("Salida: ", "").trim(),"salida",panel_Diagrama);
            } else if (line.startsWith("Documento:")) {
                Dibujar.dibujarFigura(x, yPosition,line.replace("Documento: ", "").trim(),"documento",panel_Diagrama);
            } else if (line.startsWith("Proceso:")) {
                Dibujar.dibujarFigura(x, yPosition,line.replace("Proceso: ", "").trim(),"proceso",panel_Diagrama);
            } else if (line.startsWith("Condicional")) {
                Dibujar.dibujarFigura(x, yPosition,line.replace("Condicional: ", "").trim(),"condicional",panel_Diagrama);
            } else if (line.startsWith("Hacer Mientras:")) {
                Dibujar.dibujarFigura(x, yPosition,line.replace("Hacer Mientras: ", "").trim(),"hacer mientras",panel_Diagrama);
            } else if (line.startsWith("Mientras:")) {
                Dibujar.dibujarFigura(x, yPosition,line.replace("Mientras: ", "").trim(),"mientras",panel_Diagrama);
            } else if (line.startsWith("Para:")) {
                Dibujar.dibujarFigura(x, yPosition,line.replace("Para: ", "").trim(),"para",panel_Diagrama);
            }
            yPosition += 100.0; // Ajustar posición para la siguiente figura
        }
    }
}
