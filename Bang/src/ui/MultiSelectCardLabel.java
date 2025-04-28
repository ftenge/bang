package ui;

import model.cards.Card;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MultiSelectCardLabel extends CardLabel {
    private final List<Card> selected;
    private final JDialog dialog;

    public MultiSelectCardLabel(Card card, ImageIcon normalIcon, ImageIcon hoverIcon,
                                List<CardLabel> allCardLabels, List<Card> selected, JDialog dialog) {
        super(card, allCardLabels, false); // <-- nem singleSelect
        this.selected = selected;
        this.dialog = dialog;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isSelected()) {
                    setSelected(false);
                    selected.remove(getCard());
                } else {
                    if (selected.size() < 2) {
                        setSelected(true);
                        selected.add(getCard());
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Choose two cards at most!");
                    }
                }
            }
        });
    }
}
