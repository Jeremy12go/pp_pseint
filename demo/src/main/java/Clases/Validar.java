package Clases;


import java.util.HashSet;
import java.util.Set;

public class Validar {

    public static String validarPseudocodigo(String pseudocode) {
        String[] lines = pseudocode.split("\n");
        Set<String> definedVariables = new HashSet<>();
        StringBuilder errors = new StringBuilder();
        boolean insideConditional = false;
        boolean insideLoop = false;

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("Entrada:")) {
                validarAsignacion(line, definedVariables, errors, "Entrada");
            } else if (line.startsWith("Proceso:")) {
                validarAsignacion(line, definedVariables, errors, "Proceso");
            } else if (line.startsWith("Documento:")) {
                validarAsignacion(line, definedVariables, errors, "Documento");
            } else if (line.startsWith("Salida:")) {
                validarVariable(line, definedVariables, errors, "Salida");
            } else if (line.startsWith("Condicional")) {
                insideConditional = true;
            } else if (line.startsWith("Si") || line.startsWith("Sino")) {
                validarCondicion(line, definedVariables, errors);
            } else if (line.startsWith("Hacer Mientras:") || line.startsWith("Mientras:") || line.startsWith("Para:")) {
                insideLoop = true;
                validarCondicion(line, definedVariables, errors);
            } else if (line.equals("") && (insideConditional || insideLoop)) {
                insideConditional = false;
                insideLoop = false;
            } else if (insideConditional || insideLoop) {
                // Validar variables dentro de condiciones y loops
                validarCondicion(line, definedVariables, errors);
            }
        }

        if (errors.length() == 0) {
            errors.append("No se encontraron errores.");
        }

        return errors.toString();
    }

    private static void validarAsignacion(String line, Set<String> definedVariables, StringBuilder errors, String tipo) {
        String[] parts = line.split(":");
        if (parts.length > 1) {
            String expression = parts[1].trim();
            String[] assignment = expression.split("=");
            if (assignment.length > 1) {
                String variable = assignment[0].trim();
                String value = assignment[1].trim();
                definedVariables.add(variable);
                validarUsoDeVariables(value, definedVariables, errors);
            } else {
                errors.append("Error: La ").append(tipo).append(" '").append(expression).append("' no tiene una asignación válida.\n");
            }
        }
    }

    private static void validarVariable(String line, Set<String> definedVariables, StringBuilder errors, String tipo) {
        String[] parts = line.split(":");
        if (parts.length > 1) {
            String variable = parts[1].trim();
            if (!definedVariables.contains(variable)) {
                errors.append("Error: La variable '").append(variable).append("' no está definida para ").append(tipo).append(".\n");
            }
        }
    }

    private static void validarCondicion(String line, Set<String> definedVariables, StringBuilder errors) {
        String[] tokens = line.split("\\W+");
        for (String token : tokens) {
            if (!token.equals("Si") && !token.equals("Sino") && !token.matches("\\d+") && !definedVariables.contains(token)) {
                errors.append("Error: La variable '").append(token).append("' no está definida en la condición o bucle.\n");
            }
        }
    }

    private static void validarUsoDeVariables(String expression, Set<String> definedVariables, StringBuilder errors) {
        String[] tokens = expression.split("\\W+");
        for (String token : tokens) {
            if (!token.matches("\\d+") && !definedVariables.contains(token)) {
                errors.append("Error: La variable '").append(token).append("' no está definida.\n");
            }
        }
    }
}