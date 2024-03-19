package Clases;

public class Posicion {
    public Punto punto_topLeft;
    public Punto punto_topRight;
    public Punto punto_botLeft;
    public Punto punto_botRight;

    public Posicion(Punto punto_topLeft, Punto punto_topRight, Punto punto_botLeft, Punto punto_botRight) {
        this.punto_topLeft = punto_topLeft;
        this.punto_topRight = punto_topRight;
        this.punto_botLeft = punto_botLeft;
        this.punto_botRight = punto_botRight;
    }
}
