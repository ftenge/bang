package utilities.characters;

import cards.Card;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

import java.util.ArrayList;
import java.util.List;

public class PedroRamirez extends BaseModel {
    public PedroRamirez(Role role, boolean isBot) {
        super(new Character("Pedro Ramirez", 4), role, isBot);
    }
    // Az első lapot a dobott lapok közül is választhatja.

    //done

    @Override
    public void roundStartDraw(GameLogic gameLogic){
        if(!gameInstance.getDeck().isDiscardPileEmpty()){

            int decision = gameLogic.chooseOption(name, "Choose to draw normally or draw the first card from the discard pile!", "Draw normally", gameInstance.getDeck().lastDiscardedName());
            if(decision == 1){
                Card card = gameInstance.getDeck().getLastDiscard();
                handCards.add(card);
                drawCard();
                return;
            }
        }
        for(int i = 0; i < 2; i++){
            drawCard();
        }
    }
}
