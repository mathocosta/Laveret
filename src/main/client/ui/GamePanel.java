package main.client.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import main.client.AppWindow;

import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class GamePanel extends JPanel {

  private static final long serialVersionUID = 7012750157649731007L;

  private final AppWindow appWindow;

  // Onde o jogador digita um numero para ser testado no servidor.
  private JLabel lblNumberField;
  private JTextField numberField;
  private JButton btnTestar;

  // Dica que volta do servidor.
  private JLabel lblTip;

  // Log que mostra as jogadas feitas por outros jogadores em tempo real.
  private JLabel lblLog;
  private JTextPane log;


  /**
   * Inicializa o Panel
   */
  public GamePanel (AppWindow appWindow) {

    this.appWindow = appWindow;

    setLayout(null);

    lblTip = new JLabel("Digite um n\u00FAmero");
    lblTip.setHorizontalAlignment(SwingConstants.CENTER);
    lblTip.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblTip.setBounds(10, 32, 430, 20);
    add(lblTip);

    lblLog = new JLabel("As jogadas de outros jogadores");
    lblLog.setFont(new Font("Tahoma", Font.PLAIN, 12));
    lblLog.setBounds(192, 75, 219, 14);
    add(lblLog);

    log = new JTextPane();
    log.setEditable(false);
    log.setBounds(192, 100, 219, 173);
    add(log);

    lblNumberField = new JLabel("Digite um n\u00FAmero!");
    lblNumberField.setFont(new Font("Tahoma", Font.PLAIN, 12));
    lblNumberField.setBounds(10, 100, 131, 14);
    add(lblNumberField);

    numberField = new JTextField();
    numberField.setBounds(10, 125, 131, 20);
    numberField.setColumns(10);
    numberField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
        "submitNumber");
    numberField.getActionMap().put("submitNumber", submitNumberAction());
    add(numberField);

    btnTestar = new JButton("Testar");
    btnTestar.addActionListener(handlerBtnTestar());
    btnTestar.setBounds(10, 169, 89, 23);
    add(btnTestar);

  }


  private AbstractAction submitNumberAction () {
    return new AbstractAction() {
      private static final long serialVersionUID = 532557165854556395L;


      @Override
      public void actionPerformed (ActionEvent e) {
        // Apenas executa se houver texto no campo.
        if (!numberField.getText().equals(""))
          btnTestar.doClick();
      }
    };
  }


  /**
   * Passa o texto do campo 'numberField' para o cliente socket.
   * 
   * @return ActionListener
   */
  private ActionListener handlerBtnTestar () {
    return new ActionListener() {
      public void actionPerformed (ActionEvent ev) {
        appWindow.getClient().send(numberField.getText());
        numberField.setText("");
      }
    };
  }


  /**
   * Atualiza o texto na label de dica.
   * 
   * @param newTip
   */
  public void updateTip (String newTip) {
    lblTip.setText(newTip);
  }


  /**
   * Atualiza o log com a jogada dos outros jogadores
   * 
   * @param sentence
   */
  public void updateLog (String sentence) {
    String currLog = log.getText();
    log.setText(sentence + currLog);
  }


  public void turnOffBtnTestar () {
    btnTestar.setEnabled(false);
  }

}
