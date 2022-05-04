import java.util.*;

public class CLI {
    int currentStore = 0;

    public void cycleStore(int numStores) {
        currentStore++;
        if (currentStore >= numStores) {
            currentStore = 0;
        }
    }

    public void showMenu(ArrayList<Store> stores) {
        
        Logger.getLogger(stores.get(currentStore).getDay()).eventClerkChanged(stores.get(currentStore).getEmployees()[stores.get(currentStore).getOnDuty()].getName(), stores.get(currentStore).name);
        Tracker.getTracker().eventClerkChanged(stores.get(currentStore).getEmployees()[stores.get(currentStore).getOnDuty()].getName(), stores.get(currentStore).name);

        Scanner scanner = new Scanner(System.in);
        int selection = 0;
        do  {
            System.out.println("\n\nWelcome to " + stores.get(currentStore).name + "!");
            System.out.println("Please pick an option:");
            System.out.println("1: Select a store to issue commands to");
            System.out.println("2: Ask the clerk their name");
            System.out.println("3: Ask the clerk what time it is");
            System.out.println("4: Sell to clerk");
            System.out.println("5: Buy a normal item from clerk");
            System.out.println("6: Buy a custom guitar kit from clerk");
            System.out.println("7: End the interaction");

            //Handle input
            try {
                selection = scanner.nextInt();
            }
            catch (InputMismatchException ex){
                System.out.println("Please enter a value between 1 and 7");
                scanner.next();
            }
            catch (Exception ex){
                System.out.println("Error occurred, please try again!");
                scanner.next();
            }

            
            Clerk current = stores.get(currentStore).getEmployees()[stores.get(currentStore).getOnDuty()];


            switch (selection) {
                case 1: 
                    ChangeStore changeStore = new ChangeStore();
                    current.addCommand(changeStore);
                    cycleStore(stores.size());

                    Logger.getLogger(stores.get(currentStore).getDay()).eventClerkChanged(stores.get(currentStore).getEmployees()[stores.get(currentStore).getOnDuty()].getName(), stores.get(currentStore).name);
                    Tracker.getTracker().eventClerkChanged(stores.get(currentStore).getEmployees()[stores.get(currentStore).getOnDuty()].getName(), stores.get(currentStore).name);
                    break;
                case 2:
                    AskName askName = new AskName(current);
                    current.addCommand(askName);
                    break;
                case 3:
                    GetTime getTime = new GetTime();
                    current.addCommand(getTime);
                    break;
                case 4:
                    AttemptSale attemptSale = new AttemptSale(stores.get(currentStore));
                    current.addCommand(attemptSale);
                    break;
                case 5:
                    AttemptBuy attemptBuy = new AttemptBuy(stores.get(currentStore));
                    current.addCommand(attemptBuy);
                    break;
                case 6:
                    AttemptGuitarKit attemptGuitarKit = new AttemptGuitarKit(stores.get(currentStore));
                    current.addCommand(attemptGuitarKit);
                    break;
                default:
                    continue;
            }
            current.runCommands();


        } while (selection != 7);

        for (Store store : stores) {
            store.setInventory(store.employees[store.getOnDuty()].CleanTheStore(store.inventory));
            store.employees[store.getOnDuty()].LeaveTheStore();
            store.employees[store.getOnDuty()].removeObserver(store.employees[store.getOnDuty()].observers.size() - 1);
            store.removeObserver(store.observers.size() - 1);
            System.out.println("closing register (" + store.name + "): $" + store.getCashRegister());
            store.day++;
        }

        System.out.println("Thank you for shopping with us!");
        // stores.get(currentStore).setInventory(stores.get(currentStore).employees[stores.get(currentStore).getOnDuty()].CleanTheStore(stores.get(currentStore).inventory));
            
        // stores.get(currentStore).employees[stores.get(currentStore).getOnDuty()].LeaveTheStore();
        // stores.get(currentStore).employees[stores.get(currentStore).getOnDuty()].removeObserver(stores.get(currentStore).employees[stores.get(currentStore).getOnDuty()].observers.size() - 1);
        // stores.get(currentStore).removeObserver(stores.get(currentStore).observers.size() - 1);
        // //cashRegister -= (int)(Math.random() * cashRegister);
        // System.out.println("closing register: $" + stores.get(currentStore).getCashRegister()); //TODO: fix the fractions of cents
        // stores.get(currentStore).day++;
        // //return stores;
        scanner.close();
    }
    
}