public class Electronic implements Strategy {
    // guaranteed true
    public boolean tunePlayer(Player p) {
        System.out.println("Player Equalized Electronically");
        return true;
    }
    public boolean tuneStringed(Stringed s) {
        System.out.println("Instrument Tuned Electronically");
        return true;
    }
    public boolean tuneWind(Wind w) {
        System.out.println("Instrument Adjusted Electronically");
        return true;
    }
}