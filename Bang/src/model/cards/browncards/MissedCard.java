package model.cards.browncards;

import model.cards.CardType;
import model.cards.DualTargetCard;
import bl.GameLogic;
import model.utilities.BaseModel;
import model.utilities.characters.CalamityJanet;

public class MissedCard extends DualTargetCard {
    public MissedCard(String suit, int value, String imagePath) {
        super("Missed", suit, value, CardType.MISSED, imagePath);
    }

    //eldobjuk a kártyát
    //ha az origin CalamityJanet, akkor itt a Missed kártyája Bang!-ként funkcionál:
    //ha még nem lőtt vagy van gyorstüzelője akkor:
    //beállítjuk, hogy lőtt ebben a körben és meghívjuk a target bangAction-jét
    //igazzal térünk vissza
    @Override
    public boolean use(BaseModel origin, BaseModel target, GameLogic gameLogic) {
        if (origin instanceof CalamityJanet) {
            if (!origin.getBangedThisRound() || origin.getRapid()) {
                origin.setBangedThisRound(true);
                target.bangAction(origin, gameLogic);
                origin.removeCard(this);
                return true;
            }
        }
        if(!origin.equals(target)) {
            origin.removeCard(this);
            return true;
        }
        return false;
    }
}