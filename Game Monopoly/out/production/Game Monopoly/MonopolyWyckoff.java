import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class MonopolyWyckoff extends JFrame {
   private CardLayout cardLayout = new CardLayout();
   private JPanel mainContainer;
   GameManager gameManager;
   private MonopolyWyckoff$BoardPanel boardPanel;
   private JLabel lblTimer;
   private JTextArea txtPlayerInfo;
   private int secondsLeft;
   private String bgImagePath;
   private String musicPath;
   private String clickSoundPath;
   private int d1;
   private int d2;

   private class MonopolyWyckoff$StaticBackgroundPanel extends JPanel {
      public MonopolyWyckoff$StaticBackgroundPanel(MonopolyWyckoff parent, String imagePath) {
         super();
         this.setOpaque(true);
         this.setBackground(new Color(40, 40, 40));
      }
   }

   private class MonopolyWyckoff$GameStyledButton extends JButton {
      public MonopolyWyckoff$GameStyledButton(MonopolyWyckoff parent, String text, Color bg, String imagePath) {
         super(text);
         this.setBackground(bg);
         this.setForeground(Color.WHITE);
         this.setFocusPainted(false);
         this.setBorderPainted(false);
      }
   }

   private class MonopolyWyckoff$BoardPanel extends JPanel {
      public MonopolyWyckoff$BoardPanel(MonopolyWyckoff parent) {
         super();
         this.setBackground(new Color(0, 90, 0));
      }
   }

   private class MonopolyWyckoff$1 extends JPanel {
      public MonopolyWyckoff$1(MonopolyWyckoff parent) {
         super();
         this.setBackground(new Color(230, 230, 230));
      }
   }

   public MonopolyWyckoff() {
      this.mainContainer = new JPanel(this.cardLayout);
      this.gameManager = new GameManager();
      this.secondsLeft = 600;
      this.bgImagePath = "Ảnh Monopoly/monopoly_bg.png";
      this.musicPath = "music.wav";
      this.clickSoundPath = "click.wav";
      this.d1 = 1;
      this.d2 = 1;
      this.setTitle("Monopoly Wyckoff - 2026");
      this.setSize(1300, 900);
      this.setDefaultCloseOperation(3);
      this.setLocationRelativeTo((Component) null);
      this.mainContainer.add(this.createMainMenu(), "MENU");
      this.mainContainer.add(this.createLoginScreen("Facebook", new Color(66, 103, 178)), "LOGIN_FB");
      this.mainContainer.add(this.createLoginScreen("Garena", new Color(189, 39, 44)), "LOGIN_GARENA");
      this.mainContainer.add(this.createLoginScreen("Google", new Color(234, 67, 53)), "LOGIN_GOOGLE");
      this.mainContainer.add(this.createInGameScreen(), "INGAME");
      this.add(this.mainContainer);
      this.playMusic(this.musicPath);
      this.setVisible(true);
   }

   private String autoFindImagePath(String keyword) {
      File dir = new File("Ảnh Monopoly");
      if (dir.exists() && dir.isDirectory()) {
         File[] matches = dir.listFiles((d, name) -> name.toLowerCase().contains(keyword.toLowerCase())
               && (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")));
         if (matches != null && matches.length > 0) {
            return "Ảnh Monopoly/" + matches[0].getName();
         }
      }

      return null;
   }

   private JPanel createMainMenu() {
      MonopolyWyckoff$StaticBackgroundPanel panel = new MonopolyWyckoff$StaticBackgroundPanel(this, this.bgImagePath);
      panel.setLayout((LayoutManager) null);
      int btnWidth = 320;
      int centerX = (1300 - btnWidth) / 2;
      panel.add(this.setupBtn("GUEST LOGIN", new Color(30, 30, 30, 230), "guest", centerX, 580, 55, true));
      panel.add(this.setupBtn("FACEBOOK LOGIN", new Color(66, 103, 178), "facebook", centerX, 645, 45, false));
      panel.add(this.setupBtn("GARENA LOGIN", new Color(189, 39, 44), "garena", centerX, 700, 45, false));
      panel.add(this.setupBtn("GOOGLE LOGIN", new Color(70, 70, 70), "google", centerX, 755, 45, false));
      return panel;
   }

   private MonopolyWyckoff$GameStyledButton setupBtn(String text, Color bg, String key, int x, int y, int h,
         boolean isGuest) {
      MonopolyWyckoff$GameStyledButton btn = new MonopolyWyckoff$GameStyledButton(this, text, bg,
            this.autoFindImagePath(key));
      btn.setBounds(x, y, 320, h);
      btn.addActionListener((e) -> {
         this.playClickSound();
         if (isGuest) {
            this.enterGame();
         } else {
            CardLayout var10000 = this.cardLayout;
            JPanel var10001 = this.mainContainer;
            String var10002 = key.toUpperCase();
            var10000.show(var10001, "LOGIN_" + var10002.replace("FACEBOOK", "FB"));
         }

      });
      return btn;
   }

   private JPanel createLoginScreen(String platform, Color themeColor) {
      String imagePath = this.autoFindImagePath(platform.toLowerCase() + "_bg");
      if (imagePath == null) {
         imagePath = this.bgImagePath;
      }

      MonopolyWyckoff$StaticBackgroundPanel p = new MonopolyWyckoff$StaticBackgroundPanel(this, imagePath);
      p.setLayout(new GridBagLayout());
      JPanel loginBox = new JPanel(new GridBagLayout());
      loginBox.setBackground(new Color(255, 255, 255, 230));
      loginBox.setBorder(new EmptyBorder(25, 30, 25, 30));
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(10, 10, 10, 10);
      gbc.fill = 2;
      JLabel lblTitle = new JLabel(platform.toUpperCase() + " LOGIN", 0);
      lblTitle.setFont(new Font("Arial", 1, 30));
      lblTitle.setForeground(themeColor);
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.gridwidth = 2;
      loginBox.add(lblTitle, gbc);
      gbc.gridwidth = 1;
      gbc.gridy = 1;
      gbc.gridx = 0;
      loginBox.add(new JLabel("Email / SĐT:"), gbc);
      JTextField txtEmail = new JTextField(20);
      gbc.gridx = 1;
      loginBox.add(txtEmail, gbc);
      gbc.gridx = 0;
      gbc.gridy = 2;
      loginBox.add(new JLabel("Mật khẩu:"), gbc);
      JPasswordField txtPass = new JPasswordField(20);
      gbc.gridx = 1;
      loginBox.add(txtPass, gbc);
      MonopolyWyckoff$GameStyledButton btnLogin = new MonopolyWyckoff$GameStyledButton(this, "ĐĂNG NHẬP", themeColor,
            (String) null);
      btnLogin.addActionListener((e) -> {
         this.playClickSound();
         this.enterGame();
      });
      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.gridwidth = 2;
      loginBox.add(btnLogin, gbc);
      JButton btnBack = new JButton("Quay lại Menu");
      btnBack.addActionListener((e) -> {
         this.playClickSound();
         this.cardLayout.show(this.mainContainer, "MENU");
      });
      gbc.gridy = 4;
      loginBox.add(btnBack, gbc);
      p.add(loginBox);
      return p;
   }

   private JPanel createInGameScreen() {
      JPanel mainGame = new JPanel(new BorderLayout());
      this.lblTimer = new JLabel("TIME: 00:00", 0);
      this.lblTimer.setFont(new Font("Arial", 1, 24));
      this.lblTimer.setOpaque(true);
      this.lblTimer.setBackground(Color.WHITE);
      mainGame.add(this.lblTimer, "North");
      this.boardPanel = new MonopolyWyckoff$BoardPanel(this);
      mainGame.add(this.boardPanel, "Center");
      mainGame.add(this.createSideMenu(), "West");
      mainGame.add(this.createInfoPanel(), "East");
      return mainGame;
   }

   private JPanel createSideMenu() {
      JPanel menu = new JPanel(new GridLayout(6, 1, 10, 10));
      menu.setBackground(new Color(45, 45, 45));
      menu.setBorder(new EmptyBorder(20, 10, 20, 10));
      JButton btnPause = new JButton("PAUSE");
      btnPause.addActionListener((e) -> {
         this.playClickSound();
         this.gameManager.setPaused(!this.gameManager.isPaused());
         btnPause.setText(this.gameManager.isPaused() ? "RESUME" : "PAUSE");
      });
      JButton btnExit = new JButton("EXIT GAME");
      btnExit.setBackground(new Color(180, 0, 0));
      btnExit.setForeground(Color.WHITE);
      btnExit.addActionListener((e) -> System.exit(0));
      menu.add(btnPause);
      menu.add(new JButton("SAVE"));
      menu.add(new JButton("LOAD"));
      menu.add(new JButton("SOUND"));
      menu.add(new JLabel());
      menu.add(btnExit);
      return menu;
   }

   private JPanel createInfoPanel() {
      JPanel rightPanel = new JPanel(new BorderLayout());
      rightPanel.setPreferredSize(new Dimension(280, 0));
      JPanel diceArea = new MonopolyWyckoff$1(this);
      diceArea.setPreferredSize(new Dimension(220, 120));
      this.txtPlayerInfo = new JTextArea();
      this.txtPlayerInfo.setEditable(false);
      this.txtPlayerInfo.setBorder(BorderFactory.createTitledBorder("THÔNG TIN NGƯỜI CHƠI"));
      rightPanel.add(diceArea, "North");
      rightPanel.add(new JScrollPane(this.txtPlayerInfo), "Center");
      return rightPanel;
   }

   public void rollDiceAnimation(int finalD1, int finalD2) {
      Timer timer = new Timer(60, (ActionListener) null);
      int[] count = new int[] { 0 };
      timer.addActionListener((e) -> {
         this.d1 = (int) (Math.random() * (double) 6.0F) + 1;
         this.d2 = (int) (Math.random() * (double) 6.0F) + 1;
         this.repaint();
         int var10003 = count[0];
         int var10000 = count[0];
         count[0] = var10003 + 1;
         if (var10000 > 15) {
            timer.stop();
            this.d1 = finalD1;
            this.d2 = finalD2;
            this.repaint();
         }

      });
      timer.start();
   }

   public void refreshBoard() {
      if (this.boardPanel != null) {
         this.boardPanel.repaint();
      }

      this.updatePlayerInfo();
   }

   private void updatePlayerInfo() {
      StringBuilder sb = new StringBuilder();
      if (this.gameManager.getPlayers() != null) {
         for (Player p : this.gameManager.getPlayers()) {
            sb.append("\ud83d\udc64 ").append(p.getName()).append("\n");
            sb.append("\ud83d\udcb0 $").append(p.getMoney()).append("\n");
            sb.append("\ud83d\udccd Vị trí: ").append(p.getPosition()).append("\n");
            sb.append("--------------------------\n");
         }
      }

      this.txtPlayerInfo.setText(sb.toString());
   }

   private void startTimer() {
      Timer timer = new Timer(1000, (e) -> {
         if (!this.gameManager.isPaused() && this.secondsLeft > 0) {
            --this.secondsLeft;
            this.lblTimer.setText(String.format("TIME: %02d:%02d", this.secondsLeft / 60, this.secondsLeft % 60));
         }

      });
      timer.start();
   }

   private void enterGame() {
      Object[] options = new Object[] { "30 Phút", "60 Phút" };
      int timeChoice = JOptionPane.showOptionDialog(this, "Chọn thời gian chơi", "Game Setup", -1, 3, (Icon) null,
            options, options[0]);
      this.secondsLeft = timeChoice == 1 ? 3600 : 1800;
      String input = JOptionPane.showInputDialog(this, "Số người chơi (2-4):", "2");
      if (input != null) {
         try {
            int numPlayers = Integer.parseInt(input);
            if (numPlayers < 2 || numPlayers > 4) {
               JOptionPane.showMessageDialog(this, "Số người chơi phải từ 2 đến 4!");
               return;
            }

            this.gameManager.setupPlayers(numPlayers);
            this.cardLayout.show(this.mainContainer, "INGAME");
            this.startTimer();
            this.refreshBoard();
            (new Thread(() -> this.gameManager.startGame(this))).start();
         } catch (NumberFormatException var5) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!");
         }
      }

   }

   private void playMusic(String path) {
      try {
         File file = new File(path);
         if (!file.exists()) {
            return;
         }

         AudioInputStream audio = AudioSystem.getAudioInputStream(file);
         Clip clip = AudioSystem.getClip();
         clip.open(audio);
         clip.loop(-1);
         clip.start();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   private void playClickSound() {
      try {
         File file = new File(this.clickSoundPath);
         if (!file.exists()) {
            return;
         }

         AudioInputStream audio = AudioSystem.getAudioInputStream(file);
         Clip clip = AudioSystem.getClip();
         clip.open(audio);
         clip.start();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(MonopolyWyckoff::new);
   }
}
