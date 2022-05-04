public class Manual implements Strategy {
    // 80%  f->t, 20% t->f
    public boolean tunePlayer(Player p) {
        // p.setState(!p.getState());
        if (Math.random() < .8) {
            if (!p.getState()) { System.out.println("Player Equalized Manually"); }
            return true;
        } else {
            if (p.getState()) { System.out.println("Player Unequalized Manually"); }
            return false;
        }
    }
    public boolean tuneStringed(Stringed s) {
        if (Math.random() < .8) {
            if (!s.getState()) { System.out.println("Instrument Tuned Manually"); }
            return true;
        } else {
            if (s.getState()) { System.out.println("Instrument out of Tune Manually"); }
            return false;
        }
    }
    public boolean tuneWind(Wind w) {
        if (Math.random() < .8) {
            if (!w.getState()) { System.out.println("Instrument Adjusted Manually"); }
            return true;
        } else {
            if (w.getState()) { System.out.println("Instrument Adjust Failed Manually"); }
            return false;
        }
    }
}