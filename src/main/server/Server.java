package main.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
  private ServerSocket server = null;
  private ConnectionList connectionList = null;


  public Server (int port) {
    try {
      System.out.println("Iniciando na porta " + port + " aguarde ...");
      server = new ServerSocket(port);
      System.out.println("Servidor iniciado: " + server);

      connectionList = new ConnectionList(this, server);
      connectionList.start();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }


  public void stop () {
    if (server != null && !server.isClosed()) {
      try {
        connectionList.stopRunning();
        connectionList.interrupt();

        server.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
 

  // main para não precisar do 'TesteServer.java'
  // public static void main (String[] args) {
  // new Server(12345);
  // }
}
