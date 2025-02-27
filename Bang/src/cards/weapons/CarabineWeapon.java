package cards.weapons;

import cards.CardType;

public class CarabineWeapon extends Weapon {
    public CarabineWeapon(String suit, int value) {
        super("Carabine",suit, value, 4, false, CardType.CARABINE);
    }
}
