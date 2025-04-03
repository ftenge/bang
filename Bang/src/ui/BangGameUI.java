package ui;

import cards.Card;
import cards.DualTargetCard;
import gameinstance.GameInstance;
import gamelogic.GameLogic;
import utilities.BaseModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class BangGameUI extends JFrame {
    private GameInstance gameInstance;
    private GameLogic gameLogic;
    private JPanel playerPanel, tablePanel, opponentsPanel, logPanel;
    private JTextArea logTextArea;
    private JLabel discardPileLabel;
    private JButton playCardButton, discardCardButton, nextTurnButton;
    private JComboBox<BaseModel> targetPlayerSelector;

    public BangGameUI() {
        this.gameLogic = new GameLogic(this);
        this.gameInstance = GameInstance.getInstance();
        setTitle("Bang! Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // 🔹 Fő játékpanel (alul: játékos, középen: asztal, fent: ellenfelek)
        JPanel mainPanel = new JPanel(new BorderLayout());

        playerPanel = new JPanel();
        tablePanel = new JPanel();
        opponentsPanel = new JPanel();
        logPanel = new JPanel(new BorderLayout());

        mainPanel.add(playerPanel, BorderLayout.SOUTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(opponentsPanel, BorderLayout.NORTH);

        // 🔹 Log panel létrehozása (jobb oldal)
        logTextArea = new JTextArea(15, 20);
        logTextArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        logPanel.add(logScrollPane, BorderLayout.CENTER);

        discardPileLabel = new JLabel("Discard Pile: ");
        logPanel.add(discardPileLabel, BorderLayout.NORTH);

        // 🔹 Gombok és célpontválasztó hozzáadása
        JPanel controlPanel = new JPanel(new GridLayout(2, 2));

        playCardButton = new JButton("Play Selected Card");
        playCardButton.addActionListener(e -> playSelectedCard());

        discardCardButton = new JButton("Discard Selected Card");
        discardCardButton.addActionListener(e -> discardSelectedCard());

        nextTurnButton = new JButton("Next Turn");
        nextTurnButton.addActionListener(e -> nextTurn());

        targetPlayerSelector = new JComboBox<>();

        controlPanel.add(playCardButton);
        controlPanel.add(discardCardButton);
        controlPanel.add(nextTurnButton);
        controlPanel.add(targetPlayerSelector);

        logPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.EAST);

        setVisible(true);
        gameLogic.startGame();
        updateUI();
    }

    private JButton createCardButton(Card card, boolean isFaceUp) {
        JButton button;
        if (isFaceUp) {
            button = new JButton(card.getName());
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        } else {
            button = new JButton("Hidden");
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
        }
        return button;
    }

    public int selectCardFromList(List<Card> cards, String name, String title) {
        String[] cardNames = cards.stream().map(Card::getName).toArray(String[]::new);
        return JOptionPane.showOptionDialog(this, name, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, cardNames, cardNames[0]);
    }

    public int selectTargetFromList(List<BaseModel> baseModels, String name, String title) {
        String[] targetNames = baseModels.stream().map(BaseModel::getName).toArray(String[]::new);
        int answer = JOptionPane.showOptionDialog(this, name, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, targetNames, targetNames[0]);
        logMessage("A választás: " + answer);
        return answer;
    }

    public int showTwoOptionDialog(String title, String message, String option1, String option2) {
        String[] options = {option1, option2};
        int answer = JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        logMessage("A választás: " + answer);
        return answer;
    }

    public List<Card> selectTwoCardsFromThree(List<Card> ogCards, String title, String instruction) {
        List<Card> selectedCards = new ArrayList<>();
        JCheckBox[] checkBoxes = new JCheckBox[3];
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(new JLabel(instruction));

        for (int i = 0; i < 3; i++) {
            checkBoxes[i] = new JCheckBox(ogCards.get(i).getName());
            panel.add(checkBoxes[i]);
        }

        while (selectedCards.size() != 2) {
            int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                selectedCards.clear();
                for (int i = 0; i < 3; i++) {
                    if (checkBoxes[i].isSelected()) {
                        selectedCards.add(ogCards.get(i));
                    }
                }
            } else {
                return null;
            }
        }
        return selectedCards;
    }

    public void updateUI() {
        playerPanel.removeAll();
        tablePanel.removeAll();
        opponentsPanel.removeAll();
        targetPlayerSelector.removeAllItems();

        BaseModel currentPlayer = gameLogic.getCurrentPlayer();

        for (Card card : currentPlayer.getHandCards()) {
            JButton cardButton = createCardButton(card, true);
            cardButton.addActionListener(e -> playerPanel.putClientProperty("selectedCard", card));
            playerPanel.add(cardButton);
        }

        for (Card card : currentPlayer.getTableCards()) {
            JButton cardButton = createCardButton(card, true);
            tablePanel.add(cardButton);
        }

        for (BaseModel player : gameLogic.getPlayers()) {
            if (!player.equals(currentPlayer)) {
                JPanel singleOpponentPanel = new JPanel();
                singleOpponentPanel.setBorder(BorderFactory.createTitledBorder(player.getName() + " (HP: " + player.getHealth() + ")"));

                for (int i = 0; i < player.getHandCards().size(); i++) {
                    JButton hiddenCardButton = createCardButton(null, false);
                    singleOpponentPanel.add(hiddenCardButton);
                }

                for (Card card : player.getTableCards()) {
                    JButton cardButton = createCardButton(card, true);
                    singleOpponentPanel.add(cardButton);
                }

                opponentsPanel.add(singleOpponentPanel);
                targetPlayerSelector.addItem(player);
            }
        }
        discardPileLabel.setText("Discard Pile: " + gameInstance.getDeck().seeLastDiscardedCard());

        playerPanel.revalidate();
        playerPanel.repaint();
        tablePanel.revalidate();
        tablePanel.repaint();
        opponentsPanel.revalidate();
        opponentsPanel.repaint();

    }

    private void playSelectedCard() {
        Card selectedCard = (Card) playerPanel.getClientProperty("selectedCard");
        if (selectedCard == null) {
            logMessage("⚠ No card selected!");
            return;
        }

        BaseModel currentPlayer = gameLogic.getCurrentPlayer();
        BaseModel target = (BaseModel) targetPlayerSelector.getSelectedItem();
        gameLogic.cardAction(selectedCard, currentPlayer, target);

        logMessage("🃏 Played card: " + selectedCard.getName());
        updateUI();
    }

    private void discardSelectedCard() {
        Card selectedCard = (Card) playerPanel.getClientProperty("selectedCard");
        if (selectedCard == null) {
            logMessage("⚠ No card selected!");
            return;
        }

        BaseModel currentPlayer = gameLogic.getCurrentPlayer();
        gameLogic.discardCardAction(selectedCard, currentPlayer);


        logMessage("🗑️ Discarded card: " + selectedCard.getName());
        updateUI();
    }

    // 🔹 Következő kör
    private void nextTurn() {
        gameLogic.endTurn();
        logMessage("🔄 Next turn started!");
        updateUI();
    }

    // 🔹 Naplózás (logolás)
    public void logMessage(String message) {
        logTextArea.append(message + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

}
