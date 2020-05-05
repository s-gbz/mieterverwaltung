import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Tenant;
import service.TenantService;

import java.util.ArrayList;

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

        TableColumn<Tenant, Integer>  idColumn = new TableColumn("Vertragsnummer");
        TableColumn<Tenant, String>  nameColumn = new TableColumn("Name des Mieters");
        TableColumn<Tenant, String>  addressColumn = new TableColumn("Adresse");

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
//            tenantService.addTenantToList();
        } );

        editTenantButton.setMinWidth(vBox.getPrefWidth());
        editTenantButton.setMinHeight(vBox.getPrefHeight());

        deleteTenantButton.setMinWidth(vBox.getPrefWidth());
        deleteTenantButton.setMinHeight(vBox.getPrefHeight());

        deleteTenantButton.setOnAction(actionEvent -> {
            int selectedRowIndex = tenantTable.getSelectionModel().getFocusedIndex();

            if (selectedRowIndex != -1) {
                tenantData.remove(selectedRowIndex);
            }
        } );

        GridPane.setConstraints(addTenantButton, 0, 0);
        GridPane.setConstraints(editTenantButton, 0, 1);
        GridPane.setConstraints(deleteTenantButton, 0, 2);

        buttonGridPane.setVgap(10);
        buttonGridPane.setHgap(10);
        buttonGridPane.getChildren().addAll(addTenantButton, editTenantButton, deleteTenantButton);
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