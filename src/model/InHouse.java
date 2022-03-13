package model;

/**
 * This Class is the Model for the InHouse Part
 * @author Nathan Croson
 */
public class InHouse extends Part{



    private int machineId;

    /**
     * Constructor for the InHouse Part.
     * @param id ID of the Part.
     * @param name Name of the Part.
     * @param price Price of the Part.
     * @param stock Inventory of the Part.
     * @param min Minimum quantity of Parts.
     * @param max Maximum quantity of Parts.
     * @param machineId Machine ID of Part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Getter for Machine Id.
     * @return machineId machine Id of the part
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Setter for Machine Id.
     * @param machineId Machine ID of the part.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
