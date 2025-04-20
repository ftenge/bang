package utilities.characters;

import cards.Card;
import cards.browncards.BeerCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

public class BartCassidy extends BaseModel {
    public BartCassidy(Role role, boolean isBot) {
        super(new Character("Bart Cassidi", 4), role, isBot);
    }
    //ahányszor eltalálják húz egyet

    //done

    @Override
    public void receiveDamage(int damage, BaseModel source, GameLogic gameLogic){
        this.health = getHealth() - damage;
        for(int i = 0; i < damage; i++){
            drawCard();
        }
        if(health <= 0){
            while(true){
                Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Beer card or pass!");
                if(card instanceof BeerCard beerCard){
                    playSingleTargetCard(beerCard, gameLogic);
                }
                if(health > 0){
                    System.out.println("Visszahoztad magad az életbe!");
                    return;
                }
                if(card == null){
                    return;
                }
            }
        }
    }
}
