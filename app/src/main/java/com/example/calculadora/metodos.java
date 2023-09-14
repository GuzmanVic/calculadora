package com.example.calculadora;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    public boolean operadores(String equation) {// Checks if it is allowed to write an exponent
        boolean allow = false; // Initialize the 'allow' variable as false
        char last = '!'; // Initialize the 'last' character as '!'
        if (!equation.isEmpty()) { // Check if the 'equation' string is not empty
            last = equation.charAt(equation.length() - 1); // Get the last character of the 'equation' string
            if (Character.isDigit(last)) { // Check if the last character is a digit
                allow = true; // If the last character is a digit, set 'allow' to true
            }
        }
        return allow; // Return the 'allow' variable indicating whether it's allowed to write an exponent
    }

}
