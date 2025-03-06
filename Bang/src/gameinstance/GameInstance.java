package gameinstance;

import cards.Deck;
import utilities.BaseModel;
import utilities.Role;
import utilities.RoleType;
import utilities.characters.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameInstance {
    private static GameInstance instance;
    private List<BaseModel> players;
    private Deck deck;

    private GameInstance() {
        this.players = new ArrayList<>();
        this.deck = new Deck();
        //initializePlayers();
    }

    public static GameInstance getInstance() {
        if (instance == null) {
            instance = new GameInstance();
        }
        return instance;
    }

    public void initializePlayers(int numberOfPlayers) {
        List<Class<? extends BaseModel>> allCharacterClasses = getAllCharacterClasses(); // 16 karakter osztály
        Collections.shuffle(allCharacterClasses); // Véletlenszerű sorrend

        List<Role> roles = getRolesForGame(numberOfPlayers); // 7 szerep
        Collections.shuffle(roles); // Véletlenszerű sorrend

        int sheriffIndex = 0;
        for (int i = 0; i < numberOfPlayers; i++) {
            Class<? extends BaseModel> characterClass = allCharacterClasses.get(i); // Véletlenszerű karakter
            Role assignedRole = roles.get(i); // Véletlenszerű szerep
            if(roles.get(i).getType() == RoleType.SHERIFF){
                sheriffIndex = i;
            }

            try {
                BaseModel player = characterClass.getDeclaredConstructor(Role.class).newInstance(assignedRole);
                players.add(player);
                System.out.println("Assigned " + player.getName() + " as " + assignedRole.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        reorderSheriffFirst(sheriffIndex);
    }

    private List<Class<? extends BaseModel>> getAllCharacterClasses() {
        List<Class<? extends BaseModel>> characterClasses = new ArrayList<>();
        characterClasses.add(BartCassidy.class);
        characterClasses.add(BlackJack.class);
        characterClasses.add(CalamityJanet.class);
        characterClasses.add(ElGringo.class);
        characterClasses.add(JesseJones.class);
        characterClasses.add(JourDonnais.class);
        characterClasses.add(KitCarlson.class);
        characterClasses.add(LuckyDuke.class);
        characterClasses.add(PaulRegret.class);
        characterClasses.add(PedroRamirez.class);
        characterClasses.add(RoseDoolan.class);
        characterClasses.add(SlabTheKiller.class);
        characterClasses.add(SidKetchum.class);
        characterClasses.add(SuzyLafayette.class);
        characterClasses.add(VultureSam.class);
        characterClasses.add(WillyTheKid.class);
        return characterClasses;
    }

    private List<Role> getRolesForGame(int numberOfPlayers) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(RoleType.SHERIFF));
        roles.add(new Role(RoleType.OUTLAW));
        roles.add(new Role(RoleType.RENEGADE));
        roles.add(new Role(RoleType.OUTLAW));
        roles.add(new Role(RoleType.DEPUTY));
        roles.add(new Role(RoleType.OUTLAW));
        roles.add(new Role(RoleType.DEPUTY));

        return roles.subList(0,numberOfPlayers);
    }

    private void reorderSheriffFirst(int sheriffIndex) {

        // Ha van Sheriff és nem az első, akkor átrendezés
        if (sheriffIndex > 0) {
            List<BaseModel> reorderedPlayers = new ArrayList<>();

            // Sheriff és utána lévők előre kerülnek
            reorderedPlayers.addAll(players.subList(sheriffIndex, players.size()));
            // Sheriff előttiek hátra kerülnek
            reorderedPlayers.addAll(players.subList(0, sheriffIndex));

            players = reorderedPlayers;
        }
    }

    public List<BaseModel> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void removePlayer(BaseModel baseModel){
        players.remove(baseModel);
    }
}
