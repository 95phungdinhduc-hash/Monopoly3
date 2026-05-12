import java.awt.Graphics;
import java.awt.Image;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class MonopolyWyckoff$StaticBackgroundPanel extends JPanel {
   private Image img;
   // $FF: synthetic field
   final MonopolyWyckoff this$0;

   public MonopolyWyckoff$StaticBackgroundPanel(final MonopolyWyckoff this$0, String path) {
      Objects.requireNonNull(this$0);
      this.this$0 = this$0;
      super();
      if (path != null) {
         this.img = (new ImageIcon(path)).getImage();
      }

   }

   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (this.img != null) {
         g.drawImage(this.img, 0, 0, this.getWidth(), this.getHeight(), this);
      }

   }
}
