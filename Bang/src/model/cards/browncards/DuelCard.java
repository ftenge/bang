package model.cards.browncards;

import model.cards.CardType;
import model.cards.DualTargetCard;
import bl.GameLogic;
import model.utilities.BaseModel;

public class DuelCard extends DualTargetCard {
    public DuelCard(String suit, int value, String imagePath) {
        super("Duel", suit, value, CardType.DUEL, imagePath);
    }

    //eldobjuk a kártyát és meghívjuk a target duelAction-jét, ahol a baseModel a paraméter
    @Override
    public boolean use(BaseModel origin, BaseModel target, GameLogic gameLogic) {
        origin.removeCard(this);
        target.duelAction(origin, gameLogic);
        return true;
    }
}