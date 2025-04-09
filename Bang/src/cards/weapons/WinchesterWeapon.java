package cards.weapons;

import cards.CardType;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class WinchesterWeapon extends Weapon {
    public WinchesterWeapon(String suit, int value, String imagePath) {
        super("Winchester",suit, value, 5, imagePath,false, CardType.WINCHESTER);
    }

    //5 egység lőtávú fegyver
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic){
        baseModel.setWeapon(this);
        return true;
    }
}
