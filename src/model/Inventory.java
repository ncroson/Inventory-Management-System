package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is the Model for the Inventory.
 * @author Nathan Croson
 */
public class Inventory {

    public static int partIdCount = 3;
    public static int productIdCount = 3;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Adds a new Part.
     * @param newPart New Part to be added.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new Product.
     * @param newProduct New Product to be added.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Looks Up Part Id.
     * @param id Id of part
     * @return index of Part.
     */
    public static Part lookupPart(int id)
    {
      if (!allParts.isEmpty()) {
          for (int i = 0; i < allParts.size(); i++){
              if (allParts.get(i).getId() == id)
                  return allParts.get(i);
          }
      }
      return null;
    }

    /**
     * Looks up Product Id.
     * @param productId Id of Product
     * @return index of Product
     */
    public static Product lookupProduct(int productId)
    {
        if (!allProducts.isEmpty()) {
          for (int i = 0; i < allProducts.size(); i++){
              if (allProducts.get(i).getId() == productId)
                  return allProducts.get(i);
          }
      }
      return null;
    }

    /**
     * Looks up Name of Part.
     * @param name Name of Part
     * @return parts name
     */
    public static ObservableList<Part> lookupPart(String name) {
        ObservableList<Part> partialNames = FXCollections.observableArrayList();

        ObservableList<Part> fullNames = Inventory.getAllParts();
        for(Part pn : fullNames){
            if(pn.getName().contains(name)){
                partialNames.add(pn);
            }
        }
        return partialNames;
    }

    /**
     * Looks up Name of Product.
     * @param name name of product.
     * @return name of product.
     */
    public static ObservableList<Product> lookupProduct(String name) {

        ObservableList<Product> partialNames = FXCollections.observableArrayList();

        ObservableList<Product> fullNames = Inventory.getAllProducts();
        for (Product pn : fullNames) {
            if (pn.getName().contains(name)) {
                partialNames.add(pn);
            }
        }
        return partialNames;

    }

    /**
     * Getter for Parts Id count.
     * @return partIdCount number of Part Id
     */
    public static int getPartIdCount(){
        partIdCount++;
        return partIdCount;
    }

    /**
     * Getter for Products Id count.
     * @return productIdCount Number of products Ids
     */
    public static int getProductIdCount(){
        productIdCount++;
        return productIdCount;
    }

    /**
     * Updates the selected Part.
     * @param index index of selected part
     * @param selectedPart the part that has been selected
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);

    }

    /**
     * Updates the selected Product.
     * @param index index of selected Product
     * @param newProduct new Product to be created
     */
    public static void updateProduct(int index, Product newProduct) {

        allProducts.set(index, newProduct);

    }

    /**
     * Deletes part.
     * @param selectedPart part that has been selected
     * @return the part that has been removed.
     */
    public static boolean deletePart(Part selectedPart)
    {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes product.
     * @param selectedProduct product that has been selected.
     * @return the product that has been removed.
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Getter for parts observable list.
     * @return allParts in observable list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Getter for products observable list.
     * @return allProducts in observalbe list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


    static {
        addTestData();
    }

    /**
     * Adds test data.
     */
    public static void addTestData() {

        InHouse part1 = new InHouse(1, "wheel", 10.99, 5, 1, 10, 999);
        Inventory.addPart(part1);
        InHouse part2 = new InHouse(2, "seat", 5.50, 3, 2, 8, 1200);
        Inventory.addPart(part2);
        Outsourced part3 = new Outsourced(3, "handlebar", 6.55, 7, 4, 12, "Acme");
        Inventory.addPart(part3);

        Product product1 = new Product(1, "bmx bike", 100.99, 3, 1, 10);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part3);
        Inventory.addProduct(product1);
        Product product2 = new Product(2, "mountain bike", 200.25, 5, 2, 8);
        product2.addAssociatedPart(part1);
        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part2);
        Inventory.addProduct(product2);
        Product product3 = new Product(3, "fast bike", 333.33, 6, 4, 11);
        Inventory.addProduct(product3);


    }


}