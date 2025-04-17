package Main.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class LoginScherm extends JFrame {
    private final JTextField gebruikersnaamVeld;
    private final JPasswordField wachtwoordVeld;
    Database database = new Database();
    Connection con = database.connect();
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("select * from gebruikers");

    public LoginScherm() throws SQLException {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel gebruikerLabel = new JLabel("Username:");
        gebruikerLabel.setBounds(30, 30, 120, 25);
        add(gebruikerLabel);

        gebruikersnaamVeld = new JTextField();
        gebruikersnaamVeld.setBounds(160, 30, 180, 25);
        add(gebruikersnaamVeld);

        JLabel wachtwoordLabel = new JLabel("Wachtwoord:");
        wachtwoordLabel.setBounds(30, 70, 120, 25);
        add(wachtwoordLabel);

        wachtwoordVeld = new JPasswordField();
        wachtwoordVeld.setBounds(160, 70, 180, 25);
        add(wachtwoordVeld);

        JButton loginKnop = new JButton("Inloggen");
        loginKnop.setBounds(160, 110, 100, 30);
        add(loginKnop);

        loginKnop.addActionListener((ActionEvent _) -> {
            String gebruiker = gebruikersnaamVeld.getText();
            String wachtwoord = new String(wachtwoordVeld.getPassword());
            String username;
            String password;
            boolean ingelogd = false;
            while(true) {
                try {
                    if (!rs.next()){
                        break;
                    }
                    username = rs.getString("username");
                    password = rs.getString("password");

                    if (gebruiker.equals(username) && wachtwoord.equals(password)) {
                        ingelogd = true;
                        dispose();
                        if (rs.getInt("owner") == 1) {
                            SwingUtilities.invokeLater(OwnerOptieScherm::new);
                        } else {
                            SwingUtilities.invokeLater(OptieScherm::new);
                        }
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
            if (!ingelogd) {
                JOptionPane.showMessageDialog(this, "Incorrecte username/wachtwoord");
            }
        });

        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginScherm();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}