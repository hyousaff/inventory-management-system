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
 * FXML Parts Form Controller class
 *
 * <p>
 *  RUNTIME ERROR: Error during saving a part and product
 *  Null Value error. To avoid passing Null Values in the controller
 *  Alerts were created and Try/Catch method was implemented.
 * </p>
 *
 * @author Hamza Yousaf
 *
 *<p>
 * this controller class is made for the add part screen.
 *</p>
 *
 */

public class AddPartFormController implements Initializable {

    @FXML
    private Label partIdNameLabel;

    @FXML
    private RadioButton inHousepressed;

    @FXML
    private RadioButton outsourcedPressed;

    @FXML
    private TextField partName;

    @FXML
    private TextField partInventory;

    @FXML
    private TextField partPrice;

    @FXML
    private TextField partMax;

    @FXML
    private TextField partIdName;

    @FXML
    private TextField partMin;

    // Alerts Created Separately for easy use
    private void Alert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");
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
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
        }
    }

    //Changing the text to Machine ID or Company Name depending on which button is pressed.
    @FXML
    void inHousepressed(ActionEvent event) {partIdNameLabel.setText("Machine ID");}

    @FXML
    void outsourcedPressed(ActionEvent event) {partIdNameLabel.setText("Company Name");}

    //Exit Screen confirmation
    @FXML
    void cancelAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to return back to Main Menu? Any unsaved data will be deleted.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) { MainScreen(event); }
    }


    /** RUNTIME ERROR: Error While Saving. User was able to input data without any kind of validation and even null values were being passed.
    to avoid and solve that Alerts were created and Try/Catch method was implemented. */

    //Saving and Adding new part
    @FXML
    void saveAction(ActionEvent event) throws IOException {

        try {
            int id = 0;
            String name = partName.getText();
            Double price = Double.parseDouble(partPrice.getText());
            int stock = Integer.parseInt(partInventory.getText());
            int max = Integer.parseInt(partMax.getText());
            int min = Integer.parseInt(partMin.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (name.isEmpty()) {
                Alert(5);
            } else {
                if (ValidMin(min, max) && ValidInventory(min, max, stock)) {

                    if (inHousepressed.isSelected()) {
                        try {
                            machineId = Integer.parseInt(partIdName.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            newInHousePart.setId(Inventory.getPartIDCount());
                            Inventory.AddPart(newInHousePart);
                            partAddSuccessful = true;
                        } catch (Exception e) {
                            Alert(2);
                        }
                    }

                    if (outsourcedPressed.isSelected()) {
                        companyName = partIdName.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                        newOutsourcedPart.setId(Inventory.getPartIDCount());
                        Inventory.AddPart(newOutsourcedPart);
                        partAddSuccessful = true;
                    }
                    if (partAddSuccessful) {
                        MainScreen(event);
                    }
                }
            }
        } catch(Exception e) {
            Alert(1);
        }
    }

    private void MainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("Mainform.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**RUNTIME ERROR: Data Validation Error. Saving was possible without any kind of validation */
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

        inHousepressed.setSelected(true);
    }
}
