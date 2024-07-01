package Clases;
import javafx.scene.image.ImageView;
import org.example.app.AppController;


public class PseudocodeAdiagrama {
    private static ImageView imagenBasura = new ImageView();

    private AppController appController;

    public PseudocodeAdiagrama(AppController appController) {
        this.appController = appController;
    }

    public static void crearDiagramaDesdePseudocodigo(String pseudocodigo) {
        System.out.println("Pseudocódigo obtenido: \n"+ pseudocodigo + "\n\n\n" );

        String[] lineas = pseudocodigo.split("\n");
        //appController.dibujarFigura(1,1,imagenBasura,"Condicional");
        //dibujarFigura(double x, double y, ImageView sourceDiagram, String figura)
        for (String linea : lineas) {
            linea = linea.trim();
            if (linea.startsWith("Entrada:")) {
                String contenido = linea.substring("Entrada:".length()).trim();
                appController.dibujarFigura(1, 1, imagenBasura, "Entrada");
                System.out.println("Creando figura de Entrada con contenido: " + contenido);
            } else if (linea.startsWith("Salida:")) {
                String contenido = linea.substring("Salida:".length()).trim();
                appController.dibujarFigura(1, 1, imagenBasura, "Salida");
                System.out.println("Creando figura de Salida con contenido: " + contenido);
            } else if (linea.startsWith("Proceso:")) {
                String contenido = linea.substring("Proceso:".length()).trim();
                appController.dibujarFigura(1, 1, imagenBasura, "Proceso");
                System.out.println("Creando figura de Proceso con contenido: " + contenido);
            } else if (linea.startsWith("Condicional:")) {
                String contenido = linea.substring("Condicional:".length()).trim();
                appController.dibujarFigura(1, 1, imagenBasura, "Condicional");
                System.out.println("Creando figura de Condicional con contenido: " + contenido);
            } else if (linea.startsWith("Salida:")) {
                String contenido = linea.substring("Documento:".length()).trim();
                appController.dibujarFigura(1, 1, imagenBasura, "Documento");
                System.out.println("Creando figura de Documento con contenido: " + contenido);
            } else if (linea.startsWith("Hacer Mientras:")) {
                String contenido = linea.substring("Hacer Mientras:".length()).trim();
                appController.dibujarFigura(1, 1, imagenBasura, "Hacer Mientras");
                System.out.println("Creando figura de Hacer Mientras con contenido: " + contenido);
            } else if (linea.startsWith("Mientras:")) {
                String contenido = linea.substring("Mientras:".length()).trim();
                appController.dibujarFigura(1, 1, imagenBasura, "Mientras");
                System.out.println("Creando figura de Mientras con contenido: " + contenido);
            } else if (linea.startsWith("Para:")) {
                String contenido = linea.substring("Para:".length()).trim();
                appController.dibujarFigura(1, 1, imagenBasura, "Para");
                System.out.println("Creando figura de Para con contenido: " + contenido);
            }
        }
    }
}