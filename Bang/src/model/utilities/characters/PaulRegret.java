package model.utilities.characters;

import model.utilities.BaseModel;
import model.utilities.Character;
import model.utilities.Role;

public class PaulRegret extends BaseModel {
    public PaulRegret(Role role, boolean isBot) {
        super(new Character("Paul Regret", 3), role, isBot);
        this.visibility--;
    }

    //többeik eggyel messzebről látják

    //done
}
