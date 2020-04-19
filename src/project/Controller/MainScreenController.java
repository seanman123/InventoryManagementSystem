package project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.Model.Inventory;
import project.Model.Part;
import project.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {


    @FXML
    public Button searchPart;
    @FXML
    public Button addPart;
    @FXML
    public Button addProduct;
    @FXML
    public Button modifyPart;
    @FXML
    public Button modifyProduct;
    @FXML
    public TextField searchTextPart;
    @FXML
    public TableView<Part> Table1;
    @FXML
    public TableColumn IDPart;
    @FXML
    public TableColumn namePart;
    @FXML
    public TableColumn inStockPart;
    @FXML
    public TableColumn pricePart;
    @FXML
    public Button searchProduct;
    @FXML
    public TextField searchTextProduct;
    @FXML
    public TableView<Product> Table2;
    @FXML
    public TableColumn IDProduct;
    @FXML
    public TableColumn nameProduct;
    @FXML
    public TableColumn inStockProduct;
    @FXML
    public TableColumn priceProduct;

    public static Part modifyParts;
    public static int modifyPartsIndex;
    public static Product modifyProducts;
    public static int modifyProductsIndex;

    public static int partToModifyIndex() {
        return modifyPartsIndex;
    }

    public static int productToModifyIndex() {
        return modifyProductsIndex;
    }


    @FXML
    public void searchHandlerPart(ActionEvent actionEvent) throws IOException {

        String searchID = searchTextPart.getText();
        ObservableList<Part> partSearch = FXCollections.observableArrayList();

        if(searchID.equals("")) {
            Table1.setItems(Inventory.getAllParts());
        } else {
            try {
                Part lookupPart = Inventory.lookupPart(Integer.parseInt(searchID));

                if (lookupPart == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setContentText("No ID was found with ID: " + searchID + ". Please confirm a valid ID.");
                Optional<ButtonType> result = alert.showAndWait();
                } else {
                    partSearch.add(lookupPart);
                    Table1.setItems(partSearch);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setContentText("Please input an ID.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }

    @FXML
    public void addHandlerPart(ActionEvent actionEvent) throws IOException {

        loadScreen(addPart, "/project/View/addPartScreen.fxml");

    }

    @FXML
    public void modifyHandlerPart(ActionEvent actionEvent) throws IOException{

        modifyParts = Table1.getSelectionModel().getSelectedItem();
        modifyPartsIndex = Inventory.getAllParts().indexOf(modifyParts);

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/project/View/modifyPartScreen.fxml"));
            loader.load();

            ModifyPartScreenController modifyPartsController = loader.getController();
            modifyPartsController.getPart(Table1.getSelectionModel().getSelectedItem());


            Stage stage;
            Parent root = loader.getRoot();
            stage = (Stage) modifyPart.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setContentText("Please select a Part to modify.");
                Optional<ButtonType> result = alert.showAndWait();
        }
    }


    @FXML
    public void deleteHandlerPart(ActionEvent actionEvent) {

        Part partToDelete = Table1.getSelectionModel().getSelectedItem();
        ObservableList<Part> newID = FXCollections.observableArrayList();

        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setContentText("Are you sure you want to delete " + partToDelete.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.removePart(partToDelete.getId());


                int newIDNumber = 0;

                for (Part part : Inventory.getAllParts()) {
                    newIDNumber++;
                    part.setId(newIDNumber);
                    newID.add(part);
                }

            }
        } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setContentText("Please select a Part to delete.");
                Optional<ButtonType> result = alert.showAndWait();

        }
    }

    @FXML
    public void searchHandlerProduct(ActionEvent actionEvent) {

        String searchID = searchTextProduct.getText();
        ObservableList<Product> productSearch = FXCollections.observableArrayList();

        if (searchID.equals("")) {
            Table2.setItems(Inventory.getAllProducts());
        } else {
            try {
                Product lookupProduct = Inventory.lookupProduct(Integer.parseInt(searchID));

                if (lookupProduct == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initModality(Modality.NONE);
                    alert.setContentText("No ID was found with ID: " + searchID + ". Please confirm a valid ID.");
                    Optional<ButtonType> result = alert.showAndWait();
                } else {
                    productSearch.add(lookupProduct);
                    Table2.setItems(productSearch);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setContentText("Please input an ID.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }

    @FXML
    public void addHandlerProduct(ActionEvent actionEvent) throws IOException {

        loadScreen(addProduct, "/project/View/addProductScreen.fxml");

    }

    @FXML
    public void modifyHandlerProduct(ActionEvent actionEvent) throws IOException {
        modifyProducts = Table2.getSelectionModel().getSelectedItem();
        modifyProductsIndex = Inventory.getAllProducts().indexOf(modifyProducts);

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/project/View/modifyProductScreen.fxml"));
            loader.load();

            ModifyProductScreenController modifyProductsController = loader.getController();
            modifyProductsController.getProduct(Table2.getSelectionModel().getSelectedItem());


            Stage stage;
            Parent root = loader.getRoot();
            stage = (Stage) modifyProduct.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setContentText("Please select a Product to modify.");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @FXML
    public void deleteHandlerProduct(ActionEvent actionEvent) {
        Product productId = Table2.getSelectionModel().getSelectedItem();
        ObservableList<Product> newID = FXCollections.observableArrayList();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setContentText("Are you sure you want to delete " + Table2.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Inventory.removeProduct(productId.getId());
            }

            int newIDNumber = 0;

            for (Product product : Inventory.getAllProducts()) {
                newIDNumber++;
                product.setId(newIDNumber);
                newID.add(product);
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setContentText("Please select a Product to delete.");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    @FXML
    public void exitHandler(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setContentText("Are you sure you want to exit? All changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

    public void loadScreen(Button button, String resource) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) button.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));

        root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Table1.setItems(Inventory.getAllParts());
        Table2.setItems(Inventory.getAllProducts());


        IDPart.setCellValueFactory(new PropertyValueFactory<>("id"));
        namePart.setCellValueFactory(new PropertyValueFactory<>("name"));
        inStockPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePart.setCellValueFactory(new PropertyValueFactory<>("price"));

        IDProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        inStockProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
