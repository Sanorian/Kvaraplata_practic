package sanorian.app.kvaraplata_practic;

public class Estate {
    private String city, street, building;
    private Integer id, apartment, numberOfRegistredPeople;
    private Double estateSpace, coldWater, hotWater;
    public Estate(Integer id, String city, String street, String building, Integer apartment, Double hotWater, Double coldWater, Double estateSpace, Integer numberOfRegistredPeople){
        this.id = id;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.hotWater = hotWater;
        this.coldWater = coldWater;
        this.estateSpace = estateSpace;
        this.numberOfRegistredPeople = numberOfRegistredPeople;
    }

    public Integer getId() {
        return this.id;
    }
    public Double getEstateSpace() {
        return this.estateSpace;
    }
    public Integer getApartment() {
        return this.apartment;
    }
    public Integer getNumberOfRegistredPeople() {
        return this.numberOfRegistredPeople;
    }
    public String getBuilding() {
        return this.building;
    }
    public String getCity() {
        return this.city;
    }
    public String getStreet() {
        return this.street;
    }
    public Double getColdWater() {return coldWater;}
    public Double getHotWater() {return hotWater;}
}