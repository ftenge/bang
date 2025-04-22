package utilities.characters;

import cards.Card;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

public class SidKetchum extends BaseModel {
    protected int discarded = 0;
    public SidKetchum(Role role, boolean isBot) {
        super(new Character("Sid Ketchum", 4), role, isBot);
    }
    //Két lapot eldobva visszanyer egy életet.

    //done

    @Override
    public void roundStart(GameLogic gameLogic){
        discarded = 0;
        setVision();
        bangedThisRound = false;
        roundStartDraw(gameLogic);
    }

    @Override
    public void removeCard(Card card){
        discardCard(card);
        handCards.remove(card);
        if(discarded == 1){
            discarded = 0;
            if(health < maxHP){
                health++;
            }
        }else {
            discarded++;
        }
    }
}
