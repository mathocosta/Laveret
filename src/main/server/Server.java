package main.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import main.util.InfoBundle;

public class Server {
  private Socket socket = null;
  private ServerSocket server = null;
  private ObjectInputStream streamIn = null;


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
          InfoBundle bundle = (InfoBundle) streamIn.readObject();
          System.out.println(bundle.getQuestionAnswer());
        } catch (IOException e) {
          done = true;
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
          done = true;
        }
      }

      close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }


  // Inicia o recebimento do que vem do cliente
  public void open () throws IOException {
    streamIn = new ObjectInputStream(socket.getInputStream());
  }


  // Encerra todos os processos
  public void close () throws IOException {
    if (socket != null)
      socket.close();
    if (streamIn != null)
      streamIn.close();
  }

  // main para não precisar do 'TesteServer.java'
  // public static void main (String[] args) {
  // new Server(12345);
  // }
}
