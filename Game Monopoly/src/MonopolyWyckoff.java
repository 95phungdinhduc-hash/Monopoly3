import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import javax.sound.sampled.*;

public class MonopolyWyckoff extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);
    GameManager gameManager = new GameManager();
    private BoardPanel boardPanel;

    private JLabel lblTimer;
    private JTextArea txtPlayerInfo;
    private int secondsLeft = 600;

    // Đường dẫn ảnh nền chính
    private String bgImagePath = "TRANGCHU.png";
    private String musicPath = "music.wav";
    private String clickSoundPath = "click.wav";

    int d1 = 1;
    private int d2 = 1;

    public MonopolyWyckoff() {
        setTitle("Monopoly Wyckoff - 2026");
        setSize(1300, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainContainer.add(createMainMenu(), "MENU");
        mainContainer.add(createLoginScreen("Facebook", new Color(66, 103, 178)), "LOGIN_FB");
        mainContainer.add(createLoginScreen("Garena", new Color(189, 39, 44)), "LOGIN_GARENA");
        mainContainer.add(createLoginScreen("Google", new Color(234, 67, 53)), "LOGIN_GOOGLE");
        mainContainer.add(createInGameScreen(), "INGAME");

        add(mainContainer);
        playMusic(musicPath);
        setVisible(true);
    }

    // Tự động tìm file trong thư mục "Ảnh Monopoly"
    private String autoFindImagePath(String keyword) {
        File dir = new File("Ảnh Monopoly");
        if (dir.exists() && dir.isDirectory()) {
            File[] matches = dir.listFiles((d, name) -> name.toLowerCase().contains(keyword.toLowerCase()) &&
                    (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")));
            if (matches != null && matches.length > 0) {
                return "Ảnh Monopoly/" + matches[0].getName();
            }
        }
        return null;
    }

    private JPanel createMainMenu() {
        StaticBackgroundPanel panel = new StaticBackgroundPanel(bgImagePath);
        panel.setLayout(null);

        int btnWidth = 320;
        int centerX = (1300 - btnWidth) / 2;

        panel.add(setupBtn("GUEST LOGIN", new Color(30, 30, 30, 230), "guest", centerX, 580, 55, true));
        panel.add(setupBtn("FACEBOOK LOGIN", new Color(66, 103, 178), "facebook", centerX, 645, 45, false));
        panel.add(setupBtn("GARENA LOGIN", new Color(189, 39, 44), "garena", centerX, 700, 45, false));
        panel.add(setupBtn("GOOGLE LOGIN", new Color(70, 70, 70), "google", centerX, 755, 45, false));

        return panel;
    }

    private GameStyledButton setupBtn(String text, Color bg, String key, int x, int y, int h, boolean isGuest) {
        GameStyledButton btn = new GameStyledButton(text, bg, autoFindImagePath(key));
        btn.setBounds(x, y, 320, h);
        btn.addActionListener(e -> {
            playClickSound();
            if (isGuest)
                enterGame();
            else
                cardLayout.show(mainContainer, "LOGIN_" + key.toUpperCase().replace("FACEBOOK", "FB"));
        });
        return btn;
    }

    // Class Button được tinh chỉnh để Icon lấp đầy và đẹp
    class GameStyledButton extends JButton {
        public GameStyledButton(String text, Color bg, String foundPath) {
            super(text);
            setBackground(bg);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setFont(new Font("Arial", Font.BOLD, 14));
            setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

            // Xử lý lỗi trong suốt hoặc mất màu nền trên các OS khác nhau (do Look & Feel)
            setContentAreaFilled(false);
            setOpaque(false);

            if (foundPath != null) {
                ImageIcon icon = new ImageIcon(foundPath);
                // Tăng scale lên 35x35 để icon to và rõ nét
                Image img = icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(img));

                setHorizontalAlignment(SwingConstants.CENTER);
                setIconTextGap(15);
            } else {
                setHorizontalAlignment(SwingConstants.CENTER);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Tự vẽ màu nền cho button để đảm bảo cross-platform và hỗ trợ màu trong suốt
            // (alpha)
            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private JPanel createLoginScreen(String platform, Color themeColor) {
        String imagePath = autoFindImagePath(platform.toLowerCase() + "_bg");
        if (imagePath == null)
            imagePath = bgImagePath;

        StaticBackgroundPanel p = new StaticBackgroundPanel(imagePath);
        p.setLayout(new GridBagLayout());
        JPanel loginBox = new JPanel(new GridBagLayout());
        loginBox.setBackground(new Color(255, 255, 255, 230));
        loginBox.setBorder(new EmptyBorder(25, 30, 25, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel(platform.toUpperCase() + " LOGIN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 30));
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

        GameStyledButton btnLogin = new GameStyledButton("ĐĂNG NHẬP", themeColor, null);
        btnLogin.addActionListener(e -> {
            playClickSound();
            enterGame();
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginBox.add(btnLogin, gbc);

        JButton btnBack = new JButton("Quay lại Menu");
        btnBack.addActionListener(e -> {
            playClickSound();
            cardLayout.show(mainContainer, "MENU");
        });
        gbc.gridy = 4;
        loginBox.add(btnBack, gbc);

        p.add(loginBox);
        return p;
    }

    private JPanel createInGameScreen() {
        JPanel mainGame = new JPanel(new BorderLayout());
        lblTimer = new JLabel("TIME: 00:00", SwingConstants.CENTER);
        lblTimer.setFont(new Font("Arial", Font.BOLD, 24));
        lblTimer.setOpaque(true);
        lblTimer.setBackground(Color.WHITE);
        mainGame.add(lblTimer, BorderLayout.NORTH);
        boardPanel = new BoardPanel();
        mainGame.add(boardPanel, BorderLayout.CENTER);
        mainGame.add(createSideMenu(), BorderLayout.WEST);
        mainGame.add(createInfoPanel(), BorderLayout.EAST);
        return mainGame;
    }

    private JPanel createSideMenu() {
        JPanel menu = new JPanel(new GridLayout(6, 1, 10, 10));
        menu.setBackground(new Color(45, 45, 45));
        menu.setBorder(new EmptyBorder(20, 10, 20, 10));
        JButton btnPause = new JButton("PAUSE");
        btnPause.addActionListener(e -> {
            playClickSound();
            gameManager.setPaused(!gameManager.isPaused());
            btnPause.setText(gameManager.isPaused() ? "RESUME" : "PAUSE");
        });
        GameStyledButton btnExit = new GameStyledButton("EXIT GAME", new Color(180, 0, 0), null);
        btnExit.addActionListener(e -> System.exit(0));
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
        JPanel diceArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                g.drawRoundRect(30, 20, 70, 70, 10, 10);
                g.drawString(String.valueOf(d1), 55, 65);
                g.drawRoundRect(120, 20, 70, 70, 10, 10);
                g.drawString(String.valueOf(d2), 145, 65);
            }
        };
        diceArea.setPreferredSize(new Dimension(220, 120));
        txtPlayerInfo = new JTextArea();
        txtPlayerInfo.setEditable(false);
        txtPlayerInfo.setBorder(BorderFactory.createTitledBorder("THÔNG TIN NGƯỜI CHƠI"));
        rightPanel.add(diceArea, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(txtPlayerInfo), BorderLayout.CENTER);
        return rightPanel;
    }

    // --- CÁC HÀM QUAN TRỌNG ĐỂ MANAGER HẾT BÁO LỖI ---
    public void rollDiceAnimation(int finalD1, int finalD2) {
        Timer timer = new Timer(60, null);
        final int[] count = { 0 };
        timer.addActionListener(e -> {
            d1 = (int) (Math.random() * 6) + 1;
            d2 = (int) (Math.random() * 6) + 1;
            repaint();
            if (count[0]++ > 15) {
                timer.stop();
                d1 = finalD1;
                d2 = finalD2;
                repaint();
            }
        });
        timer.start();
    }

    public void refreshBoard() {
        if (boardPanel != null)
            boardPanel.repaint();
        updatePlayerInfo();
    }

    private void updatePlayerInfo() {
        StringBuilder sb = new StringBuilder();
        if (gameManager.getPlayers() != null) {
            for (Player p : gameManager.getPlayers()) {
                sb.append("👤 ").append(p.getName()).append("\n");
                sb.append("💰 $").append(p.getMoney()).append("\n");
                sb.append("📍 Vị trí: ").append(p.getPosition()).append("\n");
                sb.append("--------------------------\n");
            }
        }
        txtPlayerInfo.setText(sb.toString());
    }
    // ------------------------------------------------

    private void startTimer() {
        Timer timer = new Timer(1000, e -> {
            if (!gameManager.isPaused() && secondsLeft > 0) {
                secondsLeft--;
                lblTimer.setText(String.format("TIME: %02d:%02d", secondsLeft / 60, secondsLeft % 60));
            }
        });
        timer.start();
    }

    private void enterGame() {
        Object[] options = { "30 Phút", "60 Phút" };
        int timeChoice = JOptionPane.showOptionDialog(this, "Chọn thời gian chơi", "Game Setup",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        secondsLeft = (timeChoice == 1) ? 3600 : 1800;
        String input = JOptionPane.showInputDialog(this, "Số người chơi (2-4):", "2");
        if (input != null) {
            try {
                int numPlayers = Integer.parseInt(input);
                if (numPlayers < 2 || numPlayers > 4) {
                    JOptionPane.showMessageDialog(this, "Số người chơi phải từ 2 đến 4!");
                    return;
                }
                gameManager.setupPlayers(numPlayers);
                cardLayout.show(mainContainer, "INGAME");
                startTimer();
                refreshBoard();
                new Thread(() -> gameManager.startGame(this)).start();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!");
            }
        }
    }

    class BoardPanel extends JPanel {
        private Image bgImage;

        public BoardPanel() {
            String path = null;
            if (new File("INGAME.png").exists()) {
                path = "INGAME.png";
            } else {
                path = autoFindImagePath("INGAME.png");
            }

            if (path == null)
                path = bgImagePath; // Dùng ảnh nền mặc định nếu không có board_bg
            bgImage = new ImageIcon(path).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bgImage != null) {
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
            Graphics2D g2 = (Graphics2D) g;
            int bSize = Math.min(getWidth(), getHeight()) - 40;
            int offX = (getWidth() - bSize) / 2;
            int offY = (getHeight() - bSize) / 2;
            double c = bSize * 0.135;
            double s = (bSize - 2 * c) / 9;
            java.util.List<Tile> allTiles = null;
            if (gameManager != null && gameManager.getBoard() != null) {
                allTiles = gameManager.getBoard().getAllTiles();
            }
            g2.setFont(new Font("Arial", Font.BOLD, 10));
            FontMetrics fm = g2.getFontMetrics();

            for (int i = 0; i < 40; i++) {
                Rectangle r = getPreciseRect(i, bSize, offX, offY, c, s);
                g2.setColor(Color.WHITE);
                g2.fill(r);
                g2.setColor(Color.BLACK);
                g2.draw(r);

                if (allTiles != null && i < allTiles.size()) {
                    Tile t = allTiles.get(i);
                    g2.setColor(Color.BLACK);

                    String[] words = t.getName().split(" ");
                    int yText = r.y + 15;
                    for (String word : words) {
                        int strWidth = fm.stringWidth(word);
                        g2.drawString(word, r.x + (r.width - strWidth) / 2, yText);
                        yText += 12;
                    }
                    if (t.getPrice() > 0) {
                        String priceStr = "$" + t.getPrice();
                        int pWidth = fm.stringWidth(priceStr);
                        g2.setColor(Color.RED);
                        g2.drawString(priceStr, r.x + (r.width - pWidth) / 2, yText + 5);
                        g2.setColor(Color.BLACK);
                    }
                }
            }
            if (gameManager.getPlayers() != null) {
                for (Player p : gameManager.getPlayers()) {
                    Rectangle r = getPreciseRect(p.getPosition(), bSize, offX, offY, c, s);
                    g2.setColor(p.getColor());
                    g2.fillOval(r.x + 10, r.y + 10, 28, 28);
                    g2.setColor(Color.BLACK);
                    g2.drawOval(r.x + 10, r.y + 10, 28, 28);
                }
            }
        }

        private Rectangle getPreciseRect(int i, int bSize, int ox, int oy, double c, double s) {
            double x, y, w, h;
            if (i <= 10) {
                y = oy + bSize - c;
                h = c;
                x = (i == 10) ? ox : (i == 0) ? ox + bSize - c : ox + bSize - c - (i * s);
                w = (i == 0 || i == 10) ? c : s;
            } else if (i <= 20) {
                x = ox;
                w = c;
                y = (i == 20) ? oy : oy + bSize - c - ((i - 10) * s);
                h = (i == 20) ? c : s;
            } else if (i <= 30) {
                y = oy;
                h = c;
                x = (i == 30) ? ox + bSize - c : ox + c + ((i - 21) * s);
                w = (i == 30) ? c : s;
            } else {
                x = ox + bSize - c;
                w = c;
                y = oy + c + ((i - 31) * s);
                h = s;
            }
            return new Rectangle((int) x, (int) y, (int) Math.ceil(w), (int) Math.ceil(h));
        }
    }

    private void playMusic(String path) {
        try {
            File file = new File(path);
            if (!file.exists())
                return;
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playClickSound() {
        try {
            File file = new File(clickSoundPath);
            if (!file.exists())
                return;
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class StaticBackgroundPanel extends JPanel {
        private Image img;

        public StaticBackgroundPanel(String path) {
            if (path != null)
                img = new ImageIcon(path).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img != null)
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MonopolyWyckoff::new);
    }
}