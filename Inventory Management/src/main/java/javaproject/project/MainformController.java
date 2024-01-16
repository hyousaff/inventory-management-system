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
;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Main Form Controller class
 *
 * <p>
 *  RUNTIME ERROR: Null values able to pass in the controller, it was corrected by checking for null values
 *  and passing on an alert in case it happened.
 * </p>
 *
 * @author Hamza Yousaf
 *
 *<p>
 *this controller class is made for the Main screen.
 *</p>
 */

public class MainformController implements Initializable {

    private static Part PartModify;
    private static Product ProductModify;


    @FXML
    private TextField PartSearch;
    @FXML
    private TextField ProductSearch;


    @FXML
    private TableView<Part> PartTable;
    @FXML
    private TableColumn<Part, Integer> PartId;
    @FXML
    private TableColumn<Part, String> PartName;
    @FXML
    private TableColumn<Part, Double> PartPrice;
    @FXML
    private TableColumn<Part, Integer> PartInventory;



    @FXML
    private TableView<Product> ProductTable;
    @FXML
    private TableColumn<Product, Integer> ProductId;
    @FXML
    private TableColumn<Product, String> ProductName;
    @FXML
    private TableColumn<Product, Double> ProductPrice;
    @FXML
    private TableColumn<Product, Integer> ProductInventory;

    public static Part getPartModify() {
        return PartModify;
    }
    public static Product getProductModify() {
        return ProductModify;
    }

    //Exit program button setup
    @FXML
    void exitButtonAction(ActionEvent event) {

        System.exit(0);
    }

    // Creating Alerts
    private void Alert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Information");
                alert.setHeaderText("Part not found");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Product not found");
                alert.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Part not selected");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Product not selected");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Parts Associated");
                alertError.setContentText("All parts must be removed from product before deletion.");
                alertError.showAndWait();
                break;
        }
    }

                                                  // SEARCH SECTION
    // Parts Search Key
    @FXML
    void PartSearchButton(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getPartsArray();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = PartSearch.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }

        PartTable.setItems(partsFound);

        if (partsFound.size() == 0) {
            Alert(1);
        }
    }
    @FXML
    void PartSearchText(KeyEvent event) {

        if (PartSearch.getText().isEmpty()) {
            PartTable.setItems(Inventory.getPartsArray());
        }

    }

    // Product Search Key
    @FXML
    void ProductSearchButton(ActionEvent event) {

        ObservableList<Product> allProducts = Inventory.GetProductsArray();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = ProductSearch.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                productsFound.add(product);
            }
        }

        ProductTable.setItems(productsFound);

        if (productsFound.size() == 0) {
            Alert(2);
        }
    }
    @FXML
    void ProductSearchText(KeyEvent event) {

        if (ProductSearch.getText().isEmpty()) {
            ProductTable.setItems(Inventory.GetProductsArray());
        }
    }

                                                    // ADD SCENE SWITCH
    // Parts add Button Scene Switch
    @FXML
    void PartAdd(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("AddPartForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // Products Add Button Scene Switch
    @FXML
    void productAddAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

                                                // DELETE SECTION
    // Deleting part and deletion confirmation
    @FXML
    void PartDelete(ActionEvent event) {

        Part selectedPart = PartTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert(3);
        } else {Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.RemovePart(selectedPart);
            }
        }
    }

    //Deleting Product and deletion Confirmation
    @FXML
    void productDeleteAction(ActionEvent event) {
        Product selectedProduct = ProductTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert(4);
        } else {Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> assocParts = selectedProduct.getAllLinkedParts();
                if (assocParts.size() >= 1) {
                    Alert(5);
                } else {
                    Inventory.RemoveProduct(selectedProduct);
                }
            }
        }
    }


                                                    // MODIFY SECTION
    // Switch to part modify Scene

    /**RUNTIME ERROR: Null Values being passed to the controller. Corrected by checking for null */
    @FXML
    void PartModify(ActionEvent event) throws IOException {

        PartModify = PartTable.getSelectionModel().getSelectedItem();
        if (PartModify == null) {
            Alert(3);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("ModifyPartForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

   // Switch to product modify Scene
    /**RUNTIME ERROR: Data Validation Error. Saving was possible without any kind of validation */
    @FXML
    void productModifyAction(ActionEvent event) throws IOException {

        ProductModify = ProductTable.getSelectionModel().getSelectedItem();
        if (ProductModify == null) {
            Alert(4);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("ModifyProductForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Populate parts table view
        PartTable.setItems(Inventory.getPartsArray());
        PartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Populate products table view
        ProductTable.setItems(Inventory.GetProductsArray());
        ProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

