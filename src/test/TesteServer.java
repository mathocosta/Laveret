package test;

import main.server.Server;

public class TesteServer {

  public static void main (String[] args) {
    @SuppressWarnings("unused")
    Server servidor = new Server(12345);
  }

}
