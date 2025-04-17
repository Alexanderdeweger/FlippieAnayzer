package Main.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountAanmaken extends JFrame{
    private final JTextField nieuwGebruikersnaamVeld;
    private final JPasswordField nieuwWachtwoordVeld;
    private final JPasswordField confirmNieuwWachtwoordVeld;

    public AccountAanmaken(){
        setTitle("Account aanmaken");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titel = new JLabel("Vul nieuwe gebruikers informatie in");
        titel.setBounds(70, 10, 300, 25);
        add(titel);

        JLabel gebruikerLabel = new JLabel("Username:");
        gebruikerLabel.setBounds(30, 30, 120, 25);
        add(gebruikerLabel);

        nieuwGebruikersnaamVeld = new JTextField();
        nieuwGebruikersnaamVeld.setBounds(160, 30, 180, 25);
        add(nieuwGebruikersnaamVeld);

        JLabel wachtwoordLabel = new JLabel("Wachtwoord:");
        wachtwoordLabel.setBounds(30, 70, 120, 25);
        add(wachtwoordLabel);

        nieuwWachtwoordVeld = new JPasswordField();
        nieuwWachtwoordVeld.setBounds(160, 70, 180, 25);
        add(nieuwWachtwoordVeld);

        JLabel confirmWachtwoordLabel = new JLabel("Herhaal wachtwoord");
        confirmWachtwoordLabel.setBounds(30, 110, 120, 25);
        add(confirmWachtwoordLabel);

        confirmNieuwWachtwoordVeld = new JPasswordField();
        confirmNieuwWachtwoordVeld.setBounds(160, 110, 180, 25);
        add(confirmNieuwWachtwoordVeld);

        JButton confirmKnop = new JButton("Maak account");
        confirmKnop.setBounds(140, 225, 140, 30);
        add(confirmKnop);

        confirmKnop.addActionListener((ActionEvent _) -> {
            String username = nieuwGebruikersnaamVeld.getText();
            String wachtwoord1 = new String(nieuwWachtwoordVeld.getPassword());
            String wachtwoord2 = new String(confirmNieuwWachtwoordVeld.getPassword());
            if (!username.isEmpty() && !wachtwoord1.isEmpty() && !wachtwoord2.isEmpty()) {

                if (!wachtwoord1.equals(wachtwoord2)) {
                    JOptionPane.showMessageDialog(this, "Wachtwoorden kmen niet overeen");
                } else {
                    Database database = new Database();
                    Connection con = database.connect();
                    String SQL = "INSERT INTO `flippielyzer`.`gebruikers` (`username`, `password`) VALUES (\"" + username + "\", \"" + wachtwoord1 + "\");";
                    try {
                        Statement stmt = con.createStatement();
                        stmt.execute(SQL);
                        JOptionPane.showMessageDialog(this, "Account succesvol aangemaakt");
                        dispose();
                        SwingUtilities.invokeLater(OwnerOptieScherm::new);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Kon niet met de database connecten");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Geen velden leeglaten :D");
            }
        });

        setVisible(true);
    }
}
