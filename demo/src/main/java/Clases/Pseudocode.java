package Clases;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.Canvas;

import java.util.List;

public class Pseudocode {

    public static void initializePseudocodeTab(Tab pseudocodeTab, Label pseudocode) {
        // Crear un AnchorPane para el contenido del Tab
        AnchorPane contentPane = new AnchorPane();

        pseudocode.setWrapText(true);

        contentPane.getChildren().add(pseudocode);

        pseudocodeTab.setContent(contentPane);
    }

    public static String generatePseudocode(AnchorPane panel_Diagrama, Label pseudocode) {
        StringBuilder pseudocodeContent = new StringBuilder("\n\nInicio Titulo\n");

        // Obtener las figuras del AnchorPane
        List<Canvas> figuras = panel_Diagrama.getChildren().filtered(node -> node instanceof Canvas).stream()
                .map(node -> (Canvas) node).toList();

        // Recorrer las figuras y generar el pseudocódigo
        for (Canvas canvas : figuras) {
            Figura figura = (Figura) canvas.getUserData();
            if (figura instanceof Entrada) {
                pseudocodeContent.append("\tEntrada(").append(figura.getContenido()).append(")").append("\n");
            } else if (figura instanceof Salida) {
                pseudocodeContent.append("\tSalida(").append(figura.getContenido()).append(")").append("\n");
            } else if (figura instanceof Documento) {
                pseudocodeContent.append("\tDocumento ").append(figura.getContenido()).append("\n");
            } else if (figura instanceof Proceso) {
                pseudocodeContent.append("\tProceso ").append(figura.getContenido()).append("\n");
            } else if (figura instanceof Condicional) {
                pseudocodeContent.append("\tCondicional \n\tSi ").append(figura.getContenido()).append("\n");
                // pseudocodeContent.append("\t\tSalida ").append(((Condicional) figura).getSalidaSi()).append("\n");
                pseudocodeContent.append("\tSino\n");
                // pseudocodeContent.append("\t\tSalida ").append(((Condicional) figura).getSalidaNo()).append("\n");
            } else if (figura instanceof Hacer_Mientras) {
                pseudocodeContent.append("\tHacer Mientras ").append(figura.getContenido()).append("\n");
            } else if (figura instanceof Mientras) {
                pseudocodeContent.append("\tMientras ").append(figura.getContenido()).append("\n");
            } else if (figura instanceof Para) {
                pseudocodeContent.append("\tPara ").append(figura.getContenido()).append("\n");
            }
        }

        pseudocodeContent.append("Fin");
        pseudocode.setText(pseudocodeContent.toString());
        return pseudocode.toString();
    }
}
