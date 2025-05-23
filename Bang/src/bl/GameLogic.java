package bl;

import model.cards.Card;
import model.cards.DualTargetCard;
import model.cards.SingleTargetCard;
import model.cards.bluecards.DynamiteCard;
import model.cards.browncards.MissedCard;
import ui.BangGameUI;
import ui.GameOverDialog;
import model.utilities.BaseModel;
import model.utilities.RoleType;
import model.utilities.characters.CalamityJanet;

import java.util.List;

public class GameLogic {
    private static final int SLEEP_CONSTANT = 200;
    private GameInstance gameInstance;
    private BangGameUI ui;
    private int currentPlayerIndex;

    //létrehozza a gameLogicot és itt inicializálja a gameInstance-t
    public GameLogic(BangGameUI ui) {
        this.ui = ui;
        this.gameInstance = GameInstance.getInstance();
        this.currentPlayerIndex = 0;
    }

    //elkezdődik a játék valamennyi játékossal
    //minden játékos megejti a játék megkezdése előtti húzást, majd jön az első kör
    public void startGame(int numberOfPlayers, List<String> characterNames, List<String> roles, List<String> bots) {
        gameInstance.initializePlayers(numberOfPlayers, characterNames, roles, bots);   //custom indítás
        logUIMessage("A játék elkezdődött!");
        //System.out.println(gameInstance.getDeck().isDiscardPileEmpty());

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
        //System.out.println(getPlayers());
        logUIMessage(currentPlayer.getName() + " köre következik!");

        if (!currentPlayer.isAlive()) {
            endTurn();
            return;
        }
        if(currentPlayer.hasDynamite()){
            logUIMessage(currentPlayer.getName() + " húz a dinamitra...");
            sleepForSleepConstant();
            DynamiteCard dynamiteCard = currentPlayer.dynamiteAction(currentPlayer, this);

            if(dynamiteCard != null){
                logUIMessage("A dinamit tovább vándorol!");
                getPlayers().get((currentPlayerIndex + 1) % getPlayers().size()).addDynamite(dynamiteCard);
            }else{
                logUIMessage("A dinamit felrobban!");
                if (!currentPlayer.isAlive()) {
                    logUIMessage(currentPlayer.getName() + " belehalt a dinamit robbanásba.");
                    endTurn();
                    return;
                }
            }
        }
        if(currentPlayer.inJail()){
            logUIMessage(currentPlayer.getName() + " húz, hogy kikerüljön a börtönből...");
            sleepForSleepConstant();
            if(!currentPlayer.jailAction(this)){
                logUIMessage(currentPlayer.getName() + " a börtönben marad ebben a körben.");
                endTurn();
                return;
            }
            logUIMessage(currentPlayer.getName() + " kijutott a börtönből.");
        }
        UIUpdateUI();
        if(currentPlayer.getIsBot()){
            Bot.takeTurn(currentPlayer, this);
        }else{
            currentPlayer.roundStart(this);
        }
        UIUpdateUI();


    }

    //lép egyet, hogy ki az aktuális játékos majd, megkezdődik annak a köre
    public void endTurn() {
        BaseModel currentPlayer = getPlayers().get(currentPlayerIndex);
        currentPlayer.endTurnDiscard(this);
        currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();
        gameInstance.setCurrentPlayerIndex(currentPlayerIndex);
        UIUpdateUI();
        nextTurn();
    }

    //megnézi, hogy az adott kártyának kell-e targetot adni és a megfelelő függvényt adja át
    public boolean cardAction(Card selectedCard, BaseModel baseModel, BaseModel target){
        if (selectedCard instanceof SingleTargetCard singleTargetCard) {
            return baseModel.playSingleTargetCard(singleTargetCard, this);
        } else if (selectedCard instanceof DualTargetCard dualTargetCard) {
            if (target != null) {
                if((baseModel instanceof CalamityJanet) || !(selectedCard instanceof MissedCard)){
                    return baseModel.playDualTargetCard(dualTargetCard, target, this);
                }
            }
        }
        return false;
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
    public String getGameOver() {
        boolean isSheriffAlive = false;
        boolean areAnyOutLawsAlive = false;
        boolean isRenegadeAlive = false;
        boolean areAnyDeputiesAlive = false;
        for(BaseModel player : getPlayers()){
            if(player.getRole().getType() == RoleType.SHERIFF){
                isSheriffAlive = true;
            }else if(player.getRole().getType() == RoleType.OUTLAW){
                areAnyOutLawsAlive = true;
            }else if(player.getRole().getType() == RoleType.RENEGADE){
                isRenegadeAlive = true;
            }else if(player.getRole().getType() == RoleType.DEPUTY){
                areAnyDeputiesAlive = true;
            }
        }
        //System.out.println("isSheriffAlive " + isSheriffAlive);
        //System.out.println("areAnyOutLawsAlive " + areAnyOutLawsAlive);
        //System.out.println("isRenegadeAlive " + isRenegadeAlive);
        if(!isSheriffAlive){
            if(areAnyOutLawsAlive){
                return "A banditák nyertek!";
            }
            if(!areAnyDeputiesAlive) {
                return "A renegát nyert!";
            }
        }
        else{
            if(!areAnyOutLawsAlive && !isRenegadeAlive){
                return "A Sheriffék nyert!";
            }
        }
        return "";
    }

    //visszaadja a jelenlegi játékost
    public BaseModel getCurrentPlayer() {
        return getPlayers().get(currentPlayerIndex);
    }

    public BaseModel getHumanPlayer(){
        for(BaseModel player : getPlayers()){
            if(!player.getIsBot()){
                return player;
            }
        }
        return null;
    }

    //ha az aktuális index akkora, mint az élő játékosok száma, akkor csökkenti eggyel
    public void aPlayerRemoved(BaseModel baseModel){
        //System.out.println("A player removed");
        for(int i = 0; i < getPlayers().size(); i++){
            if(getPlayers().get(i) == baseModel){
                if(currentPlayerIndex >= i){
                    currentPlayerIndex--;
                    gameInstance.setCurrentPlayerIndex(currentPlayerIndex);
                }
            }
        }
        UIUpdateUI();
        gameInstance.removePlayer(baseModel);
        String gameOverMessage = getGameOver();
        //System.out.println("GameOver? " + gameOverMessage);
        if(!gameOverMessage.isEmpty()) {
            GameOverDialog dialog = new GameOverDialog(ui, gameOverMessage);
            dialog.setVisible(true);
        }
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

    public void UIUpdateUI(){
        ui.updateUI();
    }

    private static void sleepForSleepConstant(){
        try {
            Thread.sleep(SLEEP_CONSTANT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}