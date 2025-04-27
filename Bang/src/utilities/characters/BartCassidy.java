package utilities.characters;

import cards.Card;
import cards.browncards.BeerCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;
import utilities.RoleType;

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
            if(gameLogic.getPlayers().size() > 2) {
                if (this.isBot) {
                    for (Card card : getHandCards()) {
                        if (card instanceof BeerCard beerCard) {
                            playSingleTargetCard(beerCard, gameLogic);
                        }
                        if (health > 0) {
                            System.out.println("Visszahoztad magad az életbe!");
                            return;
                        }
                    }
                } else {
                    while (true) {
                        Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Beer card or pass!");
                        if (card instanceof BeerCard beerCard) {
                            playSingleTargetCard(beerCard, gameLogic);
                        }
                        if (health > 0) {
                            System.out.println("Visszahoztad magad az életbe!");
                            return;
                        }
                        if (card == null) {
                            break;
                        }
                    }
                }
            }
            if(role.getType() == RoleType.OUTLAW){
                source.killedAnOutlaw();
            }
            if(role.getType() == RoleType.DEPUTY && source.getRole().getType() == RoleType.SHERIFF){
                source.sheriffKilledDeputy();
            }
            gameLogic.logUIMessage(this.name + " has died!");
            gameLogic.logUIMessage(this.name + " was a " + this.getRole().toString());
            for(BaseModel baseModel : gameInstance.getPlayers()){
                if(baseModel instanceof VultureSam vultureSam && baseModel.equals(this)){
                    vultureSam.collectCard(handCards);
                    gameLogic.aPlayerRemoved(this);
                    return;
                }
            }
            discardCardsUponDeath();
            gameLogic.aPlayerRemoved(this);
        }
    }
}
