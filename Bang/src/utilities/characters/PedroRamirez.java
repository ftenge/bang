package utilities.characters;

import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

public class PedroRamirez extends BaseModel {
    public PedroRamirez(Role role) {
        super(new Character("Pedro Ramirez", 4), role);
    }
    // Az első lapot a dobott lapok közül is választhatja.
}
