package at.fhv.master.laendleenergy.domain;

public enum ElectricityPricingPlan {
    DAYNIGHT(Supplier.VKW, 12.12, "Tag/Nacht"),
    NORMAL(Supplier.VKW, 14.76, "Normal");

    private final Supplier supplier;
    private final double averagePricePerKwh;
    private final String name;

    ElectricityPricingPlan(Supplier supplier, double averagePricePerKwh, String name) {
        this.supplier = supplier;
        this.averagePricePerKwh = averagePricePerKwh;
        this.name = name;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public double getAveragePricePerKwh() {
        return averagePricePerKwh;
    }

    public String getName() {
        return name;
    }
}
