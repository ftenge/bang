package utilities.characters;

import cards.Card;
import cards.HandCard;
import cards.browncards.BeerCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;
import utilities.RoleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ElGringo extends BaseModel {
    public ElGringo(Role role, boolean isBot) {
        super(new Character("El Gringo", 3), role, isBot);
    }
    //húz egy lapot attól aki eltalálja, universal pánik, nem pánik, kézből húz
    //done

    @Override
    public void receiveDamage(int damage, BaseModel source, GameLogic gameLogic){
        this.health = getHealth() - damage;
        drawFromSomeonesHand(source, gameLogic);
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

    public void drawFromSomeonesHand(BaseModel target, GameLogic gameLogic){
        int index = 0;
        if(isBot){
            index = new Random().nextInt(target.getHandCards().size());
        }else {
            List<Card> cards = new ArrayList<>();
            for (int i = 0; i < target.getHandCards().size(); i++) {
                String cardName = "Hand card " + (i + 1);
                cards.add(new HandCard(cardName, "", i));
            }

            index = gameLogic.chooseFromHand(cards, name, "Choose a card to steal from " + target.getName());
        }
        handCards.add(target.getHandCards().get(index));
        target.getHandCards().remove(index);
        if(target instanceof SuzyLafayette suzyLafayette){
            if(suzyLafayette.isHandEmpty()){
                suzyLafayette.drawCard();
            }
        }
    }
}
