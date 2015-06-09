import java.util.LinkedList;
import java.util.List;

/**
 * authot:  Adrian Kuta
 * index:   204423
 * date:    02.06.15
 */
public class Engineer extends Thread {

    private static List<Machine> machines = new LinkedList<>();
    private static boolean isWorking = false;

    public static void fixMachine(Machine machine) {
        machines.add(machine);
        if (!isWorking)
            startWorking();
    }

    private static void startWorking() {
        isWorking = true;
        for (Machine machine : machines) {
            try {
                sleep(Settings.MACHINE_REPAIRING_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Settings.TRYB == Settings.GADATLIWY)
                System.out.println("Engineer fixed machine " + machine.toString());
            machine.fix();

        }
        isWorking = false;
    }

    @Override
    public void run() {
        super.run();
    }
}
