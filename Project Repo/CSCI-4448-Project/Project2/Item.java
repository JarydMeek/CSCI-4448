
/* ENCAPSULATION */
//The following is an example of encapsulation since we have many different data members and function members contained within a single

abstract class Item {
    String name;
    double purchasePrice; //how much it was bought for
    double listPrice; //what its being sold for
    double salePrice; //how much it actually sold for
    boolean newOrUsed; // true for new, false for used
    int dayArrived;
    int condition;
    int daySold;
    
    public Item() {
        name = "";
        condition = (int)(Math.random()*5);
        purchasePrice = ((Math.random()*10) + 1) + (condition * 10); //arbitrary cap at 50
        listPrice = purchasePrice * 2;
        newOrUsed = true;
        dayArrived = 0;
        // poor, fair, good, very good, excellent

        daySold = 0;
        salePrice = 0;
    }
    public String getName() { return name; }
    public void setName(String str) { name = str; }
    public double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(double n) { purchasePrice = n; }
    public double getListPrice() { return listPrice; }
    public void setListPrice(double n) { listPrice = n; }
    public double getSalePrice() { return salePrice; }
    public void setSalePrice(double n) { salePrice = n; }
    public boolean isNew() { return newOrUsed; }
    public void setNewOrUsed(boolean b) { newOrUsed = b; }
    public int getDayArrived() { return dayArrived; }
    public void setDayArrived(int n) { dayArrived = n; }
    public int getCondition() { return condition; }
    public void setCondition(int n) { condition = n; }
    public int getDaySold() { return daySold; }
    public void setDaySold(int n) { daySold = n; }



    /* POLYMORPHISM */
    //The following is an example of Polymorphism since it is a method that's implimentation gets overwritten and changed in each subclass.
    abstract public ItemType getType();
    // implement differently in child classes
}

enum ItemType {
    PAPERSCORE, CD, VINYL, CDPLAYER, RECORDPLAYER, MP3, GUITAR, BASS, MANDOLIN, 
    FLUTE, HARMONICA, HAT, SHIRT, BANDANA, PRACTICEAMP, CABLE, INSTRUMENTSTRING;
        
    //borrowed from https://kodejava.org/how-do-i-pick-a-random-value-from-an-enum/
    public static ItemType getRandomType() {
        return values()[(int) (Math.random() * (values().length))];
    }
}


// accessory portion

/* INHERITANCE */
//The following subclasses are examples of inheritance since they inherit the features of the superclass (item).

// names predefined for the sake of simplicity
abstract class Accessory extends Item {}
class PracticeAmp extends Accessory {
    private double wattage;
    PracticeAmp() {
        name = "Practice Amp";
        wattage = 10;
    }

    public ItemType getType() {
        return ItemType.PRACTICEAMP;
    }
}
class Cable extends Accessory {
    double length;
    Cable() {
        length = (int) (Math.random()*20 + 1);
        name = length + "ft Cable";
    }

    public ItemType getType() {
        return ItemType.CABLE;
    }
}
class InstrumentString extends Accessory {
    String type;
    InstrumentString() {
        type = "Guitar";
        name = type + " String";
    }

    public ItemType getType() {
        return ItemType.INSTRUMENTSTRING;
    }
}

// clothing portion
abstract class Clothing extends Item {}
class Hat extends Clothing {
    String hatSize;
    Hat() {
        hatSize = "Medium";
        name = hatSize + " Hat";
    }

    public ItemType getType() {
        return ItemType.HAT;
    }
}
class Shirt extends Clothing {
    String shirtSize;
    Shirt() {
        shirtSize = "Medium";
        name = shirtSize + " Shirt";
    }

    public ItemType getType() {
        return ItemType.SHIRT;
    }
}
class Bandana extends Clothing {
    Bandana() {
        name = "Bandana";
    }

    public ItemType getType() {
        return ItemType.BANDANA;
    }
}

// instrument portion
abstract class Instrument extends Item {}
// STRING
abstract class Stringed extends Instrument{
    boolean electric;
}
class Guitar extends Stringed {
    Guitar() {
        electric = true;
        if (electric) {
            name = "Electric Guitar";
        } else { //unreachable code lol
            name = "Acoustic Guitar";
        }
    }

