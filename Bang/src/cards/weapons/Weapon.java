package cards.weapons;

import cards.Card;
import cards.CardType;
import players.Player;

public class Weapon extends Card {
    protected int range;
    protected boolean rapid;

    public Weapon(String name, String suit, int value, int range, boolean rapid, CardType type) {
        super(name, suit, value, type);
        this.range = range;
        this.rapid = rapid;
    }

    public int getRange() {
        return range;
    }
    public boolean isRapid(){
        return rapid;
    }

    @Override
    public void use() {
        System.out.println(name + " used, range: " + range);
    }
}