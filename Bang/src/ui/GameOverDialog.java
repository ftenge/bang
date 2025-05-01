package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameOverDialog extends JDialog {
    public GameOverDialog(JFrame parent, String message) {
        super(parent, "Játék vége", true);
        setLayout(new BorderLayout());
        setSize(300, 150);
        setIconImage(ImageUtils.loadImage("src/assets/cards/bangicon.png", 32, 32).getImage());
        setLocationRelativeTo(parent);

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton newGameButton = new JButton("Új játék");
        JButton exitButton = new JButton("Kilépés");

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

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
