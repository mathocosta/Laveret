package test;

import main.server.Server;

public class TesteServer {

  public static void main (String[] args) {
    Server servidor = new Server(12345);
    System.out.println(servidor.getSortedNumber());
  }

}