    public ItemType getType() {
        return ItemType.GUITAR;
    }
}
class Bass extends Stringed {
    Bass () {
        electric = true;
        if (electric) {
            name = "Electric Bass";
        } else {
            name = "Acoustic Bass";
        }
    }
    public ItemType getType() {
        return ItemType.BASS;
    }
}
class Mandolin extends Stringed{
    Mandolin() {
        electric = true;
        if (electric) {
            name = "Electric Mandolin";
        } else {
            name = "Mandolin";
        }
    }

    public ItemType getType() {
        return ItemType.MANDOLIN;
    }
}
// WIND
abstract class Wind extends Instrument {}
class Flute extends Wind {
    String type;
    Flute() {
        type = "Standard";
        name = type + " Flute";
    }

    public ItemType getType() {
        return ItemType.FLUTE;
    }
}
class Harmonica extends Wind {
    String key;
    Harmonica() {
        key = "C";
        name = "Harmonica (Key of " + key + ")";
    }

    public ItemType getType() {
        return ItemType.HARMONICA;
    }
}

// music portion
abstract class Music extends Item {
    String band;
    String album;
}
class PaperScore extends Music {
    PaperScore() {
        band = "Beethoven";
        album = "Moonlight Sonata";
        name = "Paper Score - " + album + " by " + band;
    }

    public ItemType getType() {
        return ItemType.PAPERSCORE;
    }
}
class CD extends Music {
    CD() {
        band = "Music Guys";
        album = "Smooth Jazz";
        name = "CD - " + album + " by " + band;
    }

    public ItemType getType() {
        return ItemType.CD;
    }
}
class Vinyl extends Music {
    Vinyl() {
        band = "Music Guys";
        album = "Rough Jazz";
        name = "Vinyl - " + album + " by " + band;
    }

    public ItemType getType() {
        return ItemType.VINYL;
    }
}

// player portion
abstract class Player extends Item {}
class CDPlayer extends Player {
    CDPlayer() {
        name = "CD Player";
    }

    public ItemType getType() {
        return ItemType.CDPLAYER;
    }
}
class RecordPlayer extends Player {
    RecordPlayer() {
        name = "Record Player";
    }

    public ItemType getType() {
        return ItemType.RECORDPLAYER;
    }
}
class MP3 extends Player {
    MP3() {
        name = "MP3 Player";
    }

    public ItemType getType() {
        return ItemType.MP3;
    }
}


//INSTANTIATE 1 OF THIS IN THE MAIN DRIVER
final class ItemGenerator {

    /* Identity */
    //The following is an example of identity because we are creating different objects of the same type that aren't the same (have different attributes, etc.)

    //used in stocking/appraisal
    static Item generateSpecificItem(ItemType type) {
        Item newItem;
        switch (type) {
            case PAPERSCORE:
                newItem = new PaperScore();
                break;
            case CD:
                newItem = new CD();
                break;
            case VINYL:
                newItem = new Vinyl();
                break;
            case CDPLAYER:
                newItem = new CDPlayer();
                break;
            case RECORDPLAYER:
                newItem = new RecordPlayer();
                break;
            case MP3:
                newItem = new MP3();
                break;
            case GUITAR:
                newItem = new Guitar();
                break;
            case BASS:
                newItem = new Bass();
                break;
            case MANDOLIN:
                newItem = new Mandolin();
                break;
            case FLUTE:
                newItem = new Flute();
                break;
            case HARMONICA:
                newItem = new Harmonica();
                break;
            case HAT:
                newItem = new Hat();
                break;
            case SHIRT:
                newItem = new Shirt();
                break;
            case BANDANA:
                newItem = new Bandana();
                break;
            case PRACTICEAMP:
                newItem = new PracticeAmp();
                break;
            case CABLE:
                newItem = new Cable();
                break;
            case INSTRUMENTSTRING:
                newItem = new InstrumentString();
                break;
            default:
                newItem = new CD();
        } 
        return newItem;
    }
}