package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import main.util.InfoBundle;

/**
 * Objeto com todas as threads de conex�es rodando
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
   * Gerencia a comunica��o com todos da lista de conex�es.
   * 
   * @param ID
   * @param bundle
   */
  public synchronized void handleCommunication (int ID, InfoBundle bundle) {
    if (bundle.getQuestionAnswer().equals(".bye")) {
      findConnection(ID).send(bundle);
      remove(ID);
    } else {
      bundle.setDestination(ID);
      for (ConnectionThread connectionThread : connections)
        connectionThread.send(bundle);
    }
  }


  /**
   * Recebe o ID do procurado e retorna o objetos.
   * 
   * @param ID
   * @return
   */
  public ConnectionThread findConnection (int ID) {
    for (ConnectionThread conn : connections) {
      if (conn.getID() == ID)
        return conn;
    }
    return null;
  }


  /**
   * Remove um elemento da lista de conex�es de acordo com seu ID.
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
      System.out.println("Elemento n�o achado.");
    }
  }


  public void stopRunning () throws IOException {
    for (ConnectionThread connectionThread : connections) {
      connectionThread.close();
      connectionThread.interrupt();
    }
  }

}
