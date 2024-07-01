package Clases;

import java.util.HashMap;
import java.util.Map;

public class PseudocodeInterpreter {

    private Map<String, Integer> variables; // Mapa para almacenar las variables y sus valores

    public PseudocodeInterpreter() {
        this.variables = new HashMap<>();
    }

    // Método para ejecutar el pseudocódigo
    public void ejecutarPseudocodigo(String pseudocode) {
        String[] lineas = pseudocode.split("\n");

        for (String linea : lineas) {
            // Eliminar espacios en blanco al inicio y final de la línea
            linea = linea.trim();

            if (!linea.isEmpty() && !linea.equals("Inicio") && !linea.equals("Fin")) {
                procesarLinea(linea);
            }
        }
    }

    // Método para procesar una línea de pseudocódigo
    private void procesarLinea(String linea) {
        // Eliminar espacios en blanco al inicio y final de la línea
        linea = linea.trim();

        if (linea.contains("=")) {
            String[] partes = linea.split("\\s*=\\s*", 2);

            if (partes.length == 2) {
                String variable = partes[0].trim();
                String expresion = partes[1].trim();

                try {
                    int valor = evaluarExpresion(expresion);
                    variables.put(variable, valor);
                } catch (Exception e) {
                    System.err.println("Error: asignación de variable no válida: " + linea);
                }
            } else {
                System.err.println("Error: línea de pseudocódigo no válida: " + linea);
            }
        } else {
            if (variables.containsKey(linea)) {
                System.out.println(linea + " = " + variables.get(linea));
            } else {
                System.err.println("Error: Variable no definida: " + linea);
            }
        }
    }

    // Método para evaluar una expresión aritmética simple
    private int evaluarExpresion(String expresion) {
        try {
            // Reemplazar variables en la expresión
            for (Map.Entry<String, Integer> entry : variables.entrySet()) {
                expresion = expresion.replace(entry.getKey(), entry.getValue().toString());
            }

            // Evaluar la expresión
            return eval(expresion);
        } catch (Exception e) {
            System.err.println("Error al evaluar expresión: " + e.getMessage());
            throw e;
        }
    }

    // Método para evaluar una expresión aritmética con soporte básico para + y -
    private int eval(String expresion) {
        String[] partes = expresion.split("\\s*\\+\\s*|\\s*-\\s*");
        int resultado = 0;
        boolean suma = true;

        for (String parte : partes) {
            if (parte.trim().isEmpty()) continue;

            if (parte.contains("+")) {
                suma = true;
                parte = parte.replace("+", "").trim();
            } else if (parte.contains("-")) {
                suma = false;
                parte = parte.replace("-", "").trim();
            }

            int valor = Integer.parseInt(parte.trim());
            if (suma) {
                resultado += valor;
            } else {
                resultado -= valor;
            }
        }

        return resultado;
    }

    // Método para imprimir las variables y sus valores (para depuración)
    public void imprimirVariables() {
        System.out.println("\nEstado de las variables:");
        for (Map.Entry<String, Integer> entry : variables.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}