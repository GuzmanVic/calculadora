package com.example.calculadora;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    public double resolver(String ecuacion) {
        try {

            // Dividir la ecuación en términos utilizando los operadores +, -, *, /, %
            String[] terminos = ecuacion.split("[+\\-*/%]");

            // Obtener los operadores de la ecuación
            String[] operadores = ecuacion.split("[0-9.]+");

            // Inicializar el resultado con el primer término
            double resultado = Double.parseDouble(terminos[0]);

            // Realizar los cálculos
            for (int i = 1; i < terminos.length; i++) {
                double termino = Double.parseDouble(terminos[i]);
                String operador = operadores[i];

                switch (operador) {
                    case "+":
                        resultado += termino;
                        break;
                    case "-":
                        resultado -= termino;
                        break;
                    case "*":
                        resultado *= termino;
                        break;
                    case "/":
                        resultado /= termino;
                        break;
                    case "%":
                        resultado %= termino;
                        break;
                }
            }

            return resultado;
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir al evaluar la expresión
            e.printStackTrace();
            return Double.NaN; // Devolver un valor especial en caso de error
        }
    }


}
