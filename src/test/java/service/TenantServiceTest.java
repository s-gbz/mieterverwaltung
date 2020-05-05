package service;

import model.Tenant;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TenantServiceTest {

    private TenantService tenantService;

    private static final String READ_TENANT_TEST_FILE_PATH = "src\\test\\resources\\tenant-test-with-2-entries.json";
    private static final String WRITE_TENANT_TEST_FILE_PATH = "src\\test\\resources\\tenant-test-to-be-written.json";

    @Test
    void addTenantToListShouldIncreaseListSize() {
        List<Tenant> originalTenantList = returnMockTenantListWithSingleEntry();
        int originalListSize = originalTenantList.size();

        tenantService = new TenantService(originalTenantList);
        List<Tenant> updatedTenantList = tenantService.addTenantToList(new Tenant(1, "Lukas Witziger", "Hundeweg 3") );

        assertTrue(originalListSize < updatedTenantList.size());
    }

    @Test
    void deleteTenantFromListShouldReduceListSize() {
        List<Tenant> originalTenantList = returnMockTenantListWithSingleEntry();
        int originalListSize = originalTenantList.size();

        tenantService = new TenantService(originalTenantList);
        List<Tenant> updatedTenantList = tenantService.deleteTenantFromList(originalTenantList.get(0));

        assertTrue(originalListSize > updatedTenantList.size());
    }

    @Test
    void readTenantsFromJSONShouldReturnTenantListWithTwoParsedEntries() {
        tenantService = new TenantService();
        List<Tenant> parsedTenants = tenantService.readTenantsFromJSON(new File(READ_TENANT_TEST_FILE_PATH));

        assertTrue(parsedTenants.size() == 2);
    }

    @Test
    void writeTenantsToJSONShouldCreateAPreviouslyUnexistingJSONFileWithOneEntry() throws IOException {
        deleteTenantTestFileIfExistant();

        List<Tenant> originalTenantList = returnMockTenantListWithSingleEntry();

        tenantService = new TenantService(originalTenantList);
        tenantService.writeTenantsToJSON(WRITE_TENANT_TEST_FILE_PATH);

        assertTrue(new File(WRITE_TENANT_TEST_FILE_PATH).exists());
    }

    private List<Tenant> returnMockTenantListWithSingleEntry() {
        List<Tenant> tenantList = new ArrayList<Tenant>();

        tenantList.add(new Tenant(0, "Peter Witzig", "Vogelweg 2"));
        return tenantList;
    }

    private void deleteTenantTestFileIfExistant() {
        File fileToBeChecked = new File(WRITE_TENANT_TEST_FILE_PATH);

        if(fileToBeChecked.exists()) {
            fileToBeChecked.delete();
        }
    }
}