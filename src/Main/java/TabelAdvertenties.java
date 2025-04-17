package Main.java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.Vector;

public class TabelAdvertenties extends JFrame{
    Database database = new Database();
    Connection con = database.connect();
    public TabelAdvertenties() {
        setTitle("Advertentiegegevens");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable table = new JTable();
        add(new JScrollPane(table));

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM advertenties")
        ) {
            DefaultTableModel model = buildTableModel(rs);
            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fout bij laden van data:\n" + e.getMessage());
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new OptieScherm(); // open ander venster voordat het sluit
            }
        });

        setVisible(true);
    }

    public TabelAdvertenties(ResultSet rs) {
        setTitle("Advertentiegegevens");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable table = new JTable();
        add(new JScrollPane(table));

        try  {
            DefaultTableModel model = buildTableModel(rs);
            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Fout bij laden van data:\n" + e.getMessage());
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new OptieScherm(); // open ander venster voordat het sluit
            }
        });

        setVisible(true);

    }

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> kolommen = new Vector<>();
        int kolomCount = metaData.getColumnCount();

        for (int i = 1; i <= kolomCount; i++) {
            kolommen.add(metaData.getColumnName(i));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> rij = new Vector<>();
            for (int i = 1; i <= kolomCount; i++) {
                rij.add(rs.getObject(i));
            }
            data.add(rij);
        }

        return new DefaultTableModel(data, kolommen);
    }

}
