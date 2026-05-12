import javax.swing.JOptionPane;
import java.util.Random;

public class ChanceTile extends Tile {
    private Random rand = new Random();

    public ChanceTile(String name) {
        // Gọi constructor 1 tham số của lớp cha Tile
        super(name);
    }

    @Override
    public void landOn(Player player, GameManager game) {
        int r = rand.nextInt(3); // Ngẫu nhiên 0, 1, hoặc 2
        String msg = "";

        if (r == 0) {
            msg = "🎁 May mắn: Nhận được $200 từ ngân hàng!";
            // Sử dụng receive để đồng bộ với logic cộng tiền của hệ thống
            player.receive(200);
        } else if (r == 1) {
            msg = "💸 Xui xẻo: Đánh rơi $100!";
            player.pay(100);
        } else {
            msg = "🏃 Tiến lên thêm 3 bước!";
            player.move(3);
            // Sau khi tiến 3 bước, cần kích hoạt ô mới mà người chơi đặt chân đến
            // Điều này giúp game chạy liên tục đúng luật
            int newPos = player.getPosition();
            game.getBoard().getTile(newPos).landOn(player, game);
            return; // Thoát hàm để không hiện 2 thông báo cùng lúc
        }

        JOptionPane.showMessageDialog(null, msg, "Cơ hội", JOptionPane.INFORMATION_MESSAGE);
    }
}