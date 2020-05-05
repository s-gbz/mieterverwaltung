package service;


import model.Tenant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TenantService {

    private List<Tenant> tenantList;
    private static final String TENANT_FILE_PATH = "/resources/tenants.json";

    public TenantService() {
        this.tenantList = new ArrayList<>();
    }

    public TenantService(List<Tenant> tenantList) {
        this.tenantList = tenantList;
    }

    public List<Tenant> readTenantsFromJSON(File file) {
        JSONParser jsonParser = new JSONParser();
        System.out.println(file.getPath());
        try (FileReader reader = new FileReader(file.getPath()))
        {
            Object obj = jsonParser.parse(reader);

            JSONArray tenantList = (JSONArray) obj;

            System.out.println(tenantList);

            List<Tenant> parsedTenants = new ArrayList<>();

            tenantList.forEach(tenant -> {
                parsedTenants.add(parseTenantObject((JSONObject) tenant));
            });

            this.tenantList = parsedTenants;
            return parsedTenants;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeTenantsToJSON() {

    }

    public List<Tenant> addTenantToList(Tenant newTenant) {
        addTenantIdIfItIsEmpty(newTenant);

        tenantList.add(newTenant);
        return tenantList;
    }

    public List<Tenant> deleteTenantFromList(Tenant tenantToBeRemoved) {

        tenantList.remove(tenantToBeRemoved);
        return tenantList;
    }

    public List<Tenant> deleteTenantFromList(int index) {

        tenantList.remove(index);
        return tenantList;
    }

    private void addTenantIdIfItIsEmpty(Tenant newTenant) {
        if(newTenant.getId() == -1) {
            int newId = createFirstIdIfListIsEmptyOrElseReturnLastId() + 1;
            newTenant.setId(newId);
        }
    }

    private int createFirstIdIfListIsEmptyOrElseReturnLastId() {
        if(tenantList.size() == 0) {
            return 0;
        } else {
            int lastIndexInList = tenantList.size() - 1;
            return tenantList.get(lastIndexInList).getId();
        }
    }

    private Tenant parseTenantObject(JSONObject tenant)
    {
        return new Tenant(Integer.parseInt(tenant.get("id").toString()), tenant.get("name").toString(), tenant.get("address").toString());
    }
}
