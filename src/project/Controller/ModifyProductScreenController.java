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

import static project.Controller.MainScreenController.productToModifyIndex;

public class ModifyProductScreenController implements Initializable {
    @FXML
    public TextField ID;

    @FXML
    public TextField partName;

    @FXML
    public TextField Inv;

    @FXML
    public TextField priceAndCost;

    @FXML
    public TextField Max;

    @FXML
    public TextField Min;

    @FXML
    public Button Save;

    @FXML
    public Button Exit;

    @FXML
    public Button Search;

    @FXML
    public TextField searchText;

    @FXML
    public TableView<Part>Table1;

    @FXML
    public TableColumn partID1;

    @FXML
    public TableColumn partName1;

    @FXML
    public TableColumn inventoryLevel1;

    @FXML
    public TableColumn pricePerUnit1;

    @FXML
    public TableView<Part> Table2;

    @FXML
    public TableColumn partID2;

    @FXML
    public TableColumn partName2;

    @FXML
    public TableColumn inventoryLevel2;

    @FXML
    public TableColumn pricePerUnit2;

    @FXML
    public Button Add;

    @FXML
    public Button Delete;

    int productIndex = productToModifyIndex();

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private Product productToLoad;

    @FXML
    public void saveButtonHandler(ActionEvent actionEvent) throws IOException {
        String id = ID.getText();
        String part = partName.getText();
        String inv = Inv.getText();
        String min = Min.getText();
        String max = Max.getText();
        String price = priceAndCost.getText();

        try {

            String errorMessage;
            errorMessage = Inventory.getFieldValidation(part, Integer.parseInt(inv), Double.parseDouble(price), Integer.parseInt(min), Integer.parseInt(max));

            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Product Addition Warning");
                alert.setHeaderText("The product was NOT added!");
                alert.setContentText(errorMessage);
                alert.showAndWait();

            } else if (associatedParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Product Addition Warning");
                alert.setHeaderText("You must add an associated part for this product");
                alert.setContentText(errorMessage);
                alert.showAndWait();

            } else {

                Product product = new Product(Integer.parseInt(id), part, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max));

                for(Part p : associatedParts) {
                    product.addAssociatedPart(p);
                }

                Inventory.updateProduct(productIndex, product);

                loadScreen(Save, "/project/View/mainScreen.fxml");

            }

        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error: Improper fields");
            alert.setContentText("Form contains invalid or empty fields.");
            alert.showAndWait();
        }
    }

    @FXML
    public void exitButtonHandler(ActionEvent actionEvent) throws IOException {

        loadScreen(Exit, "/project/View/mainScreen.fxml");

    }

    @FXML
    public void searchHandler(ActionEvent actionEvent) {
        String searchID = searchText.getText();
        ObservableList<Part> partSearch = FXCollections.observableArrayList();

        if(searchID.equals("")) {
            Table1.setItems(project.Model.Inventory.getAllParts());
        } else {
            try {
                Part lookupPart = project.Model.Inventory.lookupPart(Integer.parseInt(searchID));

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
    public void addHandler(ActionEvent actionEvent) {
        Part part = Table1.getSelectionModel().getSelectedItem();

        if (associatedParts.contains(part)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error: Part already added");
            alert.setContentText("This part is already associated with this product.");
            alert.showAndWait();
        } else {
            associatedParts.add(part);
        }


        Table2.setItems(associatedParts);
    }

    @FXML
    public void deleteHandler(ActionEvent actionEvent) {
        Part part = Table2.getSelectionModel().getSelectedItem();
        associatedParts.remove(part);
        Table2.setItems(associatedParts);
    }

    public void getProduct(Product product) {
        productToLoad = product;
        ID.setText(String.valueOf(productToLoad.getId()));
        partName.setText(String.valueOf(productToLoad.getName()));
        Inv.setText(String.valueOf(productToLoad.getStock()));
        priceAndCost.setText(String.valueOf(productToLoad.getPrice()));
        Max.setText(String.valueOf(productToLoad.getMax()));
        Min.setText(String.valueOf(productToLoad.getMin()));
        Table2.setItems(productToLoad.getAssociatedParts());

        for(Part part1 : productToLoad.getAssociatedParts()) {
            associatedParts.add(part1);
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
    public void initialize(URL location, ResourceBundle resources) {

        partID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevel1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnit1.setCellValueFactory(new PropertyValueFactory<>("price"));

        partID2.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevel2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnit2.setCellValueFactory(new PropertyValueFactory<>("price"));


        Table1.setItems(Inventory.getAllParts());
    }


}
