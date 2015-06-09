import java.util.Random;

/**
 * author:  Adrian Kuta
 * indeks:  204423
 * date:    2015-04-08
 * email:   redione193 @ gmail.com
 */
public class Customer extends Thread {

    private String name;
    private Random random;

    public Customer() {
        name = "";
        random = new Random();
    }

    public Customer(String name) {
        this.name = name;
        random = new Random();
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                sleep(getSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String solvedTask = tryGetSolvedTask();
            if (solvedTask != null && Settings.TRYB == Settings.GADATLIWY)
                System.out.println("Customer " + name + " bought task: " + solvedTask);
        }
    }

    private synchronized String tryGetSolvedTask() {
        int randomShopIndex = random.nextInt(Settings.shopList.size());
        Shop shop = Settings.shopList.get(randomShopIndex);
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println("Customer " + name + " goes to " + shop.toString());
        boolean addOperator = random.nextBoolean();
        String boughtTask;
        if (addOperator) {
            boughtTask = shop.buyAddTask();
        } else {
            boughtTask = shop.buyMultiplyTask();
        }
        if (boughtTask == null && Settings.TRYB == Settings.GADATLIWY)
            System.out.println("Customer " + name + " returned from shop " + shop.toString() + " (no " + (addOperator ? "add " : "multiply ") + "task to buy)");
        return boughtTask;
    }

    private long getSleepTime() {
        long bound = Settings.CUSTOMER_MAX_INTERVAL - Settings.CUSTOMER_MIN_INTERVAL;
        return Settings.CUSTOMER_MIN_INTERVAL + (long) (random.nextDouble() * bound);
    }
}
