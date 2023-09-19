package com.example.calculadora;

import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class metodos {

    public void numerales(Button[] numeros, TextView display) {//Captures on the display the numbers pressed by the user
        for (int i = 0; i < numeros.length; i++) {
            numeros[i].setTag(i); //Assigns the number as a tag to the button
            numeros[i].setOnClickListener(view -> {
                Integer numeroPresionado = (Integer) view.getTag(); // Obtains the number from the tag
                display.append(numeroPresionado.toString()); // Add the number to the TextView
            });
        }
    }

    public String operadores(String equation, String op) {// Checks if it is allowed to write an exponent
        char last; // Initialize the 'last' character as '!'
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
        Stack<Double> operandStack = new Stack<>();// Stack to store the operands (numbers)
        Stack<Character> operatorStack = new Stack<>();//Stack to store the operators

        for (int i = 0; i < expression.length(); i++) {// Go through the expression character by character
            char c = expression.charAt(i); // Stores each of the characters throughout the bucle

            if (Character.isDigit(c) || c == '.') {   // If the characters is a digit or a decimal point
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {// While this and the next character is a digit or a decimal point, build the operand
                    operand.append(expression.charAt(i));  // Build the operand
                    i++;
                }
                operandStack.push(Double.parseDouble(operand.toString()));//Convert the operand into a number and add it to the operands stack
                i--; // Move back one step to compensate for the advancement in the while loop.
            } else if (c == '(') {  //If the character is an opening parenthesis
                operatorStack.push(c);  // Add the opening parenthesis to the operator stack
            } else if (c == ')') {  // If the character is a closing parenthesis
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    applyOperator(operandStack, operatorStack);//Apply operators until the corresponding opening parenthesis is found
                }
                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                    operatorStack.pop();  //Removes the opening parenthesis from the operators stack
                }
            } else if (isOperator(c)) {  // If the character is an operator
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(c)) {
                    applyOperator(operandStack, operatorStack);//  Apply operators according to the operation hierarchy
                }
                operatorStack.push(c);  // Add the operator to the operators stack
            }
        }
        while (!operatorStack.isEmpty()) {// Apply remaining operators
            applyOperator(operandStack, operatorStack);
        }

        if (operandStack.isEmpty()) {
            throw new IllegalArgumentException("La expresión no es válida"); //Throws an exception if there are no remaining operands
        }

        return operandStack.pop();// Return the final result
    }


    private static boolean isOperator(char c) {//Check if the character is a valid operator
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '√' || c == '%';
    }

    private static int precedence(char operator) {//Check the precedence of the operator to determine the hirerchy
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
        // Comprueba que haya al menos dos operandos en la pila de operandos y que no esté vacía Check that there's at least two operands in the operands stack and tha there's not empty
        if (operandStack.size() < 2 || operatorStack.isEmpty()) {//Check that there are at least two operands in the operands stack and that it is not empty
            throw new IllegalArgumentException("La expresión no es válida");
        }

        // Obtains the two top operands from the operand stack
        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();
        char operator = operatorStack.pop();// Obtain the top operator from the operator stack
        double result = 0.0; // Initialize the result variable with 0.0

//Use a switch statement to perform the corresponding operation
        switch (operator) {
            case '+':
                result = operand1 + operand2; // Add the operands
                break;
            case '-':
                result = operand1 - operand2;// Substract the operands
                break;
            case '*':
                result = operand1 * operand2; // Multiply the operands
                break;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("División por cero");
                }
                result = operand1 / operand2; // Divide the operands
                break;
            case '%':
                if (operand2 == 0) {
                    throw new ArithmeticException("Módulo por cero");
                }
                result = operand1 % operand2; //Calculate the modulus of the operands
                break;
            case '^':
                result = Math.pow(operand1, operand2); // Calcula la potencia de operand1 elevado a operand2 Calculate the potence of operand1 elevated to operand2
                break;
            case '√':
                if (operand1 < 0) {
                    throw new ArithmeticException("Raíz cuadrada de un número negativo");
                }
                result = Math.pow(operand1, operand2); // Calculate the power of operand1 raised to operand2
                break;
        }
        operandStack.push(result);// Add the result of the operation to the operands stack
    }
}