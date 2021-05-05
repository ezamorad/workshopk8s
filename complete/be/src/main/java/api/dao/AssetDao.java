package api.dao;

import api.domain.Asset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class AssetDao implements Dao<Asset> {
    
    private List<Asset> assetList = new ArrayList<Asset>();

    static final String DB_URL = "jdbc:mysql://mysql.default.svc.cluster.local:3306/demoasset";

    Connection conn = null;
    
    public AssetDao() {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch(ClassNotFoundException ex){
            System.out.println("Error with Driver: " + ex.getMessage());
        }        
    }
    
    @Override
    public Asset get(String serial) {

        Connection conn = null;
        Asset asset = null;

        try {
            conn = DriverManager.getConnection(DB_URL, "root", System.getenv().get("MYSQL_ROOT_PASSWORD"));
            // consulta select (selecciona el producto con ID especificado)
            try (PreparedStatement ps = conn.prepareStatement("select * from assets where serial_number = ?")) {
                // indicar el ID que buscamos
                ps.setString(1, serial);
                // ejecutar la consulta
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // obtener cada una de la columnas y mapearlas a la clase Product
                        asset = new Asset();
                        asset.setSerial(rs.getString("serial_number"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return asset;
    }
    
    @Override
    public List<Asset> getAll() {
        Connection conn = null;
        List assetList = new ArrayList<Asset>();
        Asset asset = null;

        try {
            conn = DriverManager.getConnection(DB_URL, "root", System.getenv().get("MYSQL_ROOT_PASSWORD"));
            // consulta select (selecciona el producto con ID especificado)
            try (PreparedStatement ps = conn.prepareStatement("select * from assets")) {
                // ejecutar la consulta
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        // obtener cada una de la columnas y mapearlas a la clase Product
                        asset = new Asset();
                        asset.setSerial(rs.getString("serial_number"));
                        assetList.add(asset);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return assetList;
    }
    
    @Override
    public void save(Asset asset) {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, "root", System.getenv().get("MYSQL_ROOT_PASSWORD"));
            System.out.println("save1");
            try (PreparedStatement stmt = conn.prepareStatement("insert into assets(serial_number,name,description,assignee_email,type) values (?,?,?,?,?)")) {
                System.out.println("save2");
                stmt.setString(1, asset.getSerial());
                stmt.setString(2, asset.getName());
                stmt.setString(3, asset.getDescription());
                stmt.setString(4, asset.getAssigneeEmail());
                stmt.setString(5, asset.getType());

                System.out.println("save3");
                stmt.executeUpdate();
                System.out.println("save4");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void update(Asset asset, String[] params) {
        assetList.add(asset);
    }
    
    @Override
    public void delete(Asset asset) {
        assetList.remove(asset);
    }
}


// public class Example {
//     public static void main(String[] args) {
//       ApiClient defaultClient = Configuration.getDefaultApiClient();
//       defaultClient.setBasePath("http://localhost");
      
//       // Configure API key authorization: BearerToken
//       ApiKeyAuth BearerToken = (ApiKeyAuth) defaultClient.getAuthentication("BearerToken");
//       BearerToken.setApiKey("YOUR API KEY");
//       // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//       //BearerToken.setApiKeyPrefix("Token");
  
//       CoreV1Api apiInstance = new CoreV1Api(defaultClient);
//       String name = "name_example"; // String | name of the Secret
//       String namespace = "demoasset"; // String | object name and auth scope, such as for teams and projects
//       String pretty = "false"; // String | If 'true', then the output is pretty printed.
//       Boolean exact = true; // Boolean | Should the export be exact.  Exact export maintains cluster-specific fields like 'Namespace'. Deprecated. Planned for removal in 1.18.
//       Boolean export = true; // Boolean | Should this value be exported.  Export strips fields that a user can not specify. Deprecated. Planned for removal in 1.18.
//       try {
//         V1Secret result = apiInstance.readNamespacedSecret(name, namespace, pretty, exact, export);
//         System.out.println(result);
//       } catch (ApiException e) {
//         System.err.println("Exception when calling CoreV1Api#readNamespacedSecret");
//         System.err.println("Status code: " + e.getCode());
//         System.err.println("Reason: " + e.getResponseBody());
//         System.err.println("Response headers: " + e.getResponseHeaders());
//         e.printStackTrace();
//       }
//     }
//   }