import java.util.*;


/* COHESION */
//The following class (and most of this project) is an example of cohesion because we are using many classes with specific purposes.

public class Store {
    
    public ArrayList<Observer> observers = new ArrayList<Observer>();
    public ArrayList<Item> inventory;
    private ArrayList<Item> soldItems;
    public ArrayList<Item> boughtItems; //for debug
    private ArrayList<Item> orders;
    private ArrayList<String> transactions;
    private double cashRegister;
    private double cashWithdrawn;
    public Clerk[] employees;
    public int day, onDuty;
    int newPurchaseCount = 0;
    int newSoldCount = 0;
    String name;
    
    public String[] conditions = {"poor", "fair", "good", "very good", "excellent"};
    
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
    public Clerk[] getEmployees() { return employees; }
    
    public void addObserver(Observer obs) { observers.add(obs); }
    public void removeObserver(int idx) { observers.remove(idx); }
    
    public Store(Clerk[] arr, String name) {
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
        this.name = name;
    }
    
    //Generate a random number based on the mean of a poisson distribution.
    //Logic for this came from here https://stackoverflow.com/questions/9832919/generate-poisson-arrival-in-java
    private static int getPoissonRandom(double mean) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
    
    
    public void printSummary() {
        System.out.println("\n\n\n====================");
        System.out.println(name + " - STORE SUMMARY: DAY " + getDay());
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
    
    public void runDay( boolean user) {
        System.out.println("\n\n\n====== Day " + getDay() + "(" + name + ")" + "======");
        if (getDay() % 7 == 0) {
            System.out.println("Store is not open on Sundays");
            day++;
        } else {
            chooseClerk();
            Logger l = Logger.getLogger(getDay());
            this.addObserver(l);
            employees[onDuty].addObserver(l);
            
            inventory.addAll(employees[onDuty].ArriveAtStore(this, day, orders));
            double toAdd = employees[onDuty].CheckRegister(cashRegister);
            cashRegister += toAdd;
            cashWithdrawn += toAdd;
            ArrayList<Item> newOrders = employees[onDuty].DoInventory(inventory, orders, getDay());
            for (int i = orders.size(); i < newOrders.size(); i++) {
                setCashRegister(getCashRegister() - newOrders.get(i).getPurchasePrice());
            }
            
            ArrayList<Item> toRemove = new ArrayList<Item>();
            for (Item order : orders) {
                if (order.dayArrived <= day) {
                    toRemove.add(order);
                }
            }
            orders.removeAll(toRemove);
            
            // orders.addAll(newOrders);
            orders = newOrders;
            
            inventory = employees[onDuty].TuneInventory(inventory);
            
            newPurchaseCount = 0;
            newSoldCount = 0;
            
            if (!user) {
                
                ArrayList<Customer> customers = generateCustomers();
                
                
                for (Customer c: customers) {
                    if (c.isBuying()) {
                        handleBuyer(c);
                    } else {
                        handleSeller(c);
                    }
                }
                
                
                for (int i = 0; i < observers.size(); i++) {
                    observers.get(i).eventItemsPurchased(newSoldCount);
                    observers.get(i).eventItemsSold(newPurchaseCount);
                }
                
                this.setInventory(employees[onDuty].CleanTheStore(inventory));
                
                employees[onDuty].LeaveTheStore();
                employees[onDuty].removeObserver(employees[onDuty].observers.size() - 1);
                this.removeObserver(observers.size() - 1);
                //cashRegister -= (int)(Math.random() * cashRegister);
                System.out.println("closing register: $" + cashRegister); //TODO: fix the fractions of cents
                day++;
            } 
        }
    }
    
    // Rotates through employees roster to make sure nobody works more than 3 in a row
    // Employee pattern: 000 111 000 111 000 ... 
    // 3 employees: 000 111 222 000 ...
    private void chooseClerk() { 
        if (Math.random() < 0.1) {
            System.out.println(employees[onDuty].getName() + " is sick today.");
            onDuty++;
            if (onDuty >= employees.length) {
                onDuty = 0;
            }
        }
        if (employees[onDuty].getDaysInARow() == 3) {
            employees[onDuty].setDaysInARow(0);
            onDuty++;
            //PROJ 3 PART 4 - SICK EMPLOYEES
            //10 PERCENT CHANGE EMPLOYEE SICK, IF SO, MOVE ON
            //
            
            if (onDuty >= employees.length) {
                //    System.out.println("Resetting list");
                onDuty = 0;
            }
        }
    }
    
    private ArrayList<Customer> generateCustomers() {
        ArrayList<Customer> lst = new ArrayList<Customer>();
        int id = 0;
        
        //SECOND CHANGE FOR PT3
        //Poisson instead of random distibution
        for (int i = 0; i < getPoissonRandom(3); i++) { // 4-10 buyers
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
    
    private void generateStartInventory() {
        System.out.println("Generating start inv");
        for (int i = 0; i < 3; i++) {
            inventory.add(new PaperScore());
            inventory.add(new CD());
            inventory.add(new Vinyl());
            inventory.add(new Cassette());
            inventory.add(new CDPlayer());
            inventory.add(new RecordPlayer());
            inventory.add(new MP3());
            inventory.add(new CassettePlayer());
            inventory.add(new Guitar());
            inventory.add(new Bass());
            inventory.add(new Mandolin());
            inventory.add(new Flute());
            inventory.add(new Harmonica());
            inventory.add(new Saxophone());
            inventory.add(new Hat());
            inventory.add(new Shirt());
            inventory.add(new Bandana());
            inventory.add(new PracticeAmp());
            inventory.add(new Cable());
            inventory.add(new InstrumentString());
            // inventory.add(new GigBag());
            inventory.add(new AnalogStringBundle());
            inventory.add(new ElectricStringBundle());
        }
    }
    
    /* these should be somewhere else maybe */
    private void handleBuyer(Customer c) {
        ArrayList<Item> inv = getInventory();
        ItemType lookingFor = c.getInterest();
        Item offering = null;
        int idx = -1; 
        double baseRate = 0;
        boolean saleOfStringed = false;
        boolean electricSale = false;
        
        for (int i = 0; i < inv.size(); i++) {
            if (inv.get(i).getType() == lookingFor) {
                idx = i;
                offering = inv.get(i);
                break;
            }
        }
        
        if (offering instanceof Player) {
            Player temp = (Player)offering;
            // changed from getEqualized()
            if (temp.getState()){
                baseRate = 0.1;
            }
        } else if (offering instanceof Stringed) {
            Stringed temp = (Stringed)offering;
            // changed from getTuned()
            if (temp.getState()){
                baseRate = 0.15;
            }
        } else if (offering instanceof Wind) {
            Wind temp = (Wind)offering;
            // changed from getAdjusted()
            if (temp.getState()){
                baseRate = 0.2;
            }
        }
        //no valid items
        if (offering == null) {
            System.out.println("Customer " + c.getId() + " wanted a " + lookingFor + ", but there were none in inventory, so they left.");
            return;
            
        } else {
            //initial offer
            if (Math.random()+baseRate > .5) {
                String receipt = employees[onDuty].getName() + " sold a " + conditions[offering.getCondition()] + " " + offering.getName() + 
                " to Customer " + c.getId() + " at " + offering.getListPrice();
                if (lookingFor == ItemType.GUITAR || lookingFor == ItemType.BASS || lookingFor == ItemType.MANDOLIN) {
                    saleOfStringed = true;
                    Stringed s = (Stringed)getInventory().get(idx);
                    if(s.getElectric()) {
                        electricSale = true;
                    }
                }
                getTransactions().add(receipt);
                System.out.println(receipt);
                offering.setDaySold(getDay());
                offering.setSalePrice(offering.getListPrice());
                getSoldItems().add(offering);
                getInventory().remove(idx);
                setCashRegister(getCashRegister() + offering.getSalePrice());
                newPurchaseCount++;
                
                //discount
            } else if (Math.random()+baseRate > .25) {
                String receipt = employees[onDuty].getName() + " sold a " + conditions[offering.getCondition()] + " " + offering.getName() + 
                " to Customer " + c.getId() + " at " + (offering.getPurchasePrice() * .9) + " after a 10% discount";
                if (lookingFor == ItemType.GUITAR || lookingFor == ItemType.BASS || lookingFor == ItemType.MANDOLIN) {
                    saleOfStringed = true;
                    Stringed s = (Stringed)getInventory().get(idx);
                    if(s.getElectric()) {
                        electricSale = true;
                    }
                }
                getTransactions().add(receipt);
                System.out.println(receipt);
                offering.setDaySold(getDay());
                offering.setSalePrice(offering.getListPrice() * .9);
                getSoldItems().add(offering);
                getInventory().remove(idx);
                setCashRegister(getCashRegister() + offering.getSalePrice());
                newPurchaseCount++;
                
                //rejected
            } else {
                System.out.println(employees[onDuty].getName() + " did not sell a " + offering.getName() + 
                " to Customer " + c.getId());
                
            }
            
            if (saleOfStringed) {
                tryToUpsell(c, electricSale);
            }
        }
    }
    
    private void tryToUpsell(Customer c, boolean wasElectric) {
        int idx = -1;
        ArrayList<Item> inv = getInventory();
        ItemType target = ItemType.ANALOGBUNDLE;
        if (wasElectric) { target = ItemType.ELECTRICBUNDLE; }
        for (int i = 0; i < inv.size(); i++) {
            if (inv.get(i).getType() == target) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            System.out.println(employees[onDuty].getName() + " could not sell a String Bundle, as it was out of stock.");
            return;
        }
        // try to sell the bundle to the Stringed buyer
        StringBundle s = (StringBundle)getInventory().get(idx);
        // assume old (proj 2) model of purchasing: 
        if (Math.random() > .5) {
            String receipt = employees[onDuty].getName() + " sold a " + conditions[s.getCondition()] + " " + s.getName() + 
            " to Customer " + c.getId() + " at " + (s.getPurchasePrice() * .9) + " after a 10% discount";
            getTransactions().add(receipt);
            System.out.println(receipt);
            s.setDaySold(getDay());
            s.setSalePrice(s.getListPrice());
            getSoldItems().add(s);
            getInventory().remove(idx);
            setCashRegister(getCashRegister() + s.getSalePrice());
            newPurchaseCount++;
        } else if (Math.random() > .5) {
            String receipt = employees[onDuty].getName() + " sold a " + conditions[s.getCondition()] + " " + s.getName() + 
            " to Customer " + c.getId() + " at " + (s.getPurchasePrice() * .9) + " after a 10% discount";
            getTransactions().add(receipt);
            System.out.println(receipt);
            s.setDaySold(getDay());
            s.setSalePrice(s.getListPrice() * .9);
            getSoldItems().add(s);
            getInventory().remove(idx);
            setCashRegister(getCashRegister() + s.getSalePrice());
            newPurchaseCount++;
        }
        
    }
    
    private void handleSeller(Customer c) {
        Item customerItem = ItemGenerator.generateSpecificItem(c.getInterest()); //appraise
        customerItem.setDayArrived(getDay());
        
        //Handles First Change for PT3
        //No more buying clothing.
        if (customerItem instanceof Clothing) {
            System.out.println(employees[onDuty].getName() + " did not buy a " + conditions[customerItem.getCondition()] + " " + customerItem.getName() + 
            " from Customer " + c.getId());
            return;
        }
        
        //couldn't afford to buy
        if (getCashRegister() < customerItem.getPurchasePrice()) {
            System.out.println("Couldn't afford to buy " + customerItem.getName() + " from Customer " + c.getId());
            return;
            
        } else {
            //initial offer
            if (Math.random() > .5) {
                String receipt = employees[onDuty].getName() + " bought a " + conditions[customerItem.getCondition()] + " " + customerItem.getName() + 
                " from Customer " + c.getId() + " at " + customerItem.getPurchasePrice();
                getTransactions().add(receipt);
                System.out.println(receipt);
                boughtItems.add(customerItem);
                setCashRegister(getCashRegister() - customerItem.getPurchasePrice());
                newSoldCount++;
                
                //discount
            } else if (Math.random() > .25) {
                String receipt = employees[onDuty].getName() + " bought a " + conditions[customerItem.getCondition()] + " " + customerItem.getName() + 
                " from Customer " + c.getId() + " at " + (customerItem.getPurchasePrice() * 1.1) + " after a 10% increase";
                getTransactions().add(receipt);
                System.out.println(receipt);
                customerItem.setPurchasePrice(customerItem.getPurchasePrice() * 1.1);
                boughtItems.add(customerItem);
                setCashRegister(getCashRegister() - customerItem.getPurchasePrice());
                newSoldCount++;
                
                //rejected
            } else {
                System.out.println(employees[onDuty].getName() + " did not buy a " + conditions[customerItem.getCondition()] + " " + customerItem.getName() + 
                " from Customer " + c.getId());
                
            }
        }
    }
}