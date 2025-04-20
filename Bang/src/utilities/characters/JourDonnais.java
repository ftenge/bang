package utilities.characters;

import cards.Card;
import cards.browncards.MissedCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

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
        while(true){
            Card card = gameLogic.chooseCard(getHandCards(), getName(),"Choose a Missed! card or pass!");
            if(card instanceof MissedCard missedCard){
                missedAction(missedCard, source, gameLogic);
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


}
