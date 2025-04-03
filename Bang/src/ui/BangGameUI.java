package ui;

import gameinstance.GameInstance;
import gamelogic.GameLogic;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utilities.BaseModel;
import cards.Card;
import cards.DualTargetCard;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class BangGameUI extends Application {
    private GameInstance gameInstance;
    private GameLogic gameLogic;
    private Label gameStateLabel;
    private Label lastDiscarded;
    private VBox playersInfoBox;
    private VBox currentPlayerInfoBox;
    private Map<BaseModel, ListView<String>> playerHandsMap;
    private Button nextTurnButton;
    private ListView<String> handCardsListView;
    private Button playCardButton;
    private Button discardCardButton;
    private ComboBox<BaseModel> targetPlayerSelector;

    //innen futtatjuk a programot egyelőre
    public static void main(String[] args) {
        launch(args);
    }

    //létrehozza a ui-hoz szükséges dolgokat, elindítja a játékot és frissíti a ui-t
    @Override
    public void start(Stage primaryStage) {
        gameInstance = GameInstance.getInstance();
        gameLogic = new GameLogic(this);

        gameStateLabel = new Label();
        lastDiscarded = new Label();
        playersInfoBox = new VBox(10);
        currentPlayerInfoBox = new VBox(10);
        playerHandsMap = new HashMap<>();

        // Kártyahasználat gomb és célpontválasztás
        playCardButton = new Button("Play Selected Card");
        playCardButton.setOnAction(e -> playSelectedCard());
        targetPlayerSelector = new ComboBox<>();

        nextTurnButton = new Button("Next Turn");
        nextTurnButton.setOnAction(e -> nextTurn());

        discardCardButton = new Button("Discard Selected Card");
        discardCardButton.setOnAction(e -> discardSelectedCard());

        BorderPane root = new BorderPane();
        root.setCenter(playersInfoBox);
        root.setRight(currentPlayerInfoBox);
        root.setBottom(new VBox(10, targetPlayerSelector, playCardButton, discardCardButton,nextTurnButton));
        root.setTop(lastDiscarded);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Bang! Game UI");
        primaryStage.setScene(scene);
        primaryStage.show();

        gameLogic.startGame();
        updateUI();
    }

    //frissíti a ui-t:
    //kiírja a körön lévő játékost, a legutolsó eldobott lapot
    //minden játékost kiír a kezükben lévő lapokkal együtt
    private void updateUI() {
        playersInfoBox.getChildren().clear();
        currentPlayerInfoBox.getChildren().clear();

        BaseModel currentPlayer = gameLogic.getCurrentPlayer();
        gameStateLabel.setText("Current Player: " + currentPlayer.getName());
        lastDiscarded.setText(gameInstance.getDeck().seeLastDiscardedCard());

        Label currentPlayerLabel = new Label("🔹 Current Player Info 🔹");


        targetPlayerSelector.getItems().clear();
        targetPlayerSelector.getItems().addAll(gameInstance.getPlayers());
        targetPlayerSelector.setValue(null); // Nincs előre kiválasztott célpont

        for (BaseModel player : gameInstance.getPlayers()) {
            Label playerLabel = new Label(player.getName());
            playersInfoBox.getChildren().add(playerLabel);
            Label playerStats = new Label(player.datas());
            currentPlayerInfoBox.getChildren().add(playerStats);

            ListView<String> handCardsView = new ListView<>();
            ObservableList<String> handCards = FXCollections.observableArrayList();
            for (Card card : player.getHandCards()) {
                handCards.add(card.toString());
            }
            handCardsView.setItems(handCards);

            //selectCardFromList(player.getHandCards(), "asd");

            handCardsView.setDisable(!player.equals(currentPlayer));
            playerHandsMap.put(player, handCardsView);

            playersInfoBox.getChildren().add(handCardsView);

            // Ha az aktuális játékosnál vagyunk, frissítsük a kézben lévő kártyák listáját
            if (player.equals(currentPlayer)) {
                handCardsListView = handCardsView;
            }
        }
    }

    //ha kiválasztottunk egy kártyát, megnézi, hogy választottunk-e mellé targetet, ha kell a kártyánk kijátszásához
    //ha nem akkor az aktuálist játékost állítjuk be célpontnak, hogy ne kelljen targetet választani
    //frissítjük a ui-t
    private void playSelectedCard() {
        BaseModel currentPlayer = gameLogic.getCurrentPlayer();
        int selectedIndex = handCardsListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            showAlert("No card selected", "Please select a card to play.");
            return;
        }

        Card selectedCard = currentPlayer.getHandCards().get(selectedIndex);

        // 🔹 Ha a kártyának célpont kell, azt kiválasztjuk
        if (selectedCard instanceof DualTargetCard) {
            BaseModel target = targetPlayerSelector.getValue();
            if (target == null || target.equals(currentPlayer)) {
                showAlert("Invalid Target", "Please select a valid target.");
                return;
            }
            gameLogic.cardAction(selectedCard, currentPlayer, target);
        } else {
            gameLogic.cardAction(selectedCard, currentPlayer, currentPlayer);
        }
        updateUI();
    }

    //a kiválasztott kártyára meghívjuk a gameLogic discardCardActionjét
    private void discardSelectedCard(){
        BaseModel currentPlayer = gameLogic.getCurrentPlayer();
        int selectedIndex = handCardsListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            return;
        }

        playCardButton.setDisable(true);
        Card selectedCard = currentPlayer.getHandCards().get(selectedIndex);

        gameLogic.discardCardAction(selectedCard, currentPlayer);
        updateUI();
    }


    //a playCardButtont nullázzuk és meghívjuk az endTurn függvényt és frissítjük a ui-t
    private void nextTurn() {
        playCardButton.setDisable(false);
        gameLogic.endTurn();
        updateUI();
    }

    //hiba esetén kiírjuk a hibát
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public int selectCardFromList(List<Card> cards, String name, String title) {
        AtomicReference<Integer> selectedCard = new AtomicReference<>(null);

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle(name);

        Label label = new Label(title);

        ListView<String> cardListView = new ListView<>();
        for(Card card : cards){
            cardListView.getItems().add(card.getName());
        }

        //cardListView.getItems().addAll(cards);

        Button okButton = new Button("OK");
        okButton.setDisable(true); // Kezdetben inaktív

        Button passButton = new Button("Pass");

        // figyeljük, hogy van-e kiválasztott kártya
        cardListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            okButton.setDisable(newSelection == null);
        });

        // OK gomb: választás esetén visszatérünk a kártyával
        okButton.setOnAction(e -> {
            selectedCard.set(cardListView.getSelectionModel().getSelectedIndex());
            dialogStage.close();
        });

        // Pass gomb: null-lal tér vissza (kihagyás)
        passButton.setOnAction(e -> {
            selectedCard.set(null);
            dialogStage.close();
        });

        VBox vbox = new VBox(10, label, cardListView, okButton, passButton);
        vbox.setPrefWidth(300);
        Scene scene = new Scene(vbox);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        return (selectedCard.get() != null) ? selectedCard.get() : -1;
    }

    public int selectTargetFromList(List<BaseModel> baseModels, String name, String title) {
        AtomicReference<Integer> selectedTarget = new AtomicReference<>(null);

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle(name);

        Label label = new Label(title);

        ListView<String> cardListView = new ListView<>();
        for(BaseModel baseModel : baseModels){
            cardListView.getItems().add(baseModel.getName());
        }

        //cardListView.getItems().addAll(cards);

        Button okButton = new Button("OK");
        okButton.setDisable(true); // Kezdetben inaktív

        Button passButton = new Button("Pass");

        // Figyeljük, hogy van-e kiválasztott kártya
        cardListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            okButton.setDisable(newSelection == null);
        });

        // OK gomb: választás esetén visszatérünk a kártyával
        okButton.setOnAction(e -> {
            selectedTarget.set(cardListView.getSelectionModel().getSelectedIndex());
            dialogStage.close();
        });

        // Pass gomb: null-lal tér vissza (kihagyás)
        passButton.setOnAction(e -> {
            selectedTarget.set(null);
            dialogStage.close();
        });

        VBox vbox = new VBox(10, label, cardListView, okButton, passButton);
        vbox.setPrefWidth(300);
        Scene scene = new Scene(vbox);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        return (selectedTarget.get() != null) ? selectedTarget.get() : -1;
    }

    public int showTwoOptionDialog(String title, String message, String option1, String option2) {
        AtomicReference<Integer> selectedOption = new AtomicReference<>(null);

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle(title);

        Label label = new Label(message);

        Button button1 = new Button(option1);
        Button button2 = new Button(option2);

        button1.setOnAction(e -> {
            selectedOption.set(1);
            dialogStage.close();
        });

        button2.setOnAction(e -> {
            selectedOption.set(2);
            dialogStage.close();
        });

        VBox vbox = new VBox(10, label, button1, button2);
        vbox.setPrefWidth(300);
        Scene scene = new Scene(vbox);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        return selectedOption.get();
    }

    public List<Card> selectTwoCardsFromThree(List<Card> ogCards, String title, String instruction) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        Label instructionLabel = new Label(instruction);

        ListView<Card> listView = new ListView<>();
        ObservableList<Card> observableCards = FXCollections.observableArrayList(ogCards);
        listView.setItems(observableCards);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button confirmButton = new Button("OK");
        confirmButton.setDisable(true); // Kezdetben letiltva

        Button passButton = new Button("Pass"); // Pass gomb

        // 🔹 Figyeljük a kiválasztott elemek listájának változását
        listView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Card>) change -> {
            confirmButton.setDisable(listView.getSelectionModel().getSelectedItems().size() != 2);
        });

        // 🔹 "OK" gomb működése
        confirmButton.setOnAction(e -> popupStage.close());

        // 🔹 "Pass" gomb működése (bezárja az ablakot és null-t ad vissza)
        passButton.setOnAction(e -> {
            listView.getSelectionModel().clearSelection(); // Kiválasztás törlése
            popupStage.close();
        });

        VBox layout = new VBox(10, instructionLabel, listView, confirmButton, passButton);
        Scene scene = new Scene(layout, 300, 300);
        popupStage.setScene(scene);
        popupStage.showAndWait();

        // 🔹 Ha a "Pass" gombot nyomták meg, null-t adunk vissza
        if (listView.getSelectionModel().getSelectedItems().isEmpty()) {
            return null;
        }

        List<Card> selectedCards = new ArrayList<>(listView.getSelectionModel().getSelectedItems());

        // 🔹 Ha valamiért nem pontosan 2 kártya lett kiválasztva, kitöltjük egy harmadikkal (de ez nem kellene megtörténjen)
        for (Card card : ogCards) {
            if (!selectedCards.contains(card)) {
                selectedCards.add(card);
                break;
            }
        }

        return selectedCards;
    }


}
