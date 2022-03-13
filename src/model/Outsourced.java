package model;

/**
 * description of class represents
 * This Class creates the Model for Outsourced Part.
 * @author nathan croson
 */
public class Outsourced extends Part{

    private String companyName;

    /**
     * description of method
     * Constructor for OutSourced Class
     * @param id Part ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Part Inventory
     * @param min Part minimum
     * @param max Part Max
     * @param companyName Part Company Name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Gets Company Name.
     * @return companyName Name of company
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Getter for Company Name.
     * @param companyName Name of the parts Company
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
