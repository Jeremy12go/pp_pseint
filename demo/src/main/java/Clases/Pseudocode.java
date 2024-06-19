package Clases;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.util.List;
import java.util.Stack;

public class Pseudocode {

    public static void initializePseudocodeTab(Tab pseudocodeTab, Label pseudocode) {
        // Crear un AnchorPane para el contenido del Tab
        AnchorPane contentPane = new AnchorPane();

        pseudocode.setWrapText(true);

        contentPane.getChildren().add(pseudocode);
        pseudocodeTab.setContent(contentPane);

        // Crear un boton
        Button boton_nuevo = new Button();
        boton_nuevo.setText("       A Pseudocódigo");
        boton_nuevo.setOnMouseEntered(e -> boton_nuevo.setStyle("-fx-border-color: #000000;"+
        "-fx-background-radius: 25 25 25 25;"+
        "-fx-border-radius: 0 0 0 0;"+
        "-fx-border-width: 2.5;"));
        boton_nuevo.setOnMouseExited(e -> boton_nuevo.setStyle("-fx-background-color: #242c3c;" +
                "    -fx-border-color: transparent;"+
                "    -fx-text-origin: bold;"+
                "    -fx-background-radius: 25 25 25 25;"+
                "    -fx-border-radius: 25 25 25 25;"+
                "    -fx-border-width: 2.5;"));


        boton_nuevo.setOnAction(actionEvent -> {
            //lo que quieres que haga :v
        });
        contentPane.getChildren().add(boton_nuevo);

    }

    public static String generatePseudocode(AnchorPane panel_Diagrama, Label pseudocode) {
        StringBuilder pseudocodeContent = new StringBuilder("\n\nInicio Titulo\n");
        Stack<String> indentStack = new Stack<>();
        indentStack.push(""); // Initial indentation level

        // Obtener las figuras del AnchorPane
        List<Canvas> figuras = panel_Diagrama.getChildren().filtered(node -> node instanceof Canvas).stream()
                .map(node -> (Canvas) node).toList();

        // Recorrer las figuras y generar el pseudocódigo
        for (Canvas canvas : figuras) {
            Figura figura = (Figura) canvas.getUserData();
            String currentIndent = indentStack.peek();

            if (figura instanceof Entrada) {
                if (figura.getContenido().equals(" Entrada ")) {
                    pseudocodeContent.append(currentIndent).append("Entrada: ").append("- completar \n");
                } else {
                    pseudocodeContent.append(currentIndent).append("Entrada: ").append(figura.getContenido()).append("\n");
                }
            } else if (figura instanceof Salida) {
                if (figura.getContenido().equals(" Salida ")) {
                    pseudocodeContent.append(currentIndent).append("Salida: ").append("- completar \n");
                } else {
                    pseudocodeContent.append(currentIndent).append("Salida: ").append(figura.getContenido()).append("\n");
                }
            } else if (figura instanceof Documento) {
                if (figura.getContenido().equals(" Documento ")) {
                    pseudocodeContent.append(currentIndent).append("Documento: ").append("- completar \n");
                } else {
                    pseudocodeContent.append(currentIndent).append("Documento: ").append(figura.getContenido()).append("\n");
                }
            } else if (figura instanceof Proceso) {
                if (figura.getContenido().equals(" Proceso ")) {
                    pseudocodeContent.append(currentIndent).append("Proceso: ").append("- completar \n");
                } else {
                    pseudocodeContent.append(currentIndent).append("Proceso: ").append(figura.getContenido()).append("\n");
                }
            } else if (figura instanceof Condicional) {
                pseudocodeContent.append(currentIndent).append("Condicional\n").append(currentIndent).append("\tSi ").append(figura.getContenido()).append(":\n");
                indentStack.push(currentIndent + "\t");
                // pseudocodeContent.append(currentIndent).append("\t\tSalida ").append(((Condicional) figura).getSalidaSi()).append("\n");
                pseudocodeContent.append(currentIndent).append("\tSino:\n");
                // pseudocodeContent.append(currentIndent).append("\t\tSalida ").append(((Condicional) figura).getSalidaNo()).append("\n");
                indentStack.pop();
            } else if (figura instanceof Hacer_Mientras) {
                if (figura.getContenido().equals(" Hacer Mientras ")) {
                    pseudocodeContent.append(currentIndent).append("Hacer Mientras: ").append("- completar \n");
                } else {
                    pseudocodeContent.append(currentIndent).append("Hacer Mientras: ").append(figura.getContenido()).append(":\n");
                }
                indentStack.push(currentIndent + "\t");
                // pseudocodeContent.append(currentIndent).append("\tProceso\n");
                indentStack.pop();
            } else if (figura instanceof Mientras) {
                if (figura.getContenido().equals(" Mientras ")) {
                    pseudocodeContent.append(currentIndent).append("Mientras: ").append("- completar \n");
                } else {
                    pseudocodeContent.append(currentIndent).append("Mientras: ").append(figura.getContenido()).append(":\n");
                }
                indentStack.push(currentIndent + "\t"); // Increase indentation level
                // pseudocodeContent.append(currentIndent).append("\tProceso\n");
                indentStack.pop();
            } else if (figura instanceof Para) {
                if (figura.getContenido().equals(" Para ")) {
                    pseudocodeContent.append(currentIndent).append("Para: ").append("- completar \n");
                } else {
                    pseudocodeContent.append(currentIndent).append("Para: ").append(figura.getContenido()).append(":\n");
                }
                indentStack.push(currentIndent + "\t");
                // pseudocodeContent.append(currentIndent).append("\tProceso\n");
                indentStack.pop();
            }
        }

        pseudocodeContent.append("Fin");
        pseudocode.setText(pseudocodeContent.toString());
        return pseudocodeContent.toString();
    }
}
