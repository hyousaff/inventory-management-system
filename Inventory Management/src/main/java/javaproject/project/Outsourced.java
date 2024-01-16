package javaproject.project;

/**
 *
 * @author Hamza Yousaf
 */
public class Outsourced extends Part {
    private String companyName;

    //Constructor
    public Outsourced(int id,
                      String name,
                      double price,
                      int stock,
                      int min,
                      int max,
                      String CName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = CName;
    }

    //Getter
    public String getCompanyName() {
        return companyName;
    }

    //Setter
    public void setCompanyName(String Name) {
        this.companyName = Name;
    }
}
