
import java.util.*;


public class pt2cards {

    public static void main(String[] args) {
        //Scanner to read stdin
        Scanner scanner = new Scanner(System.in);

        //Infinite loop to keep running until a 0 gets entered
        while (true) {

            int numToShuffle = -1; //Store the number input

            //Infinite loop to keep running until a valid input gets entered
            while (true) {
                System.out.println("Please Enter Number Of Cards To Deal");
                
                //Logic from this Try catch error handling came from here
                // https://stackoverflow.com/questions/23163764/error-handling-inputmismatchexception-in-java
                try {
                    numToShuffle = scanner.nextInt();
                } catch(InputMismatchException ignore){
                    scanner.next();
                }

                //Check if number is valid, or if it's time to exit
                if (numToShuffle < 55 && numToShuffle > 0) {
                    break;
                } else if (numToShuffle == 0) {
                    //Exit
                    scanner.close();
                    return;
                }
                //Loop and print error.
                System.out.println("Invalid entry, please enter a valid number");
            }

            //Create a new deck
            Deck test = new Deck();
            //generate the hand
            test.run(numToShuffle);
        }
    }
}


class Deck {
    //Variables for Storage
    ArrayList<String> fullDeck = new ArrayList<String>(); //Entire Deck

    //Default Initializer
    //Just create full deck
    public Deck() {
        initialize();
    }

    //Run
    //Generate Hand with specified number of cards
    //Takes number of cards to deal as parameter
    //Prints hand to terminal
    public void run(int numCards) {
        Collections.shuffle(fullDeck, new Random()); //Shuffles the Full Deck using a random seed from the random number generator
        System.out.println(fullDeck.subList(0, numCards)); //Selects the first x options in full deck after shuffling
    }
    //Initialize
    //Initialize the Full Deck (54 Cards)
    //No Parameters
    //No Output
    public void initialize() {
        String suits[] = {"♥️","♦️","♠️","♣️"};
        String rank[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for(String x: suits) {
            for(String y: rank) {
                fullDeck.add(y+x);
            }
        }
        fullDeck.add("Joker1");
        fullDeck.add("Joker2");
    }
}