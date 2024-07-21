package br.edu.uea.poo.antonioEfelipe.trabalho2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor implements Runnable {

    private ArrayList<ConnectionHandler> connections;
    private ServerSocket servidor;
    private boolean serverConcluido;
    
    public Servidor() {
        connections = new ArrayList<>();
        serverConcluido = false;
    }
    
    public void run() {
        try {
            servidor = new ServerSocket(65535);
            Thread thread;
            while (!serverConcluido) {
                Socket cliente = servidor.accept();
                ConnectionHandler handler = new ConnectionHandler(cliente);
                connections.add(handler);
                thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            encerrar();
        }
    }
    
    public void mensagemTodos(String mensagem) {
    	try {
			for (ConnectionHandler cHandler : connections) {
				if (cHandler != null) {
					cHandler.mandarMensagem(mensagem);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public void encerrar(){
    	try {
    		serverConcluido = true;
    		if (!servidor.isClosed()) {
    			servidor.close();
    		}
    		for (ConnectionHandler cHandler : connections) {
				cHandler.encerrar();
			}
    	} catch (IOException e) {
    		// ignore
    	}
    }
    class ConnectionHandler implements Runnable {
    	
    	private Socket cliente;
    	private PrintWriter out;
    	private BufferedReader in;
    	private String apelidoUsuario;
    	
        public ConnectionHandler (Socket cliente) {
        	this.cliente = cliente;
        }
        public void run() {
        	try {
        		out = new PrintWriter(cliente.getOutputStream(), true);
            	in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            	out.println("Insira seu nome para inicializarmos a conversa: ");
            	apelidoUsuario = in.readLine();
            	System.out.println(apelidoUsuario + " conectado.");
            	mensagemTodos(apelidoUsuario + " entrou na conversa. ");
            	String mensagem;
            	while ((mensagem = in.readLine()) != null) {
            		if (mensagem.startsWith("/nick ")) {
            			String[] mensagemSplit = mensagem.split(" ", 2);
            			if (mensagemSplit.length == 2) {
            				mensagemTodos(apelidoUsuario + " ajustou seu nome para " + mensagemSplit[1]);
            				System.out.println(apelidoUsuario + " ajustou seu nome para " + mensagemSplit[1]);
            				apelidoUsuario = mensagemSplit[1];
            				out.println("Nome trocado com sucesso para " + apelidoUsuario);
            			} else {
            				out.println("NOME DE USUÁRIO INVÁLIDO.");
            			}
            		} else if (mensagem.equals("/quit")){
            			mensagemTodos(apelidoUsuario + " deixou a conversa.");
            			encerrar();
            		} else {
            			mensagemTodos(apelidoUsuario + ": " +mensagem);
            		}
            	}
        	} catch (IOException e) {
        		encerrar();
        	}
        }
        
        public void mandarMensagem(String mensagem) {
        	out.println(mensagem);
        }
        
        public void encerrar() {
        	try {
            	in.close();
            	out.close();
            	if (!cliente.isClosed()) {
            		cliente.close();
            	}
        	} catch (IOException e) {
        		// Ignorar
        	}
        	
        }
    }
    public static void main(String[] args) {
		Servidor servidor = new Servidor();
		servidor.run();
	}
}
