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
            conn = DriverManager.getConnection(DB_URL, "root", "pa55word");
            // consulta select (selecciona el producto con ID especificado)
            try (PreparedStatement ps = conn.prepareStatement("select * from assets where serial = ?")) {
                // indicar el ID que buscamos
                ps.setString(1, serial);
                // ejecutar la consulta
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // obtener cada una de la columnas y mapearlas a la clase Product
                        asset = new Asset();
                        asset.setSerial(rs.getString("serial"));
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
            conn = DriverManager.getConnection(DB_URL, "root", "pa55word");
            // consulta select (selecciona el producto con ID especificado)
            try (PreparedStatement ps = conn.prepareStatement("select * from assets")) {
                // ejecutar la consulta
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // obtener cada una de la columnas y mapearlas a la clase Product
                        asset = new Asset();
                        asset.setSerial(rs.getString("serial"));
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
            conn = DriverManager.getConnection(DB_URL, "root", "pa55word");
            System.out.println("save1")
            try (PreparedStatement stmt = conn.prepareStatement("insert into assets values (?,?,?,?,?,?)")) {
                System.out.println("save1")
                stmt.setString(1, asset.getSerial());
                stmt.setString(2, asset.getName());
                stmt.setString(3, asset.getDescription());
                stmt.setString(4, asset.getAssigneeEmail());
                stmt.setDate(5, new Date(asset.getDateAssignment().getTime()));
                stmt.setDate(6, new Date(asset.getDateRegistered().getTime()));
                System.out.println("save1")
                stmt.executeUpdate();
                System.out.println("save1")
                conn.commit();
                System.out.println("save1")
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