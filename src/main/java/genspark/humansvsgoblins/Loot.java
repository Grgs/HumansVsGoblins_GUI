package genspark.humansvsgoblins;

import java.util.ArrayList;
import java.util.PrimitiveIterator;
import java.util.Random;

/**
 * A list of loot available on the land.
 */
public class Loot extends Piece {

    /**
     * Create a piece of loot.
     *
     * @param coordinates Initial coordinates of the loot.
     */
    public Loot(Coordinates coordinates) {
        super(coordinates);
        this.shape = " +";
    }

    /**
     * Generate a random list of loot.
     *
     * @return a random list of loot.
     */
    public static ArrayList<Piece> getLootList() {
        Random random = new Random();
        int numberOfLoot = Math.max(MaxCoordinates.maxCols, MaxCoordinates.maxRows);
        PrimitiveIterator.OfInt randRows = random.ints(numberOfLoot,
                0, MaxCoordinates.maxRows).iterator();
        PrimitiveIterator.OfInt randCols = random.ints(numberOfLoot,
                0, MaxCoordinates.maxCols).iterator();
        PrimitiveIterator.OfInt randValue = random.ints(numberOfLoot,
                1, 10).iterator();
        ArrayList<Piece> lootList = new ArrayList<>();
        for (int i = 0; i < numberOfLoot; i++) {
            Loot loot = new Loot(new Coordinates(randCols.nextInt(), randRows.nextInt()));
            switch (i % 3) {
                case 0:
                    loot.attack = randValue.nextInt();
                    break;
                case 1:
                    loot.health = randValue.nextInt();
                    break;
                case 2:
                    loot.defence = randValue.nextInt() / 2;
                    break;
            }
            lootList.add(loot);
        }
        return lootList;
    }

}
