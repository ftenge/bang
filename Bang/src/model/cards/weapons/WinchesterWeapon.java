package model.cards.weapons;

import model.cards.CardType;
import bl.GameLogic;
import model.utilities.BaseModel;

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
