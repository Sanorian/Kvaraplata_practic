package sanorian.app.kvaraplata_practic;


import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class DataBaseConnect {
    static public void addEstate(String city, String street, String building, Integer apartment, Double hotWater, Double coldWater, Double estateSpace, Integer numberOfRegisteredPeople){
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Estate (City, Street, Building, Apartment, Hot_water, Cold_water, Estate_space, Number_of_registered_people) VALUES ('"+city+"', '"+street+"', '"+building+"', "+apartment+", "+hotWater+", "+coldWater+", "+estateSpace+", "+numberOfRegisteredPeople+");");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement!= null) statement.close();
                if (connection!= null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
     static public void addService(String name, String formula){
         Connection connection = null;
         Statement statement = null;
         try {
             Class.forName("org.sqlite.JDBC");
             connection = DriverManager.getConnection("jdbc:sqlite:data.db");
             statement = connection.createStatement();
             statement.executeUpdate("INSERT INTO Service (Name, Formula) VALUES ('"+name+"', '"+formula+"');");
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             try {
                 if (statement!= null) statement.close();
                 if (connection!= null) connection.close();
             } catch (SQLException ex) {
                 ex.printStackTrace();
             }
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
            statement.executeUpdate("INSERT INTO Provision (Service_ID, Estate_ID, Cost) VALUES ("+serviceID+", "+estateID+", "+cost+");");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement!= null) statement.close();
                if (connection!= null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
            return provisions;
        } catch(SQLException e){
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
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
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
                 if (statement!= null) statement.close();
                 if (connection!= null) connection.close();
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
            Estate estate = null;
            while(rs.next()) {
                estate = new Estate(rs.getInt("ID"), rs.getString("City"), rs.getString("Street"), rs.getString("Building"), rs.getInt("Apartment"), rs.getDouble("Hot_water"), rs.getDouble("Cold_water"), rs.getDouble("Estate_space"), rs.getInt("Number_of_registered_people"));
            }
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
            return estate;
        }catch(SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
    }
    static public Estate getEstateByAddress(String city, String street, String building, String apartment){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Estate WHERE City='"+city+"' AND Street='"+street+"' AND Building='"+building+"' AND Apartment='"+apartment+"';");
            Estate estate = null;
            while(rs.next()) {
                estate = new Estate(rs.getInt("ID"), rs.getString("City"), rs.getString("Street"), rs.getString("Building"), rs.getInt("Apartment"), rs.getDouble("Hot_water"), rs.getDouble("Cold_water"), rs.getDouble("Estate_space"), rs.getInt("Number_of_registered_people"));
            }
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
            return estate;
        }catch(SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
    }
    static public Service getService(Integer id){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Service WHERE ID="+id+";");
            Service service = null;
            while(rs.next()) {
                service = new Service(rs.getInt("ID"), rs.getString("Name"), rs.getString("Formula"));
            }
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
            return service;
        }catch(SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
    }
    static public ArrayList<Service> getServices(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Service;");
            ArrayList<Service> services = new ArrayList<>();
            while(rs.next()) {
                services.add(new Service(rs.getInt("ID"), rs.getString("Name"), rs.getString("Formula")));
            }
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
            return services;
        }catch(SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
    }
    public static ArrayList<String> getServicesByName(String name){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Name FROM Service WHERE Name='"+name+"';");
            ArrayList<String> names = new ArrayList<>();
            while(rs.next()) {
                names.add(rs.getString("Name"));
            }
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
            return names;
        }catch(SQLException e){
            e.printStackTrace(System.err);
            return null;
        }
    }
    public static ArrayList<String> getServiceNames(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Name FROM Service;");
            ArrayList<String> names = new ArrayList<>();
            while(rs.next()) {
                names.add(rs.getString("Name"));
            }
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
            return names;
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
            statement.executeUpdate("DELETE FROM Provision WHERE id="+id+";");
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
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
            statement.executeUpdate("UPDATE Provision SET Cost="+newCost+" WHERE ID="+provisionID+";");
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
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
            statement.executeUpdate("UPDATE Service SET Formula='"+formula+"' WHERE ID="+serviceID+";");
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
            reloadCosts(serviceID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static public void updateEstateByID(Integer ID, String city, String street, String building, String apartment, String hotWater, String coldWater, String estateSpace, String numberOfRegisteredPeople){
        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Estate SET City='"+city+"' AND Street='"+street+"' AND Building='"+building+"' AND Apartment='"+apartment+"' AND Hot_water="+hotWater+" AND Cold_water="+coldWater+" AND Estate_space="+estateSpace+" AND Number_of_registered_people="+numberOfRegisteredPeople+" WHERE ID="+ID+";");
            if (statement!= null) statement.close();
            if (connection!= null) connection.close();
        }catch(SQLException e){
            e.printStackTrace(System.err);
        }
    }
    static private void reloadCosts(Integer serviceID){
        String formula = getService(serviceID).getFormula();
        getProvisionsByServiceID(serviceID).forEach(provision -> {
            Estate estate = DataBaseConnect.getEstate(provision.getEstateID());
            assert estate != null;
            Double newCost = getEvaluatedFormula(formatFormula(formula, estate.getColdWater(), estate.getHotWater(), estate.getEstateSpace(), estate.getNumberOfRegistredPeople()));
            updateProvisionCost(provision.getId(), newCost);
        });
    }
    static private String formatFormula(String unformattedFormula, Double coldWater, Double hotWater, Double space, Integer peopleCount){
        return unformattedFormula.replace("c", String.valueOf(coldWater)).replace("h", String.valueOf(hotWater)).replace("s", String.valueOf(space)).replace("p", String.valueOf(peopleCount));
    }
    static private Double getEvaluatedFormula(String formula) {
        Expression expression = new ExpressionBuilder(formula).build();
        return expression.evaluate();
    }
}