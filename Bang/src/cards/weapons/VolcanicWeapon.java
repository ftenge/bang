package cards.weapons;

import cards.CardType;

public class VolcanicWeapon extends Weapon {
    public VolcanicWeapon(String suit, int value) {
        super("Volcanic",suit, value, 1, true, CardType.VOLCANIC);
    }
}
