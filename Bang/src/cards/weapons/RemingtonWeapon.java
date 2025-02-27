package cards.weapons;

import cards.CardType;

public class RemingtonWeapon extends Weapon {
    public RemingtonWeapon(String suit, int value) {
        super("Remington",suit, value, 3, false, CardType.REMINGTON);
    }
}
