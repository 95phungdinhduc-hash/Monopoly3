import javax.swing.JOptionPane;
import java.util.*;
import java.awt.Color;

public class GameManager {
    private List<Player> players = new ArrayList<>();
    private Dice dice = new Dice();
    private Board board = new Board();
    private boolean isPaused = false;

    // --- QUẢN LÝ TRẠNG THÁI TẠM DỪNG (YÊU CẦU 4) ---
    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setupPlayers(int num) {
        players.clear();
        Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW };
        for (int i = 1; i <= num; i++) {
            // Lưu ý: Hãy đảm bảo thư mục assets nằm cùng cấp với file src
            String imagePath = "assets/chars/p" + i + ".png";
            players.add(new Player("Người chơi " + i, colors[(i - 1) % colors.length], imagePath));
        }
    }

    public void startGame(MonopolyWyckoff ui) {
        while (true) {
            for (Player p : players) {
                // 1. KIỂM TRA TẠM DỪNG (Nếu bấm PAUSE, vòng lặp dừng tại đây)
                checkPause();

                // 2. XÚC XẮC (YÊU CẦU 2): Lấy số và gọi hiệu ứng bên UI
                int d1 = (int) (Math.random() * 6) + 1;
                int d2 = (int) (Math.random() * 6) + 1;
                int totalRoll = d1 + d2;

                ui.rollDiceAnimation(d1, d2);

                // Đợi 1.2 giây cho xúc xắc quay xong rồi mới đi
                sleep(1200);

                // 3. DI CHUYỂN TỪNG BƯỚC (YÊU CẦU 3)
                for (int i = 0; i < totalRoll; i++) {
                    checkPause(); // Kiểm tra pause ngay cả khi đang đi nửa chừng

                    p.move(1);
                    ui.refreshBoard(); // Cập nhật lại hình ảnh trên bàn cờ liên tục
                    sleep(300); // Tốc độ di chuyển (300ms/ô)
                }

                // 4. LOGIC Ô ĐẤT (Mua bán, trả tiền...)
                Tile currentTile = board.getTile(p.getPosition());
                currentTile.landOn(p, this);

                ui.refreshBoard(); // Cập nhật tiền/đất sau khi thực hiện hành động

                // Nghỉ một chút trước khi tới lượt người tiếp theo
                sleep(800);
            }
        }
    }

    // Hàm hỗ trợ kiểm tra tạm dừng để tránh viết lặp lại code
    private void checkPause() {
        while (isPaused) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Hàm hỗ trợ nghỉ (sleep) để code gọn hơn
    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }
}