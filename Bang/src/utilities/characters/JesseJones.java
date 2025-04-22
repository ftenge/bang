package utilities.characters;

import cards.Card;
import cards.HandCard;
import cards.bluecards.MustangCard;
import gamelogic.GameLogic;
import utilities.BaseModel;
import utilities.Character;
import utilities.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JesseJones extends BaseModel {
    public JesseJones(Role role, boolean isBot) {
        super(new Character("Jesse Jones", 4), role, isBot);
    }
    //az első lapot húzhatja bárki kezéből

    //done

    @Override
    public void roundStartDraw(GameLogic gameLogic){
        if(isBot){
            List<BaseModel> players = this.getGameInstance().getInstance().getPlayers();
            int thisPlayerIndex = players.indexOf(this);
            List<BaseModel> possibleTargets = new ArrayList<>();
            for(int i = 0; i < players.size(); i++){
                if(thisPlayerIndex != i){
                    possibleTargets.add(players.get(i));
                }
            }
            BaseModel randomTarget = possibleTargets.get(new Random().nextInt(possibleTargets.size()));
            int index = new Random().nextInt(randomTarget.getHandCards().size());

            handCards.add(randomTarget.getHandCards().get(index));
            randomTarget.getHandCards().remove(index);

            if (randomTarget instanceof SuzyLafayette suzyLafayette) {
                if (suzyLafayette.isHandEmpty()) {
                    suzyLafayette.drawCard();
                }
            }
        }else {
            List<BaseModel> playersWithHandCards = new ArrayList<>();
            for (BaseModel baseModel : gameInstance.getPlayers()) {
                if (!baseModel.getHandCards().isEmpty() && baseModel != this) {
                    playersWithHandCards.add(baseModel);
                }
            }
            if (playersWithHandCards.isEmpty()) {
                for (int i = 0; i < 2; i++) {
                    drawCard();
                }
                return;
            }

            int decision = gameLogic.chooseOption(name, "Do you want to draw the first card from someone?", "Draw normally", "Panic from someone");
            if (decision == 0) {
                for (int i = 0; i < 2; i++) {
                    drawCard();
                }
                return;
            }
            int targetIndex = 0;
            if (playersWithHandCards.size() != 1) {
                targetIndex = gameLogic.chooseTarget(playersWithHandCards, name, "Select a player to steal from!");
            }


            BaseModel target = playersWithHandCards.get(targetIndex);

            List<Card> cards = new ArrayList<>();
            for (int i = 0; i < target.getHandCards().size(); i++) {
                String cardName = "Hand card " + (i + 1);
                cards.add(new HandCard(cardName, "", i));
            }

            int index = gameLogic.chooseFromHand(cards, name, "Choose a card to steal from " + target.getName());

            handCards.add(target.getHandCards().get(index));
            target.getHandCards().remove(index);
            if (target instanceof SuzyLafayette suzyLafayette) {
                if (suzyLafayette.isHandEmpty()) {
                    suzyLafayette.drawCard();
                }
            }
        }
        drawCard();
    }
}
