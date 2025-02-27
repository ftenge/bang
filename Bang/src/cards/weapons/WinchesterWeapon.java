package cards.weapons;

import cards.CardType;

public class WinchesterWeapon extends Weapon {
    public WinchesterWeapon(String suit, int value) {
        super("Winchester",suit, value, 5, false, CardType.WINCHESTER);
    }
}
