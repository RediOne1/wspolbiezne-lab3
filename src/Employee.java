import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author:  Adrian Kuta
 * indeks:  204423
 * date:    2015-04-08
 * email:   redione193 @ gmail.com
 */
public class Employee extends Thread implements Machine.CommunicationInterface {

    public Machine machine;
    private Random random;
    private String name;
    private Task task = null;
    private Timer timer = new Timer();
    private Factory factory;

    public Employee() {
        name = "";
        random = new Random();

    }

    public Employee(Factory factory) {
        name = "";
        random = new Random();
        this.factory = factory;
    }

    public Employee(Factory factory, String name) {
        this.name = name;
        random = new Random();
        this.factory = factory;
    }

    @Override
    public void run() {
        super.run();
        getTask();
    }

    private void getTask() {
        machine = null;
        task = null;
        while (task == null) {
            try {
                sleep(getSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            task = tryGetTask();
        }
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println("Employee " + name + " get task: " + task.arg1 + task.operator + task.arg2);
        solveTask(task);
    }

    private synchronized Task tryGetTask() {
        if (factory.taskList.size() != 0)
            return factory.taskList.remove(0);
        return null;
    }

    private void solveTask(Task task) {
        if (task.operator == '+')
            goToAddingMachine();
        else
            goToMultiplyingMachine();
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (machine != null)
                    callForHelp();
            }
        };
        timer.schedule(timerTask, Settings.GET_TASK_MAX_INTERVAL);
    }

    private void goToMultiplyingMachine() {
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println("Employee " + name + " searching Multiplying Machine");
        while (machine == null)
            for (int i = 0; machine == null && i < Settings.MULTIPLYING_MACHINES_COUNT; i++) {
                factory.multiplyingMachines.get(i).tryBookMachine(this);
            }
    }

    private void goToAddingMachine() {
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println("Employee " + name + " searching Adding Machine");
        while (machine == null)
            for (int i = 0; machine == null && i < Settings.ADDING_MACHINES_COUNT; i++) {
                factory.addingMachines.get(i).tryBookMachine(this);
            }
    }

    private synchronized void addResult(Task task) {
        if (task.operator == '+')
            factory.solvedAddTasks.add(task);
        else
            factory.solvedMultiplyTasks.add(task);
    }

    private long getSleepTime() {
        long bound = Settings.GET_TASK_MAX_INTERVAL - Settings.GET_TASK_MIN_INTERVAL;
        return Settings.GET_TASK_MIN_INTERVAL + (long) (random.nextDouble() * bound);
    }

    @Override
    public void onNeededTask() {
        getTask();
    }

    @Override
    public void startSolvingTask() {
        timer.cancel();
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println("Employee " + name + " started solving task");
        try {
            sleep(factory.SOLVING_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Task solvedTask = machine.solveTask(task);
        if (solvedTask != null) {
            task = solvedTask;
            addResult(task);
            ((CommunicationInterface) machine).onTaskSolved();
        } else {
            machineDestroyed();
        }
        getTask();

    }

    public boolean canHelp() {
        return machine == null || !machine.solvingInProgress;
    }

    public void help(Machine machine) {
        timer.cancel();
        timer.purge();
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println("Employee " + name + " will help");
        leaveTask();
        machine.tryBookMachine(this);
        if (machine == null)
            getTask();
    }

    private void callForHelp() {
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println("Employee " + name + " is calling for help");
        for (Employee employee : factory.employeeList) {
            if (employee == this)
                continue;
            if (employee.canHelp()) {
                employee.help(machine);
                return;
            }
        }
    }

    @Override
    public void machineDestroyed() {
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println("Employee " + name + " leaved broken machine");
        leaveTask();
        getTask();
    }

    private synchronized void leaveTask() {
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println("Employee " + name + " leaved task");
        factory.taskList.add(task);
        task = null;
    }

    @Override
    public String toString() {
        return name != null ? name : "";
    }

    public boolean hasTask() {
        return task != null;
    }

    public interface CommunicationInterface {
        void onTaskSolved();
    }
}
