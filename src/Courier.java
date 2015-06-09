import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * authot:  Adrian Kuta
 * index:   204423
 * date:    08.06.15
 */
public class Courier {

    public boolean isFree = true;
    public Shop originShop;
    public int ADD_MAX_CAPACITY = 4;
    public int MULTIPLY_MAX_CAPACITY = 4;
    public List<Task> solvedAddTask = new ArrayList<>();
    public List<Task> solvedMultiplyTask = new ArrayList<>();
    public boolean needAddTask = false;
    public boolean needMultiplyTask = false;
    private Timer timer;
    private String name = "";

    public Courier(String name) {
        this.name = name;
    }

    public void drive() {
        isFree = false;
        Factory factory = null;
        while (factory == null)
            for (Factory fact : Settings.factoryList) {
                if (needAddTask && needMultiplyTask && fact.solvedAddTasks.size() > 1 && fact.solvedMultiplyTasks.size() > 1)
                    factory = fact;
                else if (needAddTask && fact.solvedAddTasks.size() > 1)
                    factory = fact;
                else if (needMultiplyTask && fact.solvedMultiplyTasks.size() > 1)
                    factory = fact;
                if (factory != null)
                    break;
            }
        goToFactory(factory);
    }

    private void goToFactory(Factory factory) {
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println("Courier " + name + " goes to " + factory.toString() + " from shop: " + originShop.toString());

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (needAddTask && needMultiplyTask) {
                    solvedAddTask = factory.buyTask('+', ADD_MAX_CAPACITY);
                    solvedMultiplyTask = factory.buyTask('*', MULTIPLY_MAX_CAPACITY);
                } else if (needAddTask)
                    solvedAddTask = factory.buyTask('+', ADD_MAX_CAPACITY);
                else if (needMultiplyTask)
                    solvedMultiplyTask = factory.buyTask('*', MULTIPLY_MAX_CAPACITY);

                if (Settings.TRYB == Settings.GADATLIWY)
                    System.out.println("Courier " + name + " bought " + solvedAddTask.size() + " addTasks, and " + solvedMultiplyTask.size() + " multiplyTasks");
                goToShop();
            }
        };
        timer.schedule(timerTask, Settings.DRIVE_TIME);
    }

    private void goToShop() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (Settings.TRYB == Settings.GADATLIWY)
                    System.out.println("Courier " + name + " returned to " + originShop.toString());
                isFree = true;
                originShop.onCourierArrived(solvedAddTask, solvedMultiplyTask);
            }
        };
        timer.schedule(timerTask, Settings.DRIVE_TIME);
    }
}
