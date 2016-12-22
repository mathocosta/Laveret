package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
  private Thread thread = null;
  private ServerSocket server = null;
  private ThreadConnection client = null;


  public Server (int port) {
    try {
      System.out.println("Iniciando na porta " + port + " aguarde ...");
      server = new ServerSocket(port);
      System.out.println("Servidor iniciado: " + server);
      start();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }


  private void start () {
    if (thread == null) {
      thread = new Thread(this);
      thread.start();
    }

  }


  public void stop () {
    if (thread != null) {
      thread.interrupt();
      thread = null;
    }
  }


  @Override
  public void run () {
    while (thread != null) {
      try {
        System.out.println("Esperando um cliente...");
        addThreadConnection(server.accept());
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }

  }


  private void addThreadConnection (Socket accept) {
    System.out.println("Cliente aceito: " + accept);
    client = new ThreadConnection(this, accept);
    try {
      client.open();
      client.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // main para não precisar do 'TesteServer.java'
  // public static void main (String[] args) {
  // new Server(12345);
  // }
}
