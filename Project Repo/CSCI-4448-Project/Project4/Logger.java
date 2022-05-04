import java.util.*;
import java.io.*;

//now a singleton
class Logger implements Observer {
    int currDay;
    FileWriter fw;
    PrintWriter pw;
    String c = "";
    private static Logger log;

    private Logger() {
        currDay = -1;
    }

    public void setDay(int day) {
        //need a new log file
        if (day != currDay) {
            if (currDay > -1) {
                try {
                    fw.close();
                    pw.close();
                } catch (IOException err) {
                    //nothing
                }
            }
            
            try {
                fw = new FileWriter(new File("logs", "Logger-" + day + ".txt"));
                pw = new PrintWriter(fw);
            } catch (IOException err) {
                //hmm
            }
        }

        log.currDay = day;
    }

    public static Logger getLogger(int day) {
        if (log == null) { log = new Logger(); } //lazy instantiation

        log.setDay(day);
        return log; 
    }

    public void eventClerkArrived(String clerk, String store) {
        pw.printf("========================================\n");
        pw.printf("%s arrived at the %s store.\n", clerk, store);
        c = clerk;
    }
    public void eventClerkChanged(String clerk, String store) {
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
    }

    //dumb
    public void closeLog() {
        try {
            fw.close();
            pw.close();
        } catch (IOException err) {
            //nothing
        }
    }
}