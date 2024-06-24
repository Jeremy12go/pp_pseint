package Clases;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

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
        List<Canvas> figuras = panel_Diagrama.getChildren().stream()
                .filter(node -> node instanceof Canvas)
                .map(node -> (Canvas) node)
                .collect(Collectors.toList());

        // Recorrer las figuras y generar el pseudocódigo
        for (Canvas canvas : figuras) {
            Figura figura = (Figura) canvas.getUserData();
            String currentIndent = indentStack.peek();

            if (figura instanceof Entrada) {
                pseudocodeContent.append(currentIndent).append("Entrada: ").append(figura.getContenido().trim().equals("Entrada") ? "- completar \n" : figura.getContenido().trim()).append("\n");
            } else if (figura instanceof Salida) {
                pseudocodeContent.append(currentIndent).append("Salida: ").append(figura.getContenido().trim().equals("Salida") ? "- completar \n" : figura.getContenido().trim()).append("\n");
            } else if (figura instanceof Documento) {
                pseudocodeContent.append(currentIndent).append("Documento: ").append(figura.getContenido().trim().equals("Documento") ? "- completar \n" : figura.getContenido().trim()).append("\n");
            } else if (figura instanceof Proceso) {
                pseudocodeContent.append(currentIndent).append("Proceso: ").append(figura.getContenido().trim().equals("Proceso") ? "- completar \n" : figura.getContenido().trim()).append("\n");
            } else if (figura instanceof Condicional) {
                pseudocodeContent.append(currentIndent).append("Condicional\n").append(currentIndent).append("\tSi ").append(figura.getContenido()).append(":\n");
                indentStack.push(currentIndent + "\t");
                pseudocodeContent.append(currentIndent).append("\tSino:\n");
                indentStack.pop();
            } else if (figura instanceof Hacer_Mientras) {
                pseudocodeContent.append(currentIndent).append("Hacer Mientras: ").append(figura.getContenido().trim().equals("Hacer Mientras") ? "- completar \n" : figura.getContenido().trim()).append(":\n");
                indentStack.push(currentIndent + "\t");
                indentStack.pop();
            } else if (figura instanceof Mientras) {
                pseudocodeContent.append(currentIndent).append("Mientras: ").append(figura.getContenido().trim().equals("Mientras") ? "- completar \n" : figura.getContenido().trim()).append(":\n");
                indentStack.push(currentIndent + "\t");
                indentStack.pop();
            } else if (figura instanceof Para) {
                pseudocodeContent.append(currentIndent).append("Para: ").append(figura.getContenido().trim().equals("Para") ? "- completar \n" : figura.getContenido().trim()).append(":\n");
                indentStack.push(currentIndent + "\t");
                indentStack.pop();
            }
        }

        pseudocodeContent.append("Fin");
        pseudocode.setText(pseudocodeContent.toString());
        return pseudocodeContent.toString();
    }
}