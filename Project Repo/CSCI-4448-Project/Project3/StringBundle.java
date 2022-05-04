import java.util.*;
/*
    This is an example of a Decorator Pattern. The StringBundle adds additional functionality to the Item class 
    (spawn random Gig Bags, Practice Amps, Cables, and Strings), without having to change the actual Item class. 
    This is done due to the fact that the Bundle counts as an Item, but does not behave exactly the same as
    the regular Items in inventory do. The specific implementations, ElectricStringBundle and AnalogStringBundle, 
    are the ones actually used in code. 
*/

public abstract class StringBundle extends Item {
    private double pGigBag, pAmp, pCables, pStrings;
    private int numCables, numStrings;
    private ArrayList<Item> bundledItems; 

    public void setBundledItems(ArrayList<Item> arr) {
        bundledItems = arr;
    }
    public ArrayList<Item> getBundledItems() {
        return bundledItems;
    }

    public void setInitialPrices() {
        double n = 0;
        for (Item i : bundledItems) {
            n += i.getPurchasePrice();
        }
        setPurchasePrice(n);
        setListPrice(n*2);
    }

    public StringBundle(double a, double b, double c, double d, String n) {
        pGigBag = a;
        pAmp = b;
        pCables = c;
        pStrings = d;
        name = n;
        generateBundle();
        while (getBundledItems().isEmpty()) {
            generateBundle();
        }
    }

    // public ItemType getType() {
    //     return ItemType.STRINGBUNDLE;
    // }

    // public void increaseChances() {
    //     pGigBag += .1;
    //     pAmp += .1;
    //     pCables += .1;
    //     pStrings += .1;
    // }

    public void generateBundle() {
        // name = "Stringed Instrument Bundle";
        //System.out.println("GENERATING BUNDLE");
        String toAppend = " ( Contents: ";
        ArrayList<Item> inTheBag = new ArrayList<Item>();
        int numCables = (int)(Math.random()*2) + 1;
        int numStrings = (int)(Math.random()*3) + 1;
        if (Math.random() < pGigBag) {
            inTheBag.add(new GigBag());
            toAppend += "Gig Bag[1] ";
        }
        if (Math.random() < pAmp) {
            inTheBag.add(new PracticeAmp());
            toAppend += "Practice Amp[1] ";
        }
        if (Math.random() < pCables) {
            for (int i = 0; i < numCables; i++) {
                inTheBag.add(new Cable());
            }
            toAppend += "Cable[" + numCables + "] ";
        }
        if (Math.random() < pStrings) {
            for (int i = 0; i < numStrings; i++) {
                inTheBag.add(new InstrumentString());
            }
            toAppend += "String[" + numStrings + "] ";
        }
        toAppend += ")";
        if (!inTheBag.isEmpty()) { name += toAppend; }
        setBundledItems(inTheBag);
        setInitialPrices();
    }
}
/* 
Use a Decorator pattern to add these optional sales to the normal item sale 
methods for the following cases. When a Stringed Instrument is sold, there is 
a chance of selling accessories as well. If the Stringed Instrument is electric, 
there is a 20% chance of selling a single Gig Bag, a 25% chance of selling a 
single Practice Amp, a 30% chance of selling 1 or 2 Cables, and a 40% 
chance of selling 1 to 3 Strings. If the Stringed Instrument is not electric, 
each of these chances of an additional sale is reduced by 10%. These additional 
items should be tracked in Inventory as usual. Announce each additional item sold. */