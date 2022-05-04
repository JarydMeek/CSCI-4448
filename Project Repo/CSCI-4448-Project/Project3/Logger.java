import java.util.*;
import java.io.*;

class Logger implements Observer {
    int currDay;
    FileWriter fw;
    PrintWriter pw;
    String c = "";
    //file pointer?

    public Logger(int day) {
        currDay = day;

        try {
            fw = new FileWriter("Logger-" + currDay + ".txt");
            pw = new PrintWriter(fw);
        } catch (IOException err) {
            //uh
        }
    }

    public void eventClerkArrived(String clerk) {
        pw.printf("%s arrived at the store.\n", clerk);
        c = clerk;
    }
    public void eventItemsAdded(ArrayList<Item> items) {
        pw.printf("%s - %d items were added to the inventory.\n", c, items.size());
    }
    public void eventMoneyInRegister(double money) {
        pw.printf("%s - There is $ %.2f in the register.\n", c, money);
    }
    public void eventInventoryCheck(ArrayList<Item> items) {
        pw.printf("%s - There are %d items in the inventory.\n", c, items.size());

        int totalPrice = 0;
        for (int i = 0; i < items.size(); i++) {
            totalPrice += items.get(i).getPurchasePrice();
        }
        pw.printf("%s - The inventory is worth %d.\n", c, totalPrice);
    }
    public void eventItemsOrdered(ArrayList<Item> items) {
        pw.printf("%s - %d items were ordered.\n", c, items.size());
    }
    public void eventItemsSold(int sold) {
        pw.printf("%s - %d items were sold to customers.\n", c, sold);
    }
    public void eventItemsPurchased(int bought) {
        pw.printf("%s - %d items were bought from customers.\n", c, bought);
    }
    public void eventItemsDamaged(boolean duringTuning, ArrayList<Item> damaged) {
        if (duringTuning) {
            pw.printf("%s - %d items were damaged during tuning.\n", c, damaged.size());
        } else {
            pw.printf("%s - %d items were damaged during cleaning.\n", c, damaged.size());
        }
    }

    public void eventClerkLeft(String clerk) {
        pw.printf("%s has left the store.\n", clerk);

        try {
            pw.close();
            fw.close();
        } catch (IOException err) {

        }
    }
}