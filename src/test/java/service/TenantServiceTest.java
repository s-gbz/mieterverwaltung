package service;

import model.Tenant;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TenantServiceTest {

    private TenantService tenantService;

    @Test
    void readTenantsFromJSONShouldReturnTenantList() {
    }

    @Test
    void writeTenantsToJSON() {
    }

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

    private List<Tenant> returnMockTenantListWithSingleEntry() {
        List<Tenant> tenantList = new ArrayList<Tenant>();

        tenantList.add(new Tenant(0, "Peter Witzig", "Vogelweg 2"));
        return tenantList;
    }
}