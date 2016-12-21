package main.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import main.util.InfoBundle;

public class SimpleClient {
  private Socket socket = null;
  private BufferedReader console = null;
  private ObjectOutputStream streamOut = null;


  public SimpleClient (String serverName, int serverPort) {
    System.out.println("Tentando conex�o..");

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
        InfoBundle bundle = new InfoBundle(line);
        streamOut.writeObject(bundle);
      } catch (IOException e) {
        System.out.println("Erro de envio: " + e.getMessage());
      }
    }
  }


  public void start () throws IOException {
    console = new BufferedReader(new InputStreamReader(System.in));
    streamOut = new ObjectOutputStream(socket.getOutputStream());
  }


  public void stop () {
    try {
      if (console != null)
        console.close();
      if (streamOut != null)
        streamOut.close();
      if (socket != null)
        socket.close();
    } catch (IOException ioe) {
      System.out.println("Erro encerrando ...");
    }
  }
}
