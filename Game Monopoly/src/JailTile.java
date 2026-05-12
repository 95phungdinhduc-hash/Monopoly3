import javax.swing.JOptionPane;

public class JailTile extends Tile {

    public JailTile(String name) {
        super(name);
    }

    @Override
    public void landOn(Player player, GameManager game) {
        JOptionPane.showMessageDialog(null, "⛓️ Bạn đang thăm tù tại ô: " + getName());
    }
}