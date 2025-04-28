package model.utilities.characters;

import model.cards.Card;
import model.cards.DualTargetCard;
import model.cards.SingleTargetCard;
import model.cards.browncards.BangCard;
import bl.GameLogic;
import model.utilities.BaseModel;
import model.utilities.Character;
import model.utilities.Role;

public class SuzyLafayette extends BaseModel {
    public SuzyLafayette(Role role, boolean isBot) {
        super(new Character("Suzy Lafayette", 4), role, isBot);
    }
    //Ha nem marad egy lapja sem, akkor húzhat egyet a pakliból. if hands.empty() -> deck.draw()
    //TODO buggol a húzás üres kéznél

    public boolean isHandEmpty(){
        return handCards.isEmpty();
    }

    @Override
    public boolean playSingleTargetCard(SingleTargetCard card, GameLogic gameLogic) {
        boolean ret = card.use(this, gameLogic);
        if(isHandEmpty()){
            gameLogic.logUIMessage("Üres a kéz!");
            drawCard();
        }
        return ret;
    }

    @Override
    public boolean playDualTargetCard(DualTargetCard card, BaseModel target, GameLogic gameLogic) {
        boolean ret = card.use(this, target, gameLogic);
        if(isHandEmpty()){
            gameLogic.logUIMessage("Üres a kéz!");
            drawCard();
        }
        return ret;
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
        if(isBot){
            for(Card card : this.getHandCards()){
                if(card instanceof BangCard bangCard){
                    removeCard(bangCard);
                    if (isHandEmpty()) {
                        drawCard();
                    }
                    return;
                }
            }
        }else {
            while (true) {
                Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Bang! card or pass!");
                if (card instanceof BangCard bangCard) {
                    discardCard(bangCard);
                    handCards.remove(bangCard);
                    System.out.println("Rálőttél az indiánokra!");
                    if (isHandEmpty()) {
                        drawCard();
                    }
                    return;
                }
                if (card == null) {
                    break;
                }
            }
        }
        receiveDamage(1, source, gameLogic);
    }

    @Override
    public void duelAction(BaseModel target, GameLogic gameLogic){
        if(isBot){
            for(Card card : this.getHandCards()){
                if(card instanceof BangCard bangCard){
                    removeCard(bangCard);
                    target.duelAction(this, gameLogic);
                    return;
                }
            }
        }else {
            while (true) {
                Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Bang! card or pass!");
                if (card instanceof BangCard bangCard) {
                    discardCard(bangCard);
                    handCards.remove(bangCard);
                    System.out.println("Rálőttél az ellenre!");
                    if (isHandEmpty()) {
                        drawCard();
                    }
                    target.duelAction(this, gameLogic);
                    return;
                }
                if (card == null) {
                    break;
                }
            }
        }
        receiveDamage(1, target, gameLogic);
    }

}
