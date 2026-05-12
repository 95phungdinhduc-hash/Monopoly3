import java.util.Random;

public class Dice {
   private Random rand = new Random();

   public int roll() {
      return this.rand.nextInt(6) + 1 + this.rand.nextInt(6) + 1;
   }
}
