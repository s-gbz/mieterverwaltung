package gui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import model.Tenant;
import service.TenantService;

import java.util.List;
import java.util.Optional;

import static gui.GuiConstants.ADD_TENANT_BUTTON_LABEL;

public class Dialogs {

    public static void openDeleteTenantDialogIfRowSelectedAndRemoveTenantOnConfirmation(TableView tenantTable, ObservableList<Tenant> tenantData) {
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

    public static void openAddTenantDialogAndAddTenantOnConfirmation(TenantService tenantService, ObservableList<Tenant> tenantData) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(ADD_TENANT_BUTTON_LABEL);

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField tenantNameField = new TextField();
        tenantNameField.setPromptText(GuiConstants.TENANT_NAME_LABEL);
        TextField tenantAddressField = new TextField();
        tenantAddressField.setPromptText(GuiConstants.TENANT_ADDRESS_LABEL);

        gridPane.add(new Label(GuiConstants.TENANT_NAME_LABEL), 0, 0);
        gridPane.add(tenantNameField, 0, 1);
        gridPane.add(new Label(GuiConstants.TENANT_ADDRESS_LABEL), 0, 2);
        gridPane.add(tenantAddressField, 0, 3);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the name field by default.
        Platform.runLater(() -> tenantNameField.requestFocus());

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
}
