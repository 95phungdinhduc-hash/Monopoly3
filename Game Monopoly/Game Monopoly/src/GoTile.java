import javax.swing.JOptionPane;

public class GoTile extends Tile {

    public GoTile(String name) {
        super(name); // BẮT BUỘC: Gọi constructor của lớp cha Tile
    }

    @Override
    public void landOn(Player player, GameManager game) {
        JOptionPane.showMessageDialog(null,
                "🏁 CHÚC MỪNG!\nBạn đã đi ngang qua điểm xuất phát (GO) và nhận được $200!",
                "Điểm xuất phát",
                JOptionPane.INFORMATION_MESSAGE);

        player.addMoney(200); // Đảm bảo lớp Player dùng hàm addMoney như đã thống nhất
    }
}