package cards;


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

    public abstract void use();
}