package cards.weapons;

import cards.CardType;
import cards.SingleTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;

public class Weapon extends SingleTargetCard {
    protected int range;
    protected boolean rapid;

    //a fegyvernek van lőtávja (milyen messzire tudunk lőni Bang!-gel),
    // és gyorstüzelésű-e (ki tudunk-e egynél több Bang!-t játszani egy körben)
    public Weapon(String name, String suit, int value, int range, String imagePath, boolean rapid, CardType type) {
        super(name, suit, value, type, imagePath);
        this.range = range;
        this.rapid = rapid;
    }

    //visszaadja a fegyver lőtávját
    public int getRange() {
        return range;
    }

    //visszaadja, hogy a fegyver gyorstüzelésű-e
    public boolean isRapid(){
        return rapid;
    }

    //berakja a fegyvert az azt kijátszó játékosnak
    @Override
    public boolean use(BaseModel baseModel, GameLogic gameLogic) {
        baseModel.setWeapon(this);
        return true;
    }

}