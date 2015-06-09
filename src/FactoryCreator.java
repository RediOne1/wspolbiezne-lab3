import java.util.List;

/**
 * authot:  Adrian Kuta
 * index:   204423
 * date:    08.06.15
 */
public class FactoryCreator {

    private Factory factory;

    public FactoryCreator() {
        factory = new Factory();
    }

    public FactoryCreator setName(String name) {
        factory.name = name;
        return this;
    }

    public FactoryCreator addEmployee(String name) {
        factory.employeeList.add(new Employee(factory, name));
        return this;
    }

    public FactoryCreator setAddingMachinesCount(int count) {
        factory.ADDING_MACHINES_COUNT = count;
        return this;
    }

    public FactoryCreator setMultiplyingMachinesCount(int count) {
        factory.MULTIPLYING_MACHINES_COUNT = count;
        return this;
    }

    public FactoryCreator setSolvingTime(long time) {
        factory.SOLVING_TIME = time;
        return this;
    }

    public FactoryCreator setBreakProbability(float probability) {
        factory.BREAK_PROBABILITY = probability;
        return this;
    }

    public FactoryCreator reset() {
        this.factory = null;
        this.factory = new Factory();
        return this;
    }

    public Factory create() {
        return factory;
    }
}
