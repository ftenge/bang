package cards.browncards;

import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.characters.SlabTheKiller;

public class BangCard extends DualTargetCard {
    public BangCard(String suit, int value, String imagePath) {
        super("Bang!", suit, value, CardType.BANG, imagePath);
    }


    //a origin megtámadja Bang!-gel a targetet
    //ha az origin, nem lőtt még Bang!-gel vagy tud gyorstüzelni (fegyver vagy tulajdonság miatt)
    //akkor átírjuk, hogy már lőtt Bang!-gel ebben a körben és eldobjuk a lapot
    //ha az origin az slabTheKiller, akkor az ő külön bangAction-jét hívjuk meg a targetnél (2 Missed kártyával lehet csak levédeni)
    //ha másik karakter az origin, akkor a target sima bangactionjét hívjuk meg és igazzal térünk vissza
    @Override
    public boolean use(BaseModel origin, BaseModel target, GameLogic gameLogic) {
        int range = 1 + origin.getFieldView();
        if(origin.hasWeapon()){
            range += origin.getWeapon().getRange();
        }
        if(origin.getVision().get(gameLogic.getPlayers().indexOf(target)) <= range) {
            if (!origin.getBangedThisRound() || origin.getRapid()) {
                origin.setBangedThisRound(true);
                if (origin instanceof SlabTheKiller) {
                    target.slabTheKillerBangangAction(origin, gameLogic);
                    origin.removeCard(this);
                    return true;
                }
                target.bangAction(origin, gameLogic);
                origin.removeCard(this);
                return true;
            }
        }
        return false;
    }

}
