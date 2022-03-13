package controller;

import javafx.collections.FXCollections;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;
import static model.Inventory.lookupPart;

/**
 * Controller for add product menu
 * @author Nathan Croson
 */
public class AddProductMenuController implements Initializable {




    Stage stage;
    Parent scene;

    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private int productId;
    private int numberOfCurrentParts = 0;

    @FXML
    private TableView<Part> allPartsTbl;

    @FXML
    private TableView<Part> associatedPartsTbl;

    @FXML
    private TableColumn<?, ?> associatedPriceCol;

    @FXML
    private TableColumn<?, ?> associatedInventoryLevelCol;

    @FXML
    private TableColumn<?, ?> associatedPartIDCol;

    @FXML
    private TableColumn<?, ?> associatedPartNameCol;

    @FXML
    private TableColumn<?, ?> partIDCol;

    @FXML
    private TableColumn<?, ?> partInventoryLevelCol;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TextField productIDTxt;

    @FXML
    private TextField productInvTxt;

    @FXML
    private TextField productMaxTxt;

    @FXML
    private TextField productMinTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productPriceTxt;

    @FXML
    private TextField productSearchTxt;

    /**
     * Button for adding Parts to Associated Part Table.
     * RUNTIME ERROR
     * parts were displaying doubles on the table.
     * add a counter for current number of parts.
     * @param event
     */
    @FXML
    public void OnActionAddPartToAssociatedPartTable(ActionEvent event) {
        Part part = allPartsTbl.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        numberOfCurrentParts++;

    }

    /**
     * Cancel button on add product menu. Returns to main menu without saving.
     * @param event
     * @throws IOException
     */
    @FXML
    public void OnActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Remove associated Part button on add product menu.
     * RUNTIME ERROR
     * Alert Message was not working
     * Added if statement to check for associated parts
     * @param event
     */
    @FXML
    public void OnActionRemoveAssociatedPart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove Associated Part?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Part SP = (Part) associatedPartsTbl.getSelectionModel().getSelectedItem();
            currentParts.remove(SP);
            associatedPartsTbl.setItems(currentParts);
            numberOfCurrentParts--;
        }
    }

    /**
     * Save button on Add Product Menu.
     * RUNTIME ERROR
     * Alert messages for logical errors and type errors was not working correctly.
     * Added try catch blocks.
     * @param event
     * @throws IOException
     */
    @FXML
    public void OnActionSaveProduct(ActionEvent event) throws IOException{
        boolean logicalError = false;
        boolean typeError = false;
        int inv = 0;
        double price = 0.0;
        int max = 0;
        int min = 0;
        String name = null;

        try {
            name = productNameTxt.getText();
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("name is not a string");
            alert.showAndWait();
            typeError = true;
        }
        try {
            inv = Integer.parseInt(productInvTxt.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("inv is not an integer");
            alert.showAndWait();
            typeError = true;
        }
        try {
            price = Double.parseDouble(productPriceTxt.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("price is not a double");
            alert.showAndWait();
            typeError = true;
        }
        try {
            max = Integer.parseInt(productMaxTxt.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("max is not an integer");
            alert.showAndWait();
            typeError = true;
        }
        try {
            min = Integer.parseInt(productMinTxt.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("min is not a integer");
            alert.showAndWait();
            typeError = true;
        }

        if (min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("min is greater than max");
            alert.showAndWait();
            logicalError = true;
        }

        if (inv > max || inv < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("inv is greater than max or less than min");
            alert.showAndWait();
            logicalError = true;
        }

        Product newProduct = new Product(0,"",0.0,0,0,0);

        newProduct.setId(productId);
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setStock(inv);
        newProduct.setMax(max);
        newProduct.setMin(min);

        for(int i = 0; i < numberOfCurrentParts; i++ )
        {
            newProduct.addAssociatedPart(currentParts.get(i));
        }

        if(!logicalError && !typeError) {
            Inventory.addProduct(newProduct);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * Search box for parts on add product menu.
     * RUNTIME ERROR
     * searchText was not getting text correctly
     * productSearchTxt field was mislabeled.
     * @param event
     */
    @FXML
    public void OnActionSearchProductPart(ActionEvent event) {
        String searchText = productSearchTxt.getText();

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

        allPartsTbl.setItems(partSearch);

        if(productSearchTxt == null){
            allPartsTbl.setItems(getAllParts());
        }

    }


    /**
     * Initialized data in add product menu.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productId = Inventory.getProductIdCount();
        String productIdAsText = Integer.toString(productId);
        productIDTxt.setText(productIdAsText);
        productIDTxt.setEditable(false);

        allPartsTbl.setItems(getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTbl.setItems(currentParts);

        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


}
