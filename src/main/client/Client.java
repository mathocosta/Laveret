package main.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
  private Socket socket = null;
  private DataOutputStream streamOut = null;
  private Thread receiverThread = null;
  private AppWindow appWindow = null;


  public Client (String serverName, int serverPort, AppWindow appWindow) {
    this.appWindow = appWindow;

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
  }


  /**
   * Inicia as funcionalidades do cliente.
   * 
   * @throws IOException
   */
  public void start () throws IOException {
    streamOut = new DataOutputStream(socket.getOutputStream());

    Receiver receiver = new Receiver(this, socket);
    receiverThread = new Thread(receiver);
    receiverThread.start();

    System.out.println("Jogo iniciado, Tente advinhar o número!");
  }


  /**
   * Encerra as atividades do cliente.
   */
  public void stop () {
    try {
      if (receiverThread != null)
        receiverThread.interrupt();
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


  /**
   * Envia o texto recebido para o servidor, em formato de inteiro.
   * 
   * @param sentence
   */
  public void send (String sentence) {
    try {
      streamOut.writeInt(Integer.parseInt(sentence));
      streamOut.flush();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Gerencia as mensagem recebidas do servidor e como devem ser interpretadas.
   * É chamado pelo recebedor(Receiver) do cliente.
   * 
   * @param sentence
   */
  public void handleCommunication (String sentence) {
    if (sentence.equals(".bye")) {
      System.out.println("Desconectando...");
      appWindow.updateGamePanel("", "", true);
      stop();
    } else {
      String tip = "";
      String where = "";
      switch (sentence) {
        case "+":
          tip = "O número é maior que isso";
          where = "tip";
          break;

        case "-":
          tip = "O número é menor que isso";
          where = "tip";
          break;

        case "=":
          tip = "Você acertou";
          where = "tip";
          break;

        default:
          tip = sentence;
          where = "log";
          break;
      }

      appWindow.updateGamePanel(where, tip, false);
      System.out.println(tip);
    }
  }
}
