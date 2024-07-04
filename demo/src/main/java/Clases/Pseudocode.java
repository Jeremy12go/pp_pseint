package Clases;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.Canvas;
import org.example.app.AppController;

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

        //Crear boton editar
        Button botonEditar = new Button();
        botonEditar.setText("Editar Pseudocódigo");
        botonEditar.setOnMouseEntered(e -> botonEditar.setStyle("-fx-border-color: #000000;" +
                "-fx-background-radius: 25 25 25 25;" +
                "-fx-border-radius: 0 0 0 0;" +
                "-fx-border-width: 2.5;"));
        botonEditar.setOnMouseExited(e -> botonEditar.setStyle(
                "    -fx-border-color: transparent;" +
                        "    -fx-text-origin: bold;" +
                        "    -fx-background-radius: 25 25 25 25;" +
                        "    -fx-border-radius: 25 25 25 25;" +
                        "    -fx-border-width: 2.5;"));
        botonEditar.setLayoutX(300);
        botonEditar.setLayoutY(5);

        // Crear el botón guardar
        Button botonGuardar = new Button();
        botonGuardar.setText("Guardar");
        botonGuardar.setOnMouseEntered(e -> botonGuardar.setStyle("-fx-border-color: #000000;" +
                "-fx-background-radius: 25 25 25 25;" +
                "-fx-border-radius: 0 0 0 0;" +
                "-fx-border-width: 2.5;"));
        botonGuardar.setOnMouseExited(e -> botonGuardar.setStyle(
                "    -fx-border-color: transparent;" +
                        "    -fx-text-origin: bold;" +
                        "    -fx-background-radius: 25 25 25 25;" +
                        "    -fx-border-radius: 25 25 25 25;" +
                        "    -fx-border-width: 2.5;"));
        botonGuardar.setLayoutX(400);
        botonGuardar.setLayoutY(5);
        botonGuardar.setVisible(false); // Inicialmente oculto

        //crear boton a diagrama
        Button botonaDiagrama = new Button();
        botonaDiagrama.setText(" A Diagrama");
        botonaDiagrama.setOnMouseEntered(e -> botonaDiagrama.setStyle("-fx-border-color: #000000;" +
                "-fx-background-radius: 25 25 25 25;" +
                "-fx-border-radius: 0 0 0 0;" +
                "-fx-border-width: 2.5;"));
        botonaDiagrama.setOnMouseExited(e -> botonaDiagrama.setStyle(
                "    -fx-border-color: transparent;" +
                        "    -fx-text-origin: bold;" +
                        "    -fx-background-radius: 25 25 25 25;" +
                        "    -fx-border-radius: 25 25 25 25;" +
                        "    -fx-border-width: 2.5;"));
        // Establecer las coordenadas del botón
        botonaDiagrama.setLayoutX(500); // Coordenada X
        botonaDiagrama.setLayoutY(5);  // Coordenada Y

        // Crear un TextArea para la edicion del pseudocódigo
        TextArea textAreaPseudocode = new TextArea();
        textAreaPseudocode.setLayoutX(20);
        textAreaPseudocode.setLayoutY(50);
        textAreaPseudocode.setPrefWidth(860);
        textAreaPseudocode.setPrefHeight(500);
        textAreaPseudocode.setVisible(false); // Inicialmente oculto

        // Acción del botón "Editar Pseudocódigo"
        botonEditar.setOnAction(actionEvent -> {
            textAreaPseudocode.setText(pseudocode.getText());
            pseudocode.setVisible(false);
            textAreaPseudocode.setVisible(true);
            botonEditar.setVisible(false);
            botonGuardar.setVisible(true);
        });

        // Acción del botón "Guardar"
        botonGuardar.setOnAction(actionEvent -> {
            pseudocode.setText(textAreaPseudocode.getText());
            textAreaPseudocode.setVisible(false);
            pseudocode.setVisible(true);
            botonEditar.setVisible(true);
            botonGuardar.setVisible(false);
        });

        //accion del boton A Diagrama
        botonaDiagrama.setOnAction(actionEvent -> {
            //lo que quieres que haga :v
        });

        contentPane.getChildren().addAll(botonEditar, botonGuardar, botonaDiagrama,textAreaPseudocode);
    }

    public static String generatePseudocode(AnchorPane panel_Diagrama, Label pseudocode) {
        StringBuilder pseudocodeContent = new StringBuilder("\n\nInicio\n");
        Stack<String> indentStack = new Stack<>();
        indentStack.push("   "); // Initial indentation level

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
                // pseudocodeContent.append(currentIndent).append("\t\tSalida ").append(((Condicional) figura).getSalidaSi()).append("\n");
                pseudocodeContent.append(currentIndent).append("\tSino:\n");
                // pseudocodeContent.append(currentIndent).append("\t\tSalida ").append(((Condicional) figura).getSalidaNo()).append("\n");
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

    public static String generarPseudo(AnchorPane panel_Diagrama, Label pseudocode) {
        StringBuilder pseudocodeContent = new StringBuilder("\n\nInicio\n");
        Stack<String> indentStack = new Stack<>();
        indentStack.push("   "); // Initial indentation level

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
                pseudocodeContent.append(currentIndent).append(figura.getContenido().trim()).append("\n");
            } else if (figura instanceof Salida) {
                pseudocodeContent.append(currentIndent).append(figura.getContenido().trim()).append("\n");
            } else if (figura instanceof Proceso) {
                pseudocodeContent.append(currentIndent).append(figura.getContenido().trim()).append("\n");
            } else if (figura instanceof Condicional) {
                pseudocodeContent.append(currentIndent).append(figura.getContenido().trim()).append("\n");
                pseudocodeContent.append(currentIndent).append("\tSi ").append(figura.getContenido()).append(":\n");
                indentStack.push(currentIndent + "\t");
                pseudocodeContent.append(currentIndent).append("\tSino:\n");
                indentStack.pop();
            } else if (figura instanceof Hacer_Mientras) {
                pseudocodeContent.append(currentIndent).append(figura.getContenido().trim()).append(":\n");
                indentStack.push(currentIndent + "\t");
                indentStack.pop();
            } else if (figura instanceof Mientras) {
                pseudocodeContent.append(currentIndent).append(figura.getContenido().trim()).append(":\n");
                indentStack.push(currentIndent + "\t");
                indentStack.pop();
            } else if (figura instanceof Para) {
                pseudocodeContent.append(currentIndent).append(figura.getContenido().trim()).append(":\n");
                indentStack.push(currentIndent + "\t");
                indentStack.pop();
            }
        }

        pseudocodeContent.append("Fin");
        pseudocode.setText(pseudocodeContent.toString());
        return pseudocodeContent.toString();
    }
}
