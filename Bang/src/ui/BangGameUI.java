package ui;

import cards.Card;
import gameinstance.GameInstance;
import gamelogic.GameLogic;
import utilities.BaseModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

public class BangGameUI extends JFrame {
    private GameInstance gameInstance;
    private GameLogic gameLogic;
    private JPanel playerPanel, tablePanel, opponentsPanel, logPanel, buttonPanel;
    private JTextArea logTextArea;
    private JLabel discardPileLabel;
    private JLabel hpLabel, roleLabel, characterLabel;
    private JPanel infoPanel;
    private JButton playCardButton, discardCardButton, nextTurnButton;
    private JComboBox<BaseModel> targetPlayerSelector;

    private List<CardLabel> playerCardLabels = new ArrayList<>();

    public BangGameUI() {
        this.gameLogic = new GameLogic(this);
        this.gameInstance = GameInstance.getInstance();
        setTitle("Bang! Game");
        setIconImage(ImageUtils.loadImage("src/assets/cards/bangicon.png", 32, 32).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());

        playerPanel = new JPanel();
        tablePanel = new JPanel(new BorderLayout());
        opponentsPanel = new JPanel();
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logPanel = new JPanel(new BorderLayout());

        tablePanel.setBackground(new Color(34, 139, 34));
        buttonPanel.setBackground(new Color(34, 139, 34));
        playerPanel.setBackground(new Color(210, 180, 140));
        opponentsPanel.setBackground(new Color(210, 180, 140));

        mainPanel.add(playerPanel, BorderLayout.SOUTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(opponentsPanel, BorderLayout.NORTH);

        logTextArea = new JTextArea(15, 20);
        logTextArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        logPanel.add(logScrollPane, BorderLayout.CENTER);

        discardPileLabel = new JLabel("Discard Pile: ");
        hpLabel = new JLabel("Your HP: ");
        roleLabel = new JLabel("Your Role: ");
        characterLabel = new JLabel("Your Character: ");

        JPanel infoPanel = new JPanel(new GridLayout(4,1));
        infoPanel.add(discardPileLabel);
        infoPanel.add(hpLabel);
        infoPanel.add(roleLabel);
        infoPanel.add(characterLabel);

        logPanel.add(infoPanel, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel(new GridLayout(2, 2));

        playCardButton = new JButton("Play Selected Card");
        playCardButton.addActionListener(e -> playSelectedCard());

        discardCardButton = new JButton("Discard Selected Card");
        discardCardButton.addActionListener(e -> discardSelectedCard());

        nextTurnButton = new JButton("Next Turn");
        nextTurnButton.addActionListener(e -> {
            int answer = showTwoOptionDialog(
                    "Next turn",
                    "Are you sure to proceed the next round?",
                    "Yes", "No"
            );
            if (answer == 0) {
                nextTurn();
            }
        });

        targetPlayerSelector = new JComboBox<>();

        controlPanel.add(playCardButton);
        controlPanel.add(discardCardButton);
        controlPanel.add(nextTurnButton);
        controlPanel.add(targetPlayerSelector);

        logPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private CardLabel createCardLabel(Card card, boolean isFaceUp, List<CardLabel> cardLabelList) {
        if (isFaceUp && card != null) {
            ImageIcon normal =ImageUtils.loadImage(card.getImagePath(), 80, 120);
            ImageIcon hover = ImageUtils.loadImage(card.getImagePath(), 100, 150);
            return new CardLabel(card, cardLabelList, true);
        } else {
            ImageIcon back = ImageUtils.loadImage("src/assets/cards/cover.png", 80, 120);
            return new CardLabel(null, cardLabelList, true);
        }
    }

    public void updateUI() {
        playerPanel.removeAll();
        tablePanel.removeAll();
        buttonPanel.removeAll();
        opponentsPanel.removeAll();
        targetPlayerSelector.removeAllItems();
        playerCardLabels.clear();

        BaseModel currentPlayer = gameLogic.getHumanPlayer();

        for (Card card : currentPlayer.getHandCards()) {
            CardLabel label = createCardLabel(card, true, playerCardLabels);
            playerCardLabels.add(label);
            playerPanel.add(label);
        }

        for (Card card : currentPlayer.getTableCards()) {
            JButton tableCard = createCardButton(card, true);
            buttonPanel.add(tableCard);
        }

        for (BaseModel player : gameLogic.getPlayers()) {
            if (!player.equals(currentPlayer)) {
                JPanel singleOpponentPanel = new JPanel();
                singleOpponentPanel.setBorder(BorderFactory.createTitledBorder(player.getName() + " (HP: " + player.getHealth() + "/" + player.getMaxHP() + ")"));

                singleOpponentPanel.add(new HiddenCardLabel(player.getHandCards().size()));


                for (Card card : player.getTableCards()) {
                    JButton cardButton = createCardButton(card, true);
                    singleOpponentPanel.add(cardButton);

                }

                opponentsPanel.add(singleOpponentPanel);
                targetPlayerSelector.addItem(player);
            }
        }

        tablePanel.add(buttonPanel,BorderLayout.SOUTH);
        discardPileLabel.setText("Discard Pile: " + gameInstance.getDeck().seeLastDiscardedCard());
        hpLabel.setText("Your HP: " + currentPlayer.getHealth() + "/" + currentPlayer.getMaxHP());
        roleLabel.setText("Your Role: " + gameLogic.getHumanPlayer().getRole().toString());
        characterLabel.setText("Your Character: " + gameLogic.getHumanPlayer().getName());

        revalidate();
        repaint();
    }

    private JButton createCardButton(Card card, boolean isFaceUp) {
        JButton button;
        if (isFaceUp && card != null) {
            ImageIcon icon = new ImageIcon(card.getImagePath());
            Image scaledImage = icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
            button = new JButton(new ImageIcon(scaledImage));
            button.setToolTipText(card.getName());
        } else {
            ImageIcon icon = new ImageIcon("src/assets/cards/cover.png");
            Image scaledImage = icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
            button = new JButton(new ImageIcon(scaledImage));
        }
        button.setPreferredSize(new Dimension(80, 120));
        button.setBackground(Color.DARK_GRAY);
        return button;
    }

    public int selectCardFromList(List<Card> cards, String message, String title) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 400); // nagyobb ablak

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new FlowLayout());

        List<CardLabel> labels = new ArrayList<>();
        final int[] selectedIndex = {-1};

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            CardLabel label = new CardLabel(card, labels, true);
            final int index = i;
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedIndex[0] = index;
                    dialog.dispose();
                }
            });
            labels.add(label);
            cardPanel.add(label);
        }

        dialog.add(new JLabel(message, JLabel.CENTER), BorderLayout.NORTH);
        dialog.add(cardPanel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        return selectedIndex[0];
    }



    public int selectTargetFromList(List<BaseModel> baseModels, String name, String title) {
        String[] targetNames = baseModels.stream().map(BaseModel::getName).toArray(String[]::new);
        int answer = JOptionPane.showOptionDialog(this, name, title, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, targetNames, targetNames[0]);
        //logMessage("A választás: " + answer);
        return answer;
    }

    public int showTwoOptionDialog(String title, String message, String option1, String option2) {
        final int[] result = {0};

        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(500, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(messageLabel, BorderLayout.CENTER);

        JButton button1 = new JButton(option1);
        JButton button2 = new JButton(option2);

        button1.setFont(new Font("Arial", Font.PLAIN, 14));
        button2.setFont(new Font("Arial", Font.PLAIN, 14));

        button1.addActionListener(e -> {
            result[0] = 0;
            dialog.dispose();
        });

        button2.addActionListener(e -> {
            result[0] = 1;
            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);

        //logMessage("A választás: " + result[0]);
        return result[0];
    }


    public List<Card> selectTwoCardsFromThree(List<Card> ogCards, String title, String instruction) {
        List<Card> selected = new ArrayList<>();
        List<CardLabel> cardLabels = new ArrayList<>();

        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(800, 400); // nagyobb méret
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(instruction, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label, BorderLayout.NORTH);

        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        for (Card card : ogCards) {
            ImageIcon normalIcon = new ImageIcon(new ImageIcon(card.getImagePath()).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
            ImageIcon hoverIcon = new ImageIcon(new ImageIcon(card.getImagePath()).getImage().getScaledInstance(110, 165, Image.SCALE_SMOOTH));

            CardLabel cardLabel = new MultiSelectCardLabel(card, normalIcon, hoverIcon, cardLabels, selected, dialog);
            cardLabels.add(cardLabel);
            cardsPanel.add(cardLabel);
        }

        panel.add(cardsPanel, BorderLayout.CENTER);

        JButton confirmButton = new JButton("OK");
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmButton.addActionListener(e -> {
            if (selected.size() == 2) {
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Choose exactly two cards!");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);

        return selected;
    }



    private Card getSelectedCard() {
        for (CardLabel label : playerCardLabels) {
            if (label.isSelected()) return label.getCard();
        }
        return null;
    }

    private void playSelectedCard() {
        Card selectedCard = getSelectedCard();
        if (selectedCard == null) {
            logMessage("⚠ No card selected!");
            return;
        }

        BaseModel currentPlayer = gameLogic.getCurrentPlayer();
        BaseModel target = (BaseModel) targetPlayerSelector.getSelectedItem();

        if(gameLogic.cardAction(selectedCard, currentPlayer, target)) {
            logMessage("🃏 Played card: " + selectedCard.getName());
        }else{
            logMessage("Can't play this card: " + selectedCard.getName());
        }
        updateUI();
    }

    private void discardSelectedCard() {
        Card selectedCard = getSelectedCard();
        if (selectedCard == null) {
            logMessage("⚠ No card selected!");
            return;
        }

        BaseModel currentPlayer = gameLogic.getCurrentPlayer();
        gameLogic.discardCardAction(selectedCard, currentPlayer);

        logMessage("🗑️ Discarded card: " + selectedCard.getName());
        updateUI();
    }

    private void nextTurn() {
        gameLogic.endTurn();
        logMessage("🔄 Next turn started!");
        updateUI();
    }

    public void logMessage(String message) {
        logTextArea.append(message + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    public GameLogic getGameLogic(){
        return gameLogic;
    }
}
