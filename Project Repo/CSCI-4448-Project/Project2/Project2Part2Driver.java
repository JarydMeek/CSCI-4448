import java.util.*;

public class Project2Part2Driver {
    public static void main(String args[]) {
        // hello
        ItemGenerator ig = new ItemGenerator();
        Clerk shaggy = new Clerk("Shaggy", .2);
        Clerk velma = new Clerk("Velma", .05);
        Clerk[] theLads = {shaggy, velma};
        Store myStore = new Store(theLads); // pass clerks to store

        for (int i = 0; i < 30; i++) { // 30 day simulation
            myStore.runDay();
        }

        myStore.printSummary();
    }
}