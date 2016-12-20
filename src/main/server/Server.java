package main.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private Socket socket = null;
  private ServerSocket server = null;
  private DataInputStream streamIn = null;


  public Server (int port) {
    try {
      System.out.println("Iniciando na porta " + port + " aguarde ...");
      server = new ServerSocket(port);
      System.out.println("Servidor iniciado: " + server);
      System.out.println("Esperando um cliente...");

      socket = server.accept();
      System.out.println("Cliente aceito: " + socket);
      open();

      boolean done = false;
      while (!done) {
        try {
          String line = streamIn.readUTF();
          System.out.println(line);
          done = line.equals(".bye");
        } catch (IOException e) {
          done = true;
        }
      }

      close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }


  public void open () throws IOException {
    streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
  }


  public void close () throws IOException {
    if (socket != null)
      socket.close();
    if (streamIn != null)
      streamIn.close();
  }

}
