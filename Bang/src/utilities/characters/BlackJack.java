package utilities.characters;

import cards.Card;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

public class BlackJack extends BaseModel {
    public BlackJack(Role role) {
        super(new Character("Black Jack", 4), role);
    }
    //amikor sorra kerül és lapot
    //húz, fel kell mutatnia a második húzott lapot – ha kőr
    //vagy káró, húz egy további lapot (amit már nem kell
    //felmutatnia).

    //done

    @Override
    public void roundStartDraw(GameLogic gameLogic){
        drawCard();
        Card card = drawRetCard(gameLogic);
        addHand(card);
        if(card.getSuit().equals("Hearts") || card.getSuit().equals("Diamonds")){
            drawCard();
        }
    }
}
