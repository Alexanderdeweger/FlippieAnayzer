package Main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Filteren extends JFrame {
    public Filteren(boolean owner) {

        setTitle("Filteren");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(null);



        JLabel minDatum = new JLabel("Minimale datum:");
        minDatum.setBounds(25, 15, 120, 25);
        add(minDatum);

        JTextField minDatumveld = new JTextField("yyyy-mm-dd");
        minDatumveld.setBounds(150, 15, 120, 25);
        minDatumveld.setForeground(Color.GRAY);
        add(minDatumveld);

        minDatumveld.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (minDatumveld.getText().equals("yyyy-mm-dd")) {
                    minDatumveld.setText("");
                    minDatumveld.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (minDatumveld.getText().isEmpty()) {
                    minDatumveld.setForeground(Color.GRAY);
                    minDatumveld.setText("yyyy-mm-dd");
                }
            }
        });

        JLabel maxDatumLabel = new JLabel("Maximale datum:");
        maxDatumLabel.setBounds(25, 45, 120, 25);
        add(maxDatumLabel);

        JTextField maxDatumveld = new JTextField("yyyy-mm-dd");
        maxDatumveld.setBounds(150, 45, 120, 25);
        maxDatumveld.setForeground(Color.GRAY);
        add(maxDatumveld);


        maxDatumveld.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (maxDatumveld.getText().equals("yyyy-mm-dd")) {
                    maxDatumveld.setText("");
                    maxDatumveld.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (maxDatumveld.getText().isEmpty()) {
                    maxDatumveld.setForeground(Color.GRAY);
                    maxDatumveld.setText("yyyy-mm-dd");
                }
            }
        });

        JLabel bereikLabel = new JLabel("Minimale bereik:");
        bereikLabel.setBounds(25, 75, 120, 25);
        add(bereikLabel);

        JTextField bereikVeld = new JTextField();
        bereikVeld.setBounds(150, 75, 120, 25);
        add(bereikVeld);

        bereikVeld.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // blokkeer niet-cijfers
                }
            }
        });

        JLabel gemBereikLabel = new JLabel("Min gem bereik/dag:");
        gemBereikLabel.setBounds(25, 105, 175, 25);
        add(gemBereikLabel);

        JTextField gemBereikVeld = new JTextField();
        gemBereikVeld.setBounds(150, 105, 120, 25);
        add(gemBereikVeld);

        gemBereikVeld.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // blokkeer niet-cijfers
                }
            }
        });



        JLabel actiefLabel = new JLabel("Actief");
        actiefLabel.setBounds(25, 135, 120, 25);
        add(actiefLabel);

        String[] actiefOpties = {"-", "Actief", "Inactief"};
        JComboBox<String> actiefComboBox = new JComboBox<>(actiefOpties);
        actiefComboBox.setBounds(150, 135, 120, 25);
        add(actiefComboBox);


        JLabel leeftijdLabel = new JLabel("Leeftijd");
        leeftijdLabel.setBounds(25, 165, 120, 25);
        add(leeftijdLabel);

        String[] leeftijdOpties = {"-", "18-25", "26-35", "36-45", "46-60", "60+"};
        JComboBox<String> leeftijdComboBox = new JComboBox<>(leeftijdOpties);
        leeftijdComboBox.setBounds(150, 165, 120, 25);
        add(leeftijdComboBox);

        JButton filterKnop = new JButton("Filteren");
        filterKnop.setBounds(100, 210, 100, 30);
        add(filterKnop);

        filterKnop.addActionListener((ActionEvent _) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date minDatumSql;

            if (!minDatumveld.getText().equals("-") && !minDatumveld.getText().equals("yyyy-mm-dd")) {
                LocalDate minDatumJava = LocalDate.parse(minDatumveld.getText(), formatter);
                minDatumSql = Date.valueOf(minDatumJava);

            } else {
                minDatumSql = null;
            }
            Date maxDatumSql;
            if (!maxDatumveld.getText().equals("-") && !maxDatumveld.getText().equals("yyyy-mm-dd")) {
                LocalDate maxDatumJava = LocalDate.parse(maxDatumveld.getText(), formatter);
                maxDatumSql = Date.valueOf(maxDatumJava);
            } else {
                maxDatumSql = null;
            }
            int bereik = 0;
            int gemBereik = 0;
            if (!bereikVeld.getText().isEmpty()) {bereik = Integer.parseInt(bereikVeld.getText());}
            if (!gemBereikVeld.getText().isEmpty()) {gemBereik = Integer.parseInt(gemBereikVeld.getText());}

            String actief = (String) actiefComboBox.getSelectedItem();
            String leeftijd = (String) leeftijdComboBox.getSelectedItem();
            String sql = "SELECT * FROM advertenties WHERE 1=1";

            if (leeftijd != null && !leeftijd.equals("-")) {
                sql += " AND leeftijd = \"" + leeftijd + "\"";
            }
            if (bereik > 0) {
                sql += " AND bereik >= " + bereik;
            }

            if (actief.equals("Actief")) {
                sql += " AND actief = 1";
            } else if (actief.equals("Inactief")) {
                sql += " AND actief = 0";
            }
            if (minDatumSql != null) {
                sql += " AND datumgepost >= \"" + minDatumSql + "\"";
            }
            if (maxDatumSql != null) {
                sql += " AND datumgepost <= \"" + maxDatumSql + "\"";
            }
            if (gemBereik > 0) {
                sql += " AND DATEDIFF(CURDATE(), datumgepost) > " + gemBereik;
            }
            Database database = new Database();
            Connection conn = database.connect();
            Statement stmt;
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                dispose();
                SwingUtilities.invokeLater(() -> new TabelAdvertenties(rs, owner));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (owner) {
                    new OwnerOptieScherm(); // open ander venster voordat het sluit
                } else {
                    new OptieScherm();
                }
            }
        });


        setVisible(true);


    }
}

/*
vorige filterlijsten gebruiken
 */
