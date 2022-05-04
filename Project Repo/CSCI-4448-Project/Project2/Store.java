import java.util.*;


/* COHESION */
//The following class (and most of this project) is an example of cohesion because we are using many classes with specific purposes.

public class Store {
    private ArrayList<Item> inventory;
    private ArrayList<Item> soldItems;
    private ArrayList<Item> boughtItems; //for debug
    private ArrayList<Item> orders;
    private ArrayList<String> transactions;
    private double cashRegister;
    private double cashWithdrawn;
    private Clerk[] employees;
    private int day, onDuty;

    public static String[] conditions = {"poor", "fair", "good", "very good", "excellent"}; // for converting between int condition and string

    public ArrayList<Item> getInventory() { return inventory; }
    public ArrayList<Item> getSoldItems() { return soldItems; }
    public ArrayList<String> getTransactions() { return transactions; }
    public void setOrders(ArrayList<Item> n) { orders = n; }
    public void setInventory(ArrayList<Item> n) { inventory = n; }
    public double getCashRegister() { return cashRegister; }
    public void setCashRegister(double n) { cashRegister = n; }
    public double getCashWithdrawn() { return cashWithdrawn; }
    public void setCashWithdrawn(double n) { cashWithdrawn = n; }
    public int getDay() { return day; }
    public void setDay(int n) { day = n; }
    public int getOnDuty() { return onDuty; }

    public Store(Clerk[] arr) {
        inventory = new ArrayList<Item>();
        soldItems = new ArrayList<Item>();
        boughtItems = new ArrayList<Item>();
        orders = new ArrayList<Item>();
        transactions = new ArrayList<String>();
        generateStartInventory();
        cashRegister = 0;
        cashWithdrawn = 0;
        employees = arr;
        onDuty = 0;
        day = 1;
    }

    public void printSummary() {
        System.out.println("\n\n\n====================");
        System.out.println("STORE SUMMARY: DAY " + getDay());
        System.out.println("====================");

        /*********** inventory ***********/
        double totalPrice = 0;
        ArrayList<Item> inv = getInventory();
        System.out.println("--Inventory--");
        for (int i = 0; i < inv.size(); i++) {
            System.out.println(inv.get(i).getName() + " - $" + inv.get(i).getPurchasePrice());
            totalPrice += inv.get(i).getPurchasePrice();
        }
        System.out.println("Total Price of All Items in Inventory: $" + totalPrice);

        /*********** items sold ***********/
        totalPrice = 0;
        ArrayList<Item> sold = getSoldItems();
        System.out.println("--Items Sold--");
        for (int i = 0; i < sold.size(); i++) {
            System.out.println(sold.get(i).getName() + " - $" + sold.get(i).getSalePrice());
            totalPrice += sold.get(i).getSalePrice();
        }
        System.out.println("Total Price of All Items Sold: $" + totalPrice);

        /*********** money status ***********/
        System.out.println("--Money--");
        System.out.println("Current cash register: $" + getCashRegister());
        System.out.println("Total withdrawn from bank: $" + getCashWithdrawn());

        System.out.println("====================");
    }

    public void runDay() {
        System.out.println("\n\n\n====== Day " + getDay() + "======");
        if (getDay() % 7 == 0) {
            System.out.println("Store is not open on Sundays");
            day++;
        } else {
            chooseClerk();
            // gets all items that have arrived today (day arrived == today)
            inventory.addAll(employees[onDuty].ArriveAtStore(this, day, orders));
            // either 0 or 1000 depending on the current state of cashRegister
            double toAdd = employees[onDuty].CheckRegister(cashRegister);
            cashRegister += toAdd;
            cashWithdrawn += toAdd; // for the record
            // get list of orders based on what's not in inventory
            ArrayList<Item> newOrders = employees[onDuty].DoInventory(inventory, orders, getDay());
            
            for (int i = 0; i < newOrders.size(); i++) { // withdraw money to make the orders
                setCashRegister(getCashRegister() - newOrders.get(i).getPurchasePrice());
            }

            orders.addAll(newOrders);

            ArrayList<Customer> customers = generateCustomers(); // list of customers to deal with today
            
            for (Customer c: customers) {
                if (c.isBuying()) {
                    handleBuyer(c);
                } else {
                    handleSeller(c);
                }
            }
            this.setInventory(employees[onDuty].CleanTheStore(inventory)); // update inv based on what they damaged/broke
            
            employees[onDuty].LeaveTheStore();
            //cashRegister -= (int)(Math.random() * cashRegister);
            System.out.println("closing register: $" + cashRegister); 
            day++;
        }
    }

    // Rotates through employees roster to make sure nobody works more than 3 in a row
    // Employee pattern: 000 111 000 111 000 ... 
    // 3 employees: 000 111 222 000 ...
    private void chooseClerk() { 
       if (employees[onDuty].getDaysInARow() == 3) {
           employees[onDuty].setDaysInARow(0);
           onDuty++;
           if (onDuty >= employees.length) {
            //    System.out.println("Resetting list");
               onDuty = 0;
           }
       }
    }

