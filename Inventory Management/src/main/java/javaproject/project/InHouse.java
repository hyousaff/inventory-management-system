package javaproject.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 *
 * @author Hamza Yousaf
 */


public class InHouse extends Part {

    private int MachineID;

    //Constructor
    public InHouse(int id,
                   String name,
                   double price,
                   int stock,
                   int min,
                   int max,
                   int machineId)

    {
        super(id, name, price, stock, min, max);
        this.MachineID = machineId;
    }

    //Getter
    public int getMachineID() {
        return MachineID;
    }

    //Setter
    public void setMachineID(int machineID) {
        this.MachineID = machineID;
    }
}