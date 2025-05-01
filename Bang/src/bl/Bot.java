package bl;

import model.cards.*;
import model.cards.bluecards.*;
import model.cards.browncards.BangCard;
import model.cards.browncards.BeerCard;
import model.cards.browncards.MissedCard;
import model.cards.browncards.SaloonCard;
import model.cards.weapons.*;
import model.utilities.BaseModel;
import model.utilities.characters.CalamityJanet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Bot {
    private static final int SLEEP_CONSTANT = 200;

    public static void takeTurn(BaseModel bot, GameLogic gameLogic) {
        if (!bot.isAlive()) return;

        // 1. Húzás
        gameLogic.logUIMessage(bot.getName() + " (bot) megkezdi a körét");

        bot.roundStart(gameLogic);
        gameLogic.UIUpdateUI();

        // 2. Automatikus felszerelés
        gameLogic.logUIMessage(bot.getName() + " (bot) felszerlés kijátszás");
        sleepForSleepConstant();
        autoEquip(bot, gameLogic);
        gameLogic.UIUpdateUI();


        // 3. Hasznosítható akciók sorrendje
        sleepForSleepConstant();
        List<Card> cardsYetToPlay = setYetToPlay(bot);


        for(Card card : cardsYetToPlay){
            sleepForSleepConstant();
            //gameLogic.logUIMessage("Playing card: " + card);
            useThisCard(card, bot, gameLogic);
            gameLogic.UIUpdateUI();
        }

        sleepForSleepConstant();
        gameLogic.logUIMessage( bot.getName() + " (bot) befejezte a körét");
        gameLogic.endTurn();
    }

    private static void autoEquip(BaseModel bot, GameLogic gameLogic) {
        List<Card> hand = new ArrayList<>(bot.getHandCards());
        for (Card card : hand) {
            if (card instanceof BarrelCard && !bot.hasBarrel()) {
                gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza ezt a kártyát: " + card);
                bot.playSingleTargetCard((BarrelCard) card, gameLogic);
            } else if (card instanceof MustangCard && !bot.hasMustang()) {
                gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza ezt a kártyát: " + card);
                bot.playSingleTargetCard((MustangCard) card, gameLogic);
            } else if (card instanceof ScopeCard && !bot.hasScope()) {
                gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza ezt a kártyát: " + card);
                bot.playSingleTargetCard((ScopeCard) card, gameLogic);
            } else if (card instanceof Weapon weapon) {
                if (bot.getWeapon() == null || weapon.getRange() > bot.getWeapon().getRange()) {
                    gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza ezt a fegyvert: " + card);
                    bot.playSingleTargetCard((Weapon) card, gameLogic);
                }
            } else if (card instanceof DynamiteCard && !bot.hasDynamite()){
                gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza ezt a kártyát: " + card);
                bot.playSingleTargetCard((DynamiteCard) card, gameLogic);
            }
        }
    }

    private static List<Card> setYetToPlay(BaseModel bot){
        List<Card> hand = new ArrayList<>(bot.getHandCards());
        List<Card> cardsYetToPlay = new ArrayList<>();
        boolean alreadyHasBang = false;
        int beerInPocket = 0;
        List<BaseModel> players = bot.getGameInstance().getInstance().getPlayers();
        int thisPlayerIndex = players.indexOf(bot);
        List<BaseModel> possibleTargets = new ArrayList<>();
        int weaponRange = 1;
        if(bot.hasWeapon()){
            weaponRange = bot.getWeapon().getRange();
        }
        for(int i = 0; i < players.size(); i++){
            if(bot.getVision().get(i) <= (weaponRange + bot.getFieldView()) && thisPlayerIndex != i){
                //System.out.println("Adding this target: " + players.get(i).getName());
                possibleTargets.add(players.get(i));
            }
        }
        for(Card card : hand){
            if(card instanceof BarrelCard || card instanceof MustangCard || card instanceof Weapon ||  card instanceof ScopeCard  || card instanceof DynamiteCard){
                //System.out.println("Skipping this card: " + card);
                continue;
            }
            if(card instanceof BeerCard || card instanceof SaloonCard){
                //System.out.println("Check to add beer or saloon");
                if(bot.getGameInstance().getPlayers().size() > 2) {
                    if ((bot.getMaxHP() - bot.getHealth()) > beerInPocket) {
                        //System.out.println("Added beer or saloon");
                        beerInPocket++;
                        cardsYetToPlay.add(card);
                    }
                }
                continue;
            }
            if(!possibleTargets.isEmpty()) {
                if(card instanceof BangCard){
                    if(bot.getRapid() || !alreadyHasBang){
                            cardsYetToPlay.add(card);
                            alreadyHasBang = true;
                            //System.out.println("Added a bang");
                        }
                    continue;
                }else if(bot instanceof CalamityJanet){
                    if(card instanceof MissedCard){
                        if(bot.getRapid() || !alreadyHasBang){
                                cardsYetToPlay.add(card);
                                alreadyHasBang = true;
                                //System.out.println("Added a Missedcard (calamity)");
                            }
                        continue;
                    }
                }
            }
            //System.out.println("Added this card to play: " + card);
            cardsYetToPlay.add(card);
        }
        return cardsYetToPlay;
    }

    private static void useThisCard(Card card, BaseModel bot, GameLogic gameLogic){
        switch (card.getType()){
            case CardType.PANIC: usePanic(card, bot, gameLogic);
                break;
            case CardType.CAT_BALOU: useCatBalou(card, bot, gameLogic);
                break;
            case CardType.BEER: useBeer(card, bot, gameLogic);
                break;
            case CardType.SALOON: useSaloon(card, bot, gameLogic);
                break;
            case CardType.WELLS_FARGO: useWellsFargo(card, bot, gameLogic);
                break;
            case CardType.STAGECOACH: useStagecoach(card, bot, gameLogic);
                break;
            case CardType.GENERAL_STORE: useGeneralStore(card, bot, gameLogic);
                break;
            case CardType.INDIANS: useIndians(card, bot, gameLogic);
                break;
            case CardType.GATLING: useGatling(card, bot, gameLogic);
                break;
            case CardType.DUEL: useDuel(card, bot, gameLogic);
                break;
            case CardType.BANG: useBang(card, bot, gameLogic);
                break;
            case CardType.JAIL: useJail(card, bot, gameLogic);
                break;
        }
    }

    private static void usePanic(Card card, BaseModel bot, GameLogic gameLogic) {
        List<BaseModel> players = bot.getGameInstance().getInstance().getPlayers();
        int thisPlayerIndex = players.indexOf(bot);
        List<BaseModel> possibleTargets = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            if(bot.getVision().get(i) <= 1 && thisPlayerIndex != i){
                if(!players.get(i).getHandCards().isEmpty()){
                    possibleTargets.add(players.get(i));
                }
            }
        }
        if(possibleTargets.isEmpty()){
            return;
        }
        if(!bot.hasBarrel()){
            for(BaseModel target : possibleTargets){
                if(target.hasBarrel()){
                    bot.playDualTargetCard((DualTargetCard) card, target, gameLogic);
                    return;
                }
            }
        }
        if(!bot.hasMustang()){
            for(BaseModel target : possibleTargets){
                if(target.hasMustang()){
                    bot.playDualTargetCard((DualTargetCard) card, target, gameLogic);
                    return;
                }
            }
        }
        if(!bot.hasScope()){
            for(BaseModel target : possibleTargets){
                if(target.hasScope()){
                    bot.playDualTargetCard((DualTargetCard) card, target, gameLogic);
                    return;
                }
            }
        }
        int weaponRange = bot.hasWeapon() ? bot.getWeapon().getRange() : 1;
        if(weaponRange < 5){
            for(BaseModel target : possibleTargets){
                if(target.hasWeapon()){
                    if(target.getWeapon().getRange() > weaponRange){
                        bot.playDualTargetCard((DualTargetCard) card, target, gameLogic);
                        return;
                    }
                }
            }
        }
        BaseModel randomTarget = possibleTargets.get(new Random().nextInt(possibleTargets.size()));
        gameLogic.logUIMessage(bot.getName() + " (bot) pánikol ettől a célponttól: " + randomTarget.getName());
        bot.playDualTargetCard((DualTargetCard) card, randomTarget, gameLogic);
    }

    private static void useCatBalou(Card card, BaseModel bot, GameLogic gameLogic) {
        List<BaseModel> players = bot.getGameInstance().getInstance().getPlayers();
        BaseModel target;
        do{
            target = players.get(new Random().nextInt(players.size()));
            if(!target.getHandCards().isEmpty()) {
                //System.out.println("Catbalou target: " + target.getName());
            }else {
                continue;
            }
        }while (bot == target);
        gameLogic.logUIMessage(bot.getName() + " (bot) Cat Balou-zik ettől a célponttól: " + target.getName());
        bot.playDualTargetCard((DualTargetCard) card, target, gameLogic);
    }

    private static void useBang(Card card, BaseModel bot, GameLogic gameLogic) {
        List<BaseModel> players = bot.getGameInstance().getInstance().getPlayers();
        int thisPlayerIndex = players.indexOf(bot);
        List<BaseModel> possibleTargets = new ArrayList<>();
        int weaponRange = 1;
        if(bot.hasWeapon()){
            weaponRange = bot.getWeapon().getRange();
        }
        for(int i = 0; i < players.size(); i++){
            if(bot.getVision().get(i) <= (weaponRange + bot.getFieldView()) && thisPlayerIndex != i){
                possibleTargets.add(players.get(i));
            }
        }
        if(!possibleTargets.isEmpty()) {
            BaseModel randomTarget = possibleTargets.get(new Random().nextInt(possibleTargets.size()));
            gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza a Bang!-et: " + card + ", célpont: " + randomTarget.getName());
            bot.playDualTargetCard((DualTargetCard) card, randomTarget, gameLogic);
        }
    }

    private static void useGatling(Card card, BaseModel bot, GameLogic gameLogic) {
        gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza a Gatling-et: " + card);
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useIndians(Card card, BaseModel bot, GameLogic gameLogic) {
        gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza a Indiánok-at: " + card);
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useBeer(Card card, BaseModel bot, GameLogic gameLogic) {
        gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza a Sört: " + card);
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useGeneralStore(Card card, BaseModel bot, GameLogic gameLogic) {
        gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza a Szatócsboltotot: " + card);
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useStagecoach(Card card, BaseModel bot, GameLogic gameLogic) {
        gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza a Postakocsit: " + card);
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useWellsFargo(Card card, BaseModel bot, GameLogic gameLogic) {
        gameLogic.logUIMessage(bot.getName() + " (bot) Wells Fargo-t: " + card);
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useSaloon(Card card, BaseModel bot, GameLogic gameLogic) {
        gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza a Kocsmát: " + card);
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useDuel(Card card, BaseModel bot, GameLogic gameLogic) {
        List<BaseModel> players = bot.getGameInstance().getInstance().getPlayers();
        BaseModel target;
        do{
            target = players.get(new Random().nextInt(players.size()));
        }while (bot == target);
        gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza a Párbajt: " + card + " célpont: " + target);
        bot.playDualTargetCard((DualTargetCard) card, target, gameLogic);
    }

    private static void useJail(Card card, BaseModel bot, GameLogic gameLogic) {
        List<BaseModel> players = bot.getGameInstance().getInstance().getPlayers();
        BaseModel target;
        do{
            target = players.get(new Random().nextInt(players.size()));
        }while (bot == target);
        gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza a Börtönt: " + card + " célpont: " + target.getName());
        bot.playDualTargetCard((DualTargetCard) card, target, gameLogic);
    }

    private static void useDynamite(Card card, BaseModel bot, GameLogic gameLogic) {
        gameLogic.logUIMessage(bot.getName() + " (bot) kijátssza a Dinamitot: " + card);
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    public Card discardExcessCards(BaseModel bot, GameLogic gameLogic) {
        for(Card card : bot.getHandCards()){
            sleepForSleepConstant();
            if(card.getType() == CardType.BARREL && bot.hasBarrel()){
                gameLogic.logUIMessage(bot.getName() + " (bot) eldobás: " + card);
                return card;
            }
            if(card.getType() == CardType.MUSTANG && bot.hasMustang()){
                gameLogic.logUIMessage(bot.getName() + " (bot) eldobás: " + card);
                return card;
            }
            if(bot.getWeapon() != null && card instanceof Weapon weapon){
                if(weapon.getRange() <= bot.getWeapon().getRange()){
                    gameLogic.logUIMessage(bot.getName() + " (bot) eldobás: " + card);
                    return card;
                }
            }
        }
        gameLogic.logUIMessage(bot.getName() + " (bot) eldobás: " + bot.getHandCards().getFirst());
        return bot.getHandCards().getFirst();
    }

    private static void sleepForSleepConstant(){
        try {
            Thread.sleep(SLEEP_CONSTANT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}