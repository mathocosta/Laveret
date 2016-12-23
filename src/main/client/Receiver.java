package main.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import main.util.InfoBundle;

/**
 * Classe que serve para gerenciar o recebimento de qualquer mensagem vinda do
 * servidor.
 * 
 * Roda em uma outra Thread criada pelo cliente.
 */
public class Receiver implements Runnable {
  private Socket socket;
  private SimpleClient client;
  private ObjectInputStream inStream;


  /**
   * @param _client
   * @param _socket
   */
  public Receiver (SimpleClient _client, Socket _socket) {
    this.socket = _socket;
    this.client = _client;

    try {
      inStream = new ObjectInputStream(socket.getInputStream());
    } catch (IOException e) {
      System.out.println("Erro em pegar o InputStream: " + e);
      client.stop();
    }
  }


  @Override
  public void run () {
    while (true) {
      try {
        client.handleCommunication((InfoBundle) inStream.readObject());
      } catch (IOException e) {
        System.out.println(e.getMessage());
        client.stop();
      } catch (ClassNotFoundException e) {
        System.out.println(e.getMessage());
        client.stop();
      }
    }
  }

}
