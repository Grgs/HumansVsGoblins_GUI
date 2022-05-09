package genspark.humansvsgoblins;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Human extends Player {

    public Human() {
        super();
        this.shape = "\uD83D\uDC64"; //👤
    }

    public void move(Scanner scanner) {
        String key;
        try {
            key = String.valueOf(scanner.next().strip().toLowerCase(Locale.ROOT).charAt(0));
        } catch (Exception e) {
            key = "n";
        }
        this.move(key);
    }

    public void move(String key) {
        switch (key) {
            case "w":
            case "up":
                this.moveNorth();
                break;
            case "a":
            case "left":
                this.moveWest();
                break;
            case "s":
            case "down":
                this.moveSouth();
                break;
            case "d":
            case "right":
                this.moveEast();
                break;
            case "q":
                System.out.println("Quitting!");
                System.exit(0);
            default:
                System.out.println("Move key not recognized. Turn skipped.");
        }
    }

    public ArrayList<Piece> absorbLoot(ArrayList<Piece> lootList) {
        List<Piece> capturedLootList = lootList.stream().filter(l -> l.coordinates.
                equals(this.getCoordinates())).collect(Collectors.toList());
        if (capturedLootList.size() == 0)
            return lootList;
        Loot capturedLoot = (Loot) capturedLootList.get(0);
        this.inventory.add(capturedLoot);
        if (capturedLoot.health > 0) {
            this.setHealth(this.getHealth() + capturedLoot.getHealth());
            System.out.printf("+%d Health%nHealth is now %d%n", capturedLoot.getHealth(), this.getHealth());
        }
        if (capturedLoot.attack > 0) {
            this.setAttack(this.getAttack() + capturedLoot.getAttack());
            System.out.printf("+%d Attack%nAttack is now %d%n", capturedLoot.getAttack(), this.getAttack());
        }
        if (capturedLoot.defence > 0) {
            this.setDefence(this.getDefence() + capturedLoot.getDefence());
            System.out.printf("+%d Defence%nDefence is now %d%n", capturedLoot.getDefence(), this.getDefence());
        }
        lootList.removeIf(l -> l.coordinates.equals(this.getCoordinates()));
        return lootList;
    }

}
