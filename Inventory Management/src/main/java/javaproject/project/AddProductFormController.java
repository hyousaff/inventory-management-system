package javaproject.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Products Form Controller class
 *
 *<p>
 *  RUNTIME ERROR: Error during saving a part and product
 *  Null Value error. To avoid passing Null Values in the controller
 *  Alerts were created and Try/Catch method was implemented.
 * </p>
 *
 * @author Hamza Yousaf
 *
 *<p>
 * this controller class is made for the add product screen.
 *</p>
 */


public class AddProductFormController implements Initializable {

    @FXML
    private TableView<Part> PartTableView;
    @FXML
    private TableColumn<Part, Integer> PartIdColumn;
    @FXML
    private TableColumn<Part, String> PartNameColumn;
    @FXML
    private TableColumn<Part, Integer> PartInvColumn;
    @FXML
    private TableColumn<Part, Double> PartPriceColumn;
    @FXML
    private TextField PartSearch;

    @FXML
    private TextField ProductId;
    @FXML
    private TextField ProductName;
    @FXML
    private TextField ProductInventory;
    @FXML
    private TextField ProductPrice;
    @FXML
    private TextField ProductMin;
    @FXML
    private TextField ProductMax;

    private ObservableList<Part> LinkedParts = FXCollections.observableArrayList();
    @FXML
    private TableView<Part> LinkedPartTableView;
    @FXML
    private TableColumn<Part, Integer> LinkedPartIdColumn;
    @FXML
    private TableColumn<Part, String> LinkedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> LinkedPartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> LinkedPartPriceColumn;

    // Setting up Alerts
    private void Alert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText("Form cannot contain blank or invalid values");
                alert.showAndWait();
                break;
            case 2:
                alertInfo.setTitle("Information");
                alertInfo.setHeaderText("Part not found");
                alertInfo.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min must be a number greater than 0 and less than Max");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Inventory");
                alert.setContentText("Inventory must be between or equal to Min and Max");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("Part not selected");
                alert.showAndWait();
                break;
            case 7:
                alert.setTitle("Error");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
                break;
        }
    }


    // Setting up action buttons.

    // cancel button command and action.
    @FXML
    void CancelButton(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to return back to Main Menu? Any unsaved data will be deleted.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
    }

    /** RUNTIME ERROR: Error While Saving. User was able to input data without any kind of validation and even null values were being passed.
     to avoid and solve that Alerts were created and Try/Catch method was implemented. */
    // Save button command and action.
    @FXML
    void SaveButton(ActionEvent event) throws IOException {

        try {
            int id = 0;
            String name = ProductName.getText();
            Double price = Double.parseDouble(ProductPrice.getText());
            int stock = Integer.parseInt(ProductInventory.getText());
            int min = Integer.parseInt(ProductMin.getText());
            int max = Integer.parseInt(ProductMax.getText());

            if (name.isEmpty()) {
                Alert(7);
            } else {
                if (ValidMin(min, max) && ValidInventory(min, max, stock)) {

                    Product newProduct = new Product(id, name, price, stock, min, max);

                    for (Part part : LinkedParts) {
                        newProduct.AddLinkedPart(part);
                    }

                    newProduct.setId(Inventory.GetProductIDCount());
                    Inventory.AddProduct(newProduct);
                    returnToMainScreen(event);
                }
            }
        } catch (Exception e){
            Alert(1);
        }
    }

    // add button command and action.
    @FXML
    void AddButton(ActionEvent event) {

        Part selectedPart = PartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert(5);
        } else {
            LinkedParts.add(selectedPart);
            LinkedPartTableView.setItems(LinkedParts);
        }
    }

    // Search button command and action.
    @FXML
    void SearchButtonParts(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getPartsArray();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = PartSearch.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        PartTableView.setItems(partsFound);

        if (partsFound.size() == 0) {
            Alert(1);
        }
    }
    // Pressing the search key.
    @FXML
    void PartSearchKey(KeyEvent event) {

        if (PartSearch.getText().isEmpty()) {
            PartTableView.setItems(Inventory.getPartsArray());
        }
    }


    // Remove button command and action.
    @FXML
    void RemoveButton(ActionEvent event) {

        Part selectedPart = LinkedPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert(5);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Associated Part will be removed. Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                LinkedParts.remove(selectedPart);
                LinkedPartTableView.setItems(LinkedParts);
            }
        }
    }




    // Returning to main screen.
    private void returnToMainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("Mainform.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


   // Checking for validation and inputs.

    /**RUNTIME ERROR: Data Validation Error. Saving was possible without any kind of validation */
    // checking for inventory valid.
    private boolean ValidInventory(int min, int max, int stock) {

        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            Alert(4);
        }

        return isValid;
    }
    // checking for minimum valid.
    private boolean ValidMin(int min, int max) {

        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            Alert(3);
        }

        return isValid;
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LinkedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        LinkedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        LinkedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        LinkedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        PartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartTableView.setItems(Inventory.getPartsArray());

    }
}
