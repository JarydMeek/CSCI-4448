import java.util.*;

public class Project4Part2Driver {
    
    public static void main(String args[]) {
        // hello
        Clerk shaggy = new Clerk("Shaggy", .2, new Haphazard());
        Clerk velma = new Clerk("Velma", .05, new Electronic());
        Clerk fred = new Clerk("Fred", .1, new Manual());
        Clerk daphne = new Clerk("Daphne", .01, new Manual());
        Clerk scooby = new Clerk("Scooby", .2, new Electronic());
        Clerk scrappy = new Clerk("Scrappy", .5, new Haphazard());
        Clerk[] theLads = {shaggy, velma, fred, daphne, scooby, scrappy};

        Tracker theBoss = Tracker.getTracker();
        for (int i = 0; i < theLads.length; i++) {
            theLads[i].addObserver(theBoss);
        }

        Store northside = new Store(theLads, "NorthSide");
        northside.addObserver(theBoss);
        Store southside = new Store(theLads, "SouthSide");
        southside.addObserver(theBoss);

        ArrayList<Store> stores = new ArrayList<Store>();
        stores.add(northside);
        stores.add(southside);


        CLI cli = new CLI();
        int days = 7;
        do {
            days = (int)(Math.random() * 20) + 10;
        } while (days % 7 == 0); //don't end on a Sunday

        for (int i = 0; i < days; i++) {
            for (Store store : stores) {
                store.runDay(i == days-1);
            }

            if ( i == days-1) {
                cli.showMenu(stores);
                Logger.getLogger(i).closeLog();
            }
                    
            theBoss.printSummary(i);
        }
         
        for (Store store : stores) {
            store.printSummary();
        }
    }
}