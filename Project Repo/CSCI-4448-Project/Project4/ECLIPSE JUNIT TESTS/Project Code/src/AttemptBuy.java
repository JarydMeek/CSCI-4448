import java.util.*;

public class AttemptBuy implements Command {
  
  Store currentStore;
  
  public AttemptBuy(Store currentStore) {
    super();
    this.currentStore = currentStore;
  }
  
  public void execute() {
    System.out.println("\n\nAttempting to Buy \n");
    
    ArrayList<Item> inv = currentStore.getInventory();
    ItemType lookingFor = ItemType.getRandomType();
    Item offering = null;
    int idx = -1; 
    double baseRate = 0;
    
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
      System.out.println("User wanted a " + lookingFor + ", but there were none in inventory, so they left.");
      return;
    } else {
      //initial offer
      System.out.println("Would you like to buy " + offering.getName() + " for $" + offering.getPurchasePrice());
      
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
        System.out.println(selection);
      }while (selection != 'Y' && selection != 'N');
      if (selection == 'Y') {
        String receipt = currentStore.getEmployees()[currentStore.getOnDuty()].getName() + " sold a " + currentStore.conditions[offering.getCondition()] + " " + offering.getName() + 
        " to User at " + offering.getListPrice();
        if (lookingFor == ItemType.GUITAR || lookingFor == ItemType.BASS || lookingFor == ItemType.MANDOLIN) {
          Stringed s = (Stringed)inv.get(idx);
        }
        currentStore.getTransactions().add(receipt);
        System.out.println(receipt);
        offering.setDaySold(currentStore.getDay());
        offering.setSalePrice(offering.getListPrice());
        currentStore.getSoldItems().add(offering);
        inv.remove(idx);
        currentStore.setCashRegister(currentStore.getCashRegister() + offering.getSalePrice());
        for (int i = 0; i < currentStore.observers.size(); i++) {
          currentStore.observers.get(i).eventItemsSold(1);
        }
        currentStore.setInventory(inv);
        return;
      } else {
        System.out.println("What about " + offering.getPurchasePrice()*0.9 + " for " + offering.getName());
        
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
          String receipt = currentStore.getEmployees()[currentStore.getOnDuty()].getName() + " sold a " + currentStore.conditions[offering.getCondition()] + " " + offering.getName() + 
          " to User at " + (offering.getPurchasePrice() * .9) + " after a 10% discount";
          if (lookingFor == ItemType.GUITAR || lookingFor == ItemType.BASS || lookingFor == ItemType.MANDOLIN) {
            Stringed s = (Stringed)inv.get(idx);
            if(s.getElectric()) {
            }
          }
          currentStore.getTransactions().add(receipt);
          System.out.println(receipt);
          offering.setDaySold(currentStore.getDay());
          offering.setSalePrice(offering.getListPrice() * .9);
          currentStore.getSoldItems().add(offering);
          inv.remove(idx);
          currentStore.setCashRegister(currentStore.getCashRegister() + offering.getSalePrice());
          for (int i = 0; i < currentStore.observers.size(); i++) {
            currentStore.observers.get(i).eventItemsSold(1);
          }
          currentStore.setInventory(inv);
          return;
          //rejected
        } else {
          System.out.println(currentStore.getEmployees()[currentStore.getOnDuty()].getName() + " did not sell a " + offering.getName() + 
          " to User ");
          
        }
      }
    }
  }
}