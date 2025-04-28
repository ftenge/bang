package ui;

import javax.swing.*;
import java.awt.*;

public class HiddenCardLabel extends JLabel {
    private int cardCount;

    public HiddenCardLabel(int cardCount) {
        this.cardCount = cardCount;
        ImageIcon backIcon = ImageUtils.loadImage("src/assets/model.cards/cover.png", 80, 120);
        setIcon(backIcon);
        setLayout(new BorderLayout());

        JPanel circlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int diameter = 30;
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;

                // Fehér kör
                g.setColor(Color.WHITE);
                g.fillOval(x, y, diameter, diameter);

                // Fekete szám
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g.getFontMetrics();
                String text = String.valueOf(cardCount);
                int textX = x + (diameter - fm.stringWidth(text)) / 2;
                int textY = y + ((diameter - fm.getHeight()) / 2) + fm.getAscent();
                g.drawString(text, textX, textY);
            }
        };

        circlePanel.setOpaque(false); // átlátszó háttér
        add(circlePanel, BorderLayout.CENTER);
    }



    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
        updateDisplay();
    }

    public int getCardCount() {
        return cardCount;
    }

    private void updateDisplay() {
        setText(String.valueOf(cardCount));
        repaint();
    }
}
