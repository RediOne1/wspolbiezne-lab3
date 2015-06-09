import java.util.Random;

/**
 * author:  Adrian Kuta
 * indeks:  204423
 * date:    2015-04-08
 * email:   redione193 @ gmail.com
 */
public class Chief extends Thread {

    private Random random;
    private String name;
    private Factory factory;

    public Chief(Factory factory){
        name = "";
        random = new Random();
        this.factory = factory;
    }

    public Chief(Factory factory, String name) {
        this.name = name;
        random = new Random();
        this.factory = factory;
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
            Task task = new Task();
            task.arg1 = random.nextInt(1024);
            task.arg2 = random.nextInt(1024);
            int operator = random.nextInt(Settings.operators.length);
            task.operator = Settings.operators[operator];
            addTask(task);
        }
    }

    private synchronized void addTask(Task task) {
        factory.taskList.add(task);
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println(name + " Add task: " + task.arg1 + task.operator + task.arg2);
    }

    private long getSleepTime() {
        long bound = Settings.ADD_TASK_MAX_INTERVAL - Settings.ADD_TASK_MIN_INTERVAL;
        return Settings.ADD_TASK_MIN_INTERVAL + (long) (random.nextDouble() * bound);
    }
}
