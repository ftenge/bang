//konzolos ui tesztre
/*package ui;

import cards.Card;
import cards.DualTargetCard;
import cards.SingleTargetCard;
import cards.browncards.BangCard;
import cards.browncards.DuelCard;
import gameinstance.GameInstance;
import gamelogic.GameLogic;
import utilities.BaseModel;

import java.util.Scanner;

public class BangConsoleUI {
    private GameInstance gameInstance;
    private GameLogic gameLogic;
    private Scanner scanner;

    public BangConsoleUI() {
        this.gameInstance = GameInstance.getInstance();
        //this.gameLogic = new GameLogic(this);
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        gameInstance.initializePlayers(2); // 2 játékos inicializálása
        System.out.println("A játék elindult!");
        gameLogic.startGame();


        while (true) {
            BaseModel currentPlayer = gameLogic.getCurrentPlayer();
            System.out.println("\n" + currentPlayer.getName() + " köre következik.");

            takeTurn(currentPlayer);
            gameLogic.endTurn();
        }

        //System.out.println("A játék véget ért!");
    }

    private void takeTurn(BaseModel baseModel) {
        baseModel.roundStart(gameLogic);
        while(true) {
            System.out.println("Kártyáid:");
            for (int i = 0; i < baseModel.getHandCards().size(); i++) {
                System.out.println((i + 1) + ". " + baseModel.getHandCards().get(i).getName());
            }

            System.out.println("Válassz egy kártyát (1 - " + baseModel.getHandCards().size() + ") vagy írj 'pass' a passzoláshoz, 'exit' a kilépéshez:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Kilépés...");
                System.exit(0);
            } else if (input.equalsIgnoreCase("pass")) {
                System.out.println(baseModel.getName() + " passzolt.");
                break;
            } else {
                try {
                    int choice = Integer.parseInt(input) - 1;
                    if (choice >= 0 && choice < baseModel.getHandCards().size()) {
                        Card selectedCard = baseModel.getHandCards().get(choice);
                        System.out.println(baseModel.getName() + " kijátszotta: " + selectedCard.getName());

                        if (selectedCard instanceof SingleTargetCard singleTargetCard) {
                            baseModel.playSingleTargetCard(singleTargetCard, gameLogic);
                        } else if (selectedCard instanceof DualTargetCard dualTargetCard) {
                            BaseModel target = selectTarget(baseModel);
                            if (target != null) {
                                baseModel.playDualTargetCard(dualTargetCard, target, gameLogic);
                            }
                        }
                    } else {
                        System.out.println("Érvénytelen választás!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Érvénytelen bemenet!");
                }
            }
        }
        System.out.println(baseModel.toString());
        System.out.println("Is alive?" + baseModel.isAlive() + ", " + baseModel.getHealth() + "/" + baseModel.getMaxHP());
    }

    private BaseModel selectTarget(BaseModel currentPlayer) {
        System.out.println("Válassz egy célpontot:");
        int index = 1;
        for (BaseModel player : gameInstance.getPlayers()) {
            if (!player.equals(currentPlayer)) {
                System.out.println(index + ". " + player.getName());
                index++;
            }
        }

        String input = scanner.nextLine();
        try {
            int choice = Integer.parseInt(input) - 1;
            if (choice >= 0 && choice < gameInstance.getPlayers().size()) {
                return gameInstance.getPlayers().get(choice);
            }
        } catch (NumberFormatException e) {
            System.out.println("Érvénytelen bemenet!");
        }
        return null;
    }

    public static void main(String[] args) {
        BangConsoleUI ui = new BangConsoleUI();
        ui.startGame();
    }
}*/