package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;

/**
 * Controller for main menu screen
 * @author Nathan Croson
 */
public class MainMenuController implements Initializable {


    Stage stage;
    Parent scene;

    @FXML
    private TableView<Product> productsTbl;

    @FXML
    private TableView<Part> partsTbl;


    @FXML
    private TableColumn<?, ?> partIDCol;

    @FXML
    private TableColumn<?, ?> partInventoryLevelCol;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TableColumn<?, ?> productIDCol;

    @FXML
    private TableColumn<?, ?> productInventoryLevelCol;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> productPriceCol;

    @FXML
    private TextField searchPartTxt;

    @FXML
    private TextField searchProductTxt;

    private static Part partToModify;
    private static int partToModifyIndex;
    private static Product productToModify;
    private static int productToModifyIndex;

    public static int indexOfModifyPart(){
        return partToModifyIndex;
    }
    public static int indexOfModifyProduct(){
        return productToModifyIndex;
    }

    /**
     * button to switch ot add part menu
     * RUNTIME ERROR
     * screens were not changing
     * added throws IOException
     * @param event
     * @throws IOException
     */
    @FXML
    public void OnActionAddPartMenu(ActionEvent event) throws IOException {
        System.out.println("part menu clicked");

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Button to switch to add product menu.
     * @param event
     * @throws IOException
     */
    @FXML
    public void OnActionAddProductMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * button to delete part from table.
     * @param event
     */
    @FXML
    public void OnActionDeletePart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete part?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {

            Part SP = (Part) partsTbl.getSelectionModel().getSelectedItem();
            Inventory.deletePart(SP);
            partsTbl.setItems(getAllParts());
        }
    }

    /**
     * button to delete product from table.
     * @param event
     */
    @FXML
    public void OnActionDeleteProduct(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete product?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {

            Product SP = (Product) productsTbl.getSelectionModel().getSelectedItem();

            ObservableList<Part> AP = SP.getAllAssociatedParts();
            if(AP.isEmpty()) {
                Inventory.deleteProduct(SP);
                productsTbl.setItems(getAllProducts());
            }
            else{
                Alert secondAlert = new Alert(Alert.AlertType.CONFIRMATION, "Selected Product has Associated Parts");

                Optional<ButtonType> secondResult = secondAlert.showAndWait();
            }
        }

    }

    /**
     * button to exit program
     * @param event
     */
    @FXML
    public void OnActionExitProgram(ActionEvent event) {
        System.exit(0);
    }

    /**
     * button to change to modify part menu
     * @param event
     * @throws IOException
     */
    @FXML
    public void OnActionModifyPartMenu(ActionEvent event) throws IOException {

        partToModify = partsTbl.getSelectionModel().getSelectedItem();
        partToModifyIndex = getAllParts().indexOf(partToModify);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * button to change ot modify product menu
     * @param event
     * @throws IOException
     */
    @FXML
    public void OnActionModifyProductMenu(ActionEvent event) throws IOException {
        productToModify = productsTbl.getSelectionModel().getSelectedItem();
        productToModifyIndex = getAllProducts().indexOf(productToModify);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * text field to search for part
     * RUNTIME ERROR
     * searchPartTxt was mislabeled
     * @param event
     */
    @FXML
    public void OnActionSearchPart(ActionEvent event) {

        String searchText = searchPartTxt.getText();

        ObservableList<Part> partSearch = lookupPart(searchText);

        if(partSearch.size() == 0) {
            try {
                int indexNumber = Integer.parseInt(searchText);
                Part indexPart = lookupPart(indexNumber);
                if (indexPart != null)
                    partSearch.add(indexPart);
            } catch (NumberFormatException e) {
                //ignore
            }
        }

        partsTbl.setItems(partSearch);

        if(searchPartTxt == null){
            partsTbl.setItems(getAllParts());
        }


    }

    /**
     * text field to search for product
     * @param event
     */
    @FXML
    public void OnActionSearchProduct(ActionEvent event) {

        String searchText = searchProductTxt.getText();

        ObservableList<Product> productSearch = lookupProduct(searchText);

        if(productSearch.size() == 0) {
            try {
                int indexNumber = Integer.parseInt(searchText);
                Product indexPart = lookupProduct(indexNumber);
                if (indexPart != null)
                    productSearch.add(indexPart);
            } catch (NumberFormatException e) {
                //ignore
            }
        }

        productsTbl.setItems(productSearch);

        if(searchPartTxt == null){
            productsTbl.setItems(getAllProducts());
        }

    }


    /**
     * Initializes part and product data into tables.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        partsTbl.setItems(getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTbl.setItems(getAllProducts());

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));




    }
}
