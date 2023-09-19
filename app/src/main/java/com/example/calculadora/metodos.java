package com.example.calculadora;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class metodos {

    public void numerales(Button[] numeros, TextView display) {//Captures on the display the numbers pressed by the user
        for (int i = 0; i < numeros.length; i++) {
            numeros[i].setTag(i); //Assigns the number as a tag to the button
            numeros[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer numeroPresionado = (Integer) view.getTag(); // Obtains the number from the tag
                    display.append(numeroPresionado.toString()); // Add the number to the TextView
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
        } else if (op.equals("√")) {
            return "2" + op;
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
        // Stack para almacenar los operandos (números)
        Stack<Double> operandStack = new Stack<>();
        // Stack para almacenar los operadores
        Stack<Character> operatorStack = new Stack<>();

        // Recorre la expresión matemática caracter por caracter
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i); // Almacena cada uno de los caracteres a lo largo del ciclo

            if (Character.isDigit(c) || c == '.') {  // Si el carácter es un dígito o un punto decimal
                StringBuilder operand = new StringBuilder();
                // Mientras el siguiente carácter sea un dígito o un punto decimal, construye el operando
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    operand.append(expression.charAt(i));  // Construye el operando
                    i++;
                }
                // Convierte el operando a un número y lo agrega a la pila de operandos
                operandStack.push(Double.parseDouble(operand.toString()));
                i--;  // Retrocede un paso para compensar el avance en el bucle while
            } else if (c == '(') {  // Si el carácter es un paréntesis de apertura
                operatorStack.push(c);  // Agrega el paréntesis de apertura a la pila de operadores
            } else if (c == ')') {  // Si el carácter es un paréntesis de cierre
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    // Aplica operadores hasta encontrar el paréntesis de apertura correspondiente
                    applyOperator(operandStack, operatorStack);
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();  // Retira el paréntesis de apertura de la pila de operadores
                }
            } else if (isOperator(c)) {  // Si el carácter es un operador
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(c)) {
                    // Aplica operadores de acuerdo a la jerarquía de operaciones
                    applyOperator(operandStack, operatorStack);
                }
                operatorStack.push(c);  // Agrega el operador a la pila de operadores
            }
        }

        // Aplica operadores restantes
        while (!operatorStack.isEmpty()) {
            applyOperator(operandStack, operatorStack);
        }

        if (operandStack.isEmpty()) {
            throw new IllegalArgumentException("La expresión no es válida");  // Lanza una excepción si no quedan operandos
        }

        // Devuelve el resultado final
        return operandStack.pop();
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
        // Comprueba que haya al menos dos operandos en la pila de operandos y que no esté vacía
        if (operandStack.size() < 2 || operatorStack.isEmpty()) {
            throw new IllegalArgumentException("La expresión no es válida");
        } else if (operandStack.size() == 1 && operatorStack.contains("√")) {
            // En caso de tener un solo operando y el operador es '√' (raíz cuadrada)
            double operand1 = operandStack.pop(); // Obtiene el único operando

            // Realiza la operación de raíz cuadrada
            if (operand1 < 0) {
                throw new ArithmeticException("Raíz cuadrada de un número negativo");
            }
            double result = Math.sqrt(operand1);

            // Agrega el resultado a la pila de operandos
            operandStack.push(result);

            // Elimina el operador de raíz cuadrada de la pila de operadores
            operatorStack.remove("√");
        }

        // Obtiene los dos operandos superiores de la pila de operandos
        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();

        // Obtiene el operador superior de la pila de operadores
        char operator = operatorStack.pop();

        double result = 0.0; // Inicializa la variable de resultado con 0.0

        // Utiliza un interruptor (switch) para realizar la operación correspondiente
        switch (operator) {
            case '+':
                result = operand1 + operand2; // Suma los operandos
                break;
            case '-':
                result = operand1 - operand2; // Resta los operandos
                break;
            case '*':
                result = operand1 * operand2; // Multiplica los operandos
                break;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("División por cero");
                }
                result = operand1 / operand2; // Divide los operandos
                break;
            case '%':
                if (operand2 == 0) {
                    throw new ArithmeticException("Módulo por cero");
                }
                result = operand1 % operand2; // Calcula el módulo de los operandos
                break;
            case '^':
                result = Math.pow(operand1, operand2); // Calcula la potencia de operand1 elevado a operand2
                break;
            case '√':
                if (operand1 < 0) {
                    throw new ArithmeticException("Raíz cuadrada de un número negativo");
                }
                result = Math.sqrt(operand2); // Calcula la raíz cuadrada de operand1
                break;
        }

        // Agrega el resultado de la operación a la pila de operandos
        operandStack.push(result);
    }


}
