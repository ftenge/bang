package utilities;

import cards.*;
import cards.bluecards.*;
import cards.browncards.*;
import cards.weapons.Weapon;
import gameinstance.GameInstance;
import gamelogic.GameLogic;
import utilities.characters.SuzyLafayette;
import utilities.characters.VultureSam;

import java.util.*;


//ez az osztály az összes játékososztály alapja

public class BaseModel {
    protected String name;
    protected int health;
    protected final int maxHP;
    protected Role role;
    protected List<Card> handCards;
    protected List<Card> tableCards;
    protected BarrelCard barrel = null;
    protected MustangCard mustang = null;
    protected ScopeCard scope = null;
    protected JailCard jail = null;
    protected DynamiteCard dynamite = null;
    protected boolean rapid = false;
    protected boolean bangedThisRound = false;
    protected int visibility = 0;
    protected int fieldView = 0;
    protected Weapon weapon;
    protected Character character;
    protected GameInstance gameInstance;
    protected List<Integer> vision;
    protected boolean isBot;

    public BaseModel(Character character, Role role, boolean isBot) {
        this.gameInstance = GameInstance.getInstance();
        this.name = character.getName();
        this.character = character;
        this.health = character.getHealth();
        this.handCards = new ArrayList<>();
        this.tableCards = new ArrayList<>();
        this.vision = new ArrayList<>(gameInstance.getInstance().getPlayers().size());
        this.role = role;
        if(role.getType() == RoleType.SHERIFF){
            this.health += 1;
        }
        this.maxHP = this.health;
        this.isBot = isBot;
    }

    @Override
    public String toString(){
        return name;
    }


    public String datas(){
        return ("Name: " + name + "\n" +
                "Health: " + health + "\n" +
                "MaxHP: " + maxHP + "\n" +
                "Vision: " + vision + "\n" +
                "Hand: " + handCards.size() + "\n" +
                "Table: " + tableCards + "\n" +
                "Barrel: " + barrel + "\n" +
                "Mustang: " + mustang + "\n" +
                "Scope: " + scope + "\n" +
                "Jail: " + jail + "\n" +
                "Dynamite: " + dynamite + "\n" +
                "Rapid: " + rapid + "\n" +
                "Used Bang: " + bangedThisRound + "\n" +
                "Visibility: " + visibility + "\n" +
                "FieldView: " + fieldView + "\n" +
                "Weapon: " + weapon + "\n" +
                "Role: " + role.getType() + "\n");
    }

    public void drawCard() {
        Card card = gameInstance.getDeck().draw();
        handCards.add(card);
        System.out.println("Sikeres húzás, a kártya: " + card);
    }

    public Card drawRetCard(GameLogic gameLogic){
        return gameInstance.getDeck().draw();
    }

    public void roundStartDraw(GameLogic gameLogic){
        for(int i = 0; i < 2; i++){
            drawCard();
        }
    }

    public void roundStart(GameLogic gameLogic){
        setVision();
        bangedThisRound = false;
        roundStartDraw(gameLogic);
    }

    public void gameStartDraw(){
        for(int i = 0; i < this.maxHP; i++){
            drawCard();
        }
    }

    public void addHand(Card card){
        handCards.add(card);
    }

    public void playSingleTargetCard(SingleTargetCard card, GameLogic gameLogic) {
        card.use(this, gameLogic);
    }

    public void playDualTargetCard(DualTargetCard card, BaseModel target, GameLogic gameLogic) {
        card.use(this, target, gameLogic);
    }

    public void discardCard(Card card) {
        System.out.println("Sikeres eldobás, a kártya: " + card);
        gameInstance.getDeck().discard(card);
    }

    public void removeCard(Card card){
        discardCard(card);
        handCards.remove(card);
    }

    public void removeCardFromHand(Card card){
        handCards.remove(card);
    }

    public List<Card> getHandCards() {
        return handCards;
    }

    public List<Card> getTableCards() {
        return tableCards;
    }

