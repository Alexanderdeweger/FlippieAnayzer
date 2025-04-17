package Main.java;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException, ParseException {
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginScherm();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}