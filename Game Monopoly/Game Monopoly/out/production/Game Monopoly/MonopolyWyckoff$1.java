import java.awt.Font;
import java.awt.Graphics;
import java.util.Objects;
import javax.swing.JPanel;

class MonopolyWyckoff$1 extends JPanel {
   // $FF: synthetic field
   final MonopolyWyckoff this$0;

   MonopolyWyckoff$1(final MonopolyWyckoff this$0) {
      Objects.requireNonNull(this$0);
      this.this$0 = this$0;
      super();
   }

   private int getD1() {
      try {
         java.lang.reflect.Field field = MonopolyWyckoff.class.getDeclaredField("d1");
         field.setAccessible(true);
         return field.getInt(this.this$0);
      } catch (Exception e) {
         return 0;
      }
   }

   private int getD2() {
      try {
         java.lang.reflect.Field field = MonopolyWyckoff.class.getDeclaredField("d2");
         field.setAccessible(true);
         return field.getInt(this.this$0);
      } catch (Exception e) {
         return 0;
      }
   }

   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setFont(new Font("Arial", 1, 30));
      g.drawRoundRect(30, 20, 70, 70, 10, 10);
      g.drawString(String.valueOf(getD1()), 55, 65);
      g.drawRoundRect(120, 20, 70, 70, 10, 10);
      g.drawString(String.valueOf(getD2()), 145, 65);
   }
}
