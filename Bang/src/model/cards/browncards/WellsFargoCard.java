package model.cards.browncards;

import model.cards.CardType;
import model.cards.SingleTargetCard;
import bl.GameLogic;
import model.utilities.BaseModel;

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
