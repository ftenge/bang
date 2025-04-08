package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import cards.Card;

public class CardLabel extends JLabel {
    private final Card card;
    private final ImageIcon normalIcon;
    private final ImageIcon hoverIcon;
    protected boolean selected = false;
    private final List<CardLabel> allCardLabels;
    private final boolean singleSelect;

    public CardLabel(Card card, List<CardLabel> allCardLabels, boolean singleSelect) {
        this.card = card;
        this.allCardLabels = allCardLabels;
        this.singleSelect = singleSelect;

        // Betöltés és méretezés egyszerűsítve
        this.normalIcon = ImageUtils.loadImage(card.getImagePath(), 80, 120);
        this.hoverIcon = ImageUtils.loadImage(card.getImagePath(), 100, 150);

        setIcon(normalIcon);
        setBorder(null);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Egyéni kattintás kezelés a singleSelect esetében
        if (singleSelect) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!selected) setIcon(hoverIcon);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!selected) setIcon(normalIcon);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    selectThisCard();
                }
            });
        }
    }

    // Egy kártya kiválasztása és a többi deaktíválása
    public void selectThisCard() {
        for (CardLabel label : allCardLabels) {
            label.deselect();
        }
        this.selected = true;
        updateVisualState();
    }

    // Kiválasztás eltávolítása
    public void deselect() {
        this.selected = false;
        updateVisualState();
    }

    // Kiválasztás beállítása
    public void setSelected(boolean selected) {
        this.selected = selected;
        updateVisualState();
    }

    // Kiválasztott állapot lekérdezése
    public boolean isSelected() {
        return selected;
    }

    // Kártya lekérése
    public Card getCard() {
        return card;
    }

    // Kép ikonok lekérése
    public ImageIcon getHoverIcon() {
        return hoverIcon;
    }

    public ImageIcon getNormalIcon() {
        return normalIcon;
    }

    // Vizuális állapot frissítése (pl. határoló szín)
    private void updateVisualState() {
        if (selected) {
            setIcon(hoverIcon);
            setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        } else {
            setIcon(normalIcon);
            setBorder(null);
        }
    }
}
