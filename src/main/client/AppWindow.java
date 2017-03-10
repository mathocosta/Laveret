package main.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import main.client.ui.GamePanel;
import main.client.ui.InitialPanel;

import java.awt.CardLayout;


public class AppWindow {

  private JFrame frame;

  // Panels do jogo.
  private InitialPanel initialPanel;
  private GamePanel gamePanel;
  private JPanel contentPane;

  private Client client;


  /**
   * Launch the application.
   */
  public static void main (String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run () {
        try {
          AppWindow window = new AppWindow();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }


  /**
   * Create the application.
   */
  public AppWindow () {
    initialize();
  }


  /**
   * Initialize the contents of the frame.
   */
  private void initialize () {
    frame = new JFrame();
    frame.setTitle("Jogo dos N\u00FAmeros");
    frame.setBounds(100, 100, 438, 325);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    contentPane = new JPanel(new CardLayout());
    frame.setContentPane(contentPane);

    initialPanel = new InitialPanel(this);
    contentPane.add(initialPanel, "InitialPanel");

    gamePanel = new GamePanel(this);
    contentPane.add(gamePanel, "gamePanel");

    // TEST:
    changeContentPanel("gamePanel");
  }


  /**
   * @param nameOfThePanel
   */
  public void changeContentPanel (String nameOfThePanel) {
    CardLayout cd = (CardLayout) contentPane.getLayout();
    cd.show(contentPane, nameOfThePanel);
  }


  /**
   * @param where
   * @param sentence
   */
  public void updateGamePanel (String where, String sentence, boolean finished) {
    if (where == "tip") {
      gamePanel.updateTip(sentence);
    } else {
      gamePanel.updateLog(sentence);
    }

    if (finished)
      gamePanel.turnOffBtnTestar();
  }


  public Client getClient () {
    return client;
  }


  public void setClient (Client client) {
    this.client = client;
  }


  /**
   * @return frame
   */
  public JFrame getFrame () {
    return frame;
  }

}
