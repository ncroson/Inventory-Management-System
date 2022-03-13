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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainMenuController.indexOfModifyPart;
import static model.Inventory.getAllParts;

/**
 * Controller for modify part menu
 * @author Nathan Croson
 */
public class ModifyPartMenuController implements Initializable {


    Stage stage;
    Parent scene;

    int partIndex = indexOfModifyPart();
    private int partID;
    boolean outsourced = false;
    boolean inHouse = false;

    @FXML
    public Label machineIdCompanyLbl;

    @FXML
    public RadioButton inHouseRbtn;

    @FXML
    public ToggleGroup inHouseOutsourcedTG;

    @FXML
    public RadioButton outSourcedRbtn;

    @FXML
    private TextField partCostTxt;

    @FXML
    private TextField partIDTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partMachineIDTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameTxt;


    /**
     * Cancel button on modify part menu. Returns to main menu without saving.
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
     * Save button on Modify Part screen.
     * RUNTIME ERROR
     * Alert Messages were not displaying
     * Added try catch blocks to fix
     * @param event
     * @throws IOException
     */
    @FXML
    public void OnActionSaveModifyPart(ActionEvent event) throws IOException {
        boolean logicalError = false;
        boolean typeError = false;
        String partName = null;
        int partInv = 0;
        double partPrice = 0.0;
        int partMax = 0;
        int partMin = 0;
        String partCompanyName = null;
        int partMachineId = 0;

        try {
            partName = partNameTxt.getText();
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("name is not a string");
            alert.showAndWait();
            typeError = true;
        }

        try {
            partInv = Integer.parseInt(partInvTxt.getText());
        } catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("inv is not an integer");
            alert.showAndWait();
            typeError = true;
        }
        try {
            partPrice = Double.parseDouble(partCostTxt.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("price is not a double");
            alert.showAndWait();
            typeError = true;
        }
        try {
            partMax = Integer.parseInt(partMaxTxt.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("max is not an integer");
            alert.showAndWait();
            typeError = true;
        }
        try {
            partMin = Integer.parseInt(partMinTxt.getText());
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("min is not an integer");
            alert.showAndWait();
            typeError = true;
        }
        if (inHouse) {


            InHouse inhousePart = new InHouse(0, "", 0.0, 0, 0, 0, 0);

            try{
                partMachineId = Integer.parseInt(partMachineIDTxt.getText());
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Machine ID is not an integer");
                alert.showAndWait();
                typeError = true;
            }

            inhousePart.setId(partID);
            inhousePart.setName(partName);
            inhousePart.setPrice(partPrice);
            inhousePart.setStock(partInv);
            inhousePart.setMin(partMin);
            inhousePart.setMax(partMax);
            inhousePart.setMachineId(partMachineId);


            int min = inhousePart.getMin();
            int max = inhousePart.getMax();
            int inv = inhousePart.getStock();

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
                Inventory.updatePart(partIndex, inhousePart);
            }
        }

        else if (outsourced) {
            Outsourced outsourcedPart = new Outsourced(0, "", 0.0, 0, 0, 0, "");

            try{
                partCompanyName = partMachineIDTxt.getText();
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Company Name is not a String");
                alert.showAndWait();
                typeError = true;
            }

            outsourcedPart.setId(partID);
            outsourcedPart.setName(partName);
            outsourcedPart.setPrice(partPrice);
            outsourcedPart.setStock(partInv);
            outsourcedPart.setMin(partMin);
            outsourcedPart.setMax(partMax);
            outsourcedPart.setCompanyName(partCompanyName);


            int min = outsourcedPart.getMin();
            int max = outsourcedPart.getMax();
            int inv = outsourcedPart.getStock();

            if (min > max){
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
                Inventory.updatePart(partIndex, outsourcedPart);
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
    public void OnActionSetOutsourcedRbtn(ActionEvent actionEvent) {
        machineIdCompanyLbl.setText("Company");
        outsourced = true;
        inHouse = false;

    }

    /**
     * Radio button for Inhouse part
     * RUNTIME ERROR
     * could not find labels for the radio button
     * added labels in scenebuilder
     * @param actionEvent
     */
    public void OnActionSetInHouseRbtn(ActionEvent actionEvent) {

        machineIdCompanyLbl.setText("Machine ID");
        outsourced = false;
        inHouse = true;
    }

    /**
     * Initializes part data in modify part menu.
     * RUNTIME ERROR
     * could edit data from radio buttons
     * used instanceof keyword
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb){
        Part part = getAllParts().get(partIndex);
        partID = getAllParts().get(partIndex).getId();
        partIDTxt.setText(Integer.toString(partID));
        partIDTxt.setEditable(false);
        partNameTxt.setText(part.getName());
        partInvTxt.setText(Integer.toString(part.getStock()));
        partCostTxt.setText(Double.toString(part.getPrice()));
        partMinTxt.setText(Integer.toString(part.getMin()));
        partMaxTxt.setText(Integer.toString(part.getMax()));
        if (part instanceof InHouse){
            partMachineIDTxt.setText(Integer.toString(((InHouse) part).getMachineId()));
            machineIdCompanyLbl.setText("Machine ID");
            inHouseRbtn.setSelected(true);
            outSourcedRbtn.setSelected(false);
            inHouse = true;
            outsourced = false;
        }
        else if (part instanceof Outsourced) {
            partMachineIDTxt.setText(((Outsourced) getAllParts().get(partIndex)).getCompanyName());
            machineIdCompanyLbl.setText("Company");
            outSourcedRbtn.setSelected(true);
            inHouseRbtn.setSelected(false);
            outsourced = true;
            inHouse = false;
        }
    }
}
