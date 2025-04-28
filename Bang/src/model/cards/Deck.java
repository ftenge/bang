package model.cards;

import model.cards.bluecards.*;
import model.cards.browncards.*;
import model.cards.weapons.*;

import java.util.*;

public class Deck {
    private Stack<Card> cards;
    private Stack<Card> discardPile;

    //létrehozza a húzó és a dobópakli, és létrehozza a kártyákat
    public Deck() {
        this.cards = new Stack<>();
        this.discardPile = new Stack<>();
        initializeDeck();
    }

    //létrehozzuk a paklit úgy, hogy minden kártya kapjon egy random színt és számot.
    //minden körben újakat kapnak, minden szín/érték kombó legalább egyszer benne van a pakliban

    private void initializeDeck(){
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

        generateCardSuitValue(BangCard.class, 25, suitValue, suits, "src/assets/model.cards/bang.png");
        generateCardSuitValue(MissedCard.class, 12, suitValue, suits, "src/assets/model.cards/missed.png");
        generateCardSuitValue(BeerCard.class, 6, suitValue, suits, "src/assets/model.cards/beer.png");
        generateCardSuitValue(PanicCard.class, 4, suitValue, suits, "src/assets/model.cards/panic.png");
        generateCardSuitValue(CatBalouCard.class, 4, suitValue, suits, "src/assets/model.cards/catbalou.png");
        generateCardSuitValue(DuelCard.class, 3, suitValue, suits, "src/assets/model.cards/duel.png");
        generateCardSuitValue(StagecoachCard.class, 2, suitValue, suits, "src/assets/model.cards/stagecoach.png");
        generateCardSuitValue(GeneralStore.class, 2, suitValue, suits, "src/assets/model.cards/generalstore.png");
        generateCardSuitValue(IndiansCard.class, 2, suitValue, suits, "src/assets/model.cards/indians.png");
        generateCardSuitValue(WellsFargoCard.class, 1, suitValue, suits, "src/assets/model.cards/wellsfargo.png");
        generateCardSuitValue(GatlingCard.class, 1, suitValue, suits, "src/assets/model.cards/gatling.png");
        generateCardSuitValue(SaloonCard.class, 1, suitValue, suits, "src/assets/model.cards/saloon.png");
        generateCardSuitValue(SchofieldWeapon.class, 3, suitValue, suits, "src/assets/model.cards/schofield.png");
        generateCardSuitValue(VolcanicWeapon.class, 2, suitValue, suits, "src/assets/model.cards/volcanic.png");
        generateCardSuitValue(RemingtonWeapon.class, 1, suitValue, suits, "src/assets/model.cards/remington.png");
        generateCardSuitValue(CarabineWeapon.class, 1, suitValue, suits, "src/assets/model.cards/carabine.png");
        generateCardSuitValue(WinchesterWeapon.class, 1, suitValue, suits, "src/assets/model.cards/winchester.png");
        generateCardSuitValue(JailCard.class, 3, suitValue, suits, "src/assets/model.cards/jail.png");
        generateCardSuitValue(BarrelCard.class, 2, suitValue, suits, "src/assets/model.cards/barrel.png");
        generateCardSuitValue(MustangCard.class, 2, suitValue, suits, "src/assets/model.cards/mustang.png");
        generateCardSuitValue(ScopeCard.class, 1, suitValue, suits, "src/assets/model.cards/scope.png");
        generateCardSuitValue(DynamiteCard.class, 1, suitValue, suits, "src/assets/model.cards/dynamite.png");

        Collections.shuffle(cards);
    }

    //létrehozzuk a különböző kártyaosztályokat a getDeclaredConstructorral, a suitValue első értéke alapján meghatározzuk
    //a kártya színét és értékét, majd kivesszük
    private <T extends Card> void generateCardSuitValue(Class<T> cardType, int count, List<Integer> suitValue, List<String> suits, String imagePath) {
        try {
            for (int i = 0; i < count; i++) {
                cards.add(cardType.getConstructor(String.class, int.class, String.class).newInstance(suits.get(suitValue.getFirst() % 4), ((suitValue.getFirst() % 13) + 2), imagePath));
                suitValue.removeFirst();
                //System.out.println(discardPile.get(79 - suitValue.size()).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //húzunk egy kártyát, ha üres a pakli újrakeverjük, majd visszaadjuk a legfelső elemet
    public Card draw() {
        if (cards.isEmpty()) reshuffleDiscards();
        return cards.pop();
    }

    //az eldobott kártya a dobópakli tetejére kerül
    public void discard(Card card) {
        discardPile.add(card);
    }

    //a legutolsó eldobott lap kivételével összekeverjük az eldobott lapokat
    //és hozzáadjuk a sima paklihoz, a legutolsó eldobott lapot otthagyjuk,
    //mert néhány karakter tud interaktálni a dobópakli tetején lévő lappal
    public void reshuffleDiscards() {
        Card card = discardPile.pop();
        Collections.shuffle(discardPile);
        cards.addAll(discardPile);
        discardPile.clear();
        discardPile.add(card);
    }

    //megnézi, hogy üres-e a dobópakli
    public boolean isDiscardPileEmpty(){
        return discardPile.isEmpty();
    }

    //megadja a dobópkali tetején lévő kártya nevét, ha van
    public String seeLastDiscardedCard(){
        if(discardPile.isEmpty()){
            return "Discard pile is empty!";
        }
        return discardPile.lastElement().toString();
    }

    //visszatesznek a pakliba egy lapot
    public void putBack(Card card){
        cards.add(card);
    }

    //visszaadja a dobópakli tetején lévő lapot
    public Card getLastDiscard(){
        return discardPile.pop();
    }

    //visszaadja a dobópakli tetején lévő lap nevét
    public String lastDiscardedName(){
        return discardPile.getLast().toString();
    }

    public Stack<Card> getDiscardPile(){
        return discardPile;
    }
}