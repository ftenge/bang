package cards.browncards;

import cards.CardType;
import cards.DualTargetCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.characters.CalamityJanet;
import utilities.characters.SlabTheKiller;

public class MissedCard extends DualTargetCard {
    public MissedCard(String suit, int value) {
        super("Missed", suit, value, CardType.MISSED);
    }

    //eldobjuk a kártyát
    //ha az origin CalamityJanet, akkor itt a Missed kártyája Bang!-ként funkcionál:
    //ha még nem lőtt vagy van gyorstüzelője akkor:
    //beállítjuk, hogy lőtt ebben a körben és meghívjuk a target bangAction-jét
    //igazzal térünk vissza
    @Override
    public boolean use(BaseModel origin, BaseModel target, GameLogic gameLogic) {
        origin.removeCard(this);
        if(origin instanceof CalamityJanet){
            if(!origin.getBangedThisRound() || origin.getRapid()) {
                origin.setBangedThisRound(true);
                target.bangAction(origin, gameLogic);
            }
        }
        // Implementation of missed
        return true;
    }
}