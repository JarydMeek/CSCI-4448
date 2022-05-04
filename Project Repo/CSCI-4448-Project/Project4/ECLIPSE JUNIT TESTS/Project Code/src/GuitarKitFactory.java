import java.util.*;

//abstract factory
public abstract class GuitarKitFactory {
    ArrayList<Component> bridges;
    ArrayList<Component> knobs;
    ArrayList<Component> covers;
    ArrayList<Component> necks;
    ArrayList<Component> pickguards;
    ArrayList<Component> pickups;

    CustomGuitarKit assembleGuitar(int bridgeN, int knobN, int coverN, int neckN, int pickguardN, int pickupN) {
        ArrayList<Component> pieces = new ArrayList<Component>();
        pieces.add(bridges.get(bridgeN));
        pieces.add(knobs.get(knobN));
        pieces.add(covers.get(coverN));
        pieces.add(necks.get(neckN));
        pieces.add(pickguards.get(pickguardN));
        pieces.add(pickups.get(pickupN));

        return new CustomGuitarKit(pieces);
    }    
}

class GuitarKitNorth extends GuitarKitFactory {
    GuitarKitNorth() {
        bridges = new ArrayList<Component>();
        knobs = new ArrayList<Component>();
        covers = new ArrayList<Component>();
        necks = new ArrayList<Component>();
        pickguards = new ArrayList<Component>();
        pickups = new ArrayList<Component>();

        bridges.add(new Component("Bridge A", 10));
        bridges.add(new Component("Bridge B", 12));
        bridges.add(new Component("Bridge C", 15));

        knobs.add(new Component("Knob A", 10));
        knobs.add(new Component("Knob B", 12));
        knobs.add(new Component("Knob C", 15));

        covers.add(new Component("Cover A", 10));
        covers.add(new Component("Cover B", 12));
        covers.add(new Component("Cover C", 15));

        necks.add(new Component("Neck A", 10));
        necks.add(new Component("Neck B", 12));
        necks.add(new Component("Neck C", 15));

        pickguards.add(new Component("Pickguard A", 10));
        pickguards.add(new Component("Pickguard B", 12));
        pickguards.add(new Component("Pickguard C", 15));

        pickups.add(new Component("Pickup A", 10));
        pickups.add(new Component("Pickup B", 12));
        pickups.add(new Component("Pickup C", 15));
    }
}

class GuitarKitSouth extends GuitarKitFactory {
    GuitarKitSouth() {
        bridges = new ArrayList<Component>();
        knobs = new ArrayList<Component>();
        covers = new ArrayList<Component>();
        necks = new ArrayList<Component>();
        pickguards = new ArrayList<Component>();
        pickups = new ArrayList<Component>();

        bridges.add(new Component("Bridge A", 10));
        bridges.add(new Component("Bridge B", 12));
        bridges.add(new Component("Bridge D", 20));

        knobs.add(new Component("Knob A", 10));
        knobs.add(new Component("Knob B", 12));
        knobs.add(new Component("Knob D", 20));

        covers.add(new Component("Cover A", 10));
        covers.add(new Component("Cover B", 12));
        covers.add(new Component("Cover D", 20));

        necks.add(new Component("Neck A", 10));
        necks.add(new Component("Neck B", 12));
        necks.add(new Component("Neck D", 20));

        pickguards.add(new Component("Pickguard A", 10));
        pickguards.add(new Component("Pickguard B", 12));
        pickguards.add(new Component("Pickguard D", 20));

        pickups.add(new Component("Pickup A", 10));
        pickups.add(new Component("Pickup B", 12));
        pickups.add(new Component("Pickup D", 20));
    }
}

class Component {
    String name;
    double price;

    Component(String n, double p) {
        name = n;
        price = p;
    }

    double getPrice() { return price; }
    String getName() { return name; }
}

class CustomGuitarKit extends Item {
    CustomGuitarKit(ArrayList<Component> pieces) {
        name = "Custom Guitar - ";
        purchasePrice = 0;

        for (int i = 0; i < pieces.size(); i++) {
            name += pieces.get(i).getName() + " ";
            purchasePrice += pieces.get(i).getPrice();
        }

        listPrice = purchasePrice;
        salePrice = purchasePrice;
        newOrUsed = true;
        dayArrived = 0;
        condition = 4;
        daySold = 0;
    }

    public ItemType getType() {
        return ItemType.GUITAR; //change this?
    }
}