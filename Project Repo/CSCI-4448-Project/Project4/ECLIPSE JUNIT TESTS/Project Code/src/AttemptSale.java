import java.util.*;

public class AttemptSale implements Command {
    
    Store currentStore;
    
    public AttemptSale(Store currentStore) {
        super();
        this.currentStore = currentStore;
    }
    
    public void execute() {
        System.out.println("\n\nAttempting to sell \n");
        ArrayList<Item> inv = currentStore.getInventory();
        
        Item customerItem = ItemGenerator.generateSpecificItem(ItemType.getRandomType()); //appraise
        customerItem.setDayArrived(currentStore.getDay());
        
        //Handles First Change for PT3
        //No more buying clothing.
        if (customerItem instanceof Clothing) {
            System.out.println(currentStore.getEmployees()[currentStore.getOnDuty()].getName() + " did not buy a " + currentStore.conditions[customerItem.getCondition()] + " " + customerItem.getName() + 
            " from User");
            return;
        }
        
        //couldn't afford to buy
        if (currentStore.getCashRegister() < customerItem.getPurchasePrice()) {
            System.out.println("Couldn't afford to buy " + customerItem.getName() + " from User");
            return;
            
        } else {
            //initial offer
            System.out.println("Would You Accept " + customerItem.getPurchasePrice() + " for " + customerItem.getName());
            Scanner scanner = new Scanner(System.in);
            char selection = 'z';
            do  {
                System.out.println("Please enter 'Y' or 'N'");
                //Handle input
                try {
                    selection = scanner.next().charAt(0);
                }
                catch (InputMismatchException ex){
                    System.out.println("Please either Y or N");
                    scanner.next();
                }
                catch (Exception ex){
                    System.out.println("Error occurred, please try again!");
                    scanner.next();
                }
            }while (selection != 'Y' && selection != 'N');
            if (selection == 'Y') {
                inv.add(customerItem);
                String receipt = currentStore.getEmployees()[currentStore.getOnDuty()].getName() + " bought a " + currentStore.conditions[customerItem.getCondition()] + " " + customerItem.getName() + 
                " from User at " + customerItem.getPurchasePrice();
                currentStore.getTransactions().add(receipt);
                System.out.println(receipt);
                currentStore.boughtItems.add(customerItem);
                currentStore.setCashRegister(currentStore.getCashRegister() - customerItem.getPurchasePrice());
                for (int i = 0; i < currentStore.observers.size(); i++) {
                    currentStore.observers.get(i).eventItemsPurchased(1);
                }
                currentStore.setInventory(inv);
                return;
            } else {
                System.out.println("Would You Accept " + customerItem.getPurchasePrice()*1.1 + " for " + customerItem.getName());
                selection = 'z';
                do  {
                    System.out.println("Please enter 'Y' or 'N'");
                    //Handle input
                    try {
                        selection = scanner.next().charAt(0);
                    }
                    catch (InputMismatchException ex){
                        System.out.println("Please either Y or N");
                        scanner.next();
                    }
                    catch (Exception ex){
                        System.out.println("Error occurred, please try again!");
                        scanner.next();
                    }
                    System.out.println(selection);
                }while (selection != 'Y' && selection != 'N');
                if (selection == 'Y') {
                    inv.add(customerItem);
                    String receipt = currentStore.getEmployees()[currentStore.getOnDuty()].getName() + " bought a " + currentStore.conditions[customerItem.getCondition()] + " " + customerItem.getName() + 
                    " from User at " + customerItem.getPurchasePrice()*1.1 + " after a 10% increase";
                    currentStore.getTransactions().add(receipt);
                    System.out.println(receipt);
                    currentStore.boughtItems.add(customerItem);
                    currentStore.setCashRegister(currentStore.getCashRegister() - customerItem.getPurchasePrice()*1.1);
                    for (int i = 0; i < currentStore.observers.size(); i++) {
                        currentStore.observers.get(i).eventItemsPurchased(1);
                    }
                    currentStore.setInventory(inv);
                    return;
                } else {
                    System.out.println(currentStore.getEmployees()[currentStore.getOnDuty()].getName() + " did not buy a " + currentStore.conditions[customerItem.getCondition()] + " " + customerItem.getName() + 
                    " from User");
                }
            }
        }
    }
}