import java.awt.Component;
import java.util.Random;
import javax.swing.JOptionPane;

public class ChanceTile extends Tile {
   private Random rand = new Random();

   public ChanceTile(String name) {
      super(name);
   }

   public void landOn(Player player, GameManager game) {
      int r = this.rand.nextInt(3);
      String msg = "";
      if (r == 0) {
         msg = "\ud83c\udf81 May mắn: Nhận được $200 từ ngân hàng!";
         player.receive(200);
      } else {
         if (r != 1) {
            msg = "\ud83c\udfc3 Tiến lên thêm 3 bước!";
            player.move(3);
            int newPos = player.getPosition();
            game.getBoard().getTile(newPos).landOn(player, game);
            return;
         }

         msg = "\ud83d\udcb8 Xui xẻo: Đánh rơi $100!";
         player.pay(100);
      }

      JOptionPane.showMessageDialog((Component) null, msg, "Cơ hội", 1);
   }
}
