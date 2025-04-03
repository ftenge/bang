package cards;


import utilities.BaseModel;

public abstract class Card {
    protected String name;
    protected String suit;
    protected int value;
    protected CardType type;

    //abstract kártya osztály, minden kártyának van neve, színe, értéke és típusa

    public Card(String name, String suit, int value, CardType type) {
        this.name = name;
        this.suit = suit;
        this.value = value;
        this.type = type;
    }

    //visszaadja a kártya nevét
    public String getName(){
        return this.name;
    }

    //stringgé alakítja a kártya adatait
    public String toString(){
        return (this.name + " " + this.suit + " " + this.value + " " + this.type);
    }

    //visszaadja a kártya színét
    public String getSuit(){
        return this.suit;
    }

    //visszaadja a kártya értékét
    public int getValue(){
        return  this.value;
    }
}