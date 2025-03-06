package cards.weapons;

import cards.CardType;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class WinchesterWeapon extends Weapon {
    public WinchesterWeapon(String suit, int value) {
        super("Winchester",suit, value, 5, false, CardType.WINCHESTER);
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic){
        baseModel.setWeapon(this);
        return true;
    }
}
