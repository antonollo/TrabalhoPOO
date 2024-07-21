package br.edu.uea.poo.antonioEfelipe.trabalho2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente implements Runnable {

    private Socket cliente;
    private BufferedReader in;
    private PrintWriter out;
    private boolean concluido = false;

    public void run() {
        try {
            cliente = new Socket("127.0.0.1", 65535);
            out = new PrintWriter(cliente.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            InputHandler inHandler = new InputHandler();
            Thread thread = new Thread(inHandler);
            thread.start();

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                System.out.println(inMessage);
            }
        } catch (UnknownHostException e) {
            encerrar();
        } catch (IOException e) {
            encerrar();
        }
    }

    public void encerrar() {
        concluido = true;
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (cliente != null && !cliente.isClosed()) {
                cliente.close();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    class InputHandler implements Runnable {
        public void run() {
            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                String mensagem;
                while (!concluido) {
                    mensagem = inReader.readLine();
                    if (mensagem.equals("/quit")) {
                        out.println(mensagem);
                        encerrar();
                        break;
                    } else {
                        out.println(mensagem);
                    }
                }
            } catch (IOException e) {
                encerrar();
            }
        }
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.run();
    }
}
