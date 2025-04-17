package Main.java;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;


public class OwnerOptieScherm extends OptieScherm{

    public OwnerOptieScherm() {
        super();
        setSize(800, 400);

        JButton refreshen = new JButton("Refreshen");
        refreshen.setBounds(600, 70, 120, 25);
        add(refreshen);

        JButton accountMaken = new JButton("Nieuw account creeren");
        accountMaken.setBounds(540, 30, 200, 25);
        add(accountMaken);

        refreshen.addActionListener((ActionEvent _) -> {
            try {
                Eigenaar eigenaar = new Eigenaar();
                eigenaar.refreshAds();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Kon niet connecten met de database");
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Er ging iets verkeerd met de lijst importen");
            }
            JOptionPane.showMessageDialog(this, "Succescol lijst gerefreshed");

        });


        accountMaken.addActionListener((ActionEvent _) -> {
            dispose();
            SwingUtilities.invokeLater(AccountAanmaken::new);
        });
    }

}
