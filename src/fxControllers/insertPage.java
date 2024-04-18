package fxControllers;

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
import javafx.stage.Stage;
import numberPlateClass.numberPlate;
import utils.DatabaseOp;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class insertPage implements Initializable{

    public TextField textfieldNr1;
    public Button insertDataBtn;
    public Button goBackToMainPageID;
    public TextField nameTextField;
    public TextField formTextField;
    public TextField costTextField;
    @FXML
    public ChoiceBox<String> formChoiceBoxField;
    public ChoiceBox<String> lookChoiceBoxField;
    public ChoiceBox<String> typeChoiceBoxField;

    public TableColumn nameTableColInsert;
    public TableColumn costTableColInsert;
    public TableColumn formTableColInsert;
    public TextField sav_nameField;
    public TextField numInfo;
    public TextField userNumInfo;
    private String[] formCompare = {"300150","250150","145120"};
    private String[] form = {"Dydis 300x150","Dydis 250x150","Dydis 145x120"};
    private String[] look = {"EU","LT"};
    String bendrinio_naudojimo = "Bendr_Naud";
    //private String[] typeCompare = {"Bendr_Naud","Vardiniai","Taksi","Isskirtin","Istoriniai","Diplomat", "Laik_Prek", "Laik_Tranz","Elektromob"};
    //private String[] type = {"Bendrinio naudojimo numeriai","Vardiniai numeriai","Taksi numeriai","Isskirtiniai numeriai","Istoriniai numeriai","Diplomatiniai numeriai", "Laikinos prekybos numeriai", "Laikinos tranzakcijos numeriai","Elektromobiliu numeriai"};
    ObservableList<String> typeCompare = FXCollections.observableArrayList("Bendr_Naud","Vardiniai","Taksi","Isskirtin","Istoriniai","Diplomat", "Laik_Prek", "Laik_Tranz","Elektromob");
    ObservableList<String> type = FXCollections.observableArrayList("Bendrinio naudojimo numeriai","Vardiniai numeriai","Taksi numeriai","Isskirtiniai numeriai","Istoriniai numeriai","Diplomatiniai numeriai", "Laikinos prekybos numeriai", "Laikinos tranzakcijos numeriai","Elektromobiliu numeriai");
    ObservableList<String> regularOption = FXCollections.observableArrayList("Vardiniai numeriai", "Isskirtiniai numeriai");
    ObservableList<String> regularOption1 = FXCollections.observableArrayList("Bendrinio naudojimo numeriai");
    ObservableList<String> regularOption2 = FXCollections.observableArrayList("Taksi numeriai");
    ObservableList<String> regularOption3 = FXCollections.observableArrayList("Istoriniai numeriai");
    ObservableList<String> regularOption4 = FXCollections.observableArrayList("Diplomatiniai numeriai");
    ObservableList<String> regularOption5 = FXCollections.observableArrayList("Laikinos prekybos numeriai", "Laikinos tranzakcijos numeriai");
    ObservableList<String> regularOption6 = FXCollections.observableArrayList("Elektromobiliu numeriai");

    @FXML
    /*public void LoadInsertData(){
        String name = nameTextField.getText();
        int cost = Integer.parseInt(costTextField.getText());
        String form = formChoiceBoxField.getValue();
        Timestamp date =;

        // Create a new Person object
        numberPlate plate = new numberPlate(name, cost, form);

        // Add the person to the table view
        numberTableViewInsert.getItems().add(plate);
    }*/
    public void insertDataBtn(ActionEvent actionEvent) throws SQLException {

       /* if(nameTextField == null){

                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Error");
                alert1.setContentText("No name");
                Optional<ButtonType> result1 = alert1.showAndWait();

        }else{

        }*/
        if (nameTextField.getText().isEmpty() || costTextField.getText().isEmpty() || formChoiceBoxField.getSelectionModel().isEmpty() || lookChoiceBoxField.getSelectionModel().isEmpty() || typeChoiceBoxField.getSelectionModel().isEmpty() || sav_nameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Klaida");
            alert.setHeaderText("Duomenu ivestis klaidinga");
            alert.setContentText("Neturi but palikta tusciu langu");

            alert.showAndWait();
        } else {
            String sql = "INSERT INTO NUMERIS (NUM_PAV, NUM_KAINA, NUM_FORMA, NUM_ISVAIZD, NUM_PATALP_DATA, NUM_TIPAS) VALUES (?, ?, ?, ?, ?,?)";

            Timestamp currentDate = new Timestamp(System.currentTimeMillis());
            try {
                // get database connection
                Connection connectDB = new DatabaseOp().connectToDb();
                // create prepared statement for SQL query
                PreparedStatement statement = connectDB.prepareStatement(sql);

                // set values for the parameters in the SQL query

                statement.setString(1, nameTextField.getText());
                statement.setInt(2, Integer.parseInt(costTextField.getText()));
                for (int i = 0; i < 3; i++) {
                    if (formChoiceBoxField.getValue() == form[i]) {
                        statement.setString(3, formCompare[i]);
                    }
                }
                statement.setString(4, lookChoiceBoxField.getValue());
                statement.setTimestamp(5, currentDate);
                for (int i = 0; i < 9; i++) {
                    if (typeChoiceBoxField.getValue() == type.get(i)) {
                        statement.setString(6, typeCompare.get(i));
                    }
                }
                // statement.setString(6, typeChoiceBoxField.getValue());


                // execute SQL query
                statement.executeUpdate();

                // close database connection and statement
                statement.close();
                connectDB.close();

                //LoadInsertData();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            String sqlUser = "INSERT INTO SAVININKAS (D_SAV_ASMN_K, NUM_PAV, D_SAV_VARD, D_SAV_PAVARD, D_SAV_GIM_D) VALUES (?, ?, ?, ?, ?)";
            try {
                // get database connection
                Connection connectDB = new DatabaseOp().connectToDb();
                // create prepared statement for SQL query
                PreparedStatement statement = connectDB.prepareStatement(sqlUser);

                // set values for the parameters in the SQL query
                statement.setInt(1, Integer.parseInt(costTextField.getText()));
                statement.setString(2, nameTextField.getText());
                statement.setString(3, sav_nameField.getText());
                statement.setString(4, "Test");
                statement.setTimestamp(5, currentDate);


                // statement.setString(6, typeChoiceBoxField.getValue());

                // execute SQL query
                statement.executeUpdate();

                // close database connection and statement
                statement.close();
                connectDB.close();

                FXMLLoader fxmlLoader = new FXMLLoader(mainPage.class.getResource("../View/main-page.fxml"));
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) goBackToMainPageID.getScene().getWindow();
                stage.setTitle("Number Plat System");
                stage.setScene(scene);
                stage.show();
                //LoadInsertData();

            } catch (SQLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                throw new RuntimeException(e);

            }

        }
    }

    public void goBackToMainPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(mainPage.class.getResource("../View/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) goBackToMainPageID.getScene().getWindow();
        stage.setTitle("Number Plat System");
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("[a-zA-Z]{5}\\d{1}")) {
                typeChoiceBoxField.setItems(regularOption);
            }
            else if (newValue.matches("[a-zA-Z]{3}\\d{3}")) {
                typeChoiceBoxField.setItems(regularOption1);
            }
            else if (newValue.matches("[tT]\\d{5}")) {//NEVEIK
                typeChoiceBoxField.setItems(regularOption2);
            }
            else if (newValue.matches("[hH]\\d{5}")) {
                typeChoiceBoxField.setItems(regularOption3);
            }
            else if (newValue.matches("\\d+")) {
                typeChoiceBoxField.setItems(regularOption4);
            }
            else if (newValue.matches("\\d{4}[a-zA-Z]{2}")) {
                typeChoiceBoxField.setItems(regularOption5);
            }
            else if (newValue.matches("E[a-zA-Z]\\d{4}")) {
                typeChoiceBoxField.setItems(regularOption6);
            }
            else {
                typeChoiceBoxField.setItems(type);
            }
        });

        formChoiceBoxField.getItems().addAll(form);
        lookChoiceBoxField.getItems().addAll(look);
        typeChoiceBoxField.getItems().addAll(type);




    }
}
