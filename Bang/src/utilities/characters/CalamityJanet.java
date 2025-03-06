package utilities.characters;

import cards.Card;
import cards.browncards.BangCard;
import cards.browncards.MissedCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

import java.util.ArrayList;
import java.util.List;

public class CalamityJanet extends BaseModel {
    public CalamityJanet(Role role) {
        super(new Character("Calamity Janet", 4), role);
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
        while(true){
            boolean bothAreMissed = true;
            List<Card> cards = new ArrayList<>();
            cards = gameLogic.selectTwoCards(getHandCards(), name, "Select 2 Missed/Bang! cards to dodge Bang!");
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
        System.out.println("Betalált a bang :/, aktuális hp: " + getHealth());
        receiveDamage(1, source, gameLogic);
        System.out.println("ReceiveDMG utáni hp: " + getHealth());
    }

    @Override
    public void indiansAction(BaseModel source, GameLogic gameLogic){
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
        receiveDamage(1, source, gameLogic);
    }

    @Override
    public void duelAction(BaseModel target, GameLogic gameLogic){
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
        receiveDamage(1, target, gameLogic);
    }
}
