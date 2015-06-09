import java.util.ArrayList;
import java.util.List;

/**
 * authot:  Adrian Kuta
 * index:   204423
 * date:    08.06.15
 */
public class Factory {

    public List<Employee> employeeList = new ArrayList<>();
    public List<Task> taskList = new ArrayList<>();
    public List<Task> solvedAddTasks = new ArrayList<>();
    public List<Task> solvedMultiplyTasks = new ArrayList<>();
    public List<AddingMachine> addingMachines = new ArrayList<>();
    public List<MultiplyingMachine> multiplyingMachines = new ArrayList<>();
    public String name = "";

    //Machine settings
    public long SOLVING_TIME = Settings.SOLVING_TIME;
    public float BREAK_PROBABILITY = Settings.BREAK_PROBABILITY;
    public int ADDING_MACHINES_COUNT = Settings.ADDING_MACHINES_COUNT;
    public int MULTIPLYING_MACHINES_COUNT = Settings.MULTIPLYING_MACHINES_COUNT;

    public void start(){

        for (int i = 0; i < ADDING_MACHINES_COUNT; i++)
            addingMachines.add(new AddingMachine(this));

        for (int i = 0; i < MULTIPLYING_MACHINES_COUNT; i++)
            multiplyingMachines.add(new MultiplyingMachine(this));

        Chief chief = new Chief(this, "Chief");
        chief.start();

        employeeList.forEach(Employee::start);
    }

    public synchronized List<Task> buyTask(char operator, int maxCount){
        List<Task> boughtTask = new ArrayList<>();
        if(operator == '+'){
            while(solvedAddTasks.size()>0 && boughtTask.size()<maxCount)
                boughtTask.add(solvedAddTasks.remove(0));
        } else
            while(solvedMultiplyTasks.size()>0 && boughtTask.size()<maxCount)
                boughtTask.add(solvedMultiplyTasks.remove(0));
        return boughtTask;
    }

    @Override
    public String toString(){
        return name;
    }
}
