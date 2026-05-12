public abstract class Tile {
   protected String name;
   protected int price;
   protected int rent;
   protected Player owner = null;

   public Tile(String name) {
      this.name = name;
      this.price = 0;
      this.rent = 0;
   }

   public Tile(String name, int price, int rent) {
      this.name = name;
      this.price = price;
      this.rent = rent;
   }

   public String getName() {
      return this.name;
   }

   public Player getOwner() {
      return this.owner;
   }

   public void setOwner(Player owner) {
      this.owner = owner;
   }

   public int getPrice() {
      return this.price;
   }

   public int getRent() {
      return this.rent;
   }

   public abstract void landOn(Player var1, GameManager var2);
}
