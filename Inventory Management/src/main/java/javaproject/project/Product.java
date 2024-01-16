package javaproject.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Hamza Yousaf
 */
public class Product {
    private ObservableList<Part> LinkedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private int stock;
    private double price;
    private int max;
    private int min;




    //Constructor
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.max = max;
        this.min = min;

    }

    //Getter
    public int getId() {return id;}

    //Setter
    public void setId(int id) {this.id = id;}


    //Getter
    public String getName() {return name;}

    //Setter
    public void setName(String name) {this.name = name;}


    //Getter
    public int getStock() {return stock;}

    //Setter
    public void setStock(int stock) {this.stock = stock;}



    //Getter
    public double getPrice() {return price;}

    //Setter
    public void setPrice(double price) {this.price = price;}


    //Getter
    public int getMax() {return max;}

    //Setter
    public void setMax(int max) {this.max = max;}


    //Getter
    public int getMin() {return min;}

    //Setter
    public void setMin(int min) {this.min = min;}







    //Add Linked Part
    public void AddLinkedPart(Part part) {
        LinkedParts.add(part);
    }


    //Remove Linked Part
    public boolean RemoveLinkedPart(Part selectedAssociatedPart) {
        if (LinkedParts.contains(selectedAssociatedPart)) {
            LinkedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }


    //Get All Linked Parts
    public ObservableList<Part> getAllLinkedParts() {return LinkedParts;}
}