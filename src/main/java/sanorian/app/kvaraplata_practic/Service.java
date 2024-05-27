package sanorian.app.kvaraplata_practic;

public class Service {
    private String name, formula;
    private Integer id;
    public Service(Integer id, String name, String formula){
        this.id = id;
        this.name = name;
        this.formula = formula;
    }
    public Integer getId() {
        return this.id;
    }
    public String getFormula() {
        return this.formula;
    }
    public String getName() {
        return this.name;
    }
}