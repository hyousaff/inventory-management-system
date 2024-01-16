package javaproject.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Hamza Yousaf
 */

public class Inventory {
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();



    private static int ProductIDCount = 0;
    private static int PartIDCount = 0;



    public static ObservableList<Product> GetProductsArray() {
        return products;
    }
    public static int GetProductIDCount() {
        return ++ProductIDCount;
    }
    public static void AddProduct(Product newProduct) {
        products.add(newProduct);
    }


    public static ObservableList<Part> getPartsArray() {
        return allParts;
    }
    public static int getPartIDCount() {
        return ++PartIDCount;
    }
    public static void AddPart(Part newPart) {
        allParts.add(newPart);
    }



                        // PRODUCT SECTION

    //Search Product by ID
    public static Product lookupProduct(int id) {
        Product productFound = null;
        for (Product product : products) {
            if (product.getId() == id) {
                productFound = product;
            }
        }
        return productFound;
    }


    //Search Product By Name
    public static ObservableList<Product> lookupProduct(String name) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        for (Product product : products) {
            if (product.getName().equals(name)) {
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    // Update Product
    public static void UpdateProduct(int index, Product product) {

        products.set(index, product);
    }

    // Remove Product
    public static boolean RemoveProduct (Product product) {
        if (products.contains(product)) {
            products.remove(product);
            return true;
        }
        else {
            return false;
        }
    }



                        // PART SECTION


    //Search part by ID
    public static Part lookupPart(int id) {
        Part partFound = null;
        for (Part part : allParts) {
            if (part.getId() == id) {
                partFound = part;
            }
        }
        return partFound;
    }

    //Search Part by Name
    public static ObservableList<Part> lookupPart(String name) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().equals(name)) {
                partsFound.add(part);
            }
        }
        return partsFound;
    }

    // Update Part
    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }


    //Remove Part
    public static boolean RemovePart(Part part) {
        if (allParts.contains(part)) {
            allParts.remove(part);
            return true;
        }
        else {
            return false;
        }
    }


}