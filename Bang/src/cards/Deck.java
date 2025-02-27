package cards;

import cards.bluecards.*;
import cards.browncards.*;
import cards.weapons.*;

import java.util.*;

public class Deck {
    private Stack<Card> cards;
    private List<Card> discardPile;

    public Deck() {
        this.cards = new Stack<>();
        this.discardPile = new ArrayList<>();

    }

    private void generateCard(){
        List<String> suits = new ArrayList<>();
        suits.add("Hearts");
        suits.add("Diamonds");
        suits.add("Spades");
        suits.add("Clovers");
        Collections.shuffle(suits);

        List<Integer> suitValue = new ArrayList<>();
        for(int i = 1; i <= 80; i++){
            suitValue.add(i);
        }
        Collections.shuffle(suitValue);

        generateCardSuitValue(BangCard.class, 25, suitValue, suits);
        generateCardSuitValue(MissedCard.class, 12, suitValue, suits);
        generateCardSuitValue(BeerCard.class, 6, suitValue, suits);
        generateCardSuitValue(PanicCard.class, 4, suitValue, suits);
        generateCardSuitValue(CatBalouCard.class, 4, suitValue, suits);
        generateCardSuitValue(DuelCard.class, 3, suitValue, suits);
        generateCardSuitValue(StagecoachCard.class, 2, suitValue, suits);
        generateCardSuitValue(GeneralStore.class, 2, suitValue, suits);
        generateCardSuitValue(IndiansCard.class, 2, suitValue, suits);
        generateCardSuitValue(WellsFargoCard.class, 1, suitValue, suits);
        generateCardSuitValue(GatlingCard.class, 1, suitValue, suits);
        generateCardSuitValue(SaloonCard.class, 1, suitValue, suits);
        generateCardSuitValue(SchofieldWeapon.class, 3, suitValue, suits);
        generateCardSuitValue(VolcanicWeapon.class, 2, suitValue, suits);
        generateCardSuitValue(RemingtonWeapon.class, 1, suitValue, suits);
        generateCardSuitValue(CarabineWeapon.class, 1, suitValue, suits);
        generateCardSuitValue(WinchesterWeapon.class, 1, suitValue, suits);
        generateCardSuitValue(JailCard.class, 3, suitValue, suits);
        generateCardSuitValue(BarrelCard.class, 2, suitValue, suits);
        generateCardSuitValue(MustangCard.class, 2, suitValue, suits);
        generateCardSuitValue(ScopeCard.class, 1, suitValue, suits);
        generateCardSuitValue(DynamiteCard.class, 1, suitValue, suits);

        reshuffleDiscards();
    }

    private <T extends Card> void generateCardSuitValue(Class<T> cardType, int count, List<Integer> suitValue, List<String> suits) {
        try {
            for (int i = 0; i < count; i++) {
                discardPile.add(cardType.getDeclaredConstructor(String.class, int.class).newInstance(suits.get(suitValue.get(0) % 4), (suitValue.get(0) % 13) + 2));
                suitValue.remove(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Card draw() {
        if (cards.isEmpty()) reshuffleDiscards();
        return cards.pop();
    }

    public void reshuffleDiscards() {
        Collections.shuffle(discardPile);
        cards.addAll(discardPile);
        discardPile.clear();
    }
}