package utilities.characters;

import cards.Card;
import cards.DualTargetCard;
import cards.SingleTargetCard;
import cards.browncards.BangCard;
import cards.browncards.MissedCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

public class SuzyLafayette extends BaseModel {
    public SuzyLafayette(Role role) {
        super(new Character("Suzy Lafayette", 4), role);
    }
    //Ha nem marad egy lapja sem, akkor húzhat egyet a pakliból. if hands.empty() -> deck.draw()
    //TODO buggol a húzás üres kéznél

    public boolean isHandEmpty(){
        return handCards.isEmpty();
    }

    @Override
    public void playSingleTargetCard(SingleTargetCard card, GameLogic gameLogic) {
        card.use(this, gameLogic);
        if(isHandEmpty()){
            gameLogic.logUIMessage("Üres a kéz!");
            drawCard();
        }
    }

    @Override
    public void playDualTargetCard(DualTargetCard card, BaseModel target, GameLogic gameLogic) {
        card.use(this, target, gameLogic);
        if(isHandEmpty()){
            gameLogic.logUIMessage("Üres a kéz!");
            drawCard();
        }
    }

    @Override
    public void removeCard(Card card){
        discardCard(card);
        handCards.remove(card);
        if(isHandEmpty()){
            System.out.println("Üres a kéz a removeCard után!");
            drawCard();
        }
    }

    @Override
    public void indiansAction(BaseModel source, GameLogic gameLogic){
        while(true){
            Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Bang! card or pass!");
            if(card instanceof BangCard bangCard){
                discardCard(bangCard);
                handCards.remove(bangCard);
                System.out.println("Rálőttél az indiánokra!");
                if(isHandEmpty()){
                    drawCard();
                }
                return;
            }
            if(card == null){
                break;
            }
        }
        receiveDamage(1, source, gameLogic);
    }

    @Override
    public void duelAction(BaseModel target, GameLogic gameLogic){
        while(true){
            Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Bang! card or pass!");
            if(card instanceof BangCard bangCard){
                discardCard(bangCard);
                handCards.remove(bangCard);
                System.out.println("Rálőttél az ellenre!");
                if(isHandEmpty()){
                    drawCard();
                }
                target.duelAction(this, gameLogic);
                return;
            }
            if(card == null){
                break;
            }
        }
        receiveDamage(1, target, gameLogic);
    }

}
