package genspark.humansvsgoblins;

/**
 * Goblin and human players.
 */
public class Players {
    Human human;
    Goblin goblin;

    /**
     * @param human  The human player
     * @param goblin The goblin player
     */
    public Players(Human human, Goblin goblin) {
        this.human = human;
        this.goblin = goblin;
    }

    public Human getHuman() {
        return human;
    }

    public Goblin getGoblin() {
        return goblin;
    }
}
