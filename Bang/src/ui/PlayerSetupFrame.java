package ui;

import gameinstance.GameInstance;
import gamelogic.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayerSetupFrame extends JFrame {
    private int numberOfPlayers;
    private List<JComboBox<String>> botSelector = new ArrayList<>();
    private List<JComboBox<String>> characterSelectors = new ArrayList<>();
    private List<JComboBox<String>> roleSelectors = new ArrayList<>();
    private GameInstance gameInstance;
    private JPanel errorPanel;
    private JButton startButton;

    public PlayerSetupFrame(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.gameInstance = GameInstance.getInstance();

        setTitle("Játékosok beállítása");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(numberOfPlayers, 1, 10, 10));

        for (int i = 0; i < numberOfPlayers; i++) {
            JPanel playerPanel = new JPanel();

            playerPanel.add(new JLabel("Játékos " + (i + 1)));

            JComboBox<String> botBox = new JComboBox<>(getBotOptions());
            if (i == 0) botBox.setSelectedItem("False");

            JComboBox<String> characterBox = new JComboBox<>(getCharacterOptions());
            JComboBox<String> roleBox = new JComboBox<>(getRoleOptions());

            botSelector.add(botBox);
            characterSelectors.add(characterBox);
            roleSelectors.add(roleBox);

            playerPanel.add(new JLabel("Bot:"));
            playerPanel.add(botBox);
            playerPanel.add(new JLabel("Karakter:"));
            playerPanel.add(characterBox);
            playerPanel.add(new JLabel("Szerep:"));
            playerPanel.add(roleBox);

            mainPanel.add(playerPanel);

            botBox.addActionListener(e -> validateInputs());
            characterBox.addActionListener(e -> validateInputs());
            roleBox.addActionListener(e -> validateInputs());
        }

        errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));
        errorPanel.setBackground(Color.WHITE);

        startButton = new JButton("Játék indítása");
        startButton.addActionListener(e -> {
            if (validateInputs()) {
                List<String> selectedBots = new ArrayList<>();
                List<String> selectedCharacters = new ArrayList<>();
                List<String> selectedRoles = new ArrayList<>();

                for (int i = 0; i < numberOfPlayers; i++) {
                    selectedBots.add((String) botSelector.get(i).getSelectedItem());
                    selectedCharacters.add((String) characterSelectors.get(i).getSelectedItem());
                    selectedRoles.add((String) roleSelectors.get(i).getSelectedItem());
                    System.out.println( "választó: "+ (String) roleSelectors.get(i).getSelectedItem());
                }

                BangGameUI gameUI = new BangGameUI();
                GameLogic gameLogic = gameUI.getGameLogic();

                gameLogic.startGame(numberOfPlayers, selectedCharacters, selectedRoles, selectedBots);
                gameUI.setVisible(true);
                setVisible(false);
                dispose();
            }
        });

        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        add(errorPanel, BorderLayout.NORTH);
        add(startButton, BorderLayout.SOUTH);

        validateInputs(); // első validáció
        setVisible(true);
    }

    private boolean validateInputs() {
        errorPanel.removeAll();

        Set<String> characters = new HashSet<>();
        Map<String, Integer> rolesCount = new HashMap<>();
        int humanCount = 0;
        boolean valid = true;

        for (int i = 0; i < numberOfPlayers; i++) {
            JComboBox<String> botBox = botSelector.get(i);
            JComboBox<String> characterBox = characterSelectors.get(i);
            JComboBox<String> roleBox = roleSelectors.get(i);

            String bot = (String) botBox.getSelectedItem();
            String character = (String) characterBox.getSelectedItem();
            String role = (String) roleBox.getSelectedItem();

            // alapértelmezett szín visszaállítása
            botBox.setBackground(Color.WHITE);
            characterBox.setBackground(Color.WHITE);
            roleBox.setBackground(Color.WHITE);

            if ("False".equals(bot)) {
                humanCount++;
            }

            if (characters.contains(character) && !Objects.equals(character, "Random")) {
                JLabel errorLabel = new JLabel("Nem választható ki ugyanaz a karakter kétszer: " + character);
                errorLabel.setForeground(Color.RED);
                errorPanel.add(errorLabel);
                characterBox.setBackground(Color.PINK);
                valid = false;
            } else {
                characters.add(character);
            }

            rolesCount.put(role, rolesCount.getOrDefault(role, 0) + 1);
        }

        if (humanCount != 1) {
            JLabel errorLabel = new JLabel("Pontosan egy emberi játékos kell! (Most: " + humanCount + ")");
            errorLabel.setForeground(Color.RED);
            errorPanel.add(errorLabel);
            for (JComboBox<String> botBox : botSelector) {
                botBox.setBackground(Color.PINK);
            }
            valid = false;
        }

        if (!rolesCount.containsKey("Random") && rolesCount.getOrDefault("Sheriff", 0) != 1) {
            JLabel errorLabel = new JLabel("Pontosan 1 Sheriff szükséges!");
            errorLabel.setForeground(Color.RED);
            errorPanel.add(errorLabel);
            for (JComboBox<String> roleBox : roleSelectors) {
                if ("Sheriff".equals(roleBox.getSelectedItem())) {
                    roleBox.setBackground(Color.WHITE);
                } else {
                    roleBox.setBackground(Color.PINK);
                }
            }
            valid = false;
        }

        if (rolesCount.getOrDefault("Outlaw", 0) > 3) {
            JLabel errorLabel = new JLabel("Legfeljebb 3 Outlaw lehet!");
            errorLabel.setForeground(Color.RED);
            errorPanel.add(errorLabel);
            highlightRole("Outlaw");
            valid = false;
        }

        if (rolesCount.getOrDefault("Renegade", 0) > 1) {
            JLabel errorLabel = new JLabel("Legfeljebb 1 Renegade lehet!");
            errorLabel.setForeground(Color.RED);
            errorPanel.add(errorLabel);
            highlightRole("Renegade");
            valid = false;
        }

        if (rolesCount.getOrDefault("Deputy", 0) > 2) {
            JLabel errorLabel = new JLabel("Legfeljebb 2 Deputy lehet!");
            errorLabel.setForeground(Color.RED);
            errorPanel.add(errorLabel);
            highlightRole("Deputy");
            valid = false;
        }

        errorPanel.revalidate();
        errorPanel.repaint();

        startButton.setEnabled(valid);
        return valid;
    }

    private void highlightRole(String role) {
        for (JComboBox<String> box : roleSelectors) {
            if (role.equals(box.getSelectedItem())) {
                box.setBackground(Color.PINK);
            }
        }
    }

    private String[] getBotOptions() {
        return new String[]{"True", "False"};
    }

    private String[] getCharacterOptions() {
        String[] characters = gameInstance.getAllCharacterNames();
        String[] charactersWithRandom = new String[characters.length + 1];
        charactersWithRandom[0] = "Random";
        System.arraycopy(characters, 0, charactersWithRandom, 1, characters.length);
        return charactersWithRandom;
    }

    private String[] getRoleOptions() {
        return new String[]{"Random", "Sheriff", "Deputy", "Outlaw", "Renegade"};
    }
}
