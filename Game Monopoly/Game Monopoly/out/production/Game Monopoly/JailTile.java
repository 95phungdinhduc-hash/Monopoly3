import java.awt.Component;
import javax.swing.JOptionPane;

public class JailTile extends Tile {
   public JailTile(String name) {
      super(name);
   }

   public void landOn(Player player, GameManager game) {
      JOptionPane.showMessageDialog((Component) null, "⛓️ Bạn đang thăm tù tại ô: " + this.getName());
   }
}
