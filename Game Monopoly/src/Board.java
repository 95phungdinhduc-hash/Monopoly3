import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Tile> tiles = new ArrayList<>();

    public Board() {
        // CẠNH DƯỚI (0-10)
        tiles.add(new GoTile("Bắt Đầu"));
        tiles.add(new PropertyTile("Hà Giang", 60));
        tiles.add(new ChanceTile("Khí Vận"));
        tiles.add(new PropertyTile("Cao Bằng", 60));
        tiles.add(new TaxTile("Thuế Thu Nhập"));
        tiles.add(new PropertyTile("Bến Xe Miền Tây", 200));
        tiles.add(new PropertyTile("Lào Cai", 100));
        tiles.add(new ChanceTile("Cơ Hội"));
        tiles.add(new PropertyTile("Hòa Bình", 100));
        tiles.add(new PropertyTile("Ninh Bình", 120));
        tiles.add(new JailTile("Ô Tù"));

        // CẠNH TRÁI (11-20)
        tiles.add(new PropertyTile("Quảng Ninh", 140));
        tiles.add(new PropertyTile("Công Ty Điện", 150));
        tiles.add(new PropertyTile("Hà Nội", 140));
        tiles.add(new PropertyTile("Bãi Biển Sầm Sơn", 160));
        tiles.add(new PropertyTile("Bến Xe Giáp Bát", 200));
        tiles.add(new PropertyTile("Quảng Bình", 180));
        tiles.add(new ChanceTile("Khí Vận"));
        tiles.add(new PropertyTile("Huế", 180));
        tiles.add(new PropertyTile("Đà Nẵng", 200));
        // Lưu ý: Nếu bạn chưa tạo class WorldCupTile, hãy tạm để ChanceTile
        tiles.add(new ChanceTile("World Cup"));

        // CẠNH TRÊN (21-30)
        tiles.add(new PropertyTile("Quảng Nam", 220));
        tiles.add(new ChanceTile("Cơ Hội"));
        tiles.add(new PropertyTile("Quy Nhơn", 220));
        tiles.add(new PropertyTile("Phú Yên", 240));
        tiles.add(new PropertyTile("Bến Xe Đà Nẵng", 200));
        tiles.add(new PropertyTile("Khánh Hòa", 260));
        tiles.add(new PropertyTile("Đà Lạt", 260));
        tiles.add(new PropertyTile("Công Ty Nước", 150));
        tiles.add(new PropertyTile("Bãi Biển Nha Trang", 280));
        // Lưu ý: Tương tự World Cup, dùng ChanceTile nếu chưa có class riêng
        tiles.add(new ChanceTile("Máy Bay"));

        // CẠNH PHẢI (31-39)
        tiles.add(new PropertyTile("Phú Quốc", 300));
        tiles.add(new PropertyTile("Bình Thuận", 300));
        tiles.add(new ChanceTile("Bãi Biển Mũi Né"));
        tiles.add(new PropertyTile("Bến Xe Miền Đông", 200));
        tiles.add(new PropertyTile("Vũng Tàu", 350));
        tiles.add(new ChanceTile("Bãi Biển Vũng Tàu"));
        tiles.add(new TaxTile("Thuế Đặc Biệt"));
        tiles.add(new PropertyTile("Tây Ninh", 400));
        tiles.add(new PropertyTile("TP.HCM", 400));
    }

    // Sửa lại hàm này để đảm bảo an toàn, không bị văng lỗi IndexOutOfBounds
    public Tile getTile(int index) {
        if (tiles.isEmpty())
            return null;
        // Dùng phép chia lấy dư % 40 để nếu p.getPosition() có lớn hơn 40 vẫn không lỗi
        return tiles.get(Math.abs(index) % tiles.size());
    }

    // Thêm hàm này để lớp đồ họa biết danh sách ô đất
    public List<Tile> getAllTiles() {
        return tiles;
    }
}