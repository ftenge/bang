package model.utilities.characters;

import model.cards.Card;
import model.cards.bluecards.BarrelCard;
import model.cards.bluecards.MustangCard;
import model.cards.bluecards.ScopeCard;
import model.cards.weapons.Weapon;
import bl.GameLogic;
import model.utilities.BaseModel;
import model.utilities.Character;
import model.utilities.Role;

import java.util.ArrayList;
import java.util.List;

public class KitCarlson extends BaseModel {
    public KitCarlson(Role role, boolean isBot) {
        super(new Character("Kit Carlson", 4), role, isBot);
    }
    //húzáskor 3 lapból választhat 2-t, a maradékot visszateszi

    //done

    @Override
    public void roundStartDraw(GameLogic gameLogic){
        List<Card> cards = new ArrayList<>();
        cards.add(gameInstance.getDeck().draw());
        cards.add(gameInstance.getDeck().draw());
        cards.add(gameInstance.getDeck().draw());

        List<Card> chosenCards = new ArrayList<>();

        if(isBot){
            if(!this.hasBarrel()){
                for(Card card : cards){
                    if(card instanceof BarrelCard){
                        chosenCards.addFirst(card);
                    }
                }
            }
            if(!this.hasMustang()){
                for(Card card : cards){
                    if(card instanceof MustangCard){
                        chosenCards.addFirst(card);
                    }
                }
            }
            if(!this.hasScope()){
                for(Card card : cards){
                    if(card instanceof ScopeCard){
                        chosenCards.addFirst(card);
                    }
                }
            }
            int weaponrange = this.hasWeapon() ? this.getWeapon().getRange() : 1;
            if(weaponrange < 5){
                for(Card card : cards){
                    if(card instanceof Weapon weaponCard){
                        if(weaponCard.getRange() > weaponrange){
                            chosenCards.addFirst(weaponCard);
                        }
                    }
                }
            }
            if (chosenCards.isEmpty()) {
                handCards.add(cards.get(0));
                handCards.add(cards.get(1));
                gameInstance.getDeck().putBack(cards.getLast());
                return;
            }else if(chosenCards.size() < 2){
                if(chosenCards.getFirst().equals(cards.getFirst())){
                    chosenCards.add(cards.get(1));
                }else{
                    chosenCards.add(cards.getFirst());
                }
            }
        }else {

            chosenCards = gameLogic.selectTwoCards(cards, name, "Choose 2 model.cards and put back the rest!");
            if (chosenCards.isEmpty()) {
                handCards.add(cards.get(0));
                handCards.add(cards.get(1));
                gameInstance.getDeck().putBack(cards.getLast());
                return;
            }
        }
        handCards.add(chosenCards.get(0));
        handCards.add(chosenCards.get(1));
        gameInstance.getDeck().putBack(chosenCards.getLast());


    }
}
