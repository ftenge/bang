package cards.weapons;

import cards.CardType;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class VolcanicWeapon extends Weapon {
    public VolcanicWeapon(String suit, int value, String imagePath) {
        super("Volcanic",suit, value, 1, imagePath,true, CardType.VOLCANIC);
    }

    //1 egység lőtávú fegyver, az egyetlen gyorstüzelő
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic){
        baseModel.setWeapon(this);
        return true;
    }
}
