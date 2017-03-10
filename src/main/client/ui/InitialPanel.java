package main.client.ui;

import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.client.AppWindow;
import main.client.Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class InitialPanel extends JPanel {

  private static final long serialVersionUID = -217109462087763722L;

  private final AppWindow appWindow;

  private JTextField textFieldNome;
  private JTextField textFieldIP;
  private JTextField textFieldPorta;
  private JButton btnIniciar;


  /**
   * JanelaInicial é a classe que representa a primeira tela do jogo. Nela o
   * Usuário digita um nome(que será usado apenas como decoração), o endereço do
   * ip do Servidor com o jogo rodando, e a porta que ele está escutando.
   */
  public InitialPanel (AppWindow appWindow) {
    this.appWindow = appWindow;

    setLayout(null);

    JLabel lblBemVindo = new JLabel("Bem Vindo ao Jogo dos N\u00FAmeros!");
    lblBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
    lblBemVindo.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblBemVindo.setBounds(10, 11, 430, 23);
    add(lblBemVindo);

    JLabel lblDigiteONome = new JLabel("Digite o seu nome:");
    lblDigiteONome.setFont(new Font("Tahoma", Font.PLAIN, 12));
    lblDigiteONome.setBounds(10, 62, 243, 14);
    add(lblDigiteONome);

    textFieldNome = new JTextField();
    textFieldNome.setBounds(10, 87, 243, 20);
    add(textFieldNome);
    textFieldNome.setColumns(10);

    JLabel lblDigiteOIp = new JLabel("Digite o IP do Servidor:");
    lblDigiteOIp.setFont(new Font("Tahoma", Font.PLAIN, 12));
    lblDigiteOIp.setBounds(10, 120, 243, 14);
    add(lblDigiteOIp);

    textFieldIP = new JTextField();
    textFieldIP.setToolTipText("Ex: 192.168.100.1");
    textFieldIP.setBounds(10, 145, 243, 20);
    add(textFieldIP);
    textFieldIP.setColumns(10);

    JLabel lblDigiteAPorta = new JLabel("Digite a porta do Servidor:");
    lblDigiteAPorta.setFont(new Font("Tahoma", Font.PLAIN, 12));
    lblDigiteAPorta.setBounds(10, 176, 243, 14);
    add(lblDigiteAPorta);

    textFieldPorta = new JTextField();
    textFieldPorta.setBounds(10, 201, 243, 20);
    add(textFieldPorta);
    textFieldPorta.setColumns(10);

    btnIniciar = new JButton("Iniciar");
    btnIniciar.addActionListener(handleBtnIniciar());
    btnIniciar.setBounds(328, 251, 89, 23);
    add(btnIniciar);
  }


  /**
   * Método que gerencia o que acontece quando o botão "iniciar" é clicado.
   * 
   * @return ActionListener
   */
  private ActionListener handleBtnIniciar () {
    return new ActionListener() {
      public void actionPerformed (ActionEvent arg0) {
        String servidor = textFieldIP.getText();
        int porta = Integer.parseInt(textFieldPorta.getText());

        Client client = new Client(servidor, porta, appWindow);
        appWindow.setClient(client);

        appWindow.changeContentPanel("gamePanel");
      }
    };
  }

}
