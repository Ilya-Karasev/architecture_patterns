package layer_architecture.domain;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int shelfLifeDays;
    private final int criticalLevel;
    private final String temperatureMode;
    private final int minimumStock;
    private final int optimalStock;

    public Product(String name, int shelfLifeDays, int criticalLevel, String temperatureMode, int minimumStock, int optimalStock) {
        this.name = name;
        this.shelfLifeDays = shelfLifeDays;
        this.criticalLevel = criticalLevel;
        this.temperatureMode = temperatureMode;
        this.minimumStock = minimumStock;
        this.optimalStock = optimalStock;
    }

    public String getName() {
        return name;
    }

    public int getShelfLifeDays() {
        return shelfLifeDays;
    }

    public int getCriticalLevel() {
        return criticalLevel;
    }

    public String getTemperatureMode() {
        return temperatureMode;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public int getOptimalStock() {
        return optimalStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return name.equalsIgnoreCase(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}