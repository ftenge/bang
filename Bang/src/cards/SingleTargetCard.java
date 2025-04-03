package cards;


import gamelogic.GameLogic;
import utilities.BaseModel;

public abstract class SingleTargetCard extends Card {
    protected String name;
    protected String suit;
    protected int value;
    protected CardType type;

    //öröklődik a Card ősosztályból
    public SingleTargetCard(String name, String suit, int value, CardType type) {
        super(name, suit, value, type);
    }

    //use, ami kéri a GameLogicot, hogy tudjon az éppen aktuális játékkal interaktálni,
    // illetve a baseModel, aki kijátszotta a lapot
    //igazat ad vissza, ha sikeresen ki tudtuk játszani a lapot
    public abstract boolean use(BaseModel baseModel, GameLogic gameLogic);
}