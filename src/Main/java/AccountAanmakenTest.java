package Main.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

class AccountAanmakenTest {
    private AccountAanmaken accountAanmaken;

    @BeforeEach
    void setUp() {
        // Maak een nieuwe instantie van AccountAanmaken voor elke test
        accountAanmaken = new AccountAanmaken();
    }

    @Test
    void testAccountAanmakenButtonClick() throws SQLException {
        // Stel de gebruikersnaam en wachtwoord in
        accountAanmaken.nieuwGebruikersnaamVeld.setText("testuser");
        accountAanmaken.nieuwWachtwoordVeld.setText("password123");
        accountAanmaken.confirmNieuwWachtwoordVeld.setText("password123");

        // Zoek de bevestigingsknop
        JButton confirmKnop = (JButton) accountAanmaken.confirmKnop; // Stel de index in voor de knop
        confirmKnop.doClick(); // Simuleer het klikken op de knop

        // Verifieer of de gebruiker is toegevoegd aan de H2 database
        Database database = new Database();
        Statement stmt = database.connect().createStatement();
        var resultSet = stmt.executeQuery("SELECT * FROM gebruikers WHERE username = 'testuser'");
        assertTrue(resultSet.next());  // Zorg ervoor dat er een record met username 'testuser' bestaat
        assertEquals("testuser", resultSet.getString("username"));
        assertEquals("password123", resultSet.getString("password"));
        stmt.close();
    }

    @Test
    void testAccountAanmakenWithEmptyFields() {
        // Stel de velden in als leeg
        accountAanmaken.nieuwGebruikersnaamVeld.setText("");
        accountAanmaken.nieuwWachtwoordVeld.setText("");
        accountAanmaken.confirmNieuwWachtwoordVeld.setText("");

        // Zoek de bevestigingsknop
        JButton confirmKnop = null;
        for (Component component : accountAanmaken.getComponents()) {
            if (component instanceof JButton && "confirmButton".equals(component.getName())) {
                confirmKnop = (JButton) component;
                break;
            }
        }

        // Test: Kliek op de "Maak account" knop met lege velden
        if (confirmKnop != null) {
            // Simuleer het klikken op de knop
            confirmKnop.doClick();

            // Controleer of er een waarschuwing is voor lege velden
            JOptionPane.showMessageDialog(accountAanmaken, "Geen velden leeglaten :D");
        }
    }

    @Test
    void testWachtwoordenNietOvereenkomen() {
        // Stel de velden in voor ongeldige wachtwoorden
        accountAanmaken.nieuwGebruikersnaamVeld.setText("testuser");
        accountAanmaken.nieuwWachtwoordVeld.setText("password123");
        accountAanmaken.confirmNieuwWachtwoordVeld.setText("wrongpassword");

        // Zoek de bevestigingsknop
        JButton confirmKnop = null;
        for (Component component : accountAanmaken.getComponents()) {
            if (component instanceof JButton && "confirmButton".equals(component.getName())) {
                confirmKnop = (JButton) component;
                break;
            }
        }

        // Test: Kliek op de "Maak account" knop met niet-overeenkomende wachtwoorden
        if (confirmKnop != null) {
            // Simuleer het klikken op de knop
            confirmKnop.doClick();

            // Controleer of er een waarschuwing is voor niet-overeenkomende wachtwoorden
            JOptionPane.showMessageDialog(accountAanmaken, "Wachtwoorden komen niet overeen");
        }
    }
}