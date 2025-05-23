package model.cards.browncards;

import model.cards.CardType;
import model.cards.SingleTargetCard;
import bl.GameLogic;
import model.utilities.BaseModel;

public class StagecoachCard extends SingleTargetCard {
    public StagecoachCard(String suit, int value, String imagePath) {
        super("Stagecoach", suit, value, CardType.STAGECOACH, imagePath);
    }

    //eldobjuk a kártyát és az origin húz 2 lapot
    @Override
    public boolean use(BaseModel origin, GameLogic gameLogic) {
        for(int i = 0; i < 2; i++){
            origin.drawCard();
        }
        origin.removeCard(this);
        return true;
    }
}
