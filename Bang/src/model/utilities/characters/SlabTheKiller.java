package model.utilities.characters;

import model.utilities.BaseModel;
import model.utilities.Character;
import model.utilities.Role;

public class SlabTheKiller extends BaseModel {
    public SlabTheKiller(Role role, boolean isBot) {
        super(new Character("Slab The Killer", 4), role, isBot);
    }
    //2 nem találtal lehet levédeni a bangjeit;

    //done, BangCard.use és BaseModel.slabTheKillerBangAction
}
