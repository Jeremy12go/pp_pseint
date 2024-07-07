package Clases;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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

    private int evaluarExpresion(String expresion) {
        try {
            // Reemplazar variables en la expresión
            for (Map.Entry<String, Integer> entry : variables.entrySet()) {
                expresion = expresion.replace(entry.getKey(), entry.getValue().toString());
            }

            // Convertir la expresión a notación postfija (RPN)
            String postfija = infija_a_postfija(expresion);

            // Evaluar la expresión en notación postfija
            return evalPostFija(postfija);
        } catch (Exception e) {
            System.err.println("Error al evaluar expresión: " + e.getMessage());
            throw e;
        }
    }

    // Método para convertir una expresión infija a notación postfija
    private String infija_a_postfija(String expresion) {
        StringBuilder output = new StringBuilder();
        Stack<Character> operators = new Stack<>();
        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);
            if (Character.isDigit(c)) {
                output.append(c);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    output.append(' ').append(operators.pop());
                }
                operators.pop();
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    output.append(' ').append(operators.pop());
                }
                output.append(' ');
                operators.push(c);
            }
        }
        while (!operators.isEmpty()) {
            output.append(' ').append(operators.pop());
        }
        return output.toString();
    }

    // Método para evaluar una expresión en notación postfija
    private int evalPostFija(String postfija) {
        Stack<Integer> stack = new Stack<>();
        String[] tokens = postfija.split("\\s+");
        for (String token : tokens) {
            if (isOperator(token.charAt(0)) && token.length() == 1) {
                int b = stack.pop();
                int a = stack.pop();
                switch (token.charAt(0)) {
                    case '+':
                        stack.push(a + b);
                        break;
                    case '-':
                        stack.push(a - b);
                        break;
                    case '*':
                        stack.push(a * b);
                        break;
                    case '/':
                        stack.push(a / b);
                        break;
                }
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    // Método para verificar si un carácter es un operador
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Método para obtener la precedencia de un operador
    private int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    // Método para imprimir las variables y sus valores para depurar valores
    public void imprimirVariables() {
        System.out.println("\nEstado de las variables:");
        for (Map.Entry<String, Integer> entry : variables.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        System.out.println("\n");
    }
}
