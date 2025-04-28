package model.cards;


import bl.GameLogic;
import model.utilities.BaseModel;

public abstract class DualTargetCard extends Card {

    //öröklődik a Card ősosztályból
    public DualTargetCard(String name, String suit, int value, CardType type, String imagePath) {
        super(name, suit, value, type, imagePath);
    }

    //use, ami kéri a GameLogicot, hogy tudjon az éppen aktuális játékkal interaktálni,
    // egy baseModel, aki kijátszotta a lapot
    // és egy target, akit megcélzünk a lappal
    //igazat ad vissza, ha sikeresen ki tudtuk játszani a lapot
    public abstract boolean use(BaseModel baseModel, BaseModel target, GameLogic gameLogic);

}