    public void endTurnDiscard(GameLogic gameLogic){
        while(handCards.size() > health){
            if(this.isBot){
                Card card = new Bot().discardExcessCards(this, gameLogic);
                removeCard(card);
            }else {
                Card card = gameLogic.chooseCard(getHandCards(), "You can't have more card in your hand than your hp at the end of your turn!\nHandsize: " + handCards.size() + "\nHealth: " + health, name + "Discard card end turn.");
                removeCard(card);
            }
        }
    }

    public void bangAction(BaseModel source, GameLogic gameLogic){
        if(hasBarrel()){
            if(barrelAction(gameLogic)){
                System.out.println("A hordó levédte!");
                return;
            }
        }
        if(isBot){
            for(Card card : this.getHandCards()){
                if(card instanceof MissedCard missedCard){
                    missedAction(missedCard, source, gameLogic);
                    return;
                }
            }
        }else {
            while (true) {
                Card card = gameLogic.chooseCard(getHandCards(), getName(), "Choose a Missed! card or pass!");
                if (card instanceof MissedCard missedCard) {
                    missedAction(missedCard, source, gameLogic);
                    System.out.println("Volt nem talált lap!");
                    return;
                }
                if (card == null) {
                    break;
                }
            }
        }
        System.out.println("Betalált a bang :/, aktuális hp: " + getHealth());
        receiveDamage(1, source, gameLogic);
        System.out.println("ReceiveDMG utáni hp: " + getHealth());
    }

    public void slabTheKillerBangangAction(BaseModel source, GameLogic gameLogic){
        if(hasBarrel()){
            if(barrelAction(gameLogic)){
                System.out.println("A hordó levédte!");
                return;
            }
        }
        if(isBot){
            List<MissedCard> missedCards = new ArrayList<>();
            for(Card card : this.getHandCards()){
                if(card instanceof MissedCard missedCard){
                    missedCards.add(missedCard);
                }
            }
            if(missedCards.size() >= 2){
                missedAction(missedCards.get(0), source, gameLogic);
                missedAction(missedCards.get(1), source, gameLogic);
                System.out.println("Volt nem talált lap!");
                return;
            }
        }else {
            while (true) {
                boolean bothAreMissed = true;
                List<Card> cards = new ArrayList<>();
                cards = gameLogic.selectTwoCards(getHandCards(), name, "Select 2 Missed cards to dodge Bang!");
                if (cards.isEmpty()) {
                    break;
                }
                for (Card card : cards) {
                    if (!(card instanceof MissedCard)) {
                        bothAreMissed = false;
                        break;
                    }
                }
                if (bothAreMissed) {
                    for (Card card : cards) {
                        if (card instanceof MissedCard missedCard) {
                            missedAction(missedCard, source, gameLogic);
                            System.out.println("Volt nem talált lap!");
                        }
                    }
                    return;
                }
            }
        }
        System.out.println("Betalált a bang :/, aktuális hp: " + getHealth());
        receiveDamage(1, source, gameLogic);
        System.out.println("ReceiveDMG utáni hp: " + getHealth());
    }

    public void missedAction(MissedCard missedCard, BaseModel target, GameLogic gameLogic){
        playDualTargetCard(missedCard, target, gameLogic);
    }

    public boolean beerAction(){
        //TODO 2 játékosnál nincs sörheal;
        System.out.println("HP BEVOR: " +health);
        health++;
        System.out.println("HP AFTER: " +health);
        return true;
    }

    public void saloonAction(GameLogic gameLogic){
        if(health < maxHP){
            health++;
        }
    }


    public void indiansAction(BaseModel source, GameLogic gameLogic){
        if(isBot){
            for(Card card : this.getHandCards()){
                if(card instanceof BangCard bangCard){
                    removeCard(bangCard);
                    return;
                }
            }
        }else{
            while(true){
                Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Bang! card or pass!");
                if(card instanceof BangCard bangCard){
                    removeCard(bangCard);
                    System.out.println("Rálőttél az indiánokra!");
                    return;
                }
                if(card == null){
                    break;
                }
            }
        }
        receiveDamage(1, source, gameLogic);
    }

