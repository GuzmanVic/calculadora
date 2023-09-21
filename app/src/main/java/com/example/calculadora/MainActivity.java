package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public int parentesisA = 0, parentesisC = 0;//Variables to control the correct use of parentheses
    metodos method = new metodos();//Instance of the class "metodos" wich controlls all the logic of the app

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button[] numeros = new Button[10];//Load all the numbers into an array for more efficient control.
        //Import all the components from the activity_main.xml
        TextView display = findViewById(R.id.display);
        numeros[0] = findViewById(R.id.btn0);
        numeros[1] = findViewById(R.id.btn1);
        numeros[2] = findViewById(R.id.btn2);
        numeros[3] = findViewById(R.id.btn3);
        numeros[4] = findViewById(R.id.btn4);
        numeros[5] = findViewById(R.id.btn5);
        numeros[6] = findViewById(R.id.btn6);
        numeros[7] = findViewById(R.id.btn7);
        numeros[8] = findViewById(R.id.btn8);
        numeros[9] = findViewById(R.id.btn9);
        Button exp = findViewById(R.id.btnExp);
        Button root = findViewById(R.id.btnRaiz);
        Button deleteAll = findViewById(R.id.btnDeleteAll);
        Button delete = findViewById(R.id.btnBorrar);
        Button parenthesesO = findViewById(R.id.btnparentesisA);
        Button parenthesesC = findViewById(R.id.btnParentesisC);
        Button mod = findViewById(R.id.btnMod);
        Button div = findViewById(R.id.btnDiv);
        Button sum = findViewById(R.id.btnSum);
        Button mul = findViewById(R.id.btnMul);
        Button men = findViewById(R.id.btnMen);
        Button equals = findViewById(R.id.btnIgual);
        Button dock = findViewById(R.id.btnPunto);

        method.numerales(numeros, display, this);//Load the number buttons to allow user input on the display
        //Click on button "Exponent"
        exp.setOnClickListener(view -> display.append(method.operadores(display.getText().toString(), "^")));
        //Click on button "root"
        root.setOnClickListener(view -> display.append(method.operadores(display.getText().toString(), "√")));
        //Click on button "Delete all"
        deleteAll.setOnClickListener(view -> {
            display.setText("");//Delete all the text on the display
//Restart the parentheses counter for keep a correct control of them
            parentesisC = 0;
            parentesisA = 0;
        });

        //Click on button "Delete"
        delete.setOnClickListener(view -> {
            display.setText(method.delete(display.getText().toString(), MainActivity.this));//Calls a method that deletes the last characther on the display
        });
        //Click on button "Aperture parentheses"
        parenthesesO.setOnClickListener(view -> {
            display.append("(");
            parentesisA += 1;//increments the counter of parentheses
        });
        //Click on button "parentheses close"
        parenthesesC.setOnClickListener(view -> {
            if (method.parenteshesControl(parentesisA, parentesisC, MainActivity.this)) {//Check wether allowed to write a CLose parentheses
                display.append(")");
            }
        });
        //Click on button "modulus"
        mod.setOnClickListener(view -> {
            display.append(method.operadores(display.getText().toString(), "%"));//Check whether allowed to write a module operator
        });
        //Click on button "division"
        div.setOnClickListener(view -> {
            display.append(method.operadores(display.getText().toString(), "/"));//Check whether allowed to write a division operator

        });
        //Click on button "add"
        sum.setOnClickListener(view -> {
            display.append(method.operadores(display.getText().toString(), "+"));//Check whether allowed to write a plus operator
        });
        //Click on button "multiply"
        mul.setOnClickListener(view -> {
            display.append(method.operadores(display.getText().toString(), "*"));//Check whether allowed to write a multiplication operator
        });
        //Click on button "substract"
        men.setOnClickListener(view -> {
            display.append(method.operadores(display.getText().toString(), "-"));//Check whether allowed to write a minus operator
        });
        //Click on button "decimal point"
        dock.setOnClickListener(view -> display.append(method.puntos(display.getText().toString())));
        //Click on button "equals"
        equals.setOnClickListener(view -> {
            if (!display.getText().toString().isEmpty()) {
                display.setText(String.valueOf(metodos.resolver(display.getText().toString())));
            }
        });

        //Controls the appearance of the buttons
        deleteAll.setOnTouchListener((v, event) -> metodos.colores(deleteAll, this, event));
        exp.setOnTouchListener((v, event) -> metodos.colores(exp, this, event));
        root.setOnTouchListener((v, event) -> metodos.colores(root, this, event));
        delete.setOnTouchListener((v, event) -> metodos.colores(delete, this, event));
        parenthesesO.setOnTouchListener((v, event) -> metodos.colores(parenthesesO, this, event));
        parenthesesC.setOnTouchListener((v, event) -> metodos.colores(parenthesesO, this, event));
        mod.setOnTouchListener((v, event) -> metodos.colores(mod, this, event));
        div.setOnTouchListener((v, event) -> metodos.colores(div, this, event));
        sum.setOnTouchListener((v, event) -> metodos.colores(sum, this, event));
        mul.setOnTouchListener((v, event) -> metodos.colores(mul, this, event));
        men.setOnTouchListener((v, event) -> metodos.colores(men, this, event));
        dock.setOnTouchListener((v, event) -> metodos.colores(dock, this, event));
        equals.setOnTouchListener((v, event) -> metodos.colores(equals, this, event));
    }
}