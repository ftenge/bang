package model.cards;

public class HandCard extends Card {
    //egy külön osztály, kézben tartott kártyák elhúzására
    public HandCard(String name, String suit, int value) {
        super(name, suit, value, CardType.COVER, "src/assets/cards/cover.png");
    }

}
