import java.awt.Component;
import javax.swing.JOptionPane;

public class PropertyTile extends Tile {
   public PropertyTile(String name, int price) {
      super(name, price, price / 2);
   }

   public void landOn(Player player, GameManager game) {
      if (this.owner == null) {
         int response = JOptionPane.showConfirmDialog((Component) null,
               "Bạn đang ở " + this.getName() + " (Giá: $" + this.price + "). Bạn có muốn mua không?", "Mua tài sản",
               0);
         if (response == 0) {
            if (player.getMoney() >= this.price) {
               player.pay(this.price);
               this.owner = player;
               player.addProperty(this);
               JOptionPane.showMessageDialog((Component) null, "Chúc mừng! Bạn đã sở hữu " + this.getName());
            } else {
               JOptionPane.showMessageDialog((Component) null, "Bạn không đủ tiền!");
            }
         }
      } else if (this.owner != player) {
         String var10001 = this.owner.getName();
         JOptionPane.showMessageDialog((Component) null,
               "Bạn đang ở đất của " + var10001 + ". Phí thuê: $" + this.rent);
         player.pay(this.rent);
         this.owner.receive(this.rent);
      }

   }
}
