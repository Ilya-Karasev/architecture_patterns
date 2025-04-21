package hexagonal_architecture.out_adapters;

import hexagonal_architecture.domain.Delivery;
import hexagonal_architecture.domain.Product;
import hexagonal_architecture.domain.QualityCheckResult;
import hexagonal_architecture.domain.SupplyOrder;
import hexagonal_architecture.out_ports.QualityChecker;
import hexagonal_architecture.out_ports.SupplyOrderRepository;

import java.util.List;
import java.util.Optional;

public class DefectChecker implements QualityChecker {
    private final SupplyOrderRepository orderRepository;
    public DefectChecker(SupplyOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public QualityCheckResult check(Delivery delivery) {
        List<Product> deliveredProducts = delivery.getDeliveredProducts();
        boolean allQuantitiesPositive = deliveredProducts.stream().allMatch(p -> p.getQuantity() > 0);
        boolean containsDefective = deliveredProducts.stream().anyMatch(p -> p.getName().toLowerCase().contains("брак"));
        if (!allQuantitiesPositive) {
            return QualityCheckResult.FAILED;
        }
        if (containsDefective) {
            return QualityCheckResult.REQUIRES_REVIEW;
        }
        Optional<SupplyOrder> orderOpt = orderRepository.findById(delivery.getSupplyOrderId());
        if (orderOpt.isEmpty()) return QualityCheckResult.FAILED;
        SupplyOrder order = orderOpt.get();
        List<Product> orderedProducts = order.getProducts();
        if (orderedProducts.size() != deliveredProducts.size()) {
            return QualityCheckResult.FAILED;
        }
        for (Product ordered : orderedProducts) {
            boolean matchFound = deliveredProducts.stream().anyMatch(delivered ->
                    delivered.getName().equalsIgnoreCase(ordered.getName()) &&
                            delivered.getQuantity() == ordered.getQuantity()
            );
            if (!matchFound) {
                return QualityCheckResult.FAILED;
            }
        }
        return QualityCheckResult.PASSED;
    }
}
