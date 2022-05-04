public class Haphazard implements Strategy {
    // 50/50 chance
    public boolean tunePlayer(Player p) {
        // p.setState(!p.getState());
        if (Math.random() < .5) {
            if (p.getState()) {
                System.out.println("Player Equalized Haphazardly");
            } else {
                System.out.println("Player Unequalized Haphazardly");
            }
            return !p.getState();
        }
        return p.getState();
    }
    public boolean tuneStringed(Stringed s) {
        if (Math.random() < .5) {
            if (s.getState()) {
                System.out.println("Instrument Tuned Haphazardly");
            } else {
                System.out.println("Instrument out of Tune Haphazardly");
            }
            return !s.getState();
        }
        return s.getState();
    }
    public boolean tuneWind(Wind w) {
        if (Math.random() < .5) {
            if (w.getState()) {
                System.out.println("Instrument Adjusted Haphazardly");
            } else {
                System.out.println("Instrument Adjust Failed Haphazardly");
            }
            return !w.getState();
        }
        return w.getState();
    }
}