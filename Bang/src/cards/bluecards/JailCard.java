package cards.bluecards;

import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.RoleType;

public class JailCard extends DualTargetCard {

    public JailCard(String suit, int value, String imagePath) {
        super("Jail", suit, value, CardType.JAIL, imagePath);
    }

    //ha még nincs előtte börtön kártya és nem a sheriff,
    //akkor a baseModel bebörtönzi a targetet:
    //a baseModel kezéből kikerül a kártya,
    //a target elé kerül a börtönkártya
    //igazat ad vissza ha sikerül, hamisat ha nem
    @Override
    public boolean use(BaseModel baseModel, BaseModel target, GameLogic gameLogic) {
        if(!target.inJail() && target.getRole().getType() != RoleType.SHERIFF){
            baseModel.removeCardFromHand(this);
            target.addJail(this);
            return true;
        }
        return false;
    }

}
