package cards.browncards;

import cards.Card;
import cards.CardType;
import cards.SingleTargetCard;
import gameinstance.GameInstance;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class WellsFargoCard extends SingleTargetCard {
    public WellsFargoCard(String suit, int value, String imagePath) {
        super("Wells Fargo", suit, value, CardType.WELLS_FARGO, imagePath);
    }

    //eldobjuk a kártyát és az origin húz 2 lapot
    @Override
    public boolean use(BaseModel origin, GameLogic gameLogic) {
        for(int i = 0; i < 3; i++){
            origin.drawCard();
        }
        origin.removeCard(this);
        return true;
    }

}
