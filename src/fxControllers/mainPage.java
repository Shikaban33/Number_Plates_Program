package fxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import numberPlateClass.*;
import utils.DatabaseOp;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class mainPage implements Initializable{
    public TextField textfieldNr1;
    public TextField savininkasSearch;
    public TextField pavadinimasSearch;
    public DatePicker dateToSearch;
    public DatePicker dateFromSearch;
    public TextField kainaIkiSearch;
    public TextField kainaNuoSearch;
    public ChoiceBox formChoiceBoxSearch;
    private Stage stage;
    private Scene scene;
    private Parent parent;
    @FXML
    public Button goToEditPageBtnID;
    @FXML
    public Button deleteDataID;
    @FXML
    public Button goToInsertPageBtnID;
    @FXML
    private TableView<numberPlate> numberTableView;
    @FXML
    private TableColumn<numberPlate, String> nameTableCol;
    @FXML
    private TableColumn<numberPlate, Integer> costTableCol;
    @FXML
    private TableColumn<numberPlate, String> formTableCol;
    @FXML
    private TableColumn<numberPlate,String> typeTableCol;
    @FXML
    private TableColumn<numberPlate, String>  lookTableCol;
    @FXML
    private TableColumn<numberPlate,Timestamp>  dateTableCol;
    @FXML
    private TableColumn<numberPlate, String> userTableCol;

    private String[] formCompare2 = {"300150","250150","145120"};
    private String[] form2 = {"Dydis 300x150","Dydis 250x150","Dydis 145x120"};
    private String[] look2 = {"EU","LT"};

    ObservableList<String> typeCompare2 = FXCollections.observableArrayList("Bendr_Naud","Vardiniai","Taksi","Isskirtin","Istoriniai","Diplomat", "Laik_Prek", "Laik_Tranz","Elektromob");
    ObservableList<String> type2 = FXCollections.observableArrayList("Bendrinio naudojimo numeriai","Vardiniai numeriai","Taksi numeriai","Isskirtiniai numeriai","Istoriniai numeriai","Diplomatiniai numeriai", "Laikinos prekybos numeriai", "Laikinos tranzakcijos numeriai","Elektromobiliu numeriai");


    private ObservableList<numberPlate> numberPlateObservableList = FXCollections.observableArrayList();

    public void LoadData(){
        DatabaseOp conn = new DatabaseOp();
        Connection connectDB = conn.connectToDb();

        String numberPlateViewQuery = "SELECT SAV.NUM_PAV, SAV.D_SAV_VARD, NUM.NUM_PAV,NUM.NUM_PATALP_DATA, NUM.NUM_KAINA,NUM.NUM_TIPAS, NUM.NUM_FORMA,NUM.NUM_ISVAIZD FROM NUMERIS AS NUM, SAVININKAS AS SAV WHERE NUM.NUM_PAV = SAV.NUM_PAV";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(numberPlateViewQuery);

            while (queryOutput.next()){
                String queryNumberPlateName = queryOutput.getString("NUM_PAV");
                Integer queryNumberPlateCost = queryOutput.getInt("NUM_KAINA");
                String queryNumberPlateForm = queryOutput.getString("NUM_FORMA");

                Timestamp queryNumberPlateDate = queryOutput.getTimestamp("NUM_PATALP_DATA");
                String queryNumberPlateType = queryOutput.getString("NUM_TIPAS");
                String queryNumberPlateLook = queryOutput.getString("NUM_ISVAIZD");
                String queryNumberUser= queryOutput.getString("D_SAV_VARD");

                numberPlateObservableList.add(new numberPlate(queryNumberPlateName,queryNumberPlateDate,queryNumberPlateCost,queryNumberPlateType,queryNumberPlateForm,queryNumberPlateLook,queryNumberUser));

            }
            //numberPlateObservableList.forEach(numberPlate -> System.out.println(numberPlate));

            nameTableCol.setCellValueFactory(new PropertyValueFactory<>("num_pav"));
            costTableCol.setCellValueFactory(new PropertyValueFactory<>("num_kaina"));
            formTableCol.setCellValueFactory(new PropertyValueFactory<>("num_forma"));

            dateTableCol.setCellValueFactory(new PropertyValueFactory<>("num_data"));
            typeTableCol.setCellValueFactory(new PropertyValueFactory<>("num_tipas"));
            lookTableCol.setCellValueFactory(new PropertyValueFactory<>("num_isvaizd"));

            userTableCol.setCellValueFactory(new PropertyValueFactory<>("num_user_pav"));

            numberTableView.setItems(numberPlateObservableList);

            DatabaseOp.disconnectFromDb(connectDB, statement);
            FilteredList<numberPlate> filteredData = new FilteredList<>(numberPlateObservableList, b -> true);

            textfieldNr1.textProperty().addListener((observable, oldValue,newValue) ->{
                filteredData.setPredicate(numberPlate -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (numberPlate.getNum_pav().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if (numberPlate.getNum_kaina().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    }

                else if (numberPlate.getNum_user_pav().toString().indexOf(searchKeyword) > -1) {
                    return true;
                }
                else if (numberPlate.getNum_tipas().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    }
                    else {
                        return false;
                    }
                });
            });

            savininkasSearch.textProperty().addListener((observable, oldValue,newValue) ->{
                filteredData.setPredicate(numberPlate -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (numberPlate.getNum_user_pav().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else {
                        return false;
                    }
                });
            });

            pavadinimasSearch.textProperty().addListener((observable, oldValue,newValue) ->{
                filteredData.setPredicate(numberPlate -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (numberPlate.getNum_pav().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    else {
                        return false;
                    }
                });
            });

            dateFromSearch.valueProperty().addListener((observable, oldValue,newValue) ->{
                filteredData.setPredicate(numberPlate -> {
                    if(newValue == null){
                        return true;
                    }
                    String searchKeyword = String.valueOf(newValue);
                    LocalDate searchDate = newValue;
                    Timestamp searchTimestamp = Timestamp.valueOf(searchDate.atStartOfDay());
                    Timestamp num_pav_date = numberPlate.getNum_data();
                    return num_pav_date != null && num_pav_date.after(searchTimestamp);
                });
            });

            dateToSearch.valueProperty().addListener((observable, oldValue,newValue) ->{
                filteredData.setPredicate(numberPlate -> {
                    if(newValue == null){
                        return true;
                    }
                    else {
                        String searchKeyword = String.valueOf(newValue);
                        LocalDate searchDate = newValue;
                        Timestamp searchTimestamp = Timestamp.valueOf(searchDate.atStartOfDay());
                        Timestamp num_pav_date = numberPlate.getNum_data();
                        return num_pav_date != null && num_pav_date.before(searchTimestamp);
                    }

                });
            });

            kainaNuoSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(numberPlate -> {
                    if (newValue == null || newValue.isBlank()) {
                        return true;
                    }
                    String searchKeyword = newValue.trim().toLowerCase();
                    try {
                        double searchKaina = Double.parseDouble(searchKeyword);
                        double num_kaina = numberPlate.getNum_kaina();
                        return num_kaina >= searchKaina;
                    } catch (NumberFormatException e) {
                        return false; // return false if searchKeyword is not a valid number
                    }
                });
            });
            kainaIkiSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(numberPlate -> {
                    if (newValue == null || newValue.isBlank()) {
                        return true;
                    }
                    String searchKeyword = newValue.trim().toLowerCase();
                    try {
                        double searchKaina = Double.parseDouble(searchKeyword);
                        double num_kaina = numberPlate.getNum_kaina();
                        return num_kaina <= searchKaina;
                    } catch (NumberFormatException e) {
                        return false; // return false if searchKeyword is not a valid number
                    }
                });
            });


            SortedList<numberPlate> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(numberTableView.comparatorProperty());

            numberTableView.setItems(sortedData);

        }catch (SQLException e){
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadData();
        formChoiceBoxSearch.getItems().addAll(form2);
    }
    @FXML
    public void goToInsertPageBtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(insertPage.class.getResource("../View/insert-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) goToInsertPageBtnID.getScene().getWindow();
        stage.setTitle("Number Plate Edit");
        stage.setScene(scene);
        stage.show();
    }

    public void deleteData(ActionEvent actionEvent) throws SQLException {

        if (numberTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Klaida");
            alert1.setContentText("Numeriai nepasirinkti");
            Optional<ButtonType> result1 = alert1.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Šalinimas");
            alert.setHeaderText("Ar jūs įsitikinę, kad norite pašalinti numerius?");
            alert.setContentText("Numeriai bus pašalinti iš duomenų bazės");

            Optional<ButtonType> result = alert.showAndWait();



            if (result.get() == ButtonType.OK) {
                try (Connection connectDB = new DatabaseOp().connectToDb();) {
                    numberPlate selectedNumberPlate = numberTableView.getSelectionModel().getSelectedItem();
                    String sql = "DELETE FROM NUMERIS WHERE NUM_PAV = ?";
                    PreparedStatement statement = connectDB.prepareStatement(sql);
                    statement.setString(1, selectedNumberPlate.getNum_pav());
                    //numberPlateObservableList.forEach(numberPlate -> System.out.println(numberPlate));
                    statement.executeUpdate();

                    String sql1 = "DELETE FROM SAVININKAS WHERE NUM_PAV = ?";
                    PreparedStatement statement1 = connectDB.prepareStatement(sql1);
                    statement1.setString(1, selectedNumberPlate.getNum_pav());
                    //numberPlateObservableList.forEach(numberPlate -> System.out.println(numberPlate));
                    statement.executeUpdate();

                    DatabaseOp.disconnectFromDb(connectDB, statement);
                    //

                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setTitle("Error");
                    alert1.setHeaderText("Nepasirinktas");
                }
            } else {
                alert.close();
            }
            //numberPlateObservableList.forEach(numberPlate -> System.out.println(numberPlate));

            numberPlateObservableList.clear();
            LoadData();
    }

    }
    public void editItem(ActionEvent actionEvent) throws IOException {

       // book.setGenre(Genre.valueOf(bookGenreCB.getSelectionModel().getSelectedItem().toString()));
        //numberPlate selectedNumberPlate = numberTableView.getSelectionModel().getSelectedItem();
        //System.out.printf(String.valueOf(selectedNumberPlate.getNum_pav()));
/*
        FXMLLoader fxmlLoader = new FXMLLoader(editPage.class.getResource("../View/edit-page.fxml"));
        Parent parent = fxmlLoader.load();

        //editPage editPageController = fxmlLoader.getController();
        //editPageController.editDataBtn(selectedNumberPlate);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) goToEditPageBtnID.getScene().getWindow();
        stage.setTitle("Number Plate Edit");
        stage.setScene(scene);
        stage.show();

        FXMLLoader fxmlLoader = new FXMLLoader(editPage.class.getResource("/View/edit-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) goToEditItemID.getScene().getWindow();
        stage.setTitle("Number Plate Edit");
        stage.setScene(scene);
        stage.show();


        //System.out.printf(String.valueOf(selectedNumberPlate.getNum_pav()));
*/
    }
    @FXML
    public void goToEditPageBtn(ActionEvent event) throws IOException {
        /*

        FXMLLoader fxmlLoader = new FXMLLoader(editPage.class.getResource("../View/edit-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) goToEditPageBtnID.getScene().getWindow();
        stage.setScene(scene);
        stage.show();*/

        if (numberTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Klaida");
            alert1.setContentText("Numeriai nepasirinkti");
            Optional<ButtonType> result1 = alert1.showAndWait();
        }
        else {


            numberPlate selectedNumberPlate = numberTableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(editPage.class.getResource("../View/edit-page.fxml"));
            parent = fxmlLoader.load();
            editPage editPageController = fxmlLoader.getController();
            editPageController.editDataBtn(selectedNumberPlate);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }


    }
}
