package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public int parentesisA = 0, parentesisC = 0;//Variables to control the correct use of parentheses
    metodos method = new metodos();//Instance of the class "metodos" wich controlls all the logic of the app

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

        method.numerales(numeros, display);//Load the number buttons to allow user input on the display
        exp.setOnClickListener(new View.OnClickListener() {//Click on button "Exponent"
            @Override
            public void onClick(View view) {
                display.append(method.operadores((String) display.getText().toString(), "^"));
            }
        });
        root.setOnClickListener(new View.OnClickListener() {//Click on button "root"
            @Override
            public void onClick(View view) {
                display.append(method.operadores((String) display.getText().toString(), "√"));
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {//Click on button "Delete all"
            @Override
            public void onClick(View view) {
                display.setText("");//Delete all the text on the display
                parentesisC = 0;//Restart the parentheses counter for keep a correct control of them
                parentesisA = 0;
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {//Click on button "Delete"
            @Override
            public void onClick(View view) {
                display.setText(method.delete(display.getText().toString(), MainActivity.this));//Calls a method that deletes the last characther on the display
            }
        });
        parenthesesO.setOnClickListener(new View.OnClickListener() {//Click on button "Aperture parentheses"
            @Override
            public void onClick(View view) {
                display.append("(");
                parentesisA += 1;//increments the counter of parentheses
            }
        });
        parenthesesC.setOnClickListener(new View.OnClickListener() {//Click on button "parentheses close"
            @Override
            public void onClick(View view) {
                if (method.parenteshesControl(parentesisA, parentesisC, MainActivity.this)) {//Check if it is allowed to write a CLose parentheses
                    display.append(")");
                }
            }
        });
        mod.setOnClickListener(new View.OnClickListener() {//Click on button "modulo"
            @Override
            public void onClick(View view) {
                display.append(method.operadores((String) display.getText().toString(), "%"));//Check whether allowed to write a module operator
            }
        });
        div.setOnClickListener(new View.OnClickListener() {//Click on button "division"
            @Override
            public void onClick(View view) {
                display.append(method.operadores((String) display.getText().toString(), "/"));//Check whether allowed to write a division operator

            }
        });
        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.append(method.operadores((String) display.getText().toString(), "+"));//Check whether allowed to write a plus operator
            }
        });
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.append(method.operadores((String) display.getText().toString(), "*"));//Check whether allowed to write a multiplication operator
            }
        });
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.append(method.operadores((String) display.getText().toString(), "-"));//Check whether allowed to write a minus operator
            }
        });
        dock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.append(method.puntos(display.getText().toString()));
            }
        });
        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText(String.valueOf(method.resolver((String) display.getText().toString())));
            }
        });
    }

}