package model.cards;


import bl.GameLogic;
import model.utilities.BaseModel;

public abstract class SingleTargetCard extends Card {

    //öröklődik a Card ősosztályból
    public SingleTargetCard(String name, String suit, int value, CardType type, String imagePath) {
        super(name, suit, value, type, imagePath);
    }

    //use, ami kéri a GameLogicot, hogy tudjon az éppen aktuális játékkal interaktálni,
    // illetve a baseModel, aki kijátszotta a lapot
    //igazat ad vissza, ha sikeresen ki tudtuk játszani a lapot
    public abstract boolean use(BaseModel baseModel, GameLogic gameLogic);
}