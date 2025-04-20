package utilities;

import cards.*;
import cards.bluecards.BarrelCard;
import cards.bluecards.MustangCard;
import cards.bluecards.ScopeCard;
import cards.browncards.BangCard;
import cards.browncards.BeerCard;
import cards.browncards.MissedCard;
import cards.browncards.SaloonCard;
import cards.weapons.*;
import gamelogic.GameLogic;
import utilities.characters.CalamityJanet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

//public void startTurn() {
//    BaseModel currentPlayer = players.get(currentPlayerIndex);
//
//    if (currentPlayer.isDead()) {
//        nextTurn(); // automatikusan továbblépünk, ha halott
//        return;
//    }
//
//    System.out.println("Kör indul: " + currentPlayer.getName());
//
//    if (currentPlayer.isBot()) {
//        System.out.println("BOT jön: " + currentPlayer.getName());
//        Bot bot = new Bot();
//        bot.takeTurn(currentPlayer, this);
//        endTurn();
//    } else {
//        // Emberi játékos köre indul – UI eseményekre várunk
//        currentPlayer.drawCards(2);
//        // UI segítségével lehetőséget adunk játékosnak a kártyák kijátszására
//    }
//}
//
//public void endTurn() {
//    BaseModel currentPlayer = players.get(currentPlayerIndex);
//    currentPlayer.discardExcessCards();
//
//    // Továbblépés a következő élő játékosra
//    do {
//        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
//    } while (players.get(currentPlayerIndex).isDead());
//
//    startTurn(); // következő játékos köre indul
//}
//
//// Egyéb logikák, például támadás, győzelemellenőrzés stb.
//}

public class Bot {
    public static void takeTurn(BaseModel bot, GameLogic gameLogic) {
        if (!bot.isAlive()) return;

        // 1. Húzás
        bot.roundStart(gameLogic);

        // 2. Automatikus felszerelés
        autoEquip(bot, gameLogic);

        // 3. Hasznosítható akciók sorrendje
        List<Card> cardsYetToPlay = setYetToPlay(bot);


        while(!cardsYetToPlay.isEmpty()){
            for(Card card : cardsYetToPlay){
                useThisCard(card, bot, gameLogic);
            }
            cardsYetToPlay = setYetToPlay(bot);
        }

        gameLogic.endTurn();
    }

    private static void autoEquip(BaseModel bot, GameLogic gameLogic) {
        List<Card> hand = new ArrayList<>(bot.getHandCards());
        for (Card card : hand) {
            if (card instanceof BarrelCard && bot.hasBarrel()) {
                bot.playSingleTargetCard((BarrelCard) card, gameLogic);
            } else if (card instanceof MustangCard && bot.hasMustang()) {
                bot.playSingleTargetCard((MustangCard) card, gameLogic);
            } else if (card instanceof ScopeCard && bot.hasScope()) {
                bot.playSingleTargetCard((ScopeCard) card, gameLogic);
            } else if (card instanceof Weapon weapon) {
                if (bot.getWeapon() == null || weapon.getRange() > bot.getWeapon().getRange()) {
                    bot.playSingleTargetCard((Weapon) card, gameLogic);
                }
            }
        }
    }

    private static List<Card> setYetToPlay(BaseModel bot){
        List<Card> cardsYetToPlay = new ArrayList<>();
        boolean alreadyHasBang = false;
        int beerInPocket = 0;
        for(Card card : bot.getHandCards()){
            if(card instanceof BarrelCard || card instanceof MustangCard || card instanceof Weapon ||  card instanceof ScopeCard){
                continue;
            }
            if(card instanceof BeerCard || card instanceof SaloonCard){
                if((bot.getMaxHP() - bot.getHealth()) > beerInPocket){
                    beerInPocket++;
                    cardsYetToPlay.add(card);
                }
                continue;
            }
            if(card instanceof BangCard){
                if(bot.getRapid() || !alreadyHasBang){
                    cardsYetToPlay.add(card);
                    alreadyHasBang = true;
                }
                continue;
            }
            if(card instanceof MissedCard){
                if(bot instanceof CalamityJanet){
                    if(bot.getRapid() || !alreadyHasBang){
                        cardsYetToPlay.add(card);
                        alreadyHasBang = true;
                    }
                }
                continue;
            }
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
            case CardType.DYNAMITE: useDynamite(card, bot, gameLogic);
                break;
        }
    }

