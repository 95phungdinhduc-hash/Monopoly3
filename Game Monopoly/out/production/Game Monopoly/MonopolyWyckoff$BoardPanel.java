import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Objects;
import javax.swing.JPanel;

class MonopolyWyckoff$BoardPanel extends JPanel {
   // $FF: synthetic field
   final MonopolyWyckoff this$0;

   MonopolyWyckoff$BoardPanel(final MonopolyWyckoff this$0) {
      Objects.requireNonNull(this$0);
      this.this$0 = this$0;
      super();
   }

   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      int bSize = Math.min(this.getWidth(), this.getHeight()) - 40;
      int offX = (this.getWidth() - bSize) / 2;
      int offY = (this.getHeight() - bSize) / 2;
      double c = (double) bSize * 0.135;
      double s = ((double) bSize - (double) 2.0F * c) / (double) 9.0F;

      for (int i = 0; i < 40; ++i) {
         Rectangle r = this.getPreciseRect(i, bSize, offX, offY, c, s);
         g2.setColor(Color.WHITE);
         g2.fill(r);
         g2.setColor(Color.BLACK);
         g2.draw(r);
      }

      Object gameManager = this.this$0.gameManager;
      if (gameManager != null) {
         try {
            java.lang.reflect.Method getPlayersMethod = gameManager.getClass().getMethod("getPlayers");
            Object playersObj = getPlayersMethod.invoke(gameManager);
            if (playersObj instanceof Iterable) {
               for (Object playerObj : (Iterable<?>) playersObj) {
                  Player p = (Player) playerObj;
                  Rectangle r = this.getPreciseRect(p.getPosition(), bSize, offX, offY, c, s);
                  g2.setColor(p.getColor());
                  g2.fillOval(r.x + 10, r.y + 10, 28, 28);
                  g2.setColor(Color.BLACK);
                  g2.drawOval(r.x + 10, r.y + 10, 28, 28);
               }
            } else if (playersObj != null && playersObj.getClass().isArray()) {
               int len = java.lang.reflect.Array.getLength(playersObj);
               for (int idx = 0; idx < len; ++idx) {
                  Player p = (Player) java.lang.reflect.Array.get(playersObj, idx);
                  Rectangle r = this.getPreciseRect(p.getPosition(), bSize, offX, offY, c, s);
                  g2.setColor(p.getColor());
                  g2.fillOval(r.x + 10, r.y + 10, 28, 28);
                  g2.setColor(Color.BLACK);
                  g2.drawOval(r.x + 10, r.y + 10, 28, 28);
               }
            }
         } catch (Exception ignored) {
         }
      }

   }

   private Rectangle getPreciseRect(int i, int bSize, int ox, int oy, double c, double s) {
      double x;
      double y;
      double w;
      double h;
      if (i <= 10) {
         y = (double) (oy + bSize) - c;
         h = c;
         x = i == 10 ? (double) ox : (i == 0 ? (double) (ox + bSize) - c : (double) (ox + bSize) - c - (double) i * s);
         w = i != 0 && i != 10 ? s : c;
      } else if (i <= 20) {
         x = (double) ox;
         w = c;
         y = i == 20 ? (double) oy : (double) (oy + bSize) - c - (double) (i - 10) * s;
         h = i == 20 ? c : s;
      } else if (i <= 30) {
         y = (double) oy;
         h = c;
         x = i == 30 ? (double) (ox + bSize) - c : (double) ox + c + (double) (i - 21) * s;
         w = i == 30 ? c : s;
      } else {
         x = (double) (ox + bSize) - c;
         w = c;
         y = (double) oy + c + (double) (i - 31) * s;
         h = s;
      }

      return new Rectangle((int) x, (int) y, (int) Math.ceil(w), (int) Math.ceil(h));
   }
}
