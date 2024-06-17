package Clases;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import java.util.Optional;

public class PseudocodeAdiagrama {

    public static void generateFlowDiagram(Label pseudocodeLabel, AnchorPane panel_Diagrama) {
        String pseudocode = pseudocodeLabel.getText();
        String[] lines = pseudocode.split("\n");

        double yPosition = 20.0; // Posición inicial

        for (String line : lines) {
            if (line.startsWith("Entrada:")) {
                createEntrada(panel_Diagrama, line.replace("Entrada: ", "").trim(), yPosition);
            } else if (line.startsWith("Salida:")) {
                createSalida(panel_Diagrama, line.replace("Salida: ", "").trim(), yPosition);
            } else if (line.startsWith("Documento:")) {
                createDocumento(panel_Diagrama, line.replace("Documento: ", "").trim(), yPosition);
            } else if (line.startsWith("Proceso:")) {
                createProceso(panel_Diagrama, line.replace("Proceso: ", "").trim(), yPosition);
            } else if (line.startsWith("Condicional")) {
                createCondicional(panel_Diagrama, line.replace("Condicional ", "").trim(), yPosition);
            } else if (line.startsWith("Hacer Mientras:")) {
                createHacerMientras(panel_Diagrama, line.replace("Hacer Mientras: ", "").trim(), yPosition);
            } else if (line.startsWith("Mientras:")) {
                createMientras(panel_Diagrama, line.replace("Mientras: ", "").trim(), yPosition);
            } else if (line.startsWith("Para:")) {
                createPara(panel_Diagrama, line.replace("Para: ", "").trim(), yPosition);
            }
            yPosition += 100.0; // Ajustar posición para la siguiente figura
        }
    }

    private static void createEntrada(AnchorPane panel_Diagrama, String contenido, double yPosition) {
        Figura entrada = new Entrada(contenido); // Crear figura Entrada
        createCanvas(panel_Diagrama, entrada, yPosition);
    }

    private static void createSalida(AnchorPane panel_Diagrama, String contenido, double yPosition) {
        Figura salida = new Salida(contenido); // Crear figura Salida
        createCanvas(panel_Diagrama, salida, yPosition);
    }

    private static void createDocumento(AnchorPane panel_Diagrama, String contenido, double yPosition) {
        Figura documento = new Documento(contenido); // Crear figura Documento
        createCanvas(panel_Diagrama, documento, yPosition);
    }

    private static void createProceso(AnchorPane panel_Diagrama, String contenido, double yPosition) {
        Figura proceso = new Proceso(contenido); // Crear figura Proceso
        createCanvas(panel_Diagrama, proceso, yPosition);
    }

    private static void createCondicional(AnchorPane panel_Diagrama, String contenido, double yPosition) {
        Figura condicional = new Condicional(contenido); // Crear figura Condicional
        createCanvas(panel_Diagrama, condicional, yPosition);
    }

    private static void createHacerMientras(AnchorPane panel_Diagrama, String contenido, double yPosition) {
        Figura hacerMientras = new Hacer_Mientras(contenido); // Crear figura Hacer Mientras
        createCanvas(panel_Diagrama, hacerMientras, yPosition);
    }

    private static void createMientras(AnchorPane panel_Diagrama, String contenido, double yPosition) {
        Figura mientras = new Mientras(contenido); // Crear figura Mientras
        createCanvas(panel_Diagrama, mientras, yPosition);
    }

    private static void createPara(AnchorPane panel_Diagrama, String contenido, double yPosition) {
        Figura para = new Para(contenido); // Crear figura Para
        createCanvas(panel_Diagrama, para, yPosition);
    }

    private static void createCanvas(AnchorPane panel_Diagrama, Figura figura, double yPosition) {
        Canvas canvas = new Canvas(200, 50); // Crear canvas para la figura
        canvas.setLayoutX(20.0);
        canvas.setLayoutY(yPosition);
        canvas.setUserData(figura);

        // Dibujar la figura en el canvas
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.strokeText(figura.getContenido(), 10, 25);

        panel_Diagrama.getChildren().add(canvas); // Agregar el canvas al panel de diagrama
    }

}
