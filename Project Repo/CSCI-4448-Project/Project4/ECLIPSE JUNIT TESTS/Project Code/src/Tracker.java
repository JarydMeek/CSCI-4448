import java.util.*;

//now a singleton
class Tracker implements Observer {
    private int clerkidx;
    String currClerk;

    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Integer> soldCount = new ArrayList<Integer>();
    private ArrayList<Integer> purchaseCount = new ArrayList<Integer>();
    private ArrayList<Integer> damagedCount = new ArrayList<Integer>();

    private Tracker() {};
    public static Tracker tracker = new Tracker(); //eager instantiation
    public static Tracker getTracker() {
        return tracker;
    }

    public void eventClerkArrived(String clerk, String store) {
        if (!names.contains(clerk)) {
            names.add(clerk);
            soldCount.add(0);
            purchaseCount.add(0);
            damagedCount.add(0);
        }
        currClerk = clerk;
        clerkidx = names.indexOf(clerk);
    }
    public void eventClerkChanged(String clerk, String store) {
        eventClerkArrived(clerk, store);
    }
    public void eventItemsAdded(ArrayList<Item> items) { }
    public void eventMoneyInRegister(double money) { }
    public void eventInventoryCheck(ArrayList<Item> items) { }
    public void eventItemsOrdered(ArrayList<Item> items) { }

    public void eventItemsSold(int sold) {
        soldCount.set(clerkidx, soldCount.get(clerkidx) + sold);
    }
    public void eventItemsPurchased(int bought) {
        purchaseCount.set(clerkidx, purchaseCount.get(clerkidx) + bought);
    }
    public void eventItemsDamaged(boolean duringTuning, ArrayList<Item> damaged) {
        damagedCount.set(clerkidx, damagedCount.get(clerkidx) + damaged.size());
    }
    public void eventClerkLeft(String clerk) { }

    public void printSummary(int day) {
        System.out.println("Clerk   | Items Sold | Items Purchased | Items Damaged");
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i) + " | " + soldCount.get(i) + " | " 
                + purchaseCount.get(i) + " | " + damagedCount.get(i));
        }
    }
}