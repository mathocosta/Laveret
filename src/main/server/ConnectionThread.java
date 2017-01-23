package main.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.util.InfoBundle;

/**
 * Classe para ser cuidar de cada conexão realizada ao servidor, colocando em
 * sua devida thread.
 */
public class ConnectionThread extends Thread {
  private Socket socket = null;
  private Server server = null;
  private int identifier = -1;
  private ObjectInputStream streamIn = null;
  private ObjectOutputStream streamOut = null;


  public ConnectionThread (Server _server, Socket _socket) {
    server = _server;
    socket = _socket;
    identifier = socket.getPort();
  }


  @Override
  public void run () {
    System.out.println("Connection " + identifier + " running");

    while (!this.isInterrupted()) {
      try {
        InfoBundle bundle = (InfoBundle) streamIn.readObject();
        System.out.println(identifier + ": " + bundle.getQuestionAnswer());
        server.getConnectionList().handleCommunication(identifier, bundle);
      } catch (IOException e) {
        System.out.println("Erro em " + identifier + ": " + e.getMessage());
        server.getConnectionList().remove(identifier);
        interrupt();
      } catch (ClassNotFoundException e) {
        System.out.println("Erro em " + identifier + e.getMessage());
        e.printStackTrace();
      }
    }
  }


  /**
   * @return the iD
   */
  public int getIdentifier () {
    return identifier;
  }


  /**
   * Inicia o recebimento(Input) pela conexão.
   * 
   * @throws IOException
   */
  public void open () throws IOException {
    streamIn = new ObjectInputStream(socket.getInputStream());
    streamOut = new ObjectOutputStream(socket.getOutputStream());
  }


  /**
   * Fecha a conexão com o socket e a ObjectInputStream da conexão.
   * 
   * @throws IOException
   */
  public void close () throws IOException {
    if (streamIn != null)
      streamIn.close();
    // if (socket != null)
    // socket.close();
  }


  /**
   * Envia uma mensagem para o cliente da conexão feita.
   * 
   * @param bundle
   */
  public void send (InfoBundle bundle) {
    try {
      streamOut.writeObject(bundle);
    } catch (IOException e) {
      System.out.println(identifier + " ERRO: " + e.getMessage());
    }
  }
}
