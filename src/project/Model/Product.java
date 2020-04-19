package project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private  ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int Id;
    private String name;
    private Double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, Double price, int stock, int min, int max) {
        Id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void addAssociatedPart(Part part) {
            associatedParts.add(part);

    }


    public  ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }


}
