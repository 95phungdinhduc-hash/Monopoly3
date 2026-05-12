import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

class MonopolyWyckoff$GameStyledButton extends JButton {
   // $FF: synthetic field
   final MonopolyWyckoff this$0;

   public MonopolyWyckoff$GameStyledButton(final MonopolyWyckoff this$0, String text, Color bg, String foundPath) {
      Objects.requireNonNull(this$0);
      this.this$0 = this$0;
      super(text);
      this.setBackground(bg);
      this.setForeground(Color.WHITE);
      this.setFocusPainted(false);
      this.setFont(new Font("Arial", 1, 14));
      this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
      if (foundPath != null) {
         ImageIcon icon = new ImageIcon(foundPath);
         Image img = icon.getImage().getScaledInstance(35, 35, 4);
         this.setIcon(new ImageIcon(img));
         this.setHorizontalAlignment(0);
         this.setIconTextGap(15);
      } else {
         this.setHorizontalAlignment(0);
      }

   }
}
