package project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static int partIDCount = 0;
    private static int productIDCount = 0;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static Part lookupPart(int partID) {

        for(Part parts : getAllParts()){
            if(parts.getId() == partID) {
                return parts;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productID) {

        for(Product products : getAllProducts()){
            if(products.getId() == productID) {
                return products;
            }
        }
        return null;
    }

    public static void updatePart(int index, Part part) {
       allParts.set(index, part);
    }

    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }

    public static boolean removePart(int id) {

        for(Part parts : getAllParts()){
            if(parts.getId() == id){
                partIDCount--;
                return getAllParts().remove(parts);
            }
        }

        return false;

    }


        public static boolean removeProduct(int id) {

        for(Product products : getAllProducts()){
            if(products.getId() == id){
                productIDCount--;
                return getAllProducts().remove(products);
            }
        }

        return false;
    }


    public static int getPartIDNumber() {
        partIDCount++;
        return partIDCount;
    }

    public static int decrementPartIDNumber() {
        partIDCount--;
        return partIDCount;
    }

    public static int getProductIDNumber() {
        productIDCount++;
        return productIDCount;
    }

    public static int decrementProductIDNumber() {
        productIDCount--;
        return productIDCount;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


    public static String getFieldValidation(String name, int stock, double price, int min, int max) {

        String errorMessage = new String();
        if (name.equals("")) {
            errorMessage = "Please provide a name.";
        }
        if (stock < 1) {
            errorMessage = "Inventory must be positive.";
        }
        if (price <= 0) {
            errorMessage = "Price must be positive.";
        }
        if (max < min) {
            errorMessage = "Min cannot be greater than Max.";
        }
        if (stock > max) {
            errorMessage = "Inventory must be less than or equal to Max.";
        }
        if (stock < min) {
            errorMessage = "Inventory must be greater than or equal to Min.";
        }

        return errorMessage;
    }

    }
