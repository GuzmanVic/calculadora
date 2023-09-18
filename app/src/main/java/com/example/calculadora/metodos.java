package com.example.calculadora;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class metodos {

    public void numerales(Button[] numeros, TextView display) {//Escribe los números presionados por el usuario en e display
        for (int i = 0; i < numeros.length; i++) {
            numeros[i].setTag(i); // Asigna el número como una etiqueta al botón
            numeros[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer numeroPresionado = (Integer) view.getTag(); // Obtiene el número de la etiqueta
                    display.append(numeroPresionado.toString()); // Agrega el número al TextView
                }
            });
        }
    }

    public String operadores(String equation, String op) {// Checks if it is allowed to write an exponent
        char last = '!'; // Initialize the 'last' character as '!'
        if (!equation.isEmpty()) { // Check if the 'equation' string is not empty
            last = equation.charAt(equation.length() - 1); // Get the last character of the 'equation' string
            if (Character.isDigit(last) || last == ')') { // Check if the last character is a digit
                return op;
            }
        }
        return ""; // Return the 'allow' variable indicating whether it's allowed to write an exponent
    }

    public String delete(String ecuacion, MainActivity main) {
        String newText = "";
        if (ecuacion.length() > 0) {
            if (ecuacion.toCharArray().length - 1 == ')') {//Checks if the las characther was a parentheses for control the use of them
                main.parentesisC -= 1;
            } else if (ecuacion.toCharArray().length - 1 == '(') {
                main.parentesisA -= 1;
            }
            newText = ecuacion.substring(0, ecuacion.length() - 1);//Delete the last characther on the display
        }
        return newText;//Returns the new Text
    }

    public boolean parenteshesControl(int parentesisA, int parentesisC, MainActivity main) {//Checks if it is allowed to write a close parentheses
        if (parentesisA > parentesisC) {
            main.parentesisC += 1;
            return true;
        } else {
            return false;
        }
    }

    public String puntos(String display) {
        if (!display.isEmpty()) {
            char last = display.charAt(display.length() - 1);

            // Check whether the last character is a digit or a decimal point.
            if (Character.isDigit(last) || last == '.') {
                // Find the last operator in the string
                int lastIndexOperator = Math.max(display.lastIndexOf('+'), Math.max(display.lastIndexOf('-'), Math.max(display.lastIndexOf('*'), Math.max(display.lastIndexOf('/'), display.lastIndexOf('%')))));
                // Find the last term in the string
                String lastTerm = display.substring(lastIndexOperator + 1);
                // Check whether the last term contains a decimal point
                if (!lastTerm.contains(".")) {
                    return ".";
                }
            }
        }
        return "";
    }

    public static double resolver(String expression) {
        Stack<Double> operandStack = new Stack<>();  // Stack for the operands
        Stack<Character> operatorStack = new Stack<>();  // Stack for the operators

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {  // If the character is a digit or a decimal point
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    operand.append(expression.charAt(i));  // Build an operand
                    i++;
                }
                operandStack.push(Double.parseDouble(operand.toString()));  // Convierte el operando a un número y lo agrega a la pila
                i--;  // Retrocede un paso para compensar el avance en el bucle while
            } else if (c == '(') {  // Si el carácter es un paréntesis de apertura
                operatorStack.push(c);  // Agrega el paréntesis de apertura a la pila de operadores
            } else if (c == ')') {  // Si el carácter es un paréntesis de cierre
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    applyOperator(operandStack, operatorStack);  // Aplica operadores hasta encontrar el paréntesis de apertura correspondiente
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();  // Retira el paréntesis de apertura de la pila de operadores
                }
            } else if (isOperator(c)) {  // Si el carácter es un operador
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(c)) {
                    applyOperator(operandStack, operatorStack);  // Aplica operadores de acuerdo a la jerarquía de operaciones
                }
                operatorStack.push(c);  // Agrega el operador a la pila de operadores
            }
        }

        while (!operatorStack.isEmpty()) {
            applyOperator(operandStack, operatorStack);  // Aplica operadores restantes
        }

        if (operandStack.isEmpty()) {
            throw new IllegalArgumentException("La expresión no es válida");  // Lanza una excepción si no quedan operandos
        }

        return operandStack.pop();  // Devuelve el resultado final

    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '√' || c == '%';
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/' || operator == '%') {
            return 2;
        } else if (operator == '^' || operator == '√') {
            return 3;
        }
        return 0;
    }

    private static void applyOperator(Stack<Double> operandStack, Stack<Character> operatorStack) {
        if (operandStack.size() < 2 || operatorStack.isEmpty()) {
            throw new IllegalArgumentException("La expresión no es válida");
        }

        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();
        char operator = operatorStack.pop();

        double result = 0.0;

        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("División por cero");
                }
                result = operand1 / operand2;
                break;
            case '%':
                if (operand2 == 0) {
                    throw new ArithmeticException("Módulo por cero");
                }
                result = operand1 % operand2;
                break;
            case '^':
                result = Math.pow(operand1, operand2);
                break;
            case '√':
                if (operand1 < 0) {
                    throw new ArithmeticException("Raíz cuadrada de un número negativo");
                }
                result = Math.sqrt(operand1);
                break;
        }

        operandStack.push(result);
    }


}
