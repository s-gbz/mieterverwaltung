import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.TenantService;

public class Main extends Application {

    private TenantService tenantService;

    private Stage window;
    private GridPane gridPane = new GridPane();
    private GridPane buttonGridPane = new GridPane();

    private TableView tenantTable = new TableView();

    private Button addTenantButton = new Button("Mieter hinzufügen");
    private Button editTenantButton = new Button("Mieter bearbeiten");
    private Button deleteTenantButton = new Button("Mieter löschen");

    private final static double WINDOW_WIDTH = 830;
    private final static double WINDOW_HEIGHT = 620;

    private final static double TENANT_TABLE_WIDTH = 400;
    private final static double TENANT_TABLE_HEIGHT = 600;

    private final static double TENANT_BUTTON_WIDTH = 400;
    private final static double TENANT_BUTTON_HEIGHT = 100;

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
        window.show();
    }


    private void initializeTenantTable() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(TENANT_TABLE_WIDTH);
        vBox.setPrefHeight(TENANT_TABLE_HEIGHT);

        TableColumn nameColumn = new TableColumn("Name des Mieters");
        TableColumn addressColumn = new TableColumn("Adresse");

        nameColumn.setMinWidth(vBox.getPrefWidth()/2);
        addressColumn.setMinWidth(vBox.getPrefWidth()/2);

        tenantTable.setMinWidth(vBox.getPrefWidth());
        tenantTable.setMinHeight(vBox.getPrefHeight());

        buttonGridPane.setVgap(10);
        buttonGridPane.setHgap(10);

        GridPane.setConstraints(addTenantButton, 0, 0);
        GridPane.setConstraints(editTenantButton, 0, 1);
        GridPane.setConstraints(deleteTenantButton, 0, 2);
        tenantTable.getColumns().addAll(nameColumn, addressColumn);
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
//           tenantService.addTenantToList();
        } );

        buttonGridPane.getChildren().addAll(addTenantButton, editTenantButton, deleteTenantButton);
    }

    private void initializeGridPaneConstraints() {
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        GridPane.setConstraints(tenantTable, 0, 0);
        GridPane.setConstraints(buttonGridPane, 1, 0);

        gridPane.getChildren().addAll(tenantTable, buttonGridPane);
    }

}