package cards.weapons;

import cards.CardType;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class RemingtonWeapon extends Weapon {
    public RemingtonWeapon(String suit, int value, String imagePath) {
        super("Remington",suit, value, 3, imagePath, false, CardType.REMINGTON);
    }

    //3 egység lőtávú fegyver
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic){
        baseModel.setWeapon(this);
        return true;
    }
}
