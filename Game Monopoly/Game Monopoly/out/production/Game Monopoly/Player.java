import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public class Player {
   private String name;
   private int position = 0;
   private int money = 1500;
   private Color color;
   private Image characterImage;
   private List<Tile> ownedProperties = new ArrayList();

   public Player(String name, Color color, String imagePath) {
      this.name = name;
      this.color = color;

      try {
         this.characterImage = (new ImageIcon(imagePath)).getImage();
      } catch (Exception var5) {
         System.out.println("Không tìm thấy ảnh nhân vật cho: " + name);
      }

   }

   public void move(int steps) {
      this.position = (this.position + steps) % 40;
   }

   public int getPosition() {
      return this.position;
   }

   public String getName() {
      return this.name;
   }

   public int getMoney() {
      return this.money;
   }

   public Color getColor() {
      return this.color;
   }

   public void pay(int amount) {
      this.money -= amount;
   }

   public void addMoney(int amount) {
      this.money += amount;
   }

   public void receive(int amount) {
      this.addMoney(amount);
   }

   public void addProperty(Tile tile) {
      this.ownedProperties.add(tile);
   }

   public List<Tile> getOwnedProperties() {
      return this.ownedProperties;
   }

   public Image getCharacterImage() {
      return this.characterImage;
   }
}
