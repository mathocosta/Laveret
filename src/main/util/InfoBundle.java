package main.util;

import java.io.Serializable;

/**
 * Objeto com todas as informações necessárias na comunicação entre os clientes
 * e o servidor.
 * 
 * Informações: 1. Resposta da última questão 2. Endereco de IP do destinatário
 * 3. Pergunta mais recente
 *
 */
public class InfoBundle implements Serializable {
  private static final long serialVersionUID = -3896054514926163002L;
  private String questionAnswer;
  private String question;
   private int destinationIP;

  // FIXME: Remover os setters do construtor 
  public InfoBundle (String questionAnswer) {
    this.setQuestionAnswer(questionAnswer);
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
}
