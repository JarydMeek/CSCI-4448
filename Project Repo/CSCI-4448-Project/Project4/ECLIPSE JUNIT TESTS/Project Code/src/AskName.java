public class AskName implements Command {

  Clerk currClerk;

  public AskName(Clerk clerk) {
    super();
    this.currClerk = clerk;
  }
 
  public void execute() {
    System.out.println("\n\nHi my name is: " + currClerk.getName() + "\n");
  }
}