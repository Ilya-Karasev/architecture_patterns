package hexagonal_architecture.out_ports;

import hexagonal_architecture.domain.Delivery;
import hexagonal_architecture.domain.DeliveryId;
import hexagonal_architecture.domain.SupplyOrderId;

import java.util.Optional;

public interface DeliveryRepository {
    void save(Delivery delivery);
    Optional<Delivery> findById(DeliveryId id);
    Optional<Delivery> findBySupplyOrderId(SupplyOrderId id);
}