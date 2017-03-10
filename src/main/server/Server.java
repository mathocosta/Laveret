package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;


public class Server {
  private ServerSocket server = null;
  private ConnectionList connectionList = null;
  private float sortedNumber;


  /**
   * @return connectionList
   */
  public ConnectionList getConnectionList () {
    return connectionList;
  }


  public Server (int port) {
    try {
      System.out.println("Iniciando na porta " + port + " aguarde ...");
      server = new ServerSocket(port);
      System.out.println("Servidor iniciado: " + server);

      // gera o número a ser usado para o jogo
      sortedNumber = generateRandomNumber();

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


  /**
   * Gera um número inteiro randomicamente entre 1 e 100, e retorna ele.
   * 
   * @return float
   */
  public int generateRandomNumber () {
    Random rn = new Random();
    int num = rn.nextInt((100 - 1) + 1) + 1;
    return num;
  }


  public float getSortedNumber () {
    return sortedNumber;
  }


  public static void main (String[] args) {
    new Server(12345);
  }
}
