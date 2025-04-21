package hexagonal_architecture.out_ports;

import hexagonal_architecture.domain.SupplyOrder;
import hexagonal_architecture.domain.SupplyOrderId;

import java.util.List;
import java.util.Optional;

public interface SupplyOrderRepository {
    List<Optional<SupplyOrder>> getAll();
    void save(SupplyOrder order);
    Optional<SupplyOrder> findById(SupplyOrderId id);
}