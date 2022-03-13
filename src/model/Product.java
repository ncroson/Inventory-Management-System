package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates the model for the Product.
 * @author Nathan Croson
 */
public class Product {

    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for the product.
     * @param id Id of the product
     * @param name name of the product
     * @param price price of the product
     * @param stock inventory of the product
     * @param min minimum quantity of product
     * @param max maximum qunatity of product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Getter for product id.
     * @return id product id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for product id.
     * @param id product id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for product name.
     * @return name name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for product name.
     * @param name name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for product price.
     * @return price price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for product price.
     * @param price price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for product Inventory.
     * @return stock Inventory level of product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for Inventory level of product.
     * @param stock Inventory level of product.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for minimum quantity of products.
     * @return min minimum quantity of products.
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for minimum quantity of products.
     * @param min minimum quantity of prodcuts.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for maximum quantity of Products.
     * @return max maximum quantity of products
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for maximum quantity of Products.
     * @param max maximum quantity of Products.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds associated Part to Product
     * @param part associated part of product
     */

    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }

    /**
     * deletes associated part of product.
     * @param selectedAssociatedPart associated part that has been selected.
     * @return associated part to be removed.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Getter for list of associated parts.
     * @return associated parts observable list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;

    }

}
