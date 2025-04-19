package Main.java;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class LoginSchermTest {
    private Connection connection;

    @BeforeEach
    public void setUpDatabase() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("sa");

        connection = ds.getConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS gebruikers (username VARCHAR(255), password VARCHAR(255), owner INT)");
            stmt.execute("INSERT INTO gebruikers (username, password, owner) VALUES ('admin', 'admin123', 1)");
        }
    }

    @Test
    public void testLoginWithCorrectCredentials() throws SQLException, InvocationTargetException, InterruptedException {

        SwingUtilities.invokeAndWait(() -> {
            try {

                LoginScherm loginScherm = new LoginScherm() {
                    {
                        this.con = connection;
                        this.stmt = con.createStatement();
                        this.rs = stmt.executeQuery("SELECT * FROM gebruikers");
                    }
                };

                JTextField gebruikersnaamVeld = (JTextField) findComponentByType(loginScherm, JTextField.class, 0);
                JPasswordField wachtwoordVeld = (JPasswordField) findComponentByType(loginScherm, JPasswordField.class, 0);
                JButton loginKnop = (JButton) findComponentByType(loginScherm, JButton.class, 0);

                gebruikersnaamVeld.setText("admin");
                wachtwoordVeld.setText("admin123");
                loginKnop.doClick();

                assertTrue(loginScherm.ingelogd);
                loginScherm.dispose();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void testLoginWithIncorrectCredentials() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                LoginScherm loginScherm = new LoginScherm() {
                    {
                        this.con = connection;
                        this.stmt = con.createStatement();
                        this.rs = stmt.executeQuery("SELECT * FROM gebruikers");
                    }
                };

                JTextField gebruikersnaamVeld = (JTextField) findComponentByType(loginScherm, JTextField.class, 0);
                JPasswordField wachtwoordVeld = (JPasswordField) findComponentByType(loginScherm, JPasswordField.class, 0);
                JButton loginKnop = (JButton) findComponentByType(loginScherm, JButton.class, 0);

                gebruikersnaamVeld.setText("admin");
                wachtwoordVeld.setText("verkeerdWachtwoord");
                loginKnop.doClick();

                Field ingelogdField = LoginScherm.class.getDeclaredField("ingelogd");
                ingelogdField.setAccessible(true);
                boolean ingelogd = ingelogdField.getBoolean(loginScherm);

                assertFalse(ingelogd, "Gebruiker zou niet moeten inloggen met verkeerde credentials");

                loginScherm.dispose();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Component findComponentByType(JFrame frame, Class<?> componentClass, int index) {
        Component[] components = frame.getContentPane().getComponents();
        int count = 0;

        for (Component component : components) {
            if (componentClass.isInstance(component)) {
                if (count == index) {
                    return component;
                }
                count++;
            }
        }

        return null;
    }
}
