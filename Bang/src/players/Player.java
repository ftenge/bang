package players;

import cards.Card;
import cards.Deck;
import cards.weapons.Weapon;
import utilities.Character;

import java.util.ArrayList;
import java.util.List;


public class Player {
    protected String name;
    protected int health;
    protected Role role;
    protected String personality;
    protected List<Card> handCards;
    protected boolean barrel;
    protected int visibility;
    protected int fieldView;
    protected Weapon weapon;
    protected Character character;

    public Player(String name, Character character) {
        this.name = name;
        this.character = character;
        this.health = character.getHealth();
        this.handCards = new ArrayList<>();
    }

    public void drawCard(Deck deck) {
        handCards.add(deck.draw());
    }

    public void playCard(Card card, Player target) {
        //card.use(this, target);
        handCards.remove(card);
    }
}
