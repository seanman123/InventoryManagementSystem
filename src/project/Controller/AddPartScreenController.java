package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.Model.InhousePart;
import project.Model.Inventory;
import project.Model.OutsourcedPart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartScreenController implements Initializable {
    @FXML
    public RadioButton inHouse;

    @FXML
    public RadioButton outsourced;

    @FXML
    public TextField ID;

    @FXML
    public TextField companyName;

    @FXML
    public TextField partName;

    @FXML
    public TextField inventoryField;

    @FXML
    public TextField priceAndCost;

    @FXML
    public TextField maxField;

    @FXML
    public TextField minField;

    @FXML
    public Button Save;

    @FXML
    public Button Exit;

    @FXML
    public ToggleGroup group1;

    @FXML
    public Label companyNameLabel;

    @FXML
    public Label machineIDLabel;

    @FXML
    public TextField machineIDText;

    private int partID;


    @FXML
    public void inHouseRadioButtonHandler(ActionEvent actionEvent) {
        companyName.setVisible(false);
        companyNameLabel.setVisible(false);
        machineIDLabel.setVisible(true);
        machineIDText.setVisible(true);
    }

    @FXML
    public void outsourcedRadioButtonHandler(ActionEvent actionEvent) {
        companyName.setVisible(true);
        companyNameLabel.setVisible(true);
        machineIDLabel.setVisible(false);
        machineIDText.setVisible(false);

    }

    @FXML
    public void saveButtonHandler(ActionEvent actionEvent) throws IOException {

        String part = partName.getText();
        String inv = inventoryField.getText();
        String min = minField.getText();
        String max = maxField.getText();
        String price = priceAndCost.getText();

        try {

            String errorMessage;
            errorMessage = Inventory.getFieldValidation(part, Integer.parseInt(inv), Double.parseDouble(price), Integer.parseInt(min), Integer.parseInt(max));

            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Part Addition Warning");
                alert.setHeaderText("The part was NOT added!");
                alert.setContentText(errorMessage);
                alert.showAndWait();

            } else {

                if (inHouse.isSelected()) {
                    String machineID = machineIDText.getText();
                    Inventory.addPart(new InhousePart(partID, part, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(machineID)));

                }
                if (outsourced.isSelected()) {
                    String companyNameText = companyName.getText();
                    Inventory.addPart(new OutsourcedPart(partID, part, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max), companyNameText));
                }

                loadScreen(Save, "/project/View/mainScreen.fxml");


            }

        }

        catch(NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error: Improper fields");
                alert.setContentText("Form contains invalid or empty fields.");
                alert.showAndWait();
            }


    }


    @FXML
    public void exitButtonHandler(ActionEvent actionEvent) throws IOException {

        Inventory.decrementPartIDNumber();
        loadScreen(Exit, "/project/View/mainScreen.fxml");
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
        partID = Inventory.getPartIDNumber();
        ID.setText("AUTO GEN: " + partID);
    }
}
