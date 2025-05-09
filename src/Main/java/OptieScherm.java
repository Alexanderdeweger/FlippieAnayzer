package Main.java;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OptieScherm extends JFrame{
    boolean owner = false;
    public OptieScherm(){
        setTitle("Flippielyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton advertentiesTonen = new JButton("Toon alle advertenties");
        advertentiesTonen.setBounds(80, 30, 240, 25);
        add(advertentiesTonen);


        JButton filteren = new JButton("Filter");
        filteren.setBounds(140, 70, 120, 25);
        add(filteren);


        advertentiesTonen.addActionListener((ActionEvent _) -> {
            dispose();
            SwingUtilities.invokeLater(() -> new TabelAdvertenties(owner));
        });

        filteren.addActionListener((ActionEvent e) -> {
            dispose();
            SwingUtilities.invokeLater(() -> new Filteren(owner));
        });

        setVisible(true);
    }
}
