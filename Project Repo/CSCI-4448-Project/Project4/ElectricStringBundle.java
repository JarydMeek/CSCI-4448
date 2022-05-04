
public class ElectricStringBundle extends StringBundle {
    public ElectricStringBundle() {
        super(.2, .25, .3, .4, "Electric String Bundle");
        // name = "Electric String Bundle";
    }
    public ItemType getType() {
        return ItemType.ELECTRICBUNDLE;
    }
}