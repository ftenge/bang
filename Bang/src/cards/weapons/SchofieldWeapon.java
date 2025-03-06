package cards.weapons;

import cards.CardType;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class SchofieldWeapon extends Weapon {
    public SchofieldWeapon(String suit, int value) {
        super("Schofield",suit, value, 2, false, CardType.SCHOFIELD);
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic){
        baseModel.setWeapon(this);
        return true;
    }
}
