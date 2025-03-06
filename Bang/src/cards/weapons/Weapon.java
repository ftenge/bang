package cards.weapons;

import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class Weapon extends SingleTargetCard {
    protected int range;
    protected boolean rapid;

    public Weapon(String name, String suit, int value, int range, boolean rapid, CardType type) {
        super(name, suit, value, type);
        this.range = range;
        this.rapid = rapid;
    }

    public int getRange() {
        return range;
    }
    public boolean isRapid(){
        return rapid;
    }

    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        baseModel.setWeapon(this);
        return true;
    }

}