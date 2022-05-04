import java.util.*;
public class AttemptGuitarKit implements Command {

  Store currentStore;

  public AttemptGuitarKit(Store currentStore) {
    super();
    this.currentStore = currentStore;
  }
 
  public void execute() {
    System.out.println("\n\nLets make a guitar kit!\n"); 
    int bridge, knob, cover, neck, pickguard, pickup;
    
    /* BRIDGE */
    System.out.println("Bridge Options:");
    System.out.println("Bridge 1");
    System.out.println("Bridge 2");
    System.out.println("Bridge 3");

    Scanner scanner = new Scanner(System.in);
      int selection = 0;
      do  {
        System.out.println("Please enter a selection from 1 to 3");
        //Handle input
        try {
          selection = scanner.nextInt();
        }
        catch (InputMismatchException ex){
          System.out.println("Please enter 1-3");
          scanner.next();
        }
        catch (Exception ex){
          System.out.println("Error occurred, please try again!");
          scanner.next();
        }
        System.out.println(selection);
      }while (selection != 1 && selection != 2 && selection != 3);
      bridge = selection;

      /* KNOB */
    System.out.println("Knob Options:");
    System.out.println("Knob 1");
    System.out.println("Knob 2");
    System.out.println("Knob 3");

    
    selection = 0;
      do  {
        System.out.println("Please enter a selection from 1 to 3");
        //Handle input
        try {
          selection = scanner.nextInt();
        }
        catch (InputMismatchException ex){
          System.out.println("Please enter 1-3");
          scanner.next();
        }
        catch (Exception ex){
          System.out.println("Error occurred, please try again!");
          scanner.next();
        }
        System.out.println(selection);
      }while (selection != 1 && selection != 2 && selection != 3);
      knob = selection;

      /* Cover */
    System.out.println("Cover Options:");
    System.out.println("Cover 1");
    System.out.println("Cover 2");
    System.out.println("Cover 3");

    
    selection = 0;
      do  {
        System.out.println("Please enter a selection from 1 to 3");
        //Handle input
        try {
          selection = scanner.nextInt();
        }
        catch (InputMismatchException ex){
          System.out.println("Please enter 1-3");
          scanner.next();
        }
        catch (Exception ex){
          System.out.println("Error occurred, please try again!");
          scanner.next();
        }
        System.out.println(selection);
      }while (selection != 1 && selection != 2 && selection != 3);
      cover = selection;

      /* Neck */
    System.out.println("Neck Options:");
    System.out.println("Neck 1");
    System.out.println("Neck 2");
    System.out.println("Neck 3");

    
    selection = 0;
      do  {
        System.out.println("Please enter a selection from 1 to 3");
        //Handle input
        try {
          selection = scanner.nextInt();
        }
        catch (InputMismatchException ex){
          System.out.println("Please enter 1-3");
          scanner.next();
        }
        catch (Exception ex){
          System.out.println("Error occurred, please try again!");
          scanner.next();
        }
        System.out.println(selection);
      }while (selection != 1 && selection != 2 && selection != 3);
      neck = selection;

            /* Pickguard */
    System.out.println("Pickguard Options:");
    System.out.println("Pickguard 1");
    System.out.println("Pickguard 2");
    System.out.println("Pickguard 3");

    
    selection = 0;
      do  {
        System.out.println("Please enter a selection from 1 to 3");
        //Handle input
        try {
          selection = scanner.nextInt();
        }
        catch (InputMismatchException ex){
          System.out.println("Please enter 1-3");
          scanner.next();
        }
        catch (Exception ex){
          System.out.println("Error occurred, please try again!");
          scanner.next();
        }
        System.out.println(selection);
      }while (selection != 1 && selection != 2 && selection != 3);
      pickguard = selection;

      /* Pickup */
    System.out.println("Pickup Options:");
    System.out.println("Pickup 1");
    System.out.println("Pickup 2");
    System.out.println("Pickup 3");

    
    selection = 0;
      do  {
        System.out.println("Please enter a selection from 1 to 3");
        //Handle input
        try {
          selection = scanner.nextInt();
        }
        catch (InputMismatchException ex){
          System.out.println("Please enter 1-3");
          scanner.next();
        }
        catch (Exception ex){
          System.out.println("Error occurred, please try again!");
          scanner.next();
        }
        System.out.println(selection);
      }while (selection != 1 && selection != 2 && selection != 3);
      pickup = selection;

      GuitarKitFactory producer;
      if (currentStore.name == "NorthSide") {
        producer = new GuitarKitNorth();
      } else {
        producer = new GuitarKitSouth();
      }

      CustomGuitarKit guitar = producer.assembleGuitar(bridge-1, knob-1, cover-1, neck-1, pickguard-1, pickup-1);

      System.out.println(guitar.getName() + ": $" + guitar.purchasePrice);




      //HANDLE PURCHASE
      System.out.println("\n\nAttempting to Buy \n");
    
      ArrayList<Item> inv = currentStore.getInventory();
      ItemType lookingFor = ItemType.getRandomType();
      Item offering = guitar;
      int idx = -1; 
      double baseRate = 0;

      //initial offer
      System.out.println("Would you like to buy " + offering.getName() + " for $" + offering.getPurchasePrice());
      
      char selection2 = 'z';
      do  {
        System.out.println("Please enter 'Y' or 'N'");
        //Handle input
        try {
          selection2 = scanner.next().charAt(0);
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
      }while (selection2 != 'Y' && selection2 != 'N');
      if (selection2 == 'Y') {
        String receipt = currentStore.getEmployees()[currentStore.getOnDuty()].getName() + " sold a " + currentStore.conditions[offering.getCondition()] + " " + offering.getName() + 
        " to User at " + offering.getListPrice();
        currentStore.getTransactions().add(receipt);
        System.out.println(receipt);
        offering.setDaySold(currentStore.getDay());
        offering.setSalePrice(offering.getListPrice());
        currentStore.getSoldItems().add(offering);
        currentStore.setCashRegister(currentStore.getCashRegister() + offering.getSalePrice());
        for (int i = 0; i < currentStore.observers.size(); i++) {
          currentStore.observers.get(i).eventItemsSold(1);
        }
        currentStore.setInventory(inv);
        return;
      } else {
        System.out.println("What about " + offering.getPurchasePrice()*0.9 + " for " + offering.getName());
        
        selection2 = 'z';
        do  {
          System.out.println("Please enter 'Y' or 'N'");
          //Handle input
          try {
            selection2 = scanner.next().charAt(0);
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
        }while (selection2 != 'Y' && selection2 != 'N');
        if (selection2 == 'Y') {
          String receipt = currentStore.getEmployees()[currentStore.getOnDuty()].getName() + " sold a " + currentStore.conditions[offering.getCondition()] + " " + offering.getName() + 
          " to User at " + (offering.getPurchasePrice() * .9) + " after a 10% discount";
          currentStore.getTransactions().add(receipt);
          System.out.println(receipt);
          offering.setDaySold(currentStore.getDay());
          offering.setSalePrice(offering.getListPrice() * .9);
          currentStore.getSoldItems().add(offering);
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