/* 
    This is an example of a Strategy pattern.  The three implementations, Haphazard, Electronic, and Manual implement the 
    3 operations specified in the interface definition, though slightly differently to reflect the different potential 
    outcomes as outlined in the project specifications. 
*/
public interface Strategy {
    // public Item tune(Item i); 
    public boolean tunePlayer(Player p);
    public boolean tuneStringed(Stringed s);
    public boolean tuneWind(Wind w);
}