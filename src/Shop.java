import java.util.ArrayList;
import java.util.List;

/**
 * authot:  Adrian Kuta
 * index:   204423
 * date:    08.06.15
 */
public class Shop {

    private int ADD_TASKS_MAX_COUNT = Settings.ADD_TASKS_MAX_COUNT;
    private int MULTIPLY_TASKS_MAX_COUNT = Settings.MULTIPLY_TASKS_MAX_COUNT;
    private List<Task> addTask = new ArrayList<>();
    private List<Task> multiplyTask = new ArrayList<>();
    private Courier courier;
    private String name = "";

    public Shop(){
        checkStatus();
    }

    public Shop(String name){
        this.name = name;
        checkStatus();
    }

    private void checkStatus() {
        boolean needAddTask = false, needMultiplyTask = false;
        if (courier != null)
            return;
        if (addTask.size() <= 1) {
            needAddTask = true;
        }
        if (multiplyTask.size() <= 1) {
            needMultiplyTask = true;
        }
        if (needAddTask || needMultiplyTask)
            sendCourier(needAddTask, needMultiplyTask);
    }

    private void sendCourier(boolean needAddTask, boolean needMultiplyTask) {
        while (courier == null) {
            for (Courier courier : Settings.courierList) {
                if (this.courier != null)
                    continue;
                if (courier.isFree) {
                    courier.isFree = false;
                    this.courier = courier;
                }
            }
        }
        courier.needAddTask = needAddTask;
        courier.needMultiplyTask = needMultiplyTask;
        courier.ADD_MAX_CAPACITY = ADD_TASKS_MAX_COUNT - addTask.size();
        courier.MULTIPLY_MAX_CAPACITY = MULTIPLY_TASKS_MAX_COUNT - multiplyTask.size();
        courier.originShop = this;
        courier.drive();
    }

    public synchronized String buyAddTask() {
        if (!addTask.isEmpty()){
            Task task = addTask.remove(0);
            checkStatus();
            return task.toString();
        }
        return null;
    }

    public synchronized String buyMultiplyTask() {
        if (!multiplyTask.isEmpty()){
            Task task = multiplyTask.remove(0);
            checkStatus();
            return task.toString();
        }
        return null;
    }

    public void onCourierArrived(List<Task> solvedAddTask, List<Task> solvedMultiplyTask){
        this.addTask.addAll(solvedAddTask);
        this.multiplyTask.addAll(solvedMultiplyTask);
        courier = null;
        checkStatus();
    }
    @Override
    public String toString(){
        return name;
    }
}
