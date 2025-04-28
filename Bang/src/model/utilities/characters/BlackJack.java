package model.utilities.characters;

import model.cards.Card;
import bl.GameLogic;
import model.utilities.BaseModel;
import model.utilities.Character;
import model.utilities.Role;

public class BlackJack extends BaseModel {
    public BlackJack(Role role, boolean isBot) {
        super(new Character("Black Jack", 4), role, isBot);
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
