package com.example.einzelabgabe_knapp;

import androidx.annotation.RequiresApi;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends Activity {

    TextView textView;
    EditText editText;
    Button b;
    Button berechnen;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = findViewById(R.id.Enter);
        berechnen = findViewById(R.id.button);

        textView = findViewById(R.id.Answer);
        textView.setText("Answer from Server");
        editText = findViewById(R.id.matNrInput);

        berechnen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String matNr = editText.getText().toString();
                SimpleThread t = new SimpleThread(matNr);
                t.start();
                try{
                    t.join();
                }catch(InterruptedException ie){

                }
                if(!t.getAnswer().equals("Dies ist keine gueltige Matrikelnummer")){
                    berechnen(matNr);
                }
                else{
                    textView.setText("Das ist keine Matrikelnummer");
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matNr = editText.getText().toString();
                SimpleThread t = new SimpleThread(matNr);
                t.start();
                try{
                    t.join();
                }catch(InterruptedException ie){

                }
                textView.setText(t.getAnswer());
            }
        });
    }
    public void berechnen(String matNr) {
        int firstSum = 0;
        int secondSum = 0;
        int[] numbers = new int[matNr.length()];
        char[] c = matNr.toCharArray();

        for (int i = 0; i < matNr.length(); i++) {
            numbers[i] = (int) c[i]-48;
        }
        for (int i = 0; i < numbers.length; i++) {
            if (i % 2 == 0) {
                firstSum += numbers[i];
            } else {
                secondSum += numbers[i];
            }
        }
        int alternierendeQuersumme = firstSum-secondSum;
        if(alternierendeQuersumme%2==0){
            textView.setText("GERADE");
        }
        else{
           textView.setText("UNGERADE");
        }

    }
}