    public void gatlingAction(BaseModel source, GameLogic gameLogic){
        bangAction(source, gameLogic);
    }

    public void duelAction(BaseModel target, GameLogic gameLogic){
        if(isBot){
            for(Card card : this.getHandCards()){
                if(card instanceof BangCard bangCard){
                    removeCard(bangCard);
                    return;
                }
            }
        }else {
            while (true) {
                Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Bang! card or pass!");
                if (card instanceof BangCard bangCard) {
                    discardCard(bangCard);
                    handCards.remove(bangCard);
                    System.out.println("Rálőttél az ellenre!");
                    target.duelAction(this, gameLogic);
                    return;
                }
                if (card == null) {
                    break;
                }
            }
        }
        receiveDamage(1, target, gameLogic);
    }

    public Card generalStoreAction(List<Card> cards, GameLogic gameLogic){
        Card card = null;
        if(isBot){
            Map<CardType, Card> firstCardOfType = new HashMap<>();
            for(Card gStoreCard : cards){
                firstCardOfType.putIfAbsent(gStoreCard.getType(), gStoreCard);
            }
            for(CardType prefferedType : getPreferredOrder()){
                card = firstCardOfType.get(prefferedType);
                if(card != null){
                    addHand(card);
                    return card;
                }
            }
        }else {
            while (card == null) {
                card = gameLogic.chooseCard(cards, name, "Choose a card!");
            }
            addHand(card);
        }
        return card;
    }

    public void catBalouAction(BaseModel target, GameLogic gameLogic){
        int index = 0;
        if(isBot){
            index = new Random().nextInt(target.getHandCards().size());
        }else {
            List<Card> cards = new ArrayList<>();
            for (int i = 0; i < target.getHandCards().size(); i++) {
                String cardName = "Hand card " + (i + 1);
                cards.add(new HandCard(cardName, "", i));
            }
            cards.addAll(target.tableCards);
            index = gameLogic.chooseFromHand(cards, name, "Choose a card to discard from " + target.getName());
        }
        if(index < target.getHandCards().size()){
            discardCard(target.getHandCards().get(index));
            target.getHandCards().remove(index);
            if(target instanceof SuzyLafayette){
                if(target.getHandCards().isEmpty()){
                    target.drawCard();
                }
            }
            return;
        }
        index -= target.getHandCards().size();
        Card card = target.getTableCards().get(index);
        discardCard(card);
        target.removeTableCards(card);
        if(card instanceof MustangCard){
            setVision();
        }
    }

    public void panicAction(BaseModel target, GameLogic gameLogic){
        int index = 0;
        if(isBot){
            index = new Random().nextInt(target.getHandCards().size() + target.getTableCards().size());
        }else {
            List<Card> cards = new ArrayList<>();
            for (int i = 0; i < target.getHandCards().size(); i++) {
                String cardName = "Hand card " + (i + 1);
                cards.add(new HandCard(cardName, "", i));
            }
            cards.addAll(target.tableCards);
            index = gameLogic.chooseFromHand(cards, name, "Choose a card to steal from " + target.getName());
        }
        if (index < target.getHandCards().size()) {
            handCards.add(target.getHandCards().get(index));
            target.getHandCards().remove(index);
            if (target instanceof SuzyLafayette) {
                if (target.getHandCards().isEmpty()) {
                    target.drawCard();
                }
            }
            return;
        }
        index -= target.getHandCards().size();
        Card card = target.getTableCards().get(index);
        handCards.add(card);
        target.removeTableCards(card);
        if(card instanceof MustangCard){
            setVision();
        }
    }

    public void removeTableCards(Card card){
        if(card instanceof Weapon){
            removeWeapon();
        }else if(card instanceof BarrelCard){
            removeBarrel();
        }else if(card instanceof JailCard){
            removeJail();
        }else if(card instanceof DynamiteCard){
            removeDynamite();
        }else if(card instanceof ScopeCard){
            removeScope();
        }else if(card instanceof MustangCard){
            removeMustang();
        }
    }

    public boolean barrelAction(GameLogic gameLogic){
        Card card = drawRetCard(gameLogic);
        discardCard(card);
        System.out.println(card.getSuit());
        if(card.getSuit().equals("Hearts")){
            return true;
        }
        return false;
    }

    public boolean jailAction(GameLogic gameLogic){
        Card card = drawRetCard(gameLogic);
        discardCard(card);
        System.out.println(card.getSuit());
        discardCard(jail);
        removeJail();
        return card.getSuit().equals("Hearts");
    }

    public DynamiteCard dynamiteAction(BaseModel source, GameLogic gameLogic){
        Card card = drawRetCard(gameLogic);
        discardCard(card);
        System.out.println(card.getSuit());
        DynamiteCard dynamiteCard = dynamite;
        removeDynamite();
        if(card.getSuit().equals("Spades") && card.getValue() >= 2 && card.getValue() <= 9){
            receiveDamage(3, source, gameLogic);
            discardCard(dynamiteCard);
            return null;
        }
        return dynamiteCard;
    }

    public GameInstance getGameInstance() {
        return this.gameInstance;
    }

    public int getHealth(){
        return this.health;
    }

    public void receiveDamage(int damage, BaseModel source, GameLogic gameLogic){
        this.health = health - damage;
        if(health <= 0){
            if(this.isBot){
                for (Card card : getHandCards()) {
                    if (card instanceof BeerCard beerCard) {
                        playSingleTargetCard(beerCard, gameLogic);
                    }
                    if (health > 0) {
                        System.out.println("Visszahoztad magad az életbe!");
                        return;
                    }
                }
            }
            else {
                while (true) {
                    Card card = gameLogic.chooseCard(getHandCards(), name, "Choose a Beer card or pass!");
                    if (card instanceof BeerCard beerCard) {
                        playSingleTargetCard(beerCard, gameLogic);
                    }
                    if (health > 0) {
                        System.out.println("Visszahoztad magad az életbe!");
                        return;
                    }
                    if (card == null) {
                        break;
                    }
                }
            }
            if(role.getType() == RoleType.OUTLAW){
                source.killedAnOutlaw();
            }
            if(role.getType() == RoleType.DEPUTY && source.role.getType() == RoleType.SHERIFF){
                source.sheriffKilledDeputy();
            }
            gameLogic.aPlayerRemoved(this);
            gameInstance.removePlayer(this);
            for(BaseModel baseModel : gameInstance.getPlayers()){
                if(baseModel instanceof VultureSam vultureSam){
                    vultureSam.collectCard(handCards);
                    return;
                }
            }
            discardCardsUponDeath();
        }
    }

    public int getMaxHP(){
        return this.maxHP;
    }

    public void setWeapon(Weapon weapon){
        if(getWeapon() != null){
            discardCard(getWeapon());
            removeWeapon();
        }
        handCards.remove(weapon);
        this.weapon = weapon;
        this.rapid = weapon.isRapid();
        tableCards.add(weapon);
    }

    public Weapon getWeapon(){
        return this.weapon;
    }

    public void removeWeapon(){
        tableCards.remove(weapon);
        this.weapon = null;
        this.rapid = false;
    }

    public boolean hasWeapon() {return this.weapon != null; }

    public void addBarrel(BarrelCard barrel){
        tableCards.add(barrel);
        this.barrel = barrel;
    }

    public void removeBarrel(){
        if(this.barrel != null) {
            tableCards.remove(this.barrel);
            this.barrel = null;
        }
    }

    public boolean hasBarrel(){
        return this.barrel != null;
    }

    public void setVisibility(int visibility){
        this.visibility = visibility;
    }

    public int getVisibility(){
        return visibility;
    }

    public void setFieldView(int fieldView){
        this.fieldView = fieldView;
    }

    public int getFieldView(){
        return fieldView;
    }

    public void addMustang(MustangCard mustang){
        visibility--;
        this.mustang = mustang;
        tableCards.add(mustang);
    }

    public void removeMustang(){
        if(this.mustang != null){
            visibility++;
            tableCards.remove(this.mustang);
            this.mustang = null;
        }
    }

