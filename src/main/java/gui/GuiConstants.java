package gui;

public enum GuiConstants {
    ;
    private final static double WINDOW_WIDTH_MARGIN_OFFSET = 30;
    private final static double WINDOW_HEIGHT_MARGIN_OFFSET = 20;

    public final static double WINDOW_WIDTH = 1000 + WINDOW_WIDTH_MARGIN_OFFSET;
    public final static double WINDOW_HEIGHT = 600 + WINDOW_HEIGHT_MARGIN_OFFSET;

    public final static double TENANT_BUTTON_WIDTH = (WINDOW_WIDTH - WINDOW_WIDTH_MARGIN_OFFSET) * 0.25;
    public final static double TENANT_BUTTON_HEIGHT = TENANT_BUTTON_WIDTH * 0.25;
    public final static double TENANT_TABLE_WIDTH = (WINDOW_WIDTH - WINDOW_WIDTH_MARGIN_OFFSET) * 0.75;
    public final static double TENANT_TABLE_HEIGHT = (WINDOW_HEIGHT - WINDOW_HEIGHT_MARGIN_OFFSET);

    public final static String APPLICATION_TITLE = "Mieterverwaltung";

    public final static String ADD_TENANT_BUTTON_LABEL = "Mieter hinzufügen";
    public final static String DELET_TENANT_BUTTON_LABEL = "Mieter löschen";

    public final static String LOAD_FROM_JSON_BUTTON_LABEL = "Daten laden";
    public final static String WRITE_FROM_JSON_BUTTON_LABEL = "Daten speichern";

    public final static String TENANT_ID_LABEL = "Vertragsnummer";
    public final static String TENANT_NAME_LABEL = "Name des Mieters";
    public final static String TENANT_ADDRESS_LABEL = "Adresse des Mieters";

    public static final String TENANT_FILE_PATH = "src\\main\\resources\\tenants.json";
    public static final String TENANT_FILE_DIRECTORY = "src\\main\\resources";

}
