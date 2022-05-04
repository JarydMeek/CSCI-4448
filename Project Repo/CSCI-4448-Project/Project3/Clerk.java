import java.util.*;


/* ABSTRACTION */
//The following functions contained in the Clerk class are an example of abstraction as they offer an interface to manipulate the object, but the exact implementation is hidden to the user.

public class Clerk {
    private String name;
    private int daysInARow;
    private double damageChance;
    private Strategy tuningStyle;
    public ArrayList<Observer> observers = new ArrayList<Observer>();

    public Clerk(String n, double d, Strategy s) {
        name = n;
        daysInARow = 0;
        damageChance = d;
        tuningStyle = s;
    }

    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    public void removeObserver(int idx) {
        observers.remove(idx);
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

        //inform the observers
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).eventClerkArrived(getName());
            observers.get(i).eventItemsAdded(unpacked);
        }

        store.setOrders(orders); // [shrug]
        return unpacked;
    }

    public double CheckRegister(double register) {
        System.out.println("There is $" + register + " in the cash register");
        if (register < 75) {
            for (int i = 0; i < observers.size(); i++) {
                observers.get(i).eventMoneyInRegister(register + 1000);
            }
            return GoToBank();
        }

        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).eventMoneyInRegister(register);
        }
        return 0;
    }

    public double GoToBank() {
        System.out.println(getName() + " went to the bank and withdrew $1000");
        return 1000;
    }

    public ArrayList<Item> DoInventory(ArrayList<Item> inv, ArrayList<Item> orders, int day) {
        boolean[] stockList = new boolean[23]; // true = theres at least 1 in inv
        int idx = 0;
        // first determine which items need to be stocked
        for (ItemType t : ItemType.values()) {
            for (Item i : inv) {
                if (t ==  i.getType()) { stockList[idx] = true; }
            }
            idx++;
        }
        
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).eventInventoryCheck(inv);
        }

        for (int i = 0; i < stockList.length; i++) {
            if (!stockList[i]) {
                System.out.println("out of stock of " + ItemType.values()[i]);
                //Handles First Change for PT3
                //No more reordering clothing.
                if (ItemType.values()[i] != ItemType.HAT && ItemType.values()[i] != ItemType.SHIRT && ItemType.values()[i] != ItemType.BANDANA) {
                    orders.addAll(PlaceAnOrder(ItemType.values()[i], day));
                }
            } 
        }

        return orders;
    }

    public ArrayList<Item> TuneInventory(ArrayList<Item> inv) {
        boolean initialstate;
        boolean poststate;
        
        ArrayList<Item> tunedInv = new ArrayList<Item>();
        ArrayList<Item> damaged = new ArrayList<Item>();
        ArrayList<Item> toRemove = new ArrayList<Item>();

        for (Item i : inv) {
            ItemType t = i.getType();
            // Players
            if (t == ItemType.CDPLAYER || t == ItemType.RECORDPLAYER || t == ItemType.MP3) {
                initialstate = ((Player)i).getState();
                poststate = tuningStyle.tunePlayer((Player)i);

                if (initialstate && !poststate && Math.random() < 0.1) {
                    damaged.add(i);

                    i.condition--;
                    if (i.condition < 0) {
                        System.out.println("Item " + i.getName() + " broken, removed");
                        toRemove.add(i);
                    } else {
                        i.setListPrice(i.getListPrice() * .8);
                        System.out.println("Item " + i.getName() + " damaged, price reduced to " + i.getListPrice());
                    }
                }
                Player p = (Player)i;
                p.setState(poststate);
                i = p;
            }
            // Stringed
            if (t == ItemType.GUITAR || t == ItemType.BASS || t == ItemType.MANDOLIN) {
                initialstate = ((Stringed)i).getState();
                poststate = tuningStyle.tuneStringed((Stringed)i);

                if (initialstate && !poststate) {
                    damaged.add(i);

                    i.condition--;
                    if (i.condition < 0) {
                        System.out.println("Item " + i.getName() + " broken, removed");
                        toRemove.add(i);
                    } else {
                        i.setListPrice(i.getListPrice() * .8);
                        System.out.println("Item " + i.getName() + " damaged, price reduced to " + i.getListPrice());
                    }
                }
                Stringed p = (Stringed)i;
                p.setState(poststate);
                i = p;
            }
            // Wind
            if (t == ItemType.FLUTE || t == ItemType.HARMONICA || t == ItemType.SAXOPHONE) {
                initialstate = ((Wind)i).getState();
                poststate = tuningStyle.tuneWind((Wind)i);

                if (initialstate && !poststate) {
                    damaged.add(i);

                    i.condition--;
                    if (i.condition < 0) {
                        System.out.println("Item " + i.getName() + " broken, removed");
                        toRemove.add(i);
                    } else {
                        i.setListPrice(i.getListPrice() * .8);
                        System.out.println("Item " + i.getName() + " damaged, price reduced to " + i.getListPrice());
                    }
                }
                Wind p = (Wind)i;
                p.setState(poststate);
                i = p;
            }
            tunedInv.add(i);
        }

        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).eventItemsDamaged(true, damaged);
        }

        tunedInv.removeAll(toRemove);
        return tunedInv;
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

        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).eventItemsOrdered(neworders);
        }
        return neworders;
    }

    ArrayList<Item> CleanTheStore(ArrayList<Item> inventory) {
        System.out.println(name + " is cleaning the store");

        ArrayList<Item> damaged = new ArrayList<Item>();
        ArrayList<Item> toRemove = new ArrayList<Item>();

        for (int x = 0; x < inventory.size(); x++) {
            if (Math.random() < damageChance || inventory.get(x).condition < 0) {
                //System.out.println(inventory.get(x).condition);

                //System.out.println(inventory.get(x).getName());

                inventory.get(x).condition--;
                //System.out.println(inventory.get(x).getName() + "-> " + inventory.get(x).condition);
                if (inventory.get(x).condition < 0) {
                    System.out.println("Item " + inventory.get(x).getName() + " broken, removed");
                    toRemove.add(inventory.get(x));
                } else {
                    inventory.get(x).setListPrice(inventory.get(x).getListPrice() * .8);
                    System.out.println("Item " + inventory.get(x).getName() + " damaged, price reduced to " + inventory.get(x).getListPrice());
                }
                damaged.add(inventory.get(x));
            }
            
        }
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).eventItemsDamaged(false, damaged);
        }
        inventory.removeAll(toRemove);
        return inventory;
    }

    //Announce that clerk is leaving.
    public void LeaveTheStore() {
        System.out.println(name + " is leaving the store");
        daysInARow++;
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).eventClerkLeft(getName());
        }
    }

}