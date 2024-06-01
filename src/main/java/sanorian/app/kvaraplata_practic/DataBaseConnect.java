package sanorian.app.kvaraplata_practic;


import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class DataBaseConnect {
    static public void addEstate(String city, String street, String building, Integer apartment, Double hotWater, Double coldWater, Double estateSpace, Integer numberOfRegisteredPeople){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            statement = connection.createStatement();
            statement.executeQuery("INSERT INTO Estate (City, Street, Building, Apartment, Hot_water, Cold_water, Estate_space, Number_of_registered_people) VALUES ('"+city+"', '"+street+"', '"+building+"', "+apartment+", "+hotWater+", "+coldWater+", "+estateSpace+", "+numberOfRegisteredPeople+");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     static public void addService(String name, String formula){
         Connection connection = null;
         Statement statement = null;
         try {
             Class.forName("org.sqlite.JDBC");
             connection = DriverManager.getConnection("jdbc:sqlite:data.db");
             statement = connection.createStatement();
             statement.executeQuery("INSERT INTO Service (Name, Formula) VALUES ('"+name+"', '"+formula+"');");
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
    static public void addProvision(Integer serviceID, Integer estateID){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            statement = connection.createStatement();
            Estate estate = DataBaseConnect.getEstate(estateID);
            assert estate != null;
            Double cost = getEvaluatedFormula(formatFormula(Objects.requireNonNull(getService(serviceID)).getFormula(), estate.getColdWater(), estate.getHotWater(), estate.getEstateSpace(), estate.getNumberOfRegistredPeople()));
            statement.executeQuery("INSERT INTO Provision (Service_ID, Estate_ID, Cost) VALUES ("+serviceID+", "+estateID+", "+cost+");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static public ArrayList<Provision> getProvisionByAddress(String city, String street, String building, String apartment){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Provision WHERE Estate_ID=(SELECT Estate_ID FROM Estate WHERE City='"+city+"' AND Street='"+street+"' AND Building='"+building+"' AND Apartment='"+apartment+"');");
            ArrayList<Provision> provisions = new ArrayList<>();
            while(rs.next()) {
                provisions.add(new Provision(rs.getInt("ID"), rs.getInt("Service_ID"), rs.getInt("Estate_ID"), rs.getDouble("Cost")));
            }
            return provisions;
        }catch(SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
    }
    static public ArrayList<Provision> getProvisionsByServiceID(Integer serviceID){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Provision WHERE Service_ID="+serviceID+";");
            ArrayList<Provision> provisions = new ArrayList<>();
            while(rs.next()) {
                provisions.add(new Provision(rs.getInt("ID"), rs.getInt("Service_ID"), rs.getInt("Estate_ID"), rs.getDouble("Cost")));
            }
            return provisions;
        }catch(SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
    }
     static public ArrayList<Provision> getProvision(Integer estateID){
             try{
                 Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
                 Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery("SELECT * FROM Provision WHERE Estate_ID="+estateID+";");
                 ArrayList<Provision> provisions = new ArrayList<>();
                 while(rs.next()) {
                     provisions.add(new Provision(rs.getInt("ID"), rs.getInt("Service_ID"), rs.getInt("Estate_ID"), rs.getDouble("Cost")));
                 }
                 return provisions;
             }catch(SQLException e){
                 e.printStackTrace(System.err);
                 return null;
             }
     }
    static public Estate getEstate(Integer id){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Estate WHERE ID="+id+";");
            while(rs.next()) {
                return new Estate(rs.getInt("ID"), rs.getString("City"), rs.getString("Street"), rs.getString("Building"), rs.getInt("Apartment"), rs.getDouble("Hot_water"), rs.getDouble("Cold_water"), rs.getDouble("Estate_place"), rs.getInt("Number_of_registered_people"));
            }
        }catch(SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
        return null;
    }
    static public Estate getEstateByAddress(String city, String street, String building, String apartment){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Estate WHERE City='"+city+"' AND Street='"+street+"' AND Building='"+building+"' AND Apartment='"+apartment+"';");
            while(rs.next()) {
                return new Estate(rs.getInt("ID"), rs.getString("City"), rs.getString("Street"), rs.getString("Building"), rs.getInt("Apartment"), rs.getDouble("Hot_water"), rs.getDouble("Cold_water"), rs.getDouble("Estate_place"), rs.getInt("Number_of_registered_people"));
            }
        }catch(SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
        return null;
    }
    static public Service getService(Integer id){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Estate WHERE ID="+id+";");
            while(rs.next()) {
                return new Service(rs.getInt("ID"), rs.getString("Name"), rs.getString("Formula"));
            }
        }catch(SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
        return null;
    }
    static public ArrayList<Service> getServices(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Estate;");
            ArrayList<Service> services = new ArrayList<>();
            while(rs.next()) {
                services.add(new Service(rs.getInt("ID"), rs.getString("Name"), rs.getString("Formula")));
            }
            return services;
        }catch(SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
    }
    static public void deleteProvision(Integer id){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            statement = connection.createStatement();
            statement.executeQuery("DELETE FROM Provision WHERE id="+id+";");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static public void updateProvisionCost(Integer provisionID, Double newCost){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            statement = connection.createStatement();
            statement.executeQuery("UPDATE Provision SET Cost="+newCost+" WHERE ID="+provisionID+";");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static public void updateServiceFormula(Integer serviceID, String formula){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            statement = connection.createStatement();
            statement.executeQuery("UPDATE Service SET Formula='"+formula+"' WHERE ID="+serviceID+";");
            reloadCosts(serviceID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static private void reloadCosts(Integer serviceID){
        String formula = getService(serviceID).getFormula();
        getProvisionsByServiceID(serviceID).forEach(provision -> {
            Estate estate = DataBaseConnect.getEstate(provision.getEstateID());
            try {
                Double newCost = getEvaluatedFormula(formatFormula(formula, estate.getColdWater(), estate.getHotWater(), estate.getEstateSpace(), estate.getNumberOfRegistredPeople()));
                updateProvisionCost(provision.getId(), newCost);
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
        });
    }

    static private String formatFormula(String unformattedFormula, Double coldWater, Double hotWater, Double space, Integer peopleCount){
        return unformattedFormula.replace("c", String.valueOf(coldWater)).replace("h", String.valueOf(hotWater)).replace("s", String.valueOf(space)).replace("p", String.valueOf(peopleCount));
    }
    static private Double getEvaluatedFormula(String formula) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        return ((Number) engine.eval(formula)).doubleValue();
    }
}