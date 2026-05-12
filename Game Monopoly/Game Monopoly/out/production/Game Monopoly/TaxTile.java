import java.awt.Component;
import javax.swing.JOptionPane;

public class TaxTile extends Tile {
   public TaxTile(String name) {
      super(name);
   }

   public void landOn(Player player, GameManager game) {
      int taxAmount = 200;
      JOptionPane.showMessageDialog((Component) null,
            "⚠️ " + this.getName() + "\nBạn phải nộp thuế cho nhà nước: $" + taxAmount, "Nộp thuế", 2);
      player.pay(taxAmount);
   }
}
