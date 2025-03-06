package gamelogic;

import cards.Card;
import cards.DualTargetCard;
import cards.SingleTargetCard;
import cards.bluecards.DynamiteCard;
import gameinstance.GameInstance;
import ui.BangGameUI;
import utilities.BaseModel;

import java.util.List;

public class GameLogic {
    private GameInstance gameInstance;
    private List<BaseModel> players;
    private BangGameUI ui;
    private int currentPlayerIndex;

    public GameLogic(BangGameUI ui) {
        this.ui = ui;
        this.gameInstance = GameInstance.getInstance();
        this.players = gameInstance.getPlayers();
        this.currentPlayerIndex = 0;
    }

    public void startGame() {
        gameInstance.initializePlayers(2);
        System.out.println("Game started!");

        for (BaseModel player : getPlayers()) {
            player.gameStartDraw();
        }
        nextTurn();
    }

    public void nextTurn() {
        BaseModel currentPlayer = getPlayers().get(currentPlayerIndex);
        System.out.println("It's " + currentPlayer.getName() + "'s turn!");

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
        currentPlayer.roundStart(this);




       // endTurn();
    }

    public void endTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();
        nextTurn();
    }

    public void cardAction(Card selectedCard, BaseModel baseModel, BaseModel target){
        if (selectedCard instanceof SingleTargetCard singleTargetCard) {
            baseModel.playSingleTargetCard(singleTargetCard, this);
        } else if (selectedCard instanceof DualTargetCard dualTargetCard) {
            if (target != null) {
                baseModel.playDualTargetCard(dualTargetCard, target, this);
            }
        }
    }

    public void discardCardAction(Card selectedCard, BaseModel baseModel){
        baseModel.removeCard(selectedCard);
    }

    public Card chooseCard(List<Card> cards, String name, String title) {
        int index = ui.selectCardFromList(cards, name, title);

        return (index == -1) ? null : cards.get(index);
    }

    public int chooseFromHand(List<Card> cards, String name, String title) {
        int index = ui.selectCardFromList(cards, name, title);

        return (index == -1) ? 0 : index;
    }

    public int chooseOption(String name, String title, String option1, String option2){
        return ui.showTwoOptionDialog(name, title, option1, option2);
    }

    public List<Card> selectTwoCards(List<Card> threeCards){
        return ui.selectTwoCardsFromThree(threeCards);
    }


    public boolean isGameOver() {
        //return gameInstance.checkWinCondition();
        return false;
    }

    public BaseModel getCurrentPlayer() {
        return getPlayers().get(currentPlayerIndex);
    }

    public List<BaseModel> getPlayers(){
        return gameInstance.getPlayers();
    }
}