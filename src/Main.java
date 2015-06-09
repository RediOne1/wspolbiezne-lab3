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
        factoryCreator
                .setName("FM Logistic")
                .addEmployee("Jan")
                .addEmployee("Adam")
                /*.addEmployee("Stalin")
                .addEmployee("Hitler")
                .addEmployee("Ma³ysz")
                .addEmployee("Nowak")
                .addEmployee("Seba")*/
                .addEmployee("Krzysiek");
        Settings.factoryList.add(factoryCreator.create());

        /*factoryCreator
                .reset()
                .setName("LV Creator")
                .addEmployee("Jan")
                .addEmployee("Adam")
                .addEmployee("Stalin")
                .addEmployee("Hitler")
                .addEmployee("Ma³ysz")
                .addEmployee("Nowak")
                .addEmployee("Seba")
                .addEmployee("Krzysiek");
        Settings.factoryList.add(factoryCreator.create());*/

        Settings.factoryList.forEach(Factory::start);

        for (int i = 0; i < Settings.COURIER_COUNT; i++)
            Settings.courierList.add(new Courier("" + (i + 1)));

        for (int i = 0; i < Settings.SHOP_COUNT; i++)
            Settings.shopList.add(new Shop("" + (i + 1)));

        List<Customer> customerList = new ArrayList<>();
        for (int i = 0; i < Settings.CUSTOMER_COUNT; i++)
            customerList.add(new Customer("" + (i + 1)));
        customerList.forEach(Customer::start);


        if (Settings.TRYB == Settings.SPOKOJNY)
            launchScanner();
    }

    private static void launchScanner() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            switch (command) {
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
