import java.util.*;
import java.text.SimpleDateFormat;  

public class GetTime implements Command {

  public GetTime() {
    super();
  }
 
  public void execute() {
        SimpleDateFormat formatter= new SimpleDateFormat("h:mm a");
        Date date = new Date(System.currentTimeMillis());
        System.out.println("\n\nThe current time is: " + formatter.format(date)+ "\n");
  }
}