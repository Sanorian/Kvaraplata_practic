package sanorian.app.kvaraplata_practic;


import java.sql.*;
import java.util.ArrayList;

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
            // cost посчитать по формуле из service
            statement.executeQuery("INSERT INTO Service (Service_ID, Estate_ID, Cost) VALUES ("+serviceID+", "+estateID+", );");
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
}