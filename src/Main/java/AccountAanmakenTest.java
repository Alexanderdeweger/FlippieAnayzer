package Main.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


class AccountAanmakenTest {
    private AccountAanmaken accountAanmaken;

    @BeforeEach
    void setUp() {
        accountAanmaken = new AccountAanmaken();
    }

    @Test
    void testAccountAanmakenButtonClick() throws SQLException {
        accountAanmaken.nieuwGebruikersnaamVeld.setText("testuser");
        accountAanmaken.nieuwWachtwoordVeld.setText("password123");
        accountAanmaken.confirmNieuwWachtwoordVeld.setText("password123");

        JButton confirmKnop = accountAanmaken.confirmKnop;
        confirmKnop.doClick();


        Database database = new Database();
        Statement stmt = database.connect().createStatement();
        var resultSet = stmt.executeQuery("SELECT * FROM gebruikers WHERE username = 'testuser'");
        assertTrue(resultSet.next());
        assertEquals("testuser", resultSet.getString("username"));
        assertEquals("password123", resultSet.getString("password"));
        stmt.close();
    }

    @Test
    void testAccountAanmakenWithEmptyFields() {
        accountAanmaken.nieuwGebruikersnaamVeld.setText("");
        accountAanmaken.nieuwWachtwoordVeld.setText("");
        accountAanmaken.confirmNieuwWachtwoordVeld.setText("");

        JButton confirmKnop = accountAanmaken.confirmKnop;


        if (confirmKnop != null) {
            confirmKnop.doClick();

            JOptionPane.showMessageDialog(accountAanmaken, "Geen velden leeglaten :D");
        } else {
            System.out.println("1234");
        }
    }

    @Test
    void testWachtwoordenNietOvereenkomen() {
        accountAanmaken.nieuwGebruikersnaamVeld.setText("testuser");
        accountAanmaken.nieuwWachtwoordVeld.setText("password123");
        accountAanmaken.confirmNieuwWachtwoordVeld.setText("wrongpassword");

        JButton confirmKnop = accountAanmaken.confirmKnop;


        if (confirmKnop != null) {
            confirmKnop.doClick();

            JOptionPane.showMessageDialog(accountAanmaken, "Wachtwoorden komen niet overeen");
        }
    }
}