    private ArrayList<Customer> generateCustomers() {
        // item of interest generated within Customer constructor
        ArrayList<Customer> lst = new ArrayList<Customer>();
        int id = 0;
        for (int i = 0; i < 4 + (int)(Math.random()*6); i++) { // 4-10 buyers
            lst.add(new Customer(id, true)); 
            id++;
        }
        //System.out.println(id);
        for (int i = 0; i < 1 + (int)(Math.random()*3); i++) { // 1-4 sellers
            lst.add(new Customer(id, false));
            id++;
        }
        //System.out.println(id);
        return lst;
    }

    private void generateStartInventory() { // add 3 of each item to inventory on day 1
        System.out.println("Generating start inv");
        for (int i = 0; i < 3; i++) {
            inventory.add(new PaperScore());
            inventory.add(new CD());
            inventory.add(new Vinyl());
            inventory.add(new CDPlayer());
            inventory.add(new RecordPlayer());
            inventory.add(new MP3());
            inventory.add(new Guitar());
            inventory.add(new Bass());
            inventory.add(new Mandolin());
            inventory.add(new Flute());
            inventory.add(new Harmonica());
            inventory.add(new Hat());
            inventory.add(new Shirt());
            inventory.add(new Bandana());
            inventory.add(new PracticeAmp());
            inventory.add(new Cable());
            inventory.add(new InstrumentString());
        }
    }

    
    private void handleBuyer(Customer c) {
        ArrayList<Item> inv = getInventory();
        ItemType lookingFor = c.getInterest();
        Item offering = null;
        int idx = -1; 

        for (int i = 0; i < inv.size(); i++) { // try to find the item they want in inventory
            if (inv.get(i).getType() == lookingFor) {
                idx = i;
                offering = inv.get(i);
                break;
            }
        }

         //no valid items
        if (offering == null) {
            System.out.println("Customer " + c.getId() + " wanted a " + lookingFor + ", but there were none in inventory, so they left.");
            return;
        
        } else {
            //initial offer
            if (Math.random() > .5) {
                String receipt = employees[onDuty].getName() + " sold a " + conditions[offering.getCondition()] + " " + offering.getName() + 
                    " to Customer " + c.getId() + " at " + offering.getListPrice();
                // append records
                getTransactions().add(receipt);
                System.out.println(receipt);
                offering.setDaySold(getDay());
                offering.setSalePrice(offering.getListPrice());
                getSoldItems().add(offering);
                getInventory().remove(idx);
                // update register
                setCashRegister(getCashRegister() + offering.getSalePrice());

            //discount
            } else if (Math.random() > .25) {
                String receipt = employees[onDuty].getName() + " sold a " + conditions[offering.getCondition()] + " " + offering.getName() + 
                    " to Customer " + c.getId() + " at " + (offering.getPurchasePrice() * .9) + " after a 10% discount";
                // append records
                getTransactions().add(receipt);
                System.out.println(receipt);
                offering.setDaySold(getDay());
                offering.setSalePrice(offering.getListPrice() * .9);
                getSoldItems().add(offering);
                getInventory().remove(idx);
                // update register
                setCashRegister(getCashRegister() + offering.getSalePrice());

            //rejected
            } else {
                System.out.println(employees[onDuty].getName() + " did not sell a " + offering.getName() + 
                " to Customer " + c.getId());
                
            }
        }
    }

    private void handleSeller(Customer c) {
        Item customerItem = ItemGenerator.generateSpecificItem(c.getInterest()); //appraise
        customerItem.setDayArrived(getDay());

        //couldn't afford to buy
        if (getCashRegister() < customerItem.getPurchasePrice()) {
            System.out.println("Couldn't afford to buy " + customerItem.getName() + " from Customer " + c.getId());
            return;
        
        } else {
            //initial offer
            if (Math.random() > .5) {
                String receipt = employees[onDuty].getName() + " bought a " + conditions[customerItem.getCondition()] + " " + customerItem.getName() + 
                    " from Customer " + c.getId() + " at " + customerItem.getPurchasePrice();
                // append records
                getTransactions().add(receipt);
                System.out.println(receipt);
                boughtItems.add(customerItem);
                // update register
                setCashRegister(getCashRegister() - customerItem.getPurchasePrice());

            //discount
            } else if (Math.random() > .25) {
                String receipt = employees[onDuty].getName() + " bought a " + conditions[customerItem.getCondition()] + " " + customerItem.getName() + 
                    " from Customer " + c.getId() + " at " + (customerItem.getPurchasePrice() * 1.1) + " after a 10% increase";
                // append records
                getTransactions().add(receipt);
                System.out.println(receipt);
                customerItem.setPurchasePrice(customerItem.getPurchasePrice() * 1.1);
                boughtItems.add(customerItem);
                // update register
                setCashRegister(getCashRegister() - customerItem.getPurchasePrice());

            //rejected
            } else {
                System.out.println(employees[onDuty].getName() + " did not buy a " + conditions[customerItem.getCondition()] + " " + customerItem.getName() + 
                " from Customer " + c.getId());
                
            }
        }
    }
}