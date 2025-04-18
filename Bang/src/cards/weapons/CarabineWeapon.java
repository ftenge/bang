package cards.weapons;

import cards.CardType;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class CarabineWeapon extends Weapon {
    public CarabineWeapon(String suit, int value, String imagePath) {
        super("Carabine",suit, value, 4, imagePath, false, CardType.CARABINE);
    }

    //4 egység lőtávú fegyver
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic){
        baseModel.setWeapon(this);
        return true;
    }
}
