import javax.swing.JOptionPane;

public class PropertyTile extends Tile {

    // KHÔNG khai báo lại private int price, owner... ở đây nữa vì đã có ở Tile.java

    public PropertyTile(String name, int price) {
        // Gọi Constructor 3 tham số của lớp cha Tile(name, price, rent)
        super(name, price, price / 2);
    }

    @Override
    public void landOn(Player player, GameManager game) {
        // Sử dụng trực tiếp các biến price, owner, rent từ lớp cha Tile
        if (owner == null) {
            int response = JOptionPane.showConfirmDialog(null,
                    "Bạn đang ở " + getName() + " (Giá: $" + price + "). Bạn có muốn mua không?",
                    "Mua tài sản", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                if (player.getMoney() >= price) {
                    player.pay(price);
                    this.owner = player; // Gán chủ sở hữu

                    // YÊU CẦU QUAN TRỌNG: Thêm đất vào danh sách của người chơi để hiện ở bảng bên
                    // phải
                    player.addProperty(this);

                    JOptionPane.showMessageDialog(null, "Chúc mừng! Bạn đã sở hữu " + getName());
                } else {
                    JOptionPane.showMessageDialog(null, "Bạn không đủ tiền!");
                }
            }
        } else if (owner != player) {
            // Logic trừ lương/trả tiền thuê như bạn muốn
            JOptionPane.showMessageDialog(null, "Bạn đang ở đất của " + owner.getName() + ". Phí thuê: $" + rent);
            player.pay(rent);
            owner.receive(rent); // Dùng receive cho đồng bộ với các class khác
        }
    }
}