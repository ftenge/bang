package model.utilities.characters;

import model.cards.Card;
import model.cards.browncards.BangCard;
import model.cards.browncards.MissedCard;
import bl.GameLogic;
import model.utilities.BaseModel;
import model.utilities.Character;
import model.utilities.Role;

import java.util.ArrayList;
import java.util.List;

public class CalamityJanet extends BaseModel {
    public CalamityJanet(Role role, boolean isBot) {
        super(new Character("Calamity Janet", 4), role, isBot);
    }
    //bang és missed használhatja egymás helyett

    //done

    @Override
    public void bangAction(BaseModel source, GameLogic gameLogic){
        if(hasBarrel()){
            if(barrelAction(gameLogic)){
                System.out.println("A hordó levédte!");
                return;
            }
        }
        if(isBot){
            for(Card card : this.getHandCards()){
                if(card instanceof MissedCard missedCard){
                    missedAction(missedCard, source, gameLogic);
                    return;
                }else if(card instanceof BangCard bangCard){
                    handCards.remove(bangCard);
                    discardCard(bangCard);
                    return;
                }
            }
        }else {
            while(true){
                Card card = gameLogic.chooseCard(getHandCards(), getName(),"Choose a Bang!/Missed! card or pass!");
                if(card instanceof MissedCard missedCard){
                    handCards.remove(missedCard);
                    discardCard(missedCard);
                    System.out.println("Volt nem talált lap!");
                    return;
                }else if(card instanceof BangCard bangCard){
                    handCards.remove(bangCard);
                    discardCard(bangCard);
                    System.out.println("Volt nem talált lap!");
                    return;
                }
                if(card == null){
                    break;
                }
            }
        }
        System.out.println("Betalált a bang :/, aktuális hp: " + getHealth());
        receiveDamage(1, source, gameLogic);
        System.out.println("ReceiveDMG utáni hp: " + getHealth());
    }

    @Override
    public void slabTheKillerBangangAction(BaseModel source, GameLogic gameLogic){
        if(hasBarrel()){
            if(barrelAction(gameLogic)){
                System.out.println("A hordó levédte!");
                return;
            }
        }
        if(isBot){
            List<Card> missedCards = new ArrayList<>();
            for(Card card : this.getHandCards()){
                if(card instanceof MissedCard missedCard){
                    missedCards.add(missedCard);
                }else if(card instanceof BangCard bangCard){
                    missedCards.add(bangCard);
                }
            }
            if(missedCards.size() >= 2){
                handCards.remove(missedCards.get(0));
                discardCard(missedCards.get(0));
                handCards.remove(missedCards.get(1));
                discardCard(missedCards.get(1));
                System.out.println("Volt nem talált lap!");
                return;
            }
        }else {
            while(true){
                boolean bothAreMissed = true;
                List<Card> cards = new ArrayList<>();
                cards = gameLogic.selectTwoCards(getHandCards(), name, "Select 2 Missed/Bang! model.cards to dodge Bang!");
                cards.removeLast();
                if(cards == null){
                    break;
                }
                for(Card card : cards){
                    if(!(card instanceof MissedCard) && !(card instanceof BangCard)){
                        bothAreMissed = false;
                        break;
                    }
                }
                if(bothAreMissed) {
                    for (Card card : cards) {
                        if (card instanceof MissedCard missedCard) {
                            handCards.remove(missedCard);
                            discardCard(missedCard);
                            System.out.println("Volt nem talált lap!");
                        }if (card instanceof BangCard bangCard) {
                            handCards.remove(bangCard);
                            discardCard(bangCard);
                            System.out.println("Volt nem talált lap!");
                        }
                    }
                    return;
                }
            }
        }
        System.out.println("Betalált a bang :/, aktuális hp: " + getHealth());
        receiveDamage(1, source, gameLogic);
        System.out.println("ReceiveDMG utáni hp: " + getHealth());
    }

    @Override
    public void indiansAction(BaseModel source, GameLogic gameLogic){
        if(isBot){
            for(Card card : this.getHandCards()){
                if(card instanceof BangCard || card instanceof MissedCard){
                    removeCard(card);
                    return;
                }
            }
        }else{
            while(true){
                Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Bang!/Missed! card or pass!");
                if(card instanceof BangCard bangCard){
                    discardCard(bangCard);
                    handCards.remove(bangCard);
                    System.out.println("Rálőttél az indiánokra!");
                    return;
                }else if(card instanceof MissedCard missedCard){
                    discardCard(missedCard);
                    handCards.remove(missedCard);
                    System.out.println("Rálőttél az indiánokra!");
                    return;
                }
                if(card == null){
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
                if(card instanceof BangCard || card instanceof MissedCard){
                    removeCard(card);
                    return;
                }
            }
        }else {
            while(true){
                Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Bang!/Missed! card or pass!");
                if(card instanceof BangCard bangCard){
                    discardCard(bangCard);
                    handCards.remove(bangCard);
                    target.duelAction(this, gameLogic);
                    System.out.println("Rálőttél az ellenre!");
                    return;
                }else if(card instanceof MissedCard missedCard){
                    discardCard(missedCard);
                    handCards.remove(missedCard);
                    target.duelAction(this, gameLogic);
                    System.out.println("Rálőttél az ellenre!");
                    return;
                }
                if(card == null){
                    break;
                }
            }
        }
        receiveDamage(1, target, gameLogic);
    }
}
