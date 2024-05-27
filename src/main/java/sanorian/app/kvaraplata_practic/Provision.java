package sanorian.app.kvaraplata_practic;

public class Provision {
    private Integer id, serviceID, estateID;
    private Double cost;
    public Provision(Integer id, Integer serviceID, Integer estateID, Double cost){
        this.id = id;
        this.serviceID = serviceID;
        this.estateID = estateID;
        this.cost = cost;
    }
    public Double getCost() {
        return this.cost;
    }
    public Integer getEstateID() {
        return this.estateID;
    }
    public Integer getId() {
        return this.id;
    }
    public Integer getServiceID() {
        return this.serviceID;
    }
}