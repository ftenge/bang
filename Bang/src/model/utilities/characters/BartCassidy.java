package model.utilities.characters;

import model.cards.Card;
import model.cards.browncards.BeerCard;
import bl.GameLogic;
import model.utilities.BaseModel;
import model.utilities.Character;
import model.utilities.Role;
import model.utilities.RoleType;

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
                            //System.out.println("Visszahoztad magad az életbe!");
                            return;
                        }
                    }
                } else {
                    while (true) {
                        Card card = gameLogic.chooseCard(getHandCards(), name, "Válassz egy Sört vagy passzolj!");
                        if (card instanceof BeerCard beerCard) {
                            playSingleTargetCard(beerCard, gameLogic);
                        }
                        if (health > 0) {
                            //System.out.println("Visszahoztad magad az életbe!");
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
            gameLogic.logUIMessage(this.name + " kiesett!");
            gameLogic.logUIMessage(this.name + " egy " + this.getRole().toString() + " volt");
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
