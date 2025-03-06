package utilities.characters;

import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

public class PaulRegret extends BaseModel {
    public PaulRegret(Role role) {
        super(new Character("Paul Regret", 3), role);
        this.visibility--;
    }

    //többeik eggyel messzebről látják

    //done
}
