package cards.weapons;

import cards.CardType;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class VolcanicWeapon extends Weapon {
    public VolcanicWeapon(String suit, int value) {
        super("Volcanic",suit, value, 1, true, CardType.VOLCANIC);
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic){
        baseModel.setWeapon(this);
        return true;
    }
}
