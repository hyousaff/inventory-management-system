package javaproject.project;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Modify Part Form Controller class
 *
 * @author Hamza Yousaf
 *
 * this controller class is made for the Modify Part Screen.
 */
public class ModifyPartFormController implements Initializable {

    private Part selectedPart;
    @FXML
    private Label partIdNameLabel;
    @FXML
    private TextField PartId;
    @FXML
    private TextField PartName;
    @FXML
    private TextField PartIdName;
    @FXML
    private TextField PartInventory;
    @FXML
    private TextField PartPrice;
    @FXML
    private TextField PartMax;
    @FXML
    private TextField PartMin;

    @FXML
    private RadioButton inHousePressed;
    @FXML
    private RadioButton outsourcedPressed;


    // Setting up Alerts

    private void Alert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Machine ID");
                alert.setContentText("Machine ID may only contain numbers.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Minimum must have a value less than maximum.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Inventory");
                alert.setContentText("Inventory must be between Min and Max");
                alert.showAndWait();
                break;
        }
    }

    // Setting up pressed buttons and changes

    @FXML
    void InHousePressed(ActionEvent event) {
        partIdNameLabel.setText("Machine ID");
    }

    @FXML
    void OutSourcedPressed(ActionEvent event) {
        partIdNameLabel.setText("Company Name");
    }

    // Cancel and Exit Confirmation
    @FXML
    void CancelAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to return back to Main Menu? Any unsaved data will be deleted.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            MainScreen(event);
        }
    }

    // Saving and adding after Modify

    @FXML
    void SaveAction(ActionEvent event) throws IOException {

        try {
            int id = selectedPart.getId();
            String name = PartName.getText();
            Double price = Double.parseDouble(PartPrice.getText());
            int stock = Integer.parseInt(PartInventory.getText());
            int min = Integer.parseInt(PartMin.getText());
            int max = Integer.parseInt(PartMax.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (ValidMin(min, max) && ValidInventory(min, max, stock)) {

                if (inHousePressed.isSelected()) {
                    try {
                        machineId = Integer.parseInt(PartIdName.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.AddPart(newInHousePart);
                        partAddSuccessful = true;
                    } catch (Exception e) {
                        Alert(2);
                    }
                }

                if (outsourcedPressed.isSelected()) {
                    companyName = PartIdName.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.AddPart(newOutsourcedPart);
                    partAddSuccessful = true;
                }

                if (partAddSuccessful) {Inventory.RemovePart(selectedPart);
                    MainScreen(event);}
            }
        } catch(Exception e) {
            Alert(1);}
    }

    // return on main screen
    private void MainScreen(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Mainform.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    // Checking for input Validation
    private boolean ValidMin(int min, int max) {
        boolean isValid = true;
        if (min <= 0 || min >= max) {
            isValid = false;
            Alert(3);
        }

        return isValid;
    }

    private boolean ValidInventory(int min, int max, int stock) {
        boolean isValid = true;
        if (stock < min || stock > max) {
            isValid = false;
            Alert(4);
        }
        return isValid;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectedPart = MainformController.getPartModify();
        if (selectedPart instanceof InHouse) {
            inHousePressed.setSelected(true);
            partIdNameLabel.setText("Machine ID");
            PartIdName.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
        }
        if (selectedPart instanceof Outsourced){
            outsourcedPressed.setSelected(true);
            partIdNameLabel.setText("Company Name");
            PartIdName.setText(((Outsourced) selectedPart).getCompanyName());
        }
        PartId.setText(String.valueOf(selectedPart.getId()));
        PartName.setText(selectedPart.getName());
        PartInventory.setText(String.valueOf(selectedPart.getStock()));
        PartPrice.setText(String.valueOf(selectedPart.getPrice()));
        PartMax.setText(String.valueOf(selectedPart.getMax()));
        PartMin.setText(String.valueOf(selectedPart.getMin()));
    }
}