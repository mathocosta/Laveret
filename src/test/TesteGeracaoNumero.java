package test;

import main.server.Server;

public class TesteGeracaoNumero {

  public static void main (String[] args) {
    Server servidor = new Server(12345);

    float num;
    for (int i = 0; i < 100; i++) {
      servidor.generateRandomNumber();
      num = servidor.getSortedNumber();
      if (num > 100 || num < 1)
        System.err.println(num);
    }
    System.out.println("Tudo OK");
    servidor.stop();
  }

}
