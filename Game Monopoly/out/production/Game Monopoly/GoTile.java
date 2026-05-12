import java.awt.Component;
import javax.swing.JOptionPane;

public class GoTile extends Tile {
   public GoTile(String name) {
      super(name);
   }

   public void landOn(Player player, GameManager game) {
      JOptionPane.showMessageDialog((Component) null,
            "\ud83c\udfc1 CHÚC MỪNG!\nBạn đã đi ngang qua điểm xuất phát (GO) và nhận được $200!", "Điểm xuất phát", 1);
      player.addMoney(200);
   }
}
