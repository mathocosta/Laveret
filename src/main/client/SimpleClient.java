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
        streamOut.writeUTF(line);
        streamOut.flush();
      } catch (IOException e) {
        System.out.println("Erro de envio: " + e.getMessage());
      }
    }
  }


  public void start () throws IOException {
    console = new BufferedReader(new InputStreamReader(System.in));
    streamOut = new DataOutputStream(socket.getOutputStream());
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
