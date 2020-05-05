import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Tenant;
import service.TenantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    private TenantService tenantService = new TenantService();

    private Stage window;
    private GridPane gridPane = new GridPane();
    private GridPane buttonGridPane = new GridPane();

    private TableView tenantTable = new TableView();
    ObservableList<Tenant> tenantData = FXCollections.observableList(new ArrayList<>());

    private Button addTenantButton = new Button("Mieter hinzufügen");
    private Button editTenantButton = new Button("Mieter bearbeiten");
    private Button deleteTenantButton = new Button("Mieter löschen");

    private final static double WINDOW_WIDTH = 1030;
    private final static double WINDOW_HEIGHT = 620;

    private final static double WINDOW_WIDTH_MARGIN_OFFSET = 30;
    private final static double WINDOW_HEIGHT_MARGIN_OFFSET = 20;

    private final static double TENANT_TABLE_WIDTH = (WINDOW_WIDTH - WINDOW_WIDTH_MARGIN_OFFSET) * 0.75;
    private final static double TENANT_TABLE_HEIGHT = (WINDOW_HEIGHT - WINDOW_HEIGHT_MARGIN_OFFSET);

    private final static double TENANT_BUTTON_WIDTH = (WINDOW_WIDTH - WINDOW_WIDTH_MARGIN_OFFSET) * 0.25;
    private final static double TENANT_BUTTON_HEIGHT = TENANT_BUTTON_WIDTH * 0.25;

    private final static String TENANT_ID_LABEL = "Vertragsnummer";
    private final static String TENANT_NAME_LABEL = "Name des Mieters";
    private final static String TENANT_ADDRESS_LABEL = "Adresse";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeTenantTable();
        initializeTenantButtons();
        initializeGridPaneConstraints();
        initializeWindowAndScence(primaryStage);
    }

    private void initializeWindowAndScence(Stage primaryStage) {
        Scene scene = new Scene(gridPane, WINDOW_WIDTH, WINDOW_HEIGHT);

        window = primaryStage;
        window.setTitle("Mieterverwaltung");

        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }


    private void initializeTenantTable() {
        tenantTable.setMinWidth(TENANT_TABLE_WIDTH);
        tenantTable.setMinHeight(TENANT_TABLE_HEIGHT);

        initializeTenantTableColumns();

        tenantData.add(new Tenant(0, "asd", "asd"));
        tenantTable.setItems(tenantData);
    }

    private void initializeTenantTableColumns() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(TENANT_TABLE_WIDTH);
        vBox.setPrefHeight(TENANT_TABLE_HEIGHT);

        TableColumn<Tenant, Integer>  idColumn = new TableColumn(TENANT_ID_LABEL);
        TableColumn<Tenant, String>  nameColumn = new TableColumn(TENANT_NAME_LABEL);
        TableColumn<Tenant, String>  addressColumn = new TableColumn(TENANT_ADDRESS_LABEL);

        idColumn.setMinWidth(vBox.getPrefWidth() * 0.2);
        nameColumn.setMinWidth(vBox.getPrefWidth() * 0.4);
        addressColumn.setMinWidth(vBox.getPrefWidth() * 0.4);

        idColumn.setCellValueFactory(new PropertyValueFactory("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory("address"));


        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(t -> {
            t.getRowValue().setName(t.getNewValue());
        });

        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit(t -> {
            t.getRowValue().setAddress(t.getNewValue());
        });

        tenantTable.setEditable(true);
        tenantTable.getColumns().addAll(idColumn, nameColumn, addressColumn);
    }

    private void initializeTenantButtons() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(TENANT_BUTTON_WIDTH);
        vBox.setPrefHeight(TENANT_BUTTON_HEIGHT);

        addTenantButton.setMinWidth(vBox.getPrefWidth());
        addTenantButton.setMinHeight(vBox.getPrefHeight());

        addTenantButton.setOnAction(actionEvent -> {
            openAddTenantDialogAndAddTenantOnConfirmation();
        } );

        editTenantButton.setMinWidth(vBox.getPrefWidth());
        editTenantButton.setMinHeight(vBox.getPrefHeight());

        deleteTenantButton.setMinWidth(vBox.getPrefWidth());
        deleteTenantButton.setMinHeight(vBox.getPrefHeight());

        deleteTenantButton.setOnAction(actionEvent -> {
            openDeleteTenantDialogIfRowSelectedAndRemoveTenantOnConfirmation();
        });

        GridPane.setConstraints(addTenantButton, 0, 0);
        GridPane.setConstraints(editTenantButton, 0, 1);
        GridPane.setConstraints(deleteTenantButton, 0, 2);

        buttonGridPane.setVgap(10);
        buttonGridPane.setHgap(10);
        buttonGridPane.getChildren().addAll(addTenantButton, deleteTenantButton);
    }

    private void openAddTenantDialogAndAddTenantOnConfirmation() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Mieter hinzufügen");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField tenantNameField = new TextField();
        tenantNameField.setPromptText(TENANT_NAME_LABEL);
        TextField tenantAddressField = new TextField();
        tenantAddressField.setPromptText(TENANT_ADDRESS_LABEL);

        gridPane.add(new Label(TENANT_NAME_LABEL), 0, 0);
        gridPane.add(tenantNameField, 0, 1);
        gridPane.add(new Label(TENANT_ADDRESS_LABEL), 0, 2);
        gridPane.add(tenantAddressField, 0, 3);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the name field by default.
        Platform.runLater(() -> tenantNameField.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(tenantNameField.getText(), tenantAddressField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> {
            List<Tenant> updatedTenantList = tenantService.addTenantToList(new Tenant(pair.getKey(), pair.getValue()));
            tenantData.add(updatedTenantList.get(updatedTenantList.size()-1));
        });
    }

    private void openDeleteTenantDialogIfRowSelectedAndRemoveTenantOnConfirmation() {
        int selectedRowIndex = tenantTable.getSelectionModel().getFocusedIndex();

        if (selectedRowIndex != -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                    , "Möchten Sie den Vertrag " + tenantData.get(selectedRowIndex).getId() + " wirklich löschen?"
                    , ButtonType.YES, ButtonType.NO);

            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

            if (ButtonType.YES.equals(result)) {
                tenantData.remove(selectedRowIndex);
            }
        }
    }

    private void initializeGridPaneConstraints() {
        gridPane.setPadding(new Insets(10, 0, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        GridPane.setConstraints(tenantTable, 0, 0);
        GridPane.setConstraints(buttonGridPane, 1, 0);

        gridPane.getChildren().addAll(tenantTable, buttonGridPane);
    }

}