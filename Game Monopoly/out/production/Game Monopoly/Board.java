import java.util.ArrayList;
import java.util.List;

public class Board {
   private List<Tile> tiles = new ArrayList();

   public Board() {
      this.tiles.add(new GoTile("Bắt Đầu"));
      this.tiles.add(new PropertyTile("Hà Giang", 60));
      this.tiles.add(new ChanceTile("Khí Vận"));
      this.tiles.add(new PropertyTile("Cao Bằng", 60));
      this.tiles.add(new TaxTile("Thuế Thu Nhập"));
      this.tiles.add(new PropertyTile("Bến Xe Miền Tây", 200));
      this.tiles.add(new PropertyTile("Lào Cai", 100));
      this.tiles.add(new ChanceTile("Cơ Hội"));
      this.tiles.add(new PropertyTile("Hòa Bình", 100));
      this.tiles.add(new PropertyTile("Ninh Bình", 120));
      this.tiles.add(new JailTile("Ô Tù"));
      this.tiles.add(new PropertyTile("Quảng Ninh", 140));
      this.tiles.add(new PropertyTile("Công Ty Điện", 150));
      this.tiles.add(new PropertyTile("Hà Nội", 140));
      this.tiles.add(new PropertyTile("Bãi Biển Sầm Sơn", 160));
      this.tiles.add(new PropertyTile("Bến Xe Giáp Bát", 200));
      this.tiles.add(new PropertyTile("Quảng Bình", 180));
      this.tiles.add(new ChanceTile("Khí Vận"));
      this.tiles.add(new PropertyTile("Huế", 180));
      this.tiles.add(new PropertyTile("Đà Nẵng", 200));
      this.tiles.add(new ChanceTile("World Cup"));
      this.tiles.add(new PropertyTile("Quảng Nam", 220));
      this.tiles.add(new ChanceTile("Cơ Hội"));
      this.tiles.add(new PropertyTile("Quy Nhơn", 220));
      this.tiles.add(new PropertyTile("Phú Yên", 240));
      this.tiles.add(new PropertyTile("Bến Xe Đà Nẵng", 200));
      this.tiles.add(new PropertyTile("Khánh Hòa", 260));
      this.tiles.add(new PropertyTile("Đà Lạt", 260));
      this.tiles.add(new PropertyTile("Công Ty Nước", 150));
      this.tiles.add(new PropertyTile("Bãi Biển Nha Trang", 280));
      this.tiles.add(new ChanceTile("Máy Bay"));
      this.tiles.add(new PropertyTile("Phú Quốc", 300));
      this.tiles.add(new PropertyTile("Bình Thuận", 300));
      this.tiles.add(new ChanceTile("Bãi Biển Mũi Né"));
      this.tiles.add(new PropertyTile("Bến Xe Miền Đông", 200));
      this.tiles.add(new PropertyTile("Vũng Tàu", 350));
      this.tiles.add(new ChanceTile("Bãi Biển Vũng Tàu"));
      this.tiles.add(new TaxTile("Thuế Đặc Biệt"));
      this.tiles.add(new PropertyTile("Tây Ninh", 400));
      this.tiles.add(new PropertyTile("TP.HCM", 400));
   }

   public Tile getTile(int index) {
      return this.tiles.isEmpty() ? null : (Tile) this.tiles.get(Math.abs(index) % this.tiles.size());
   }

   public List<Tile> getAllTiles() {
      return this.tiles;
   }
}
