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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.MainMenuController.indexOfModifyProduct;
import static model.Inventory.*;
import static model.Inventory.lookupPart;

/**
 * Controller for modify product menu
 * @author Nathan Croson
 */
public class ModifyProductMenuController implements Initializable {


    Stage stage;
    Parent scene;
    private int productId;
    int productIndex = indexOfModifyProduct();
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private Product productToModify;

    @FXML
    public TableView<Part> allPartsTbl;

    @FXML
    public TableView<Part> associatedPartsTbl;
    @FXML
    private TableColumn<?, ?> associatedInventoryLevelCol;

    @FXML
    private TableColumn<?, ?> associatedPartIDCol;

    @FXML
    private TableColumn<?, ?> associatedPartNameCol;

    @FXML
    private TableColumn<?, ?> associatedPriceCol;

    @FXML
    private TableColumn<?, ?> partIDCol;

    @FXML
    private TableColumn<?, ?> partInventoryLevelCol;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TextField partSearchTxt;

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

    /**
     * Button to add associated parts.
     * @param event
     */
    @FXML
    public void OnActionAddPartToAssociatedPartTable(ActionEvent event) {
        Part part = allPartsTbl.getSelectionModel().getSelectedItem();

        productToModify.addAssociatedPart(part);
        associatedPartsTbl.setItems(currentParts);



    }

    /**
     * Cancel button. returns to main menu without saving.
     * RUNTIME ERROR
     * Screens were not changing
     * added throws IOException
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
     * Remove associated part button
     * @param event
     */
    @FXML
    public void OnActionRemoveAssociatedPart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove Associated Part?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){

            Part SP = (Part) associatedPartsTbl.getSelectionModel().getSelectedItem();

            productToModify.deleteAssociatedPart(SP);
            associatedPartsTbl.setItems(currentParts);
        }

    }

    /**
     * Save Modified Product button
     * RUNTIME ERROR
     * alert messages were not displaying
     * added try catch blocks
     * @param event
     * @throws IOException
     */
    @FXML
    public void OnActionSaveModifiedProduct(ActionEvent event) throws IOException{

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

        if(!logicalError && !typeError) {
            productToModify.setId(productId);
            productToModify.setName(name);
            productToModify.setPrice(price);
            productToModify.setStock(inv);
            productToModify.setMax(max);
            productToModify.setMin(min);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * search field for looking up parts
     * @param event
     */
    @FXML
    public void OnActionSearchPart(ActionEvent event) {

        String searchText = partSearchTxt.getText();

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

        if(partSearchTxt == null){
            allPartsTbl.setItems(getAllParts());
        }


    }

    /**
     * Initializes product data in modify product menu
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        productToModify = getAllProducts().get(productIndex);
        productId = getAllProducts().get(productIndex).getId();
        productIDTxt.setText(Integer.toString(productId));
        productIDTxt.setEditable(false);
        productNameTxt.setText(productToModify.getName());
        productInvTxt.setText(Integer.toString(productToModify.getStock()));
        productPriceTxt.setText((Double.toString(productToModify.getPrice())));
        productMinTxt.setText(Integer.toString(productToModify.getMin()));
        productMaxTxt.setText(Integer.toString(productToModify.getMax()));



        allPartsTbl.setItems(getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        currentParts = productToModify.getAllAssociatedParts();
        associatedPartsTbl.setItems(currentParts);

        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
