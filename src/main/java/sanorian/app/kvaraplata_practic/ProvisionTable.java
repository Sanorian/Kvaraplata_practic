package sanorian.app.kvaraplata_practic;

public class ProvisionTable {
    private String name;
    private Double cost;
    public ProvisionTable(String name, Double cost){
        this.name = name;
        this.cost = cost;
    }
    public String getName() {
        return this.name;
    }
    public Double getCost() {
        return this.cost;
    }
}