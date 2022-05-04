import java.util.*;


/* ABSTRACTION */
//The following functions contained in the Clerk class are an example of abstraction as they offer an interface to manipulate the object, but the exact implementation is hidden to the user.

public class Clerk {
    private String name;
    private int daysInARow;
    private double damageChance;

    public Clerk(String n, double d) {
        name = n;
        daysInARow = 0;
        damageChance = d;
    }

    public String getName() {
        return name;
    }
    public int getDaysInARow() {
        return daysInARow;
    }

    public void setDaysInARow(int n) {
        daysInARow = n;
    }

    public double getDamageChance() {
        return damageChance;
    }

    // Unpack everything in orders that has arrived today, return list of items that have been unpacked
    public ArrayList<Item> ArriveAtStore(Store store, int day, ArrayList<Item> orders) {
        System.out.println(getName() + " has arrived at store on day " + day);
        ArrayList<Item> unpacked = new ArrayList<Item>();

        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getDayArrived() == day) {
                System.out.println("Unpacked " + orders.get(i).getName());
                unpacked.add(orders.get(i));
                orders.remove(i);
            }
        }

        if (unpacked.size() == 0) {
            System.out.println("Nothing to unpack today");
        }

        store.setOrders(orders); // [shrug]
        // used to tell store what items to add to inventory
        return unpacked;
    }

    // will be used by store at start of day to determine if money has to be withdrawn from the bank
    public double CheckRegister(double register) {
        System.out.println("There is $" + register + " in the cash register");
        if (register < 75) {
            return GoToBank();
        }
        return 0;
    }

    public double GoToBank() {
        System.out.println(getName() + " went to the bank and withdrew $1000");
        return 1000;
    }

    public ArrayList<Item> DoInventory(ArrayList<Item> inv, ArrayList<Item> orders, int day) {
        boolean[] stockList = new boolean[17]; // true = theres at least 1 in inv
        int idx = 0;
        // first determine which items need to be stocked
        for (ItemType t : ItemType.values()) {
            for (Item i : inv) {
                if (t ==  i.getType()) { stockList[idx] = true; }
            }
            idx++;
        }
        


        for (int i = 0; i < stockList.length; i++) {
            if (!stockList[i]) {
                System.out.println("out of stock of " + ItemType.values()[i]);
                orders.addAll(PlaceAnOrder(ItemType.values()[i], day));
            } 
        }

        // remove old orders
        ArrayList<Item> toRemove = new ArrayList<Item>();
        for (Item order : orders) {
            if (order.dayArrived <= day) {
                toRemove.add(order);
            }
        }
        orders.removeAll(toRemove);
        return orders;
    }

    //pass in current cash balance to prevent going into debt?
    public ArrayList<Item> PlaceAnOrder(ItemType type, int day) {
        System.out.println(name + " ordered 3 " + type);
        ArrayList<Item> neworders = new ArrayList<Item>();

        for (int i = 0; i < 3; i++) {
            Item it = ItemGenerator.generateSpecificItem(type);
            it.setDayArrived(day + ((int) (Math.random() * 3)) + 1); //set the day arrived to a future date
            neworders.add(it);
        }

        return neworders;
    }

    ArrayList<Item> CleanTheStore(ArrayList<Item> inventory) {
        System.out.println(name + " is cleaning the store");

        ArrayList<Item> toRemove = new ArrayList<Item>();

        for (int x = 0; x < inventory.size(); x++) {
            if (Math.random() < damageChance || inventory.get(x).condition < 0) { // item is damaged
                //System.out.println(inventory.get(x).condition);

                //System.out.println(inventory.get(x).getName());

                inventory.get(x).condition--;
                //System.out.println(inventory.get(x).getName() + "-> " + inventory.get(x).condition);
                if (inventory.get(x).condition < 0) { // item is damaged to the point of broken
                    System.out.println("Item " + inventory.get(x).getName() + " broken, removed");
                    toRemove.add(inventory.get(x));
                } else {
                    inventory.get(x).setListPrice(inventory.get(x).getListPrice() * .8);
                    System.out.println("Item " + inventory.get(x).getName() + " damaged, price reduced to " + inventory.get(x).getListPrice());
                }

            }
            
        }
        inventory.removeAll(toRemove);
        return inventory;
    }

    //Announce that clerk is leaving.
    public void LeaveTheStore() {
        System.out.println(name + " is leaving the store");
        daysInARow++;
    }

}