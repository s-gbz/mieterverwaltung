package service;


import model.Tenant;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TenantService {

    private List<Tenant> tenantList;
    private static final String TENANT_FILE_PATH = "/resources/tenants.json";

    public TenantService() { }

    public TenantService(List<Tenant> tenantList) {
        this.tenantList = tenantList;
    }

    public void readTenantsFromJSON() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(TENANT_FILE_PATH))
        {
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;

            System.out.println(employeeList);

            employeeList.forEach( emp -> parseTenantObject( (JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void writeTenantsToJSON() {

    }

    public List<Tenant> addTenantToList(Tenant newTenant) {
        tenantList.add(newTenant);
        return tenantList;
    }

    public List<Tenant> deleteTenantFromList(Tenant tenantToBeRemoved) {
        tenantList.remove(tenantToBeRemoved);
        return tenantList;
    }

    private Tenant parseTenantObject(JSONObject tenant)
    {
        return new Tenant(Integer.parseInt(tenant.get("id").toString()), tenant.get("name").toString(), tenant.get("address").toString());
    }
}
