package cards;

import gamelogic.GameLogic;
import utilities.BaseModel;

public class HandCard extends Card {
    public HandCard(String name, String suit, int value) {
        super(name, suit, value, CardType.BEER);
    }

}
