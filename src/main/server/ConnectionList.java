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
  private ConnectionThread currentConnection = null;
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

      if (connections.size() == 2) {
        InfoBundle namesBundle = new InfoBundle();
        namesBundle.setQuestion("Defina um nome para seu oponente:");
        namesBundle.setFromServer(true);

        // envia pra todos o inicio do jogo
        for (ConnectionThread conn : connections)
          handleCommunication(conn.getIdentifier(), namesBundle);

        // inicia jogo
        handleTurn(0);
        InfoBundle initialBundle = new InfoBundle();
        initialBundle.setFromServer(true);
        initialBundle.setQuestion("Escreva a pergunta");
        handleCommunication(currentConnection.getIdentifier(), initialBundle);
      }
    }
  }


  /**
   * Gerencia a vez de jogar. Decide quem ser� o pr�ximo o jogar.
   * 
   * @param currentPlayerID
   */
  public synchronized void handleTurn (int currentPlayerID) {
    // 1ra jogada, iniciando com o primeiro na lista.
    if (currentPlayerID == 0) {
      currentConnection = connections.get(currentPlayerID);
      // demais jogadas
    } else {
      currentConnection = findConnection(currentPlayerID);
      int index = connections.indexOf(currentConnection);
      currentConnection = connections.get(index + 1);
    }

    System.out.println("Proximo a jogar: " + currentConnection.getIdentifier());
  }


  /**
   * Executa a jogada com o player atual.
   * 
   * @param conn
   */
  public void turn (ConnectionThread conn) {

  }


  /**
   * Gerencia a comunica��o com todos da lista de conex�es.
   * 
   * @param ID
   * @param bundle
   */
  public synchronized void handleCommunication (int ID, InfoBundle bundle) {
    // if (bundle.getQuestionAnswer().equals(".bye")) {
    // findConnection(ID).send(bundle);
    // remove(ID);
    // } else {
    // if (!bundle.isFromServer())
    // bundle.setDestinationIP(ID);
    // for (ConnectionThread connectionThread : connections)
    // if (connectionThread.getIdentifier() != ID)
    // connectionThread.send(bundle);
    // }
    if (!bundle.isFromServer())
      bundle.setDestinationIP(ID);

    findConnection(ID).send(bundle);

    if (bundle.getQuestionAnswer().equals(".bye"))
      remove(ID);
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
