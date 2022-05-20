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

    /**
     * If the goblin and human are on top of each other, then move them apart.
     */
    public void deStackPlayers() {
        if (human.getCoordinates().equals(goblin.getCoordinates()))
            goblin.moveEast();
    }

    /**
     * Removes whoever loses from the land.
     *
     * @param gameState Current state of the game
     */
    void removeLosingPlayer(GameState gameState) {
        if (gameState.equals(GameState.WON)) {
            goblin.shape = "  ";
        } else if (gameState.equals(GameState.LOST)) {
            human.shape = "  ";
        }
    }

    public Human getHuman() {
        return human;
    }

    public Goblin getGoblin() {
        return goblin;
    }
}
