package test;

import main.client.SimpleClient;

public class TesteSimpleCliente {

  public static void main (String[] args) {
    @SuppressWarnings("unused")
    SimpleClient client = new SimpleClient("localhost", 12345);
  }

}
