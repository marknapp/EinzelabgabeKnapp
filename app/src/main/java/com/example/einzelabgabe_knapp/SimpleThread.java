package com.example.einzelabgabe_knapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SimpleThread extends Thread {

    String matNr;
    String answer;

    SimpleThread(String matNr){
        this.matNr = matNr;
    }

    @Override
    public void run() {
        try {

            Socket clientSocket = new Socket("se2-isys.aau.at", 53212);

            DataOutputStream outToServer = new DataOutputStream((clientSocket.getOutputStream()));

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.writeBytes(matNr + '\n');

            answer = inFromServer.readLine();

            clientSocket.close();

        } catch (Exception e) {
            System.out.println("crashed");
        }
    }
    public String getAnswer(){
        return answer;
    }
}
