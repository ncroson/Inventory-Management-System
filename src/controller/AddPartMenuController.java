package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the add parts menu.
 * @author Nathan Croson
 */
public class AddPartMenuController implements Initializable {



    Stage stage;
    Parent scene;

    @FXML
    public Label machineCompanyLbl;

    @FXML
    public RadioButton inHouseRbtn;

    @FXML
    public ToggleGroup inHouseOutSourcedTG;

    @FXML
    public RadioButton outSourcedRbtn;

    @FXML
    private TextField partIDTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    public TextField partMachineIDTxt;

    private int partId;

    /**
     * Switches screen to main menu.
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
     * Save button of add part screen.
     * RUNTIME ERROR
     * parts were not displaying logical errors and type errors correctly
     * added try catch blocks
     * booleans used to determine if errors are present
     *
     * RUNTIME ERROR
     * max and min were switched in inventory.addpart
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void OnActionSavePart(ActionEvent event) throws IOException{

        int id = partId;
        String name = null;
        int inv = 0;
        double price = 0.0;
        int max = 0;
        int min = 0;
        int machineId = 0;
        String companyName = null;

        boolean logicalError = false;
        boolean typeError = false;

        try {
            name = partNameTxt.getText();
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("name is not a string");
            alert.showAndWait();
            typeError = true;
        }

        try {
            inv = Integer.parseInt(partInvTxt.getText());
        } catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("inv is not an integer");
            alert.showAndWait();
            typeError = true;
        }
        try {
            price = Double.parseDouble(partPriceTxt.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("price is not a double");
            alert.showAndWait();
            typeError = true;
        }
        try {
            max = Integer.parseInt(partMaxTxt.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("max is not an integer");
            alert.showAndWait();
            typeError = true;
        }
        try {
            min = Integer.parseInt(partMinTxt.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("min is not an integer");
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

        if (inHouseRbtn.isSelected()) {
            try {
                machineId = Integer.parseInt(partMachineIDTxt.getText());

            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("machine id is not an integer");
                alert.showAndWait();
                typeError = true;

            }
            if(!logicalError && !typeError) {
                Inventory.addPart(new InHouse(id, name, price, inv, min, max, machineId));
            }

        }
        if (outSourcedRbtn.isSelected()) {
            try {
                companyName = partMachineIDTxt.getText();

            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("company name is not a string");
                alert.showAndWait();
                typeError = true;
            }
            if(!logicalError && !typeError) {
                Inventory.addPart(new Outsourced(id, name, price, inv, min, max, companyName));
            }

        }

        if(!logicalError && !typeError) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Radio button for outsourced part.
     * @param actionEvent
     */
    @FXML
    public void onActionSetOutsourcedRbtn(ActionEvent actionEvent) {
        machineCompanyLbl.setText("Company");
    }

    /**
     * Radio button for Inhouse part.
     * @param actionEvent
     */
    @FXML
    public void onActionSelectInHouseRbtn(ActionEvent actionEvent) {
        machineCompanyLbl.setText("Machine ID");
    }


    /**
     * Initializes data in add part menu.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partId = Inventory.getPartIdCount();
        String partIdAsText = Integer.toString(partId);
        partIDTxt.setText(partIdAsText);
        partIDTxt.setEditable(false);
    }
}
