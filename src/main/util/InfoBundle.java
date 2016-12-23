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
  private String questionAnswer;
  // TODO: Ver se eh melhor isso direto ou somente um inteiro.
  // private InetAddress destination;

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
}