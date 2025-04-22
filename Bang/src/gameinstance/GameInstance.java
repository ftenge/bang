package gameinstance;

import cards.Deck;
import utilities.BaseModel;
import utilities.Role;
import utilities.RoleType;
import utilities.characters.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameInstance {
    private static GameInstance instance;
    private List<BaseModel> players;
    private Deck deck;

    private GameInstance() {
        this.players = new ArrayList<>();
        this.deck = new Deck();
        //initializePlayers();
    }

    //ha nincs még gameInstance akkor létrehozza, ha van akkor azt adja vissza
    public static GameInstance getInstance() {
        if (instance == null) {
            instance = new GameInstance();
        }
        return instance;
    }

    //megkapja az összes karaktert és szerepet, majd összekeveri őket.
    //a játékos szám alapján hoz létre karaktereket random szereppel
    //majd úgy módosítja a listát, hogy a sheriffel kezdődjön
    /*public void initializePlayers2(int numberOfPlayers) {
        List<Class<? extends BaseModel>> allCharacterClasses = getAllCharacterClasses(numberOfPlayers); // 16 karakter osztály
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
    }*/

    public void initializePlayers(int count, List<String> characterNames, List<String> roleNames, List<String> bots) {
        players.clear();
        System.out.println("RoleNamesB: " + roleNames);
        List<Role> roles = getRolesForGame(count, roleNames);
        System.out.println("RoleNamesA: " + roleNames);

        ArrayList<String> allCharacterNames = new ArrayList( Arrays.asList(getAllCharacterNames()));

        for (int i = 0; i < count; i++) {
            String characterName = characterNames.get(i);
            String roleName = roleNames.get(i);
            Role role = new Role(RoleType.SHERIFF);

            System.out.println(roleName);

            if (roleName.equals("Random")) {
                roles = getRandomRole(roles);
                role = roles.getLast();
                roles.removeLast();
            }else {
                switch (roleName) {
                    case "Sheriff":
                        role = new Role(RoleType.SHERIFF);
                        break;
                    case "Outlaw":
                        role = new Role(RoleType.OUTLAW);
                        break;
                    case "Deputy":
                        role = new Role(RoleType.DEPUTY);
                        break;
                    case "Renegade":
                        role = new Role(RoleType.RENEGADE);
                        break;
                }
            }
            System.out.println(role.getType());

            if (characterName.equals("Random")) {
                allCharacterNames = getRandomCharacter(allCharacterNames);
                System.out.println(allCharacterNames);
                characterName = allCharacterNames.getLast();
                allCharacterNames.removeLast();
            }
            Class<? extends BaseModel> characterClass = getCharacterFromCharacterName(characterName);
            System.out.println("Felismerőszöveg: " + characterClass);

            BaseModel player = null;
            try {
                if(bots.get(i).equals("True")){
                    player = characterClass.getDeclaredConstructor(Role.class, boolean.class).newInstance(role, true);
                }else{
                    player = characterClass.getDeclaredConstructor(Role.class, boolean.class).newInstance(role, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            players.add(player);
        }
    }

    public List<Role> getRandomRole(List<Role> remainingRoles){
        Random rand = new Random();
        Role randomRole = remainingRoles.get(rand.nextInt(remainingRoles.size()));
        remainingRoles.remove(randomRole);
        remainingRoles.addLast(randomRole);
        return remainingRoles;
    }

    public ArrayList<String> getRandomCharacter(ArrayList<String> remainingCharacters){
        Random rand = new Random();
        String randomCharacter = remainingCharacters.get(rand.nextInt(remainingCharacters.size()));
        remainingCharacters.remove(randomCharacter);
        remainingCharacters.addLast(randomCharacter);
        return remainingCharacters;
    }

    public Class<? extends BaseModel> getCharacterFromCharacterName(String characterName){
        for(Class<? extends BaseModel> character : getAllCharacterClasses()){
            if(character.getName().substring(21).equals(characterName)){
                return character;
            }
        }
        System.out.println("Itt a hiba, charactername: " + characterName);
        return BaseModel.class;
    }

    public void setPlayers(List<BaseModel> selectedPlayers) {
        this.players = new ArrayList<>(selectedPlayers);
        int sheriffIndex = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getRole().getType() == RoleType.SHERIFF) {
                sheriffIndex = i;
                break;
            }
        }
        reorderSheriffFirst(sheriffIndex);
    }

    //létrehoz egy listát az összes characterClassal
    public List<Class<? extends BaseModel>> getAllCharacterClasses() {
        List<Class<? extends BaseModel>> characterClasses = new ArrayList<>();
        characterClasses.add(LuckyDuke.class);
        characterClasses.add(SuzyLafayette.class);
        characterClasses.add(CalamityJanet.class);
        characterClasses.add(JesseJones.class);
        characterClasses.add(WillyTheKid.class);
        characterClasses.add(KitCarlson.class);
        characterClasses.add(PedroRamirez.class);
        characterClasses.add(RoseDoolan.class);
        characterClasses.add(ElGringo.class);
        characterClasses.add(PaulRegret.class);
        characterClasses.add(SlabTheKiller.class);
        characterClasses.add(VultureSam.class);
        characterClasses.add(BartCassidy.class);
        characterClasses.add(BlackJack.class);
        characterClasses.add(JourDonnais.class);
        characterClasses.add(SidKetchum.class);
        return characterClasses;
    }

    public String[] getAllCharacterNames(){
        List<Class<? extends BaseModel>> characters = getAllCharacterClasses();
        String[] characterNames = new String[characters.size()];
        //characterNames[0] = "Random";
        for(int i = 0; i < characters.size(); i++){
            characterNames[i] = characters.get(i).getName().substring(21);
        }
        return characterNames;
    }

    //létrehoz egy listát az összes szereppel
    private List<Role> getRolesForGame(int numberOfPlayers, List<String> roleNames) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(RoleType.SHERIFF));
        roles.add(new Role(RoleType.OUTLAW));
        roles.add(new Role(RoleType.RENEGADE));
        roles.add(new Role(RoleType.OUTLAW));
        roles.add(new Role(RoleType.DEPUTY));
        roles.add(new Role(RoleType.OUTLAW));
        roles.add(new Role(RoleType.DEPUTY));

        roles = roles.subList(0, numberOfPlayers);
        List<Role> returnRoles = new ArrayList<>();
        List<String> tempRoleNames = new ArrayList<>(roleNames);
        for(Role role : roles){
            if(role.getType() == RoleType.SHERIFF && tempRoleNames.contains("Sheriff")){
                tempRoleNames.remove("Sheriff");
                continue;
            }
            if(role.getType() == RoleType.DEPUTY && tempRoleNames.contains("Deputy")){
                tempRoleNames.remove("Deputy");
                continue;
            }
            if(role.getType() == RoleType.OUTLAW && tempRoleNames.contains("Outlaw")){
                tempRoleNames.remove("Outlaw");
                continue;
            }
            if(role.getType() == RoleType.RENEGADE && tempRoleNames.contains("Renegade")){
                tempRoleNames.remove("Renegade");
                continue;
            }
            returnRoles.add(role);
        }


        return returnRoles;
    }

    //ha nem a sheriff az első, akkor azt a részlistát beszúrja a lista végére
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

    //visszaadja a játékosokat
    public List<BaseModel> getPlayers() {
        return players;
    }

    //visszaadja a paklit
    public Deck getDeck() {
        return deck;
    }

    //eltávolítja a megadott játékost a játékos listából
    public void removePlayer(BaseModel baseModel){
        players.remove(baseModel);
    }
}
