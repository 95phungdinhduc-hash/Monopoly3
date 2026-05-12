import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
   private List<Player> players = new ArrayList();
   private Dice dice = new Dice();
   private Board board = new Board();
   private boolean isPaused = false;

   public void setPaused(boolean paused) {
      this.isPaused = paused;
   }

   public boolean isPaused() {
      return this.isPaused;
   }

   public void setupPlayers(int num) {
      this.players.clear();
      Color[] colors = new Color[] { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW };

      for (int i = 1; i <= num; ++i) {
         String imagePath = "assets/chars/p" + i + ".png";
         this.players.add(new Player("Người chơi " + i, colors[(i - 1) % colors.length], imagePath));
      }

   }

   public void startGame(MonopolyWyckoff ui) {
      while (true) {
         for (Player p : this.players) {
            this.checkPause();
            int d1 = (int) (Math.random() * (double) 6.0F) + 1;
            int d2 = (int) (Math.random() * (double) 6.0F) + 1;
            int totalRoll = d1 + d2;
            ui.rollDiceAnimation(d1, d2);
            this.sleep(1200);

            for (int i = 0; i < totalRoll; ++i) {
               this.checkPause();
               p.move(1);
               ui.refreshBoard();
               this.sleep(300);
            }

            Tile currentTile = this.board.getTile(p.getPosition());
            currentTile.landOn(p, this);
            ui.refreshBoard();
            this.sleep(800);
         }
      }
   }

   private void checkPause() {
      while (this.isPaused) {
         try {
            Thread.sleep(200L);
         } catch (InterruptedException var2) {
            Thread.currentThread().interrupt();
         }
      }

   }

   private void sleep(int ms) {
      try {
         Thread.sleep((long) ms);
      } catch (Exception var3) {
      }

   }

   public Board getBoard() {
      return this.board;
   }

   public List<Player> getPlayers() {
      return this.players;
   }
}
