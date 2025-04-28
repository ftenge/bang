package ui;

import javax.swing.*;
import java.awt.*;

public class GameOverDialog extends JDialog {
    public GameOverDialog(JFrame parent, String message) {
        super(parent, "Game Over", true);
        setLayout(new BorderLayout());
        setSize(300, 150);
        setIconImage(ImageUtils.loadImage("src/assets/cards/bangicon.png", 32, 32).getImage());
        setLocationRelativeTo(parent);

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton newGameButton = new JButton("New Game");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(newGameButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Gombokhoz actionök
        newGameButton.addActionListener(e -> {
            new NewGameSettingsUI().setVisible(true);
            parent.dispose();
            dispose();
        });

        exitButton.addActionListener(e -> {
            dispose();
            System.exit(0);
        });
    }
}
