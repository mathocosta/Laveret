package main.util;

import java.io.Serializable;

/**
 * Objeto com todas as informa��es necess�rias na comunica��o entre os clientes
 * e o servidor.
 * 
 * Informa��es: 1. Resposta da �ltima quest�o 2. Endereco de IP do destinat�rio
 * 3. Pergunta mais recente
 *
 */
public class InfoBundle implements Serializable {
  private static final long serialVersionUID = -3896054514926163002L;
  private String question;
  private String questionAnswer;
  private int destinationIP;
  private boolean fromServer;


  public InfoBundle () {
    this.question = "";
    this.questionAnswer = "";
  }


  public String getQuestionAnswer () {
    return questionAnswer;
  }


  public void setQuestionAnswer (String questionAnswer) {
    this.questionAnswer = questionAnswer;
  }


  public String getQuestion () {
    return question;
  }


  public void setQuestion (String question) {
    this.question = question;
  }


  public int getDestinationIP () {
    return destinationIP;
  }


  public void setDestinationIP (int IP) {
    this.destinationIP = IP;
  }


  public boolean isFromServer () {
    return fromServer;
  }


  public void setFromServer (boolean fromServer) {
    this.fromServer = fromServer;
  }
}
