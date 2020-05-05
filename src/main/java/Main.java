import gui.GuiConstants;
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

import static gui.Dialogs.openAddTenantDialogAndAddTenantOnConfirmation;
import static gui.Dialogs.openDeleteTenantDialogIfRowSelectedAndRemoveTenantOnConfirmation;
import static gui.GuiConstants.*;

public class Main extends Application {

    private TenantService tenantService = new TenantService();

    private Stage window;
    private GridPane gridPane = new GridPane();
    private GridPane buttonGridPane = new GridPane();

    private TableView tenantTable = new TableView();
    ObservableList<Tenant> tenantData = FXCollections.observableList(new ArrayList<>());

    private Button addTenantButton = new Button(ADD_TENANT_BUTTON_LABEL);
    private Button deleteTenantButton = new Button(DELET_TENANT_BUTTON_LABEL);
    private Button loadFromJsonButton = new Button(LOAD_FROM_JSON_BUTTON_LABEL);
    private Button writeToJsonButton = new Button(WRITE_FROM_JSON_BUTTON_LABEL);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeTenantTable();

        initializeTenantButtonLayout();
        initializeTenantButtonFunctions();

        initializeMainGridPaneConstraints();
        initializeWindowAndScence(primaryStage);
    }

    private void initializeMainGridPaneConstraints() {
        gridPane.setPadding(new Insets(10, 0, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        GridPane.setConstraints(tenantTable, 0, 0);
        GridPane.setConstraints(buttonGridPane, 1, 0);

        gridPane.getChildren().addAll(tenantTable, buttonGridPane);
    }

    private void initializeWindowAndScence(Stage primaryStage) {
        Scene scene = new Scene(gridPane, GuiConstants.WINDOW_WIDTH, GuiConstants.WINDOW_HEIGHT);

        window = primaryStage;
        window.setTitle(APPLICATION_TITLE);

        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    private void initializeTenantTable() {
        tenantTable.setMinWidth(GuiConstants.TENANT_TABLE_WIDTH);
        tenantTable.setMinHeight(GuiConstants.TENANT_TABLE_HEIGHT);

        initializeTenantTableColumns();

        tenantData.add(new Tenant(0, "asd", "asd"));
        tenantTable.setItems(tenantData);
    }

    private void initializeTenantTableColumns() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(GuiConstants.TENANT_TABLE_WIDTH);
        vBox.setPrefHeight(GuiConstants.TENANT_TABLE_HEIGHT);

        TableColumn<Tenant, Integer>  idColumn = new TableColumn(GuiConstants.TENANT_ID_LABEL);
        TableColumn<Tenant, String>  nameColumn = new TableColumn(GuiConstants.TENANT_NAME_LABEL);
        TableColumn<Tenant, String>  addressColumn = new TableColumn(GuiConstants.TENANT_ADDRESS_LABEL);

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

    private void initializeTenantButtonLayout() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(GuiConstants.TENANT_BUTTON_WIDTH);
        vBox.setPrefHeight(GuiConstants.TENANT_BUTTON_HEIGHT);

        addTenantButton.setMinWidth(vBox.getPrefWidth());
        addTenantButton.setMinHeight(vBox.getPrefHeight());
        deleteTenantButton.setMinWidth(vBox.getPrefWidth());
        deleteTenantButton.setMinHeight(vBox.getPrefHeight());

        loadFromJsonButton.setMinWidth(vBox.getPrefWidth());
        loadFromJsonButton.setMinHeight(vBox.getPrefHeight());
        writeToJsonButton.setMinWidth(vBox.getPrefWidth());
        writeToJsonButton.setMinHeight(vBox.getPrefHeight());

        GridPane.setConstraints(addTenantButton, 0, 0);
        GridPane.setConstraints(deleteTenantButton, 0, 1);
        GridPane.setConstraints(loadFromJsonButton, 0, 2);
        GridPane.setConstraints(writeToJsonButton, 0, 3);

        buttonGridPane.setVgap(10);
        buttonGridPane.setHgap(10);
        buttonGridPane.getChildren().addAll(addTenantButton, deleteTenantButton, loadFromJsonButton, writeToJsonButton);
    }

    private void initializeTenantButtonFunctions() {
        addTenantButton.setOnAction(actionEvent -> {
            openAddTenantDialogAndAddTenantOnConfirmation(tenantService, tenantData);
        });

        deleteTenantButton.setOnAction(actionEvent -> {
            openDeleteTenantDialogIfRowSelectedAndRemoveTenantOnConfirmation(tenantTable, tenantData);
        });

        loadFromJsonButton.setOnAction(actionEvent -> {
            openAddTenantDialogAndAddTenantOnConfirmation(tenantService, tenantData);
        });

        writeToJsonButton.setOnAction(actionEvent -> {
            openDeleteTenantDialogIfRowSelectedAndRemoveTenantOnConfirmation(tenantTable, tenantData);
        });
    }
}