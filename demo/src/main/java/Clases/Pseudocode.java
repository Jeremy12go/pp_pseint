package Clases;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.Canvas;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Pseudocode {

    public static TextArea textAreaPseudocode = new TextArea();
    static PseudocodeInterpreter interpreter = new PseudocodeInterpreter();

    public static void initializePseudocodeTab(Tab pseudocodeTab, Label pseudocode,AnchorPane panel_diagrama) {

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

        //Crear boton ejecutar
        Button botonEjecutar = new Button();
        botonEjecutar.setText("Ejecutar");
        botonEjecutar.setOnMouseEntered(e -> botonEjecutar.setStyle("-fx-border-color: #000000;" +
                "-fx-background-radius: 25 25 25 25;" +
                "-fx-border-radius: 0 0 0 0;" +
                "-fx-border-width: 2.5;"));
        botonEjecutar.setOnMouseExited(e -> botonEjecutar.setStyle(
                "    -fx-border-color: transparent;" +
                        "    -fx-text-origin: bold;" +
                        "    -fx-background-radius: 25 25 25 25;" +
                        "    -fx-border-radius: 25 25 25 25;" +
                        "    -fx-border-width: 2.5;"));
        botonEjecutar.setLayoutX(200);
        botonEjecutar.setLayoutY(5);

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

        // Crear un TextArea para la edición del pseudocódigo
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

        // Acción del botón "Ejecutar"
        botonEjecutar.setOnAction(actionEvent -> {
            ejecutarPseudocodigo();
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
            PseudocodeAdiagrama.generateFlowDiagram(pseudocode,panel_diagrama);
        });

        contentPane.getChildren().addAll(botonEditar, botonGuardar, botonaDiagrama, botonEjecutar, textAreaPseudocode);
    }

    public static String generatePseudocode(AnchorPane panel_Diagrama, Label pseudocode) {
        StringBuilder pseudocodeContent = new StringBuilder();
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

        pseudocode.setText(pseudocodeContent.toString());
        return pseudocodeContent.toString();
    }

    public static String generarPseudo(AnchorPane panel_Diagrama, Label pseudocode) {
        StringBuilder pseudocodeContent = new StringBuilder();
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

        pseudocode.setText(pseudocodeContent.toString());
        return pseudocodeContent.toString();
    }
    @FXML
    private static void ejecutarPseudocodigo() {
        // Validar pseudocódigo
        String validationErrors = Validar.validarPseudocodigo(textAreaPseudocode.getText());

        // Mostrar errores si existen
        if (!validationErrors.equals("No se encontraron errores.")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errores de Validación");
            alert.setHeaderText(null);
            alert.setContentText(validationErrors);
            alert.showAndWait();
        } else {
            // Ejecutar el pseudocódigo
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // Intenta ejecutar el pseudocódigo
            try {
                interpreter.ejecutarPseudocodigo(textAreaPseudocode.getText());

                // Si no hay excepciones, muestra un mensaje de ejecución exitosa
                alert.setTitle("Ejecución Exitosa");
                alert.setHeaderText(null);
                alert.setContentText("El pseudocódigo se ejecutó correctamente.");
                alert.showAndWait();

                // Opcional: imprimir las variables para depuración
                interpreter.imprimirVariables();
            } catch (Exception e) {
                // Captura y muestra cualquier excepción que pueda ocurrir durante la ejecución
                alert.setTitle("Error durante la ejecución");
                alert.setHeaderText(null);
                alert.setContentText("Ocurrió un error durante la ejecución del pseudocódigo: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
