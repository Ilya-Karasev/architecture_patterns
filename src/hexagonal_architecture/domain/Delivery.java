package hexagonal_architecture.domain;

import hexagonal_architecture.out_ports.QualityChecker;

import java.util.ArrayList;
import java.util.List;

public class Delivery {
    private final DeliveryId id;
    private final SupplyOrderId supplyOrderId;
    private final List<Product> deliveredProducts;
    private boolean qualityChecked;
    private QualityCheckResult qualityResult;
    public Delivery(DeliveryId id, SupplyOrderId supplyOrderId, List<Product> deliveredProducts) {
        if (deliveredProducts == null || deliveredProducts.isEmpty()) {
            throw new IllegalArgumentException("Поставка должна содержать хотя бы один товар.");
        }
        this.id = id;
        this.supplyOrderId = supplyOrderId;
        this.deliveredProducts = new ArrayList<>(deliveredProducts);
        this.qualityChecked = false;
    }
    public void checkQuality(QualityChecker checker) {
        if (qualityChecked) {
            throw new IllegalStateException("Качество уже проверено.");
        }
        this.qualityResult = checker.check(this);
        this.qualityChecked = true;
    }
    public DeliveryId getId() {
        return id;
    }
    public SupplyOrderId getSupplyOrderId() {
        return supplyOrderId;
    }
    public List<Product> getDeliveredProducts() {
        return List.copyOf(deliveredProducts);
    }
    public boolean isQualityChecked() {
        return qualityChecked;
    }
    public QualityCheckResult getQualityResult() {
        return qualityResult;
    }
}