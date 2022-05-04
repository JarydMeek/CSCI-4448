import java.util.*;

public class Customer {
    int id;
    boolean buying;
    ItemType itemOfInterest;

    public Customer(int num, boolean buyOrSell) {
        id = num;
        buying = buyOrSell;
        itemOfInterest = ItemType.getRandomType();
    }

    public int getId() { return id; }
    public boolean isBuying() { return buying; }
    public ItemType getInterest() { return itemOfInterest; }
}