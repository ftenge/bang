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



        while(cardsYetToPlay.size() > 0){
            for(Card card : cardsYetToPlay){

            }

            ;
        }
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

    private static void usePanic(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(Panic.class)) {
            BaseModel target = GameUtils.findClosestWithBlueCard(bot, gameLogic);
            if (target != null) {
                bot.playCard(card, target);
            }
        }
    }

    private static void useCatBalou(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(CatBalou.class)) {
            BaseModel target = GameUtils.findEnemyWithCards(bot, gameLogic);
            if (target != null) {
                bot.playCard(card, target);
            }
        }
    }

    private static void useBang(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(Bang.class)) {
            if (!bot.hasPlayedBang()) {
                BaseModel target = GameUtils.findShootableTarget(bot, gameLogic);
                if (target != null) {
                    bot.playCard(card, target);
                }
            }
        }
    }

    private static void useGatling(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(Gatling.class)) {
            bot.playCard(card);
        }
    }

    private static void useIndians(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(Indians.class)) {
            bot.playCard(card);
        }
    }

    private static void useBeer(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(Beer.class)) {
            if (bot.getHealth() < bot.getMaxHealth()) {
                bot.playCard(card);
            }
        }
    }

    private static void useGeneralStore(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(GeneralStore.class)) {
            bot.playCard(card);
        }
    }

    private static void useStagecoach(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(Stagecoach.class)) {
            bot.playCard(card);
        }
    }

    private static void useWellsFargo(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(WellsFargo.class)) {
            bot.playCard(card);
        }
    }

    private static void useSaloon(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(Saloon.class)) {
            bot.playCard(card);
        }
    }

    private static void useDuel(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(Duel.class)) {
            BaseModel target = GameUtils.findDuelTarget(bot, gameLogic);
            if (target != null) {
                bot.playCard(card, target);
            }
        }
    }

    private static void useJail(BaseModel bot, GameLogic gameLogic) {
        for (Card card : bot.getHandOfType(Jail.class)) {
            BaseModel target = GameUtils.findValidJailTarget(bot, gameLogic);
            if (target != null) {
                bot.playCard(card, target);
            }
        }
    }

    private static void useDynamite(BaseModel bot, GameLogic gameLogic) {
        if (game.getDynamiteHolder() == null) {
            for (Card card : bot.getHandOfType(Dynamite.class)) {
                bot.playCard(card);
            }
        }
    }

    private static void discardExcessCards(BaseModel bot, GameLogic gameLogic) {
        int maxCards = bot.getHealth();
        while (bot.getHand().size() > maxCards) {
            Card toDiscard = bot.getHand().get(0);
            bot.discardCard(toDiscard);
        }
    }
}