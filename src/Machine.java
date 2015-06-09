import java.util.Random;

/**
 * authot:  Adrian Kuta
 * index:   204423
 * date:    01.06.15
 */
public abstract class Machine {

    public Employee employee1 = null, employee2 = null;
    public boolean solvingInProgress;
    private boolean destroyed = false;
    private String name = "";
    private Factory factory;

    public Machine(Factory factory){
        this.factory = factory;
    }

    public synchronized void tryBookMachine(Employee employee) {
        if (destroyed)
            return;
        if (employee1 == null) {
            employee1 = employee;
            if (Settings.TRYB == Settings.GADATLIWY)
                System.out.println("Employee " + employee1 + " pressed first button");
            employee1.machine = this;
        } else if (employee2 == null) {
            employee2 = employee;
            if (Settings.TRYB == Settings.GADATLIWY)
                System.out.println("Employee " + employee2 + " pressed second button");
            solvingInProgress = true;
            employee2.machine = this;
            ((CommunicationInterface) employee1).startSolvingTask();
        }
    }

    public synchronized boolean checkDestroy() {
        Random random = new Random();
        float randFloat = random.nextFloat();
        if (randFloat < factory.BREAK_PROBABILITY) {
            if (Settings.TRYB == Settings.GADATLIWY)
                System.out.println("Machine destroyed");
            destroyed = true;
            ((CommunicationInterface) employee2).machineDestroyed();
            Engineer.fixMachine(this);
            return true;
        }
        return false;
    }

    public abstract Task solveTask(Task task);

    public void fix() {
        destroyed = false;
    }

    @Override
    public String toString() {
        return name;
    }

    public interface CommunicationInterface {
        void onNeededTask();

        void startSolvingTask();

        void machineDestroyed();
    }
}
