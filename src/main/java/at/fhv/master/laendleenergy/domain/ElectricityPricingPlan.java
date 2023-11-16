package at.fhv.master.laendleenergy.domain;

public class ElectricityPricingPlan {
    private Supplier supplier;
    private long averagePricePerKwh;
    private String name;

    public ElectricityPricingPlan() {
    }

    public ElectricityPricingPlan(Supplier supplier, long averagePricePerKwh, String name) {
        this.supplier = supplier;
        this.averagePricePerKwh = averagePricePerKwh;
        this.name = name;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public long getAveragePricePerKwh() {
        return averagePricePerKwh;
    }

    public void setAveragePricePerKwh(long averagePricePerKwh) {
        this.averagePricePerKwh = averagePricePerKwh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
