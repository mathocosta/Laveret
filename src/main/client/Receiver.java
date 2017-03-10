package main.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Classe que serve para gerenciar o recebimento de qualquer mensagem vinda do
 * servidor.
 * 
 * Roda em uma outra Thread criada pelo cliente, para poder receber as mensagens
 * em tempo real.
 */
public class Receiver implements Runnable {
  private Socket socket;
  private Client client;
  private DataInputStream inStream;


  /**
   * @param _client
   * @param _socket
   */
  public Receiver (Client _client, Socket _socket) {
    this.socket = _socket;
    this.client = _client;

    try {
      inStream = new DataInputStream(socket.getInputStream());
    } catch (IOException e) {
      System.out.println("Erro em pegar o InputStream: " + e);
      client.stop();
    }
  }


  @Override
  public void run () {
    // Como a classe roda em uma propria thread, posso acessa-lá via classe
    // base e usar o método static 'interrupted', que retorna boolean se está
    // rodando ainda, para terminar o loop.
    while (!Thread.interrupted()) {
      try {
        client.handleCommunication(inStream.readUTF());
      } catch (IOException e) {
        System.out.println(e.getMessage());
        client.stop();
      }
    }
  }

}
