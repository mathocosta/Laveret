package main.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleClient {
  private Socket socket = null;
  private BufferedReader console = null;
  private DataOutputStream streamOut = null;
  private Thread receiverThread = null;


  public SimpleClient (String serverName, int serverPort) {
    System.out.println("Tentando conexão..");

    try {
      socket = new Socket(serverName, serverPort);
      System.out.println("Conectado: " + socket);
      start();
    } catch (UnknownHostException e) {
      System.out.println("Host desconhecido: " + e.getMessage());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    String line = "";
    while (!line.equals(".bye")) {
      try {
        line = console.readLine();

        if (!line.equals(".bye")) {
          streamOut.writeFloat(Float.parseFloat(line));
          streamOut.flush();
        }

      } catch (IOException e) {
        System.out.println("Erro de envio: " + e.getMessage());
        stop();
      }
    }
    stop();
  }


  /**
   * Inicia as funcionalidades do cliente.
   * 
   * @throws IOException
   */
  public void start () throws IOException {
    console = new BufferedReader(new InputStreamReader(System.in));
    streamOut = new DataOutputStream(socket.getOutputStream());

    Receiver receiver = new Receiver(this, socket);
    receiverThread = new Thread(receiver);
    receiverThread.start();
  }


  public void stop () {
    try {
      if (receiverThread != null)
        receiverThread.interrupt();
      // FIXME: Encerra a conexão, mas não termina o programa.
      // if (console != null)
      // console.close();
      if (streamOut != null)
        streamOut.close();
      if (socket != null)
        socket.close();
    } catch (IOException ioe) {
      System.out.println("Erro encerrando ...");
    } finally {
      System.out.println("Desconectado.");
    }
  }


  public void handleCommunication (String sentence) {
    if (sentence.equals(".bye")) {
      System.out.println("Desconectando...");
      stop();
    } else {
      System.out.println(sentence);
    }
  }
}
