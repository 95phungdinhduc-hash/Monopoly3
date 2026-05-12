import javax.swing.JOptionPane;

public abstract class Tile {
    // Chuyển sang protected để các lớp con (PropertyTile, ChanceTile...) có thể
    // truy cập trực tiếp
    protected String name;
    protected int price;
    protected int rent;
    protected Player owner = null;

    // Constructor 1: Dùng cho các ô đặc biệt (Go, Jail, Chance, Tax)
    public Tile(String name) {
        this.name = name;
        this.price = 0;
        this.rent = 0;
    }

    // Constructor 2: Dùng cho các ô đất có thể mua (PropertyTile)
    public Tile(String name, int price, int rent) {
        this.name = name;
        this.price = price;
        this.rent = rent;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

    public int getRent() {
        return rent;
    }

    // Hàm bắt buộc các lớp con phải ghi đè logic
    public abstract void landOn(Player player, GameManager game);
}