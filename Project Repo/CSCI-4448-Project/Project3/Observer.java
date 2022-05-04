import java.util.*;

interface Observer {

    void eventClerkArrived(String clerk);
    void eventItemsAdded(ArrayList<Item> items);
    void eventMoneyInRegister(double money);
    void eventInventoryCheck(ArrayList<Item> items);
    void eventItemsOrdered(ArrayList<Item> items);
    void eventItemsSold(int sold);
    void eventItemsPurchased(int bought);
    void eventItemsDamaged(boolean duringTuning, ArrayList<Item> damaged);
    void eventClerkLeft(String clerk);
}