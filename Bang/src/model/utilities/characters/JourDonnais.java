package model.utilities.characters;

import model.cards.Card;
import model.cards.browncards.MissedCard;
import bl.GameLogic;
import model.utilities.BaseModel;
import model.utilities.Character;
import model.utilities.Role;

import java.util.ArrayList;
import java.util.List;

public class JourDonnais extends BaseModel {
    public JourDonnais(Role role, boolean isBot) {
        super(new Character("Jour Donnais", 4), role, isBot);
    }
    //beépített hordó
    //done

    @Override
    public void bangAction(BaseModel source, GameLogic gameLogic){
        if(barrelAction(gameLogic)){
            System.out.println("A beépített hordó levédte!");
            return;
        }
        if(hasBarrel()){
            if(barrelAction(gameLogic)){
                System.out.println("A hordó levédte!");
                return;
            }
        }
        if(isBot){
            List<MissedCard> missedCards = new ArrayList<>();
            for(Card card : this.getHandCards()){
                if(card instanceof MissedCard missedCard){
                    missedCards.add(missedCard);
                }
            }
            if(missedCards.size() >= 2){
                missedAction(missedCards.get(0), source, gameLogic);
                missedAction(missedCards.get(1), source, gameLogic);
                System.out.println("Volt nem talált lap!");
                return;
            }
        }else {
            while (true) {
                Card card = gameLogic.chooseCard(getHandCards(), getName(), "Choose a Missed! card or pass!");
                if (card instanceof MissedCard missedCard) {
                    missedAction(missedCard, source, gameLogic);
                    System.out.println("Volt nem talált lap!");
                    return;
                }
                if (card == null) {
                    break;
                }
            }
        }
        System.out.println("Betalált a bang :/, aktuális hp: " + getHealth());
        receiveDamage(1, source, gameLogic);
        System.out.println("ReceiveDMG utáni hp: " + getHealth());
    }

    public void slabTheKillerBangangAction(BaseModel source, GameLogic gameLogic){
        if(barrelAction(gameLogic)){
            System.out.println("A beépített hordó levédte!");
            return;
        }
        if(hasBarrel()){
            if(barrelAction(gameLogic)){
                System.out.println("A hordó levédte!");
                return;
            }
        }
        if(isBot){
            List<MissedCard> missedCards = new ArrayList<>();
            for(Card card : this.getHandCards()){
                if(card instanceof MissedCard missedCard){
                    missedCards.add(missedCard);
                }
            }
            if(missedCards.size() >= 2){
                missedAction(missedCards.get(0), source, gameLogic);
                missedAction(missedCards.get(1), source, gameLogic);
                System.out.println("Volt nem talált lap!");
                return;
            }
        }else {
            while (true) {
                boolean bothAreMissed = true;
                List<Card> cards = new ArrayList<>();
                cards = gameLogic.selectTwoCards(getHandCards(), name, "Select 2 Missed model.cards to dodge Bang!");
                if (cards.isEmpty()) {
                    break;
                }
                for (Card card : cards) {
                    if (!(card instanceof MissedCard)) {
                        bothAreMissed = false;
                        break;
                    }
                }
                if (bothAreMissed) {
                    for (Card card : cards) {
                        if (card instanceof MissedCard missedCard) {
                            missedAction(missedCard, source, gameLogic);
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


}
