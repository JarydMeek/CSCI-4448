import java.util.*;

public class Project3Part2Driver {
    public static void main(String args[]) {
        // hello
        ItemGenerator ig = new ItemGenerator();
        Clerk shaggy = new Clerk("Shaggy", .2, new Haphazard());
        Clerk velma = new Clerk("Velma", .05, new Electronic());
        //PART 3 NUMBER 3. New CLerk
        Clerk daphne = new Clerk("Daphne", .01, new Manual());
        Clerk[] theLads = {shaggy, velma, daphne};

        Tracker theBoss = new Tracker();
        for (int i = 0; i < theLads.length; i++) {
            theLads[i].addObserver(theBoss);
        }

        Store myStore = new Store(theLads);
        myStore.addObserver(theBoss);

        for (int i = 0; i < 30; i++) {
            myStore.runDay();
            theBoss.printSummary(i);
        }

        myStore.printSummary();
        
        // test to make sure clerk breaking inventory works correctly
        /*
        System.out.println("====== before runDay() ======");
        for (Item i : myStore.getInventory()) {
            System.out.println(i.getType() + ", $" + i.getPrice() + ", Condition: " + i.getCondition());
        }
        System.out.println("Length = " + myStore.getInventory().size());
        myStore.runDay();
        System.out.println("====== after runDay() ======");
        for (Item i : myStore.getInventory()) {
            System.out.println(i.getType() + ", $" + i.getPrice() + ", Condition: " + i.getCondition());
        } */

        // Item myItem = new PracticeAmp();

        // basic 30 day simulation
        /* 
        System.out.println("Stuff at day 1: " + myStore.getInventory().size());
        for (int i = 0; i < 30; i++) {
            myStore.runDay();
        }
        System.out.println("Stuff after day 30: " + myStore.getInventory().size()); */
    }
}