package cards;


import players.Player;
import utilities.BaseModel;

public abstract class Card {
    protected String name;
    protected String suit;
    protected int value;
    protected CardType type;

    public Card(String name, String suit, int value, CardType type) {
        this.name = name;
        this.suit = suit;
        this.value = value;
        this.type = type;
    }
    public String getName(){
        return this.name;
    }

    public String toString(){
        return (this.name + " " + this.suit + " " + this.value + " " + this.type);
    }

    public String getSuit(){
        return this.suit;
    }

    public int getValue(){
        return  this.value;
    }
}