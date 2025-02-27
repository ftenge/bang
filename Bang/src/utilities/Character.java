package utilities;

import players.Player;

public class Character {
    private String name;
    private int health;

    public Character(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void useAbility(Player player) {
        // Unique character ability logic
    }
}
