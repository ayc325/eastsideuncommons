import java.sql.SQLException;
import java.util.*;
public class Test {
    public static void main(String[] args) {
        Menu menu = new Menu();
        List<String> test = menu.printAccountOptionMenu(1);
        if(test == null){
            menu.printPaymentTypeMenu();
        }
        for(int i = 0; i < test.size(); i++){
           System.out.println(test.get(i));
        }
    }

    
    
}
