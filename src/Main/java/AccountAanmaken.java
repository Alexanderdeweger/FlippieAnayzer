package Main.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountAanmaken extends JFrame{
    protected final JTextField nieuwGebruikersnaamVeld;
    protected final JPasswordField nieuwWachtwoordVeld;
    protected final JPasswordField confirmNieuwWachtwoordVeld;
    public JButton confirmKnop;

    public AccountAanmaken(){
        setTitle("Account aanmaken");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        nieuwGebruikersnaamVeld.setName("usernameveld");
        add(nieuwGebruikersnaamVeld);

        JLabel wachtwoordLabel = new JLabel("Wachtwoord:");
        wachtwoordLabel.setBounds(30, 70, 120, 25);
        add(wachtwoordLabel);

        nieuwWachtwoordVeld = new JPasswordField();
        nieuwWachtwoordVeld.setBounds(160, 70, 180, 25);
        nieuwWachtwoordVeld.setName("wachtwoordveld");
        add(nieuwWachtwoordVeld);

        JLabel confirmWachtwoordLabel = new JLabel("Herhaal wachtwoord");
        confirmWachtwoordLabel.setBounds(30, 110, 120, 25);
        add(confirmWachtwoordLabel);

        confirmNieuwWachtwoordVeld = new JPasswordField();
        confirmNieuwWachtwoordVeld.setBounds(160, 110, 180, 25);
        confirmNieuwWachtwoordVeld.setName("confirm wachtwoordveld");
        add(confirmNieuwWachtwoordVeld);

        confirmKnop = new JButton("Maak account");
        confirmKnop.setBounds(225, 225, 140, 30);
        confirmKnop.setName("confirmKnop");
        add(confirmKnop);

        confirmKnop.addActionListener((ActionEvent _) -> {
            String username = nieuwGebruikersnaamVeld.getText();
            String wachtwoord1 = new String(nieuwWachtwoordVeld.getPassword());
            String wachtwoord2 = new String(confirmNieuwWachtwoordVeld.getPassword());
            if (!username.isEmpty() && !wachtwoord1.isEmpty() && !wachtwoord2.isEmpty()) {

                if (!wachtwoord1.equals(wachtwoord2)) {
                    JOptionPane.showMessageDialog(this, "Wachtwoorden komen niet overeen");
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

        JButton terugKnop = new JButton("Optiescherm");
        terugKnop.setBounds(50,225, 150, 30);
        terugKnop.addActionListener(e -> {
            dispose(); // sluit dit venster
            SwingUtilities.invokeLater(() -> new OwnerOptieScherm()); // opent nieuw venster
        });
        add(terugKnop);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new OwnerOptieScherm(); // open ander venster voordat het sluit
            }
        });

        setVisible(true);
    }
}
