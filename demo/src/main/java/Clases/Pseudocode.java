package Clases;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;


public class Pseudocode {

    public static void initializePseudocodeTab(Tab pseudocodeTab, Label pseudocode) {
        // Crear un AnchorPane para el contenido del Tab
        AnchorPane contentPane = new AnchorPane();

        pseudocode.setText("\n\n" +
                "Inicio Titulo\n" +
                "\tEntrada (Variable) a= X\n" +
                "\tEntrada (Variable) b= Y\n" +
                "\tSalida (Imprimir) a\n" +
                "\tSalida (Imprimir) b\n" +
                "\tDocumento texto\n" +
                "\tSalida (Imprimir) texto\n" +
                "\tProceso??\n" +
                "\tCondicional\n" +
                "\t\n" +
                "\tCondicional \n" +
                "\tSi a>b\n" +
                "\t\tSalida texto\n" +
                "\t\tSalida a\n" +
                "\tSino\n" +
                "\t\tSalida texto\n" +
                "\t\tSalida b\n" +
                "\t\n" +
                "Fin");

        pseudocode.setWrapText(true);

        contentPane.getChildren().add(pseudocode);

        pseudocodeTab.setContent(contentPane);
     }

}


