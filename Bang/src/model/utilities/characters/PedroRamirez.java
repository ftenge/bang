package model.utilities.characters;

import model.cards.Card;
import model.cards.bluecards.BarrelCard;
import model.cards.bluecards.MustangCard;
import model.cards.bluecards.ScopeCard;
import model.cards.weapons.Weapon;
import bl.GameLogic;
import model.utilities.BaseModel;
import model.utilities.Character;
import model.utilities.Role;

public class PedroRamirez extends BaseModel {
    public PedroRamirez(Role role, boolean isBot) {
        super(new Character("Pedro Ramirez", 4), role, isBot);
    }
    // Az első lapot a dobott lapok közül is választhatja.

    //done

    @Override
    public void roundStartDraw(GameLogic gameLogic){
        if(!gameInstance.getDeck().isDiscardPileEmpty()){
            if(isBot){
                Card card = gameInstance.getDeck().getLastDiscard();

                if(!this.hasMustang()){
                    if(card instanceof MustangCard){
                        handCards.add(card);
                        drawCard();
                        return;
                    }
                }
                if(!this.hasBarrel()){
                    if(card instanceof BarrelCard){
                        handCards.add(card);
                        drawCard();
                        return;
                    }
                }
                if(!this.hasScope()){
                    if(card instanceof ScopeCard){
                        handCards.add(card);
                        drawCard();
                        return;
                    }
                }
                if(card instanceof Weapon weaponCard) {
                    int weaponrange = this.hasWeapon() ? this.getWeapon().getRange() : 1;
                    if (weaponrange < 5) {
                        if (weaponCard.getRange() > weaponrange) {
                            handCards.add(card);
                            drawCard();
                            return;
                        }
                    }
                }
            }else {

                int decision = gameLogic.chooseOption(name, "Válassz, hogy normálisan húzol vagy az első kártyát a dobópakli tetejéről húzod!", "Normális húzás", gameInstance.getDeck().lastDiscardedName());
                if (decision == 1) {
                    Card card = gameInstance.getDeck().getLastDiscard();
                    handCards.add(card);
                    drawCard();
                    return;
                }
            }
        }
        for(int i = 0; i < 2; i++){
            drawCard();
        }
    }
}
