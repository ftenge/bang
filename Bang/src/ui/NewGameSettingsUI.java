package ui;

import gamelogic.GameLogic;

import javax.swing.*;
import java.awt.*;

public class NewGameSettingsUI extends JFrame {

    public NewGameSettingsUI() {
        setTitle("Játék beállítások");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ComboBox játékosszám kiválasztásához
        Integer[] playerOptions = {2, 3, 4, 5, 6, 7};
        JComboBox<Integer> playerNumberComboBox = new JComboBox<>(playerOptions);

        JButton startButton = new JButton("Start Game");

        startButton.addActionListener(e -> {
            int numberOfPlayers = (Integer) playerNumberComboBox.getSelectedItem();

            //BangGameUI gameUI = new BangGameUI();
            //GameLogic gameLogic = gameUI.getGameLogic();

            //gameLogic.startGame(numberOfPlayers); // Ezt mindjárt hozzáadjuk a GameLogic-hoz

            //gameUI.setVisible(true);
            new PlayerSetupFrame(numberOfPlayers);
            dispose(); // bezárja ezt az ablakot
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Játékosok száma:"));
        panel.add(playerNumberComboBox);
        panel.add(startButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}
