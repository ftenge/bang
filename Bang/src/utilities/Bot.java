package utilities;

import cards.*;
import gamelogic.GameLogic;

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
    public static void takeTurn(BaseModel bot, GameLogic game) {
        if (!bot.isAlive()) return;

        // 1. Húzás
        bot.drawCards(2, game);

        // 2. Automatikus felszerelés
        autoEquip(bot);

        // 3. Hasznosítható akciók sorrendje
        usePanic(bot, game);
        useCatBalou(bot, game);
        useBang(bot, game);
        useGatling(bot, game);
        useIndians(bot, game);
        useBeer(bot, game);
        useGeneralStore(bot, game);
        useStagecoach(bot, game);
        useWellsFargo(bot, game);
        useSaloon(bot, game);
        useDuel(bot, game);
        useJail(bot, game);
        useDynamite(bot, game); // csak akkor, ha nincs játékban
        discardExcessCards(bot, game);
    }

    private static void autoEquip(BaseModel bot) {
        List<Card> hand = new ArrayList<>(bot.getHand());
        for (Card card : hand) {
            if (card instanceof BlueCard blue) {
                if (blue instanceof Barrel && bot.getBarrel() == null) {
                    bot.playCard(blue);
                } else if (blue instanceof Mustang && bot.getMustang() == null) {
                    bot.playCard(blue);
                } else if (blue instanceof Scope && bot.getScope() == null) {
                    bot.playCard(blue);
                } else if (blue instanceof Weapon weapon) {
                    if (bot.getWeapon() == null || weapon.getRange() > bot.getWeapon().getRange()) {
                        bot.playCard(blue);
                    }
                }
            }
        }
    }

    private static void usePanic(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(Panic.class)) {
            BaseModel target = GameUtils.findClosestWithBlueCard(bot, game);
            if (target != null) {
                bot.playCard(card, target);
            }
        }
    }

    private static void useCatBalou(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(CatBalou.class)) {
            BaseModel target = GameUtils.findEnemyWithCards(bot, game);
            if (target != null) {
                bot.playCard(card, target);
            }
        }
    }

    private static void useBang(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(Bang.class)) {
            if (!bot.hasPlayedBang()) {
                BaseModel target = GameUtils.findShootableTarget(bot, game);
                if (target != null) {
                    bot.playCard(card, target);
                }
            }
        }
    }

    private static void useGatling(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(Gatling.class)) {
            bot.playCard(card);
        }
    }

    private static void useIndians(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(Indians.class)) {
            bot.playCard(card);
        }
    }

    private static void useBeer(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(Beer.class)) {
            if (bot.getHealth() < bot.getMaxHealth()) {
                bot.playCard(card);
            }
        }
    }

    private static void useGeneralStore(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(GeneralStore.class)) {
            bot.playCard(card);
        }
    }

    private static void useStagecoach(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(Stagecoach.class)) {
            bot.playCard(card);
        }
    }

    private static void useWellsFargo(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(WellsFargo.class)) {
            bot.playCard(card);
        }
    }

    private static void useSaloon(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(Saloon.class)) {
            bot.playCard(card);
        }
    }

    private static void useDuel(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(Duel.class)) {
            BaseModel target = GameUtils.findDuelTarget(bot, game);
            if (target != null) {
                bot.playCard(card, target);
            }
        }
    }

    private static void useJail(BaseModel bot, Game game) {
        for (Card card : bot.getHandOfType(Jail.class)) {
            BaseModel target = GameUtils.findValidJailTarget(bot, game);
            if (target != null) {
                bot.playCard(card, target);
            }
        }
    }

    private static void useDynamite(BaseModel bot, Game game) {
        if (game.getDynamiteHolder() == null) {
            for (Card card : bot.getHandOfType(Dynamite.class)) {
                bot.playCard(card);
            }
        }
    }

    private static void discardExcessCards(BaseModel bot, Game game) {
        int maxCards = bot.getHealth();
        while (bot.getHand().size() > maxCards) {
            Card toDiscard = bot.getHand().get(0);
            bot.discardCard(toDiscard);
        }
    }
}
