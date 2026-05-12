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
    // THAY ĐỔI: Chuyển từ List<String> sang List<Tile> để quản lý chuyên sâu hơn
    private List<Tile> ownedProperties = new ArrayList<>();

    public Player(String name, Color color, String imagePath) {
        this.name = name;
        this.color = color;
        try {
            // Load ảnh nhân vật
            this.characterImage = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            System.out.println("Không tìm thấy ảnh nhân vật cho: " + name);
        }
    }

    // --- LOGIC GIỮ NGUYÊN ---
    public void move(int steps) {
        this.position = (this.position + steps) % 40;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public Color getColor() {
        return color;
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

    // --- SỬA ĐỂ HẾT LỖI GẠCH ĐỎ ---

    // Nhận vào đối tượng Tile thay vì String
    public void addProperty(Tile tile) {
        this.ownedProperties.add(tile);
    }

    // Trả về List<Tile> để file MonopolyWyckoff có thể lấy tên, giá... của đất
    public List<Tile> getOwnedProperties() {
        return ownedProperties;
    }

    public Image getCharacterImage() {
        return characterImage;
    }
}