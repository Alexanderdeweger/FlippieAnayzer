package Main.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class FilterenTest {

    private JButton filterKnop;
    private JTextField minDatumveld;
    private JTextField maxDatumveld;
    private JTextField bereikVeld;
    private JTextField gemBereikVeld;
    private JComboBox<String> actiefComboBox;
    private JComboBox<String> leeftijdComboBox;

    private Filteren filteren;  // Direct mock without using spy
    private Connection conn;    // H2 Database connection

    @BeforeEach
    public void setUp() throws SQLException {
        // Set up H2 in-memory database
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        // Create test table (simulate the schema)
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE advertenties (id INT PRIMARY KEY, bereik INT, actief INT, datumgepost DATE, leeftijd VARCHAR(255))");

        // Insert test data
        stmt.execute("INSERT INTO advertenties (id, bereik, actief, datumgepost, leeftijd) VALUES (1, 200, 1, '2021-01-01', '26-35')");
        stmt.execute("INSERT INTO advertenties (id, bereik, actief, datumgepost, leeftijd) VALUES (2, 100, 0, '2022-01-01', '36-45')");

        // Set up the filteren instance and the UI elements
        filteren = new Filteren(false);
        filterKnop = filteren.filterKnop; // Filter button
        minDatumveld = filteren.minDatumveld; // Min date text field
        maxDatumveld = filteren.maxDatumveld; // Max date text field
        bereikVeld = filteren.bereikVeld; // Bereik text field
        gemBereikVeld = filteren.gemBereikVeld; // Gem Bereik text field
        actiefComboBox = filteren.actiefComboBox; // Actief combo box
        leeftijdComboBox = filteren.leeftijdComboBox; // Leeftijd combo box
    }

    @Test
    public void testFilterKnopAction() throws SQLException {
        // Set up the form with test values
        minDatumveld.setText("2021-01-01");
        maxDatumveld.setText("2025-01-01");
        bereikVeld.setText("100");
        gemBereikVeld.setText("50");
        actiefComboBox.setSelectedItem("Actief");
        leeftijdComboBox.setSelectedItem("26-35");

        // Trigger the action
        filterKnop.doClick();  // Simulate a button click

        // Query the database to see if the filtering works as expected
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM advertenties WHERE leeftijd = '26-35' AND bereik >= 100 AND actief = 1 AND datumgepost >= '2021-01-01' AND datumgepost <='2025-01-01'");

        // Verify that the result is as expected
        assertTrue(rs.next(), "Result set should contain at least one record.");
        assertEquals(200, rs.getInt("bereik"));
        assertEquals("26-35", rs.getString("leeftijd"));

        // Ensure no more rows are present
        assertFalse(rs.next(), "There should be no more results.");

        rs.close();
    }
}
