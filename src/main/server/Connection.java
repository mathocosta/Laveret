package main.server;

import java.net.Socket;

/*
 * Classe para guardar dados de cada conexão com servidor.
 */
// TODO: Ver se ela eh realmente necessária.
public class Connection {
  @SuppressWarnings("unused")
  private Socket socket;


  public Connection (Socket socket) {
    this.socket = socket;
  }
}
