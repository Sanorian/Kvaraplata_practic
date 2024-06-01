package sanorian.app.kvaraplata_practic;

public class ProvisionTable {
    private String name, formula;
    private Integer id, serviceID;
    private Double cost;
    public ProvisionTable(String name, Double cost, String formula, Integer id, Integer serviceID){
        this.name = name;
        this.cost = cost;
        this.formula = formula;
        this.id = id;
        this.serviceID = serviceID;
    }
    public String getName() {
        return this.name;
    }
    public Double getCost() {
        return this.cost;
    }
    public Integer getID() {
        return this.id;
    }
    public String getFormula() {
        return this.formula;
    }
    public Integer getServiceID() {
        return serviceID;
    }
}