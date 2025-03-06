package utilities.characters;

import cards.Card;
import cards.browncards.BeerCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

public class ElGringo extends BaseModel {
    public ElGringo(Role role) {
        super(new Character("El Gringo", 3), role);
    }
    //húz egy lapot attól aki eltalálja, universal pánik
    //bug: el tudja húzni azt a lapot, amivel megsebezték

    @Override
    public void receiveDamage(int damage, BaseModel source, GameLogic gameLogic){
        panicAction(source, gameLogic);
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
