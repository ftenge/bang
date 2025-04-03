//első verziója a játékosnak
/*package players;

import cards.Card;
import cards.Deck;
import cards.weapons.Weapon;
import utilities.Character;
import utilities.Role;
import utilities.RoleType;

import java.util.ArrayList;
import java.util.List;


public class Player {
    protected String name;
    protected int health;
    protected Role role;
    protected List<Card> handCards;
    protected boolean hasBarrel = false;
    protected boolean hasMustang = false;
    protected boolean hasScope = false;
    protected boolean rapid = false;
    protected int visibility = 0;
    protected int fieldView = 1;
    protected Weapon weapon;
    protected Character character;

    public Player(Character character, Role role) {
        this.name = character.getName();
        this.character = character;
        this.health = character.getHealth();
        this.handCards = new ArrayList<>();
        this.role = role;
        if(role.getType() == RoleType.SHERIFF){
            this.health += 1;
        }
        setTraits();
    }

    private void setTraits(){
        if(this.character.getName().equals("Willy The Kid")){
            this.rapid = true;
        }else if(this.character.getName().equals("Rose Doolan")){
            this.fieldView++;
        }else if(this.character.getName().equals("Paul Regret")){
            this.visibility--;
        }
    }

    public void drawCard(Deck deck) {
        handCards.add(deck.draw());
    }

    public void playCard(Card card) {
        //card.use(this);
        handCards.remove(card);
    }

    public int getHealth(){
        return health;
    }

    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
        this.rapid = weapon.isRapid();
        this.fieldView += weapon.getRange();
    }

    public Weapon getWeapon(){
        return weapon;
    }

    public void removeWeapon(){
        this.fieldView -= weapon.getRange();
        this.weapon = null;
        this.rapid = character.getName().equals("Willy The Kid");
    }

    public void setHasBarrel(boolean hasBarrel){
        this.hasBarrel = hasBarrel;
    }

    public boolean getHasBarrel(){
        return hasBarrel;
    }

    public void setVisibility(int visibility){
        this.visibility = visibility;
    }

    public int getVisibility(){
        return visibility;
    }

    public void setFieldView(int fieldView){
        this.fieldView = fieldView;
    }

    public int getFieldView(){
        return fieldView;
    }

    public void setHasMustang(boolean hasMustang){
        if(!this.hasMustang && hasMustang){
            visibility++;
        }else if(this.hasMustang && !hasMustang){
            visibility--;
        }
        this.hasMustang = hasMustang;
    }

    public boolean getHasMustang(){
        return hasMustang;
    }

    public void setHasScope(boolean hasScope){
        if(!this.hasScope && hasScope){
            fieldView++;
        }else if(this.hasScope && !hasScope){
            fieldView--;
        }
        this.hasScope = hasScope;
    }

    public boolean getHasScope(){
        return hasScope;
    }

    public boolean isAlive(){
        return this.health > 0;
    }

}*/
