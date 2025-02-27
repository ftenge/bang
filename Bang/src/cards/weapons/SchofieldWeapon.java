package cards.weapons;

import cards.CardType;

public class SchofieldWeapon extends Weapon {
    public SchofieldWeapon(String suit, int value) {
        super("Schofield",suit, value, 2, false, CardType.SCHOFIELD);
    }
}
