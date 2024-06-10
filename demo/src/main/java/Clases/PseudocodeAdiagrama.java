package Clases;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.List;

public class PseudocodeAdiagrama {

    public static void generateFlowDiagram(Label pseudocodeLabel, AnchorPane panel_Diagrama) {
        String pseudocode = pseudocodeLabel.getText();
        String[] lines = pseudocode.split("\n");

        double yPosition = 20.0; // Initial position

        for (String line : lines) {
            if (line.startsWith("Entrada:")) {
                createCanvas(panel_Diagrama, new Entrada(line.replace("Entrada: ", "").trim()), yPosition);
            } else if (line.startsWith("Salida:")) {
                createCanvas(panel_Diagrama, new Salida(line.replace("Salida: ", "").trim()), yPosition);
            } else if (line.startsWith("Documento:")) {
                createCanvas(panel_Diagrama, new Documento(line.replace("Documento: ", "").trim()), yPosition);
            } else if (line.startsWith("Proceso:")) {
                createCanvas(panel_Diagrama, new Proceso(line.replace("Proceso: ", "").trim()), yPosition);
            } else if (line.startsWith("Condicional")) {
                createCanvas(panel_Diagrama, new Condicional(line.replace("Condicional ", "").trim()), yPosition);
            } else if (line.startsWith("Hacer Mientras:")) {
                createCanvas(panel_Diagrama, new Hacer_Mientras(line.replace("Hacer Mientras: ", "").trim()), yPosition);
            } else if (line.startsWith("Mientras:")) {
                createCanvas(panel_Diagrama, new Mientras(line.replace("Mientras: ", "").trim()), yPosition);
            } else if (line.startsWith("Para:")) {
                createCanvas(panel_Diagrama, new Para(line.replace("Para: ", "").trim()), yPosition);
            }
            yPosition += 50.0; // Adjust position for the next figure
        }
    }

    private static void createCanvas(AnchorPane panel_Diagrama, Figura figura, double yPosition) {
        Canvas canvas = new Canvas(200, 50);
        canvas.setLayoutX(20.0);
        canvas.setLayoutY(yPosition);
        canvas.setUserData(figura);

        // Optionally, draw something on the canvas to represent the figure
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.strokeText(figura.getContenido(), 10, 25);

        panel_Diagrama.getChildren().add(canvas);
    }

    public static void editPseudocode(String newPseudocode, Label pseudocodeLabel, AnchorPane panel_Diagrama) {
        pseudocodeLabel.setText(newPseudocode);
        panel_Diagrama.getChildren().clear(); // Clear the previous diagram
        generateFlowDiagram(pseudocodeLabel, panel_Diagrama); // Generate the new diagram
    }

}
