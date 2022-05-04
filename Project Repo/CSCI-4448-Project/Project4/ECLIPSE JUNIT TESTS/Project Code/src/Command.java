
//COMMAND CLASS

//There are many different reciever classes

//The Invoker is in the Clerk Class

//The Concrete Command classes are the AttemptBuy.java AttemptGuitarKit.java AttemptSale.java GetTime.java AskName.java and ChangeStore.java

//The client is the CLI class (CLI.Java)

public interface Command {
  public void execute();
}
