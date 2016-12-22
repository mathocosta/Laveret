package main.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.util.InfoBundle;

/**
 * Classe para ser cuidar de cada conexão realizada ao servidor, colocando em
 * sua devida thread.
 */
// TODO:FIXME: Pesquisar se eh essa a forma certa, bem como senao for, como
// fazer somente com o Runnable
public class ConnectionThread extends Thread {
  private Socket socket = null;
  @SuppressWarnings("unused")
  private Server server = null;
  private int ID = -1;
  private ObjectInputStream streamIn = null;
  private ObjectOutputStream streamOut = null;


  public ConnectionThread (Server _server, Socket _socket) {
    server = _server;
    socket = _socket;
    ID = socket.getPort();
  }


  @Override
  public void run () {
    System.out.println("Connection " + ID + " running");
    while (true) {
      try {
        InfoBundle bundle = (InfoBundle) streamIn.readObject();
        System.out.println(ID + ": " + bundle.getQuestionAnswer());

      } catch (IOException e) {
        System.out.println(e.getMessage());
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * @return the iD
   */
  public int getID () {
    return ID;
  }


  /**
   * Inicia o recebimento(Input) pela conexão.
   * 
   * @throws IOException
   */
  public void open () throws IOException {
    streamIn = new ObjectInputStream(socket.getInputStream());
    streamOut = new ObjectOutputStream(socket.getOutputStream());
  }


  /**
   * Fecha a conexão com o socket e a ObjectInputStream da conexão.
   * 
   * @throws IOException
   */
  public void close () throws IOException {
    if (socket != null)
      socket.close();
    if (streamIn != null)
      streamIn.close();
  }

}
