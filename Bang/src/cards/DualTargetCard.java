package cards;


import gamelogic.GameLogic;
import utilities.BaseModel;

public abstract class DualTargetCard extends Card {
    protected String name;
    protected String suit;
    protected int value;
    protected CardType type;

    public DualTargetCard(String name, String suit, int value, CardType type) {
        super(name, suit, value, type);
    }

    public abstract boolean use(BaseModel baseModel, BaseModel target, GameLogic gameLogic);

}