package ui;

import gameinstance.GameInstance;
import gamelogic.GameLogic;
import utilities.BaseModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerSetupFrame extends JFrame {
    private int numberOfPlayers;
    private List<JComboBox<String>> characterSelectors = new ArrayList<>();
    private List<JComboBox<String>> roleSelectors = new ArrayList<>();
    private GameInstance gameInstance;

    public PlayerSetupFrame(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.gameInstance = GameInstance.getInstance();

        setTitle("Játékosok beállítása");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(numberOfPlayers, 1, 10, 10));

        for (int i = 0; i < numberOfPlayers; i++) {
            JPanel playerPanel = new JPanel();

            playerPanel.add(new JLabel("Játékos " + (i + 1)));

            JComboBox<String> characterBox = new JComboBox<>(getCharacterOptions());
            JComboBox<String> roleBox = new JComboBox<>(getRoleOptions());

            characterSelectors.add(characterBox);
            roleSelectors.add(roleBox);

            playerPanel.add(new JLabel("Karakter:"));
            playerPanel.add(characterBox);
            playerPanel.add(new JLabel("Szerep:"));
            playerPanel.add(roleBox);

            mainPanel.add(playerPanel);
        }

        JButton startButton = new JButton("Játék indítása");
        startButton.addActionListener(e -> {
            List<String> selectedCharacters = new ArrayList<>();
            List<String> selectedRoles = new ArrayList<>();

            for (int i = 0; i < numberOfPlayers; i++) {
                selectedCharacters.add((String) characterSelectors.get(i).getSelectedItem());
                selectedRoles.add((String) roleSelectors.get(i).getSelectedItem());
            }

            // Itt indul a játék ezekkel a beállításokkal
            BangGameUI gameUI = new BangGameUI();
            GameLogic gameLogic = gameUI.getGameLogic();

            gameLogic.startGame(numberOfPlayers, selectedCharacters, selectedRoles);
            gameUI.setVisible(true);
            dispose();
        });

        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String[] getCharacterOptions() {
        return gameInstance.getAllCharacterNames();
    }

    private String[] getRoleOptions() {
        return new String[]{"Random", "Sheriff", "Deputy", "Outlaw", "Renegade"};
    }
}
