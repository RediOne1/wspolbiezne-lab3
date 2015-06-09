import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * author:  Adrian Kuta
 * indeks:  204423
 * date:    2015-04-08
 * email:   redione193 @ gmail.com
 */
public class Main {

    public static void main(String[] args) {

        FactoryCreator factoryCreator = new FactoryCreator();
        factoryCreator.addEmployee("Jan")
                .addEmployee("Adam");

        Settings.factoryList.add(factoryCreator.create());
        Settings.factoryList.forEach(Factory::start);

        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        customerList.forEach(Customer::start);


        if (Settings.TRYB == Settings.SPOKOJNY)
            launchScanner();
    }

    private static void launchScanner(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            String command = scanner.nextLine();
            switch (command){
                case "gadatliwy_on":
                    Settings.TRYB = Settings.GADATLIWY;
                    break;
                case "gadatliwy_off":
                    Settings.TRYB = Settings.SPOKOJNY;
                    break;
                case "stop":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong command!");
                    System.out.println("Available commands:");
                    System.out.println("task_count");
                    System.out.println("solved_count");
                    System.out.println("gadatliwy_on");
                    System.out.println("gadatliwy_off");
                    System.out.println("stop");
            }
        }
    }
}
