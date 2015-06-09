/**
 * authot:  Adrian Kuta
 * index:   204423
 * date:    01.06.15
 */
public class AddingMachine extends Machine implements Employee.CommunicationInterface {

    public AddingMachine(Factory factory) {
        super(factory);
    }

    @Override
    public void onTaskSolved() {
        if (employee2.hasTask()) {
            if (Settings.TRYB == Settings.GADATLIWY) {
                System.out.println("Employee " + employee1 + " leaved machine with solved task");
                System.out.println("Employee " + employee2 + " pressed first button");
            }
            employee1 = employee2;
            employee2 = null;
        } else {
            if (Settings.TRYB == Settings.GADATLIWY) {
                System.out.println("Employee " + employee1 + " leaved machine with solved task");
                System.out.println("Employee " + employee2 + " wait for task");
            }
            employee1 = null;
            ((CommunicationInterface) employee2).onNeededTask();
        }
        solvingInProgress = false;
    }

    @Override
    public Task solveTask(Task task) {
        if (checkDestroy())
            return null;
        task.result = "" + (task.arg1 + task.arg2);
        if (Settings.TRYB == Settings.GADATLIWY)
            System.out.println("AddingMachine solved task: " + task.arg1 + task.operator + task.arg2 + "=" + task.result);
        return task;
    }
}
