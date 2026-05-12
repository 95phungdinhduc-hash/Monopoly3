import javax.swing.JOptionPane;

public class TaxTile extends Tile {

    public TaxTile(String name) {
        super(name);
    }

    @Override
    public void landOn(Player player, GameManager game) {
        int taxAmount = 200; // Số tiền thuế cố định
        JOptionPane.showMessageDialog(null,
                "⚠️ " + getName() + "\nBạn phải nộp thuế cho nhà nước: $" + taxAmount,
                "Nộp thuế",
                JOptionPane.WARNING_MESSAGE);

        player.pay(taxAmount);
    }
}