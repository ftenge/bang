package gamelogic;

import cards.Card;
import cards.DualTargetCard;
import cards.SingleTargetCard;
import cards.bluecards.DynamiteCard;
import cards.browncards.MissedCard;
import gameinstance.GameInstance;
import ui.BangGameUI;
import utilities.BaseModel;
import utilities.Bot;
import utilities.RoleType;
import utilities.characters.CalamityJanet;

import java.awt.font.GlyphMetrics;
import java.util.List;

public class GameLogic {
    private GameInstance gameInstance;
    private List<BaseModel> players;
    private BangGameUI ui;
    private int currentPlayerIndex;

    //létrehozza a gameLogicot és itt inicializálja a gameInstance-t
    public GameLogic(BangGameUI ui) {
        this.ui = ui;
        this.gameInstance = GameInstance.getInstance();
        this.players = gameInstance.getPlayers();
        this.currentPlayerIndex = 0;
    }

    //elkezdődik a játék valamennyi játékossal
    //minden játékos megejti a játék megkezdése előtti húzást, majd jön az első kör
    public void startGame(int numberOfPlayers, List<String> characterNames, List<String> roles, List<String> bots) {
        gameInstance.initializePlayers(numberOfPlayers, characterNames, roles, bots);   //custom indítás
        logUIMessage("Game started!");
        System.out.println(gameInstance.getDeck().isDiscardPileEmpty());

        for (BaseModel player : getPlayers()) {
            player.gameStartDraw();
        }
        ui.updateUI();
        nextTurn();
    }

    //ha a játékos nem élne, akkor kilép
    //ha a játékos előtt van dinamit, akkor arra húz, ha belehal, akkor kilép
    //ha börtönben van, akkor arra húz, ha kiszabadul, akkor jöhet a köre
    public void nextTurn() {
        BaseModel currentPlayer = getPlayers().get(currentPlayerIndex);
        logUIMessage("It's " + currentPlayer.getName() + "'s turn!");

        if (!currentPlayer.isAlive()) {
            endTurn();
            return;
        }
        if(currentPlayer.hasDynamite()){
            DynamiteCard dynamiteCard = currentPlayer.dynamiteAction(currentPlayer, this);
            if(dynamiteCard != null){
                getPlayers().get((currentPlayerIndex + 1) % getPlayers().size()).addDynamite(dynamiteCard);
            }else if (!currentPlayer.isAlive()) {
                    endTurn();
                    return;
            }
        }
        if(currentPlayer.inJail()){
            if(!currentPlayer.jailAction(this)){
                endTurn();
                return;
            }
        }
        if(currentPlayer.getIsBot()){
            Bot.takeTurn(currentPlayer, this);
        }else{
            currentPlayer.roundStart(this);
            ui.updateUI();
        }



       // endTurn();
    }

    //lép egyet, hogy ki az aktuális játékos majd, megkezdődik annak a köre
    public void endTurn() {
        BaseModel currentPlayer = getPlayers().get(currentPlayerIndex);
        currentPlayer.endTurnDiscard(this);
        currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();
        nextTurn();
    }

    //megnézi, hogy az adott kártyának kell-e targetot adni és a megfelelő függvényt adja át
    public void cardAction(Card selectedCard, BaseModel baseModel, BaseModel target){
        if (selectedCard instanceof SingleTargetCard singleTargetCard) {
            baseModel.playSingleTargetCard(singleTargetCard, this);
        } else if (selectedCard instanceof DualTargetCard dualTargetCard) {
            if (target != null) {
                if((baseModel instanceof CalamityJanet) || !(selectedCard instanceof MissedCard)){
                    baseModel.playDualTargetCard(dualTargetCard, target, this);
                }
            }
        }
    }

    //meghívja azt a függvényt, amivel a játékos el tudja dobni a kártyát
    public void discardCardAction(Card selectedCard, BaseModel baseModel){
        baseModel.removeCard(selectedCard);
    }

    //visszaadja a kiválasztott kártyát
    public Card chooseCard(List<Card> cards, String name, String title) {
        int index = ui.selectCardFromList(cards, name, title);

        return (index == -1) ? null : cards.get(index);
    }

    //visszaadja a kiválasztott kártya indexét
    public int chooseFromHand(List<Card> cards, String name, String title) {
        int index = ui.selectCardFromList(cards, name, title);

        return (index == -1) ? 0 : index;
    }

    //visszaadja a kiválasztott játékos indexét
    public int chooseTarget(List<BaseModel> baseModels, String name, String title) {
        int index = ui.selectTargetFromList(baseModels, name, title);

        return (index == -1) ? 0 : index;
    }

    //visszaadja a kiválasztott lehetőség számát
    public int chooseOption(String name, String title, String option1, String option2){
        return ui.showTwoOptionDialog(name, title, option1, option2);
    }

    //visszaad egy kártyákból álló listát
    public List<Card> selectTwoCards(List<Card> cards, String title, String instruction){
        return ui.selectTwoCardsFromThree(cards, title, instruction);
    }

    //megnézi, hogy nyert-e valaki
    public boolean isGameOver() {
        boolean isSheriffAlive = false;
        boolean areAnyOutLawsAlive = false;
        boolean isRenegadeAlive = false;
        for(BaseModel player : players){
            if(player.getRole().getType() == RoleType.SHERIFF){
                isSheriffAlive = true;
            }
            if(player.getRole().getType() == RoleType.OUTLAW){
                areAnyOutLawsAlive = true;
            }
            if(player.getRole().getType() == RoleType.RENEGADE){
                isRenegadeAlive = true;
            }
        }
        if(!isSheriffAlive){
            if(areAnyOutLawsAlive){
                ui.logMessage("Outlaws have won!");
                return true;
            }
            ui.logMessage("The Renegade has won!");
            return true;
        }
        else{
            if(!areAnyOutLawsAlive && !isRenegadeAlive){
                ui.logMessage("Sheriff has won!");
                return true;
            }
        }
        return false;
    }

    //visszaadja a jelenlegi játékost
    public BaseModel getCurrentPlayer() {
        return getPlayers().get(currentPlayerIndex);
    }

    //ha az aktuális index akkora, mint az élő játékosok száma, akkor csökkenti eggyel
    public void aPlayerRemoved(){
        if(currentPlayerIndex == getPlayers().size()){
            currentPlayerIndex--;
        }
        isGameOver();
    }

    //visszaadja a még játékban lévő játékosokat
    public List<BaseModel> getPlayers(){
        return gameInstance.getPlayers();
    }

    public void logUIMessage(String string){
        ui.logMessage(string);
    }

    public GameInstance getGameInstance(){
        return GameInstance.getInstance();
    }
}