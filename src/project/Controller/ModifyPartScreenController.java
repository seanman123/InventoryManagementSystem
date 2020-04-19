package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.Model.InhousePart;
import project.Model.Inventory;
import project.Model.OutsourcedPart;
import project.Model.Part;

import java.io.IOException;

import static project.Controller.MainScreenController.partToModifyIndex;

public class ModifyPartScreenController {
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
    public ToggleGroup group1;

    @FXML
    public Label companyNameLabel;

    @FXML
    public TextField machineID;

    @FXML
    public Label machineIDLabel;

    int partIndex = partToModifyIndex();


    @FXML
    public void inHouseRadioButtonHandler(ActionEvent actionEvent) {
        companyName.setVisible(false);
        companyNameLabel.setVisible(false);
        machineID.setVisible(true);
        machineIDLabel.setVisible(true);
    }

    @FXML
    public void outsourcedRadioButtonHandler(ActionEvent actionEvent) {
        companyName.setVisible(true);
        companyNameLabel.setVisible(true);
        machineID.setVisible(false);
        machineIDLabel.setVisible(false);
    }

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
                alert.setTitle("Part Addition Warning");
                alert.setHeaderText("The part was NOT added!");
                alert.setContentText(errorMessage);
                alert.showAndWait();

            } else {

                if (inHouse.isSelected()) {
                    String machine = machineID.getText();
                    InhousePart inhousePart = new InhousePart(Integer.parseInt(id), part, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(machine));

                    project.Model.Inventory.updatePart(partIndex, inhousePart);

                }
                if (outsourced.isSelected()) {
                    String companyNameText = companyName.getText();
                    OutsourcedPart outsourcedPart = new OutsourcedPart(Integer.parseInt(id), part, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max), companyNameText);

                    project.Model.Inventory.updatePart(partIndex, outsourcedPart);
                }

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

    public void getPart(Part part) {
        ID.setText(String.valueOf(part.getId()));
        partName.setText(String.valueOf(part.getName()));
        Inv.setText(String.valueOf(part.getStock()));
        priceAndCost.setText(String.valueOf(part.getPrice()));
        Max.setText(String.valueOf(part.getMax()));
        Min.setText(String.valueOf(part.getMin()));

        if(part instanceof InhousePart) {
            this.inHouse.setSelected(true);
            this.machineID.setText(String.valueOf(((InhousePart) part).getMachineID()));
        } else if (part instanceof OutsourcedPart) {
            companyName.setVisible(true);
            companyNameLabel.setVisible(true);
            machineID.setVisible(false);
            machineIDLabel.setVisible(false);
            this.outsourced.setSelected(true);
            this.companyName.setText(String.valueOf(((OutsourcedPart) part).getCompanyName()));
        }


    }
}
