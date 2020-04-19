package project.Model;

public class InhousePart extends Part {

    private int machineID;


    public InhousePart(int id, String name, Double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }

}
