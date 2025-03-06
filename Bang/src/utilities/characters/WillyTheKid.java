package utilities.characters;

import cards.weapons.Weapon;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

public class WillyTheKid extends BaseModel {
    public WillyTheKid(Role role) {
        super(new Character("Willy The Kid", 4), role);
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
        tableCards.add(this.weapon);
    }

    @Override
    public void removeWeapon(){
        tableCards.remove(weapon);
        this.weapon = null;
    }
}
