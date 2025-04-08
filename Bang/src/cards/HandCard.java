package cards;

import gamelogic.GameLogic;
import utilities.BaseModel;

public class HandCard extends Card {
    //egy külön osztály, kézben tartott kártyák elhúzására
    public HandCard(String name, String suit, int value) {
        super(name, suit, value, CardType.BEER, "src/assets/cards/cover.png");
    }

}