    private static void usePanic(Card card, BaseModel bot, GameLogic gameLogic) {
        List<BaseModel> players = bot.getGameInstance().getInstance().getPlayers();
        int thisPlayerIndex = players.indexOf(bot);
        List<BaseModel> possibleTargets = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            if(bot.getVision().get(i) <= 1 && thisPlayerIndex != i){
                possibleTargets.add(players.get(i));
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
        if(bot.getWeapon().getRange() < 5){
            for(BaseModel target : possibleTargets){
                if(target.getWeapon().getRange() > bot.getWeapon().getRange()){
                    bot.playDualTargetCard((DualTargetCard) card, target, gameLogic);
                    return;
                }
            }
        }
        BaseModel randomTarget = possibleTargets.get(new Random().nextInt(possibleTargets.size()));
        bot.playDualTargetCard((DualTargetCard) card, randomTarget, gameLogic);
    }

    private static void useCatBalou(Card card, BaseModel bot, GameLogic gameLogic) {
        List<BaseModel> players = bot.getGameInstance().getInstance().getPlayers();
        BaseModel target;
        do{
            target = players.get(new Random().nextInt(players.size()));
        }while (bot != target);
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
        BaseModel randomTarget = possibleTargets.get(new Random().nextInt(possibleTargets.size()));
        bot.playDualTargetCard((DualTargetCard) card, randomTarget, gameLogic);
    }

    private static void useGatling(Card card, BaseModel bot, GameLogic gameLogic) {
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useIndians(Card card, BaseModel bot, GameLogic gameLogic) {
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useBeer(Card card, BaseModel bot, GameLogic gameLogic) {
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useGeneralStore(Card card, BaseModel bot, GameLogic gameLogic) {
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useStagecoach(Card card, BaseModel bot, GameLogic gameLogic) {
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useWellsFargo(Card card, BaseModel bot, GameLogic gameLogic) {
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useSaloon(Card card, BaseModel bot, GameLogic gameLogic) {
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    private static void useDuel(Card card, BaseModel bot, GameLogic gameLogic) {
        List<BaseModel> players = bot.getGameInstance().getInstance().getPlayers();
        BaseModel target;
        do{
            target = players.get(new Random().nextInt(players.size()));
        }while (bot != target);
        bot.playDualTargetCard((DualTargetCard) card, target, gameLogic);
    }

    private static void useJail(Card card, BaseModel bot, GameLogic gameLogic) {
        List<BaseModel> players = bot.getGameInstance().getInstance().getPlayers();
        BaseModel target;
        do{
            target = players.get(new Random().nextInt(players.size()));
        }while (bot != target);
        bot.playDualTargetCard((DualTargetCard) card, target, gameLogic);
    }

    private static void useDynamite(Card card, BaseModel bot, GameLogic gameLogic) {
        bot.playSingleTargetCard((SingleTargetCard) card, gameLogic);
    }

    public Card discardExcessCards(BaseModel bot, GameLogic gameLogic) {
        for(Card card : bot.getHandCards()){
            if(card.getType() == CardType.BARREL && bot.hasBarrel()){
                return card;
            }
            if(card.getType() == CardType.MUSTANG && bot.hasMustang()){
                return card;
            }
            if(bot.getWeapon() != null && card instanceof Weapon weapon){
                if(weapon.getRange() <= bot.getWeapon().getRange()){
                    return card;
                }
            }
        }
        return bot.getHandCards().getFirst();
    }
}