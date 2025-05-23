package model.utilities.characters;

import model.cards.weapons.Weapon;
import model.utilities.BaseModel;
import model.utilities.Character;
import model.utilities.Role;

public class WillyTheKid extends BaseModel {
    public WillyTheKid(Role role, boolean isBot) {
        super(new Character("Willy The Kid", 4), role, isBot);
        this.rapid = true;
    }

    // bárhányszor lőhet banggel
    //done

    @Override
    public void setWeapon(Weapon weapon){
        if(getWeapon() != null){
            discardCard(getWeapon());
            removeWeapon();
        }
        this.weapon = weapon;
        handCards.remove(weapon);
        tableCards.add(this.weapon);
    }

    @Override
    public void removeWeapon(){
        tableCards.remove(weapon);
        this.weapon = null;
    }
}