    public boolean hasMustang(){
        return this.mustang != null;
    }

    public void addScope(ScopeCard scope){
        fieldView++;
        tableCards.add(scope);
        this.scope = scope;
        setVision();
    }

    public void removeScope(){
        fieldView--;
        tableCards.remove(this.scope);
        this.scope = null;
        setVision();
    }

    public boolean hasScope(){
        return this.scope != null;
    }

    public void addJail(JailCard jail){
        tableCards.add(jail);
        this.jail = jail;
    }

    public void removeJail(){
        tableCards.remove(this.jail);
        this.jail = null;
    }

    public boolean inJail(){
        return this.jail != null;
    }

    public void addDynamite(DynamiteCard dynamite){
        tableCards.add(dynamite);
        this.dynamite = dynamite;
    }

    public void removeDynamite(){
        tableCards.remove(this.dynamite);
        this.dynamite = null;
    }

    public boolean hasDynamite(){
        return this.dynamite != null;
    }

    public void setVision(){
        List<BaseModel> players = gameInstance.getInstance().getPlayers();
        int thisPlayerindex = players.indexOf(this);
        List<Integer> tempVision = new ArrayList<>(players.size());
        for(int i = 0; i < players.size(); i++){
            tempVision.add(99);
        }
        tempVision.set(thisPlayerindex, 0);

        //System.out.println(tempVision.toString());
        //vision.set(3, 0);
        for(int i = 1; i < players.size(); i++){
            int bigger = (i + thisPlayerindex) % players.size();
            if(tempVision.get(bigger) > i){
                tempVision.set(bigger, i - players.get(bigger).visibility - fieldView);
                //System.out.println("Bigger added:" + vision.toString());
            }
            int smaller = (thisPlayerindex - i + players.size()) % players.size();
            if(tempVision.get(smaller) > i){
                tempVision.set(smaller, i - players.get(smaller).visibility - fieldView);
                //System.out.println("Smaller added:" + vision.toString());
            }
        }
        //System.out.println(tempVision.toString());
        vision.clear();
        vision.addAll(tempVision);
    }

    public List<Integer> getVision(){
        return  vision;
    }

    public boolean isAlive(){
        return this.health > 0;
    }

    public void setBangedThisRound(boolean bool){
        bangedThisRound = bool;
    }

    public boolean getBangedThisRound(){
        return bangedThisRound;
    }

    public boolean getRapid(){
        return rapid;
    }

    public String getName(){
        return name;
    }

    public Role getRole(){
        return role;
    }

    public void killedAnOutlaw(){
        for(int i = 0; i < 3; i++){
            drawCard();
        }
    }

    public void sheriffKilledDeputy(){
        List<Card> handCardsCopy = new ArrayList<>(handCards);
        List<Card> tableCardsCopy = new ArrayList<>(tableCards);

        for (Card card : handCardsCopy) {
            removeCard(card);
        }
        for (Card card : tableCardsCopy) {
            removeTableCards(card);
        }
    }

    public void discardCardsUponDeath(){
        List<Card> handCardsCopy = new ArrayList<>(handCards);
        List<Card> tableCardsCopy = new ArrayList<>(tableCards);

        for (Card card : handCardsCopy) {
            removeCard(card);
        }
        for (Card card : tableCardsCopy) {
            removeTableCards(card);
        }
    }

    public boolean getIsBot(){
        return this.isBot;
    }

    public List<CardType> getPreferredOrder(){
        return List.of(CardType.SCOPE, CardType.WINCHESTER, CardType.BARREL, CardType.MUSTANG, CardType.CARABINE,
                CardType.WELLS_FARGO, CardType.STAGECOACH, CardType.PANIC, CardType.GENERAL_STORE, CardType.INDIANS,
                CardType.GATLING, CardType.JAIL, CardType.REMINGTON, CardType.BEER, CardType.CAT_BALOU, CardType.DUEL,
                CardType.DYNAMITE, CardType.BANG, CardType.MISSED, CardType.SCHOFIELD, CardType.VOLCANIC, CardType.SALOON);
    }
}

