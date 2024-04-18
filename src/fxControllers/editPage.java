package fxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import numberPlateClass.numberPlate;
import numberPlateClass.userPlate;
import utils.DatabaseOp;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class editPage implements Initializable {


    public Button editDataBtnID;
    public TextField nameTextField;

    public TextField costTextField;
    public Button goBackToMainPageID;
    public TextField dateTextField;


    public TextField numInfo;
    public TextField numUserInfo;

    @FXML
    public ChoiceBox<String> formChoiceBoxFieldI;
    public ChoiceBox<String> lookChoiceBoxFieldI;
    public ChoiceBox<String> typeChoiceBoxFieldI;

    private String[] formCompare1 = {"300150","250150","145120"};
    private String[] form1 = {"Dydis 300x150","Dydis 250x150","Dydis 145x120"};
    private String[] look1 = {"EU","LT"};

    ObservableList<String> typeCompare1 = FXCollections.observableArrayList("Bendr_Naud","Vardiniai","Taksi","Isskirtin","Istoriniai","Diplomat", "Laik_Prek", "Laik_Tranz","Elektromob");
    ObservableList<String> type1 = FXCollections.observableArrayList("Bendrinio naudojimo numeriai","Vardiniai numeriai","Taksi numeriai","Isskirtiniai numeriai","Istoriniai numeriai","Diplomatiniai numeriai", "Laikinos prekybos numeriai", "Laikinos tranzakcijos numeriai","Elektromobiliu numeriai");


    public void editDataBtn(numberPlate selectedNumberPlate) {
        System.out.printf(String.valueOf(selectedNumberPlate.getNum_pav()));

        String num_num = selectedNumberPlate.getNum_pav();



        nameTextField.setText(selectedNumberPlate.getNum_pav());
        costTextField.setText(selectedNumberPlate.getNum_kaina().toString());
        //formTextField.setText(selectedNumberPlate.getNum_forma());

        dateTextField.setText(String.valueOf(selectedNumberPlate.getNum_data()));
        //typeTextField.setText(selectedNumberPlate.getNum_tipas());
        //lookTextField.setText(selectedNumberPlate.getNum_isvaizd());
        String typeTrim = selectedNumberPlate.getNum_tipas().trim();
        for (int i = 0; i < 9; i++) {
            if (typeTrim.equals(typeCompare1.get(i))) {
                typeChoiceBoxFieldI.setValue(type1.get(i));
                //System.out.println("lol");
            }
            //System.out.println(selectedNumberPlate.getNum_tipas().toString());
            //System.out.println(typeCompare1.get(i));
        }
        String formTrim = selectedNumberPlate.getNum_forma().trim();
        for (int i = 0; i < 3; i++) {
            if (formTrim.equals(formCompare1[i])) {
                formChoiceBoxFieldI.setValue(form1[i]);
            }
        }
        String lookTrim = selectedNumberPlate.getNum_isvaizd().trim();
        for (int i = 0; i < 2; i++) {
            if (lookTrim.equals(look1[i])) {
                lookChoiceBoxFieldI.setValue(look1[i]);
            }
        }


        nameTextField.setEditable(false);
        dateTextField.setEditable(false);
        nameTextField.setStyle("-fx-background-color: gray;-fx-text-inner-color: white;");
        dateTextField.setStyle("-fx-background-color: gray;-fx-text-inner-color: white;");

        numInfo.setEditable(false);
        numUserInfo.setEditable(false);
        numInfo.setStyle("-fx-background-color: gray;-fx-text-inner-color: white;");
        numUserInfo.setStyle("-fx-background-color: gray;-fx-text-inner-color: white;");

        numInfo.setText(selectedNumberPlate.getNum_pav());

        String queryUSER = "SELECT SAV.NUM_PAV,NUM.NUM_PAV, D_SAV_VARD FROM SAVININKAS AS SAV, NUMERIS AS NUM WHERE NUM.NUM_PAV = ? AND NUM.NUM_PAV = SAV.NUM_PAV\n";
        try{
            Connection connectDB = new DatabaseOp().connectToDb();
            // create prepared statement for SQL query
            PreparedStatement statement = connectDB.prepareStatement(queryUSER);
            statement.setString(1, num_num);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numUserInfo.setText(resultSet.getString("D_SAV_VARD"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*if(userPlate.){

        }*/





    }

    public void goBackToMainPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(mainPage.class.getResource("../View/main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) goBackToMainPageID.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void editDataBtn(ActionEvent event) {
        String name = nameTextField.getText();
        String cost = costTextField.getText();
        //String form = formTextField.getText();

        Timestamp date = Timestamp.valueOf(dateTextField.getText());
        //String type = typeTextField.getText();
        //String look = lookTextField.getText();
        String user = numUserInfo.getText();
        System.out.printf(name + cost + date);

        try {
            // Establish a connection to the database
            Connection connectDB = new DatabaseOp().connectToDb();
            // Create a SQL statement to update the data in the NUMERIS table
            String updateSql = "UPDATE NUMERIS SET NUM_PATALP_DATA=?, NUM_KAINA=?,NUM_TIPAS=?,  NUM_FORMA=?, NUM_ISVAIZD=? WHERE NUM_PAV = '"+ name +"'";

            // Create a PreparedStatement object and set the values for the parameters
            PreparedStatement pstmt = connectDB.prepareStatement(updateSql);
            pstmt.setTimestamp(1, date);
            pstmt.setDouble(2, Double.parseDouble(cost));
           /* pstmt.setString(3, type);
            pstmt.setString(4, form);
            pstmt.setString(5, look);*/

            for (int i = 0; i < 9; i++) {
                if (typeChoiceBoxFieldI.getValue() == type1.get(i)) {
                    pstmt.setString(3, typeCompare1.get(i));
                }
            }

            for (int i = 0; i < 3; i++) {
                if (formChoiceBoxFieldI.getValue() == form1[i]) {
                    pstmt.setString(4, formCompare1[i]);
                }
            }
            pstmt.setString(5, lookChoiceBoxFieldI.getValue());


            // Execute the update statement
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println(rowsUpdated + " rows updated");

            // Close the PreparedStatement and Connection objects
            pstmt.close();

            connectDB.close();
            FXMLLoader fxmlLoader = new FXMLLoader(mainPage.class.getResource("../View/main-page.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) goBackToMainPageID.getScene().getWindow();
            stage.setTitle("Number Plat System");
            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        formChoiceBoxFieldI.getItems().addAll(form1);
        lookChoiceBoxFieldI.getItems().addAll(look1);
        typeChoiceBoxFieldI.getItems().addAll(type1);
    }
}
