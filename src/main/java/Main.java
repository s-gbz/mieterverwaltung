import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage window;
    private GridPane gridPane = new GridPane();

    private Button addTenantButton = new Button("Hinzufügen");
    private Button editTenantButton = new Button("Bearbeiten");
    private Button deleteTenantButton = new Button("Löschen");

    private final static double WINDOW_WIDTH = 800;
    private final static double WINDOW_HEIGHT = 600;
    private final static double PREFFERED_TENANT_BUTTON_WIDTH = 400;
    private final static double PREFFERED_TENANT_BUTTON_HEIGHT = 100;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Mieterverwaltung");

        initializeTenantButtonWidth();
        initializeGridPaneConstraints();


        Scene scene = new Scene(gridPane, 800, 600);

        window.setScene(scene);
        window.show();
    }

    private void initializeTenantButtonWidth() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(PREFFERED_TENANT_BUTTON_WIDTH);
        vBox.setPrefHeight(PREFFERED_TENANT_BUTTON_HEIGHT);

        addTenantButton.setMinWidth(vBox.getPrefWidth());
        addTenantButton.setMinHeight(vBox.getPrefHeight());

        editTenantButton.setMinWidth(vBox.getPrefWidth());
        editTenantButton.setMinHeight(vBox.getPrefHeight());

        deleteTenantButton.setMinWidth(vBox.getPrefWidth());
        deleteTenantButton.setMinHeight(vBox.getPrefHeight());

    }

    private void initializeGridPaneConstraints() {
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        GridPane.setConstraints(addTenantButton, 1, 1);
        GridPane.setConstraints(editTenantButton, 1, 2);
        GridPane.setConstraints(deleteTenantButton, 1, 3);

        gridPane.getChildren().addAll(addTenantButton, editTenantButton, deleteTenantButton);
    }

}