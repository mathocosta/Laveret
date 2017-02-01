package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Objeto com todas as threads de conexões rodando
 */
public class ConnectionList extends Thread {
  private ServerSocket serverSocket = null;
  private Server server = null;
  private ArrayList<ConnectionThread> connections = null;
  private boolean isRunning;


  public ConnectionList (Server _server, ServerSocket _serverSocket) {
    this.server = _server;
    this.serverSocket = _serverSocket;
    this.connections = new ArrayList<ConnectionThread>();
    isRunning = true;
  }


  @Override
  public void run () {
    while (isRunning) {
      if (serverSocket.isClosed()) {
        isRunning = false;
        break;
      }
      try {
        System.out.println("Esperando um cliente...");
        Socket socket = serverSocket.accept();
        System.out.println("Cliente aceito: " + socket);

        ConnectionThread conn = new ConnectionThread(server, socket);
        connections.add(conn);

        conn.open();
        conn.start();
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }


  /**
   * Gerencia a comunicação com todos da lista de conexões.
   * 
   * @param ID
   * @param bundle
   */
  public synchronized void handleCommunication (int ID, float number) {
    float resposta = server.getSortedNumber();

    for (ConnectionThread conn : connections) {
      if (conn.getIdentifier() != ID)
        conn.send(ID + " tentou: " + number);
    }

    String tip = "";
    if (number == resposta) {
      tip = "Você acertou!!";
      for (ConnectionThread conn : connections) {
        if (conn.getIdentifier() == ID)
          conn.send(tip);
        else
          conn.send("Conexão " + ID + " acertou, fim de jogo.");
        
        conn.send(".bye");
      }
      server.stop();
    } else {
      if (number > resposta)
        tip = "menor";
      else
        tip = "maior";
    }

    findConnection(ID).send(tip);

  }


  /**
   * Recebe o ID do procurado e retorna o objetos.
   * 
   * @param ID
   * @return
   */
  public ConnectionThread findConnection (int ID) {
    for (ConnectionThread conn : connections) {
      if (conn.getIdentifier() == ID)
        return conn;
    }
    return null;
  }


  /**
   * Remove um elemento da lista de conexões de acordo com seu ID.
   * 
   * @param ID
   */
  public synchronized void remove (int ID) {
    ConnectionThread toRemove = findConnection(ID);
    if (toRemove != null) {
      System.out.println("Removendo o cliente " + ID);
      connections.remove(toRemove);

      try {
        toRemove.close();
        toRemove.interrupt();
      } catch (IOException e) {
        System.out.println("Erro em fechar a thread: " + e);
      }

      System.out.println("Cliente: " + ID + " removido.");
    } else {
      System.out.println("Elemento não achado.");
    }
  }


  public void stopRunning () throws IOException {
    for (ConnectionThread connectionThread : connections) {
      connectionThread.close();
      connectionThread.interrupt();
    }
  }

}
