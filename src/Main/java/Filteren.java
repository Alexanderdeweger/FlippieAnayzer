package Main.java;

import javax.swing.*;

public class Filteren extends JFrame {
    public Filteren() {

        setTitle("Filteren");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(null);



        JButton minDatum = new JButton("Minimale datum:");
        minDatum.setBounds(600, 70, 120, 25);
        add(minDatum);

        JButton maxDatum = new JButton("Maximale datum:");
        maxDatum.setBounds(600, 70, 120, 25);
        add(maxDatum);

        JButton bereik = new JButton("Minimale bereik:");
        bereik.setBounds(600, 70, 120, 25);
        add(bereik);

        JButton gemBereik = new JButton("Minimale gemiddelde bereik per dag:");
        gemBereik.setBounds(600, 70, 120, 25);
        add(gemBereik);

        JButton actief = new JButton("Actief)");
        actief.setBounds(600, 70, 120, 25);
        add(actief);

        JButton platformen = new JButton("Actieve platformen:");
        platformen.setBounds(600, 70, 120, 25);
        add(platformen);

        JButton leeftijd = new JButton("Leeftijd");
        leeftijd.setBounds(600, 70, 120, 25);
        add(leeftijd);



    }
}

/*
vorige filterlijsten gebruiken
 */
