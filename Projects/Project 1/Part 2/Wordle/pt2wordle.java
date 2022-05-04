import java.util.*;

public class pt2wordle {
    public static void main(String[] args) {
        //Create game then run game
        Wordle game = new Wordle();
        game.run();
    }
}


class Wordle {
    //Member Variables
    private String[] wordOptions = {"ROBOT", "POINT", "FAVOR", "DRINK", "QUERY", "SLUMP", "SIEGE", "BOOST", "SOLAR", "PROXY", "PAPER", "MAJOR"};
    String selected = "";

    //Setup Game
    public Wordle() {
        initialize();
    }

    //Run
    //Create loop that prompts user for word then finds out if that word is correct.
    //No parameter
    //Prints prompts and results from terminal
    public void run() {
        //Scanner to read input from stdin
        Scanner scanner = new Scanner(System.in);

        //loop to run until user quits game or gets correct answer
        while(true) {
            //Variables
            String userInput = "";
            String output = "";

            //Prompt user and read input
            System.out.println("Enter a 5 letter word:");
            userInput = scanner.nextLine();
            //If length of input is zero, quit game
            if (userInput.length() == 0) {
                break;
            //If length of input is 5, check word and do logic for determining how far off word is
            } else if (userInput.length() == 5) {
                //uppercase user input
                userInput = userInput.toUpperCase();
                //Checks for right word entered
                if (userInput.equals(selected)) {
                    System.out.println("Congrats! You solved it!");
                    return;
                }
                //Wrong word entered. Figure out how wrong it is.
                for (int x = 0; x < 5; x++) {
                    if (userInput.charAt(x) == selected.charAt(x)) {
                        //Totally Right letter
                        output +=  " \"" + userInput.charAt(x) + "\" is a match in the correct location\n";
                    } else if (selected.contains( String.valueOf(userInput.charAt(x)))) {
                        //Right letter wrong place
                        output +=  " \"" + userInput.charAt(x) + "\" is in the word, but in a different location\n";
                    } else {
                        //Wrong letter
                        output +=  " \"" + userInput.charAt(x) + "\" is not in the word\n";
                    }
                }
                //output how wront the letter is 
                System.out.println(output);
            } else {
                //invalid entry, prompt user with error
                System.out.println("Invalid entry. Please try again.");
            }
        }
    }

    //Initialize
    //Pick a random word
    //No Parameters
    //No Output
    public void initialize() {
        int random = (int) (Math.random() * 11.0);
        selected = wordOptions[random];
    }
}