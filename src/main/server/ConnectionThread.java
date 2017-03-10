package main.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Classe para ser cuidar de cada conexão realizada ao servidor, colocando em
 * sua devida thread.
 */
public class ConnectionThread extends Thread {
  private Socket socket = null;
  private Server server = null;
  private int identifier = -1;
  private DataInputStream streamIn = null;
  private DataOutputStream streamOut = null;


  public ConnectionThread (Server _server, Socket _socket) {
    this.server = _server;
    this.socket = _socket;
    this.identifier = socket.getPort();
  }


  @Override
  public void run () {
    System.out.println("Connection " + identifier + " running");

    while (!this.isInterrupted()) {
      try {
        int number = streamIn.readInt();
        System.out.println(identifier + ": " + number);
        server.getConnectionList().handleCommunication(identifier, number);
      } catch (IOException e) {
        System.out.println("Erro em " + identifier + ": " + e.getMessage());
        server.getConnectionList().remove(identifier);
        interrupt();
      }
    }
  }


  /**
   * @return the identifier
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
    streamIn = new DataInputStream(socket.getInputStream());
    streamOut = new DataOutputStream(socket.getOutputStream());
  }


  /**
   * Fecha a conexão com o socket e a ObjectInputStream da conexão.
   * 
   * @throws IOException
   */
  public void close () throws IOException {
    if (streamIn != null)
      streamIn.close();
  }


  /**
   * Envia uma mensagem para o cliente da conexão feita.
   * 
   * @param answer
   */
  public void send (String answer) {
    try {
      streamOut.writeUTF(answer);
      streamOut.flush();
    } catch (IOException e) {
      System.out.println(identifier + " ERRO: " + e.getMessage());
    }
  }
}
