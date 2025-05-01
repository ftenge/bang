package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenuUI extends JFrame {

    public MainMenuUI() {
        setTitle("Bang! - Főmenü");
        setSize(400, 300);
        setIconImage(ImageUtils.loadImage("src/assets/cards/bangicon.png", 32, 32).getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton newGameButton = new JButton("Új játék");
        JButton exitButton = new JButton("Kilépés");

        newGameButton.addActionListener(e -> {
            new NewGameSettingsUI().setVisible(true);
            dispose();
        });

        exitButton.addActionListener(e -> System.exit(0));

        panel.add(new JLabel("Bang! Főmenü", SwingConstants.CENTER));
        panel.add(newGameButton);
        panel.add(exitButton);

        add(panel);
        setVisible(true);
    }

}
