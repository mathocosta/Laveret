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


  public void stopRunning () throws IOException {
    for (ConnectionThread connectionThread : connections) {
      connectionThread.close();
      connectionThread.interrupt();
    }
  }
}
