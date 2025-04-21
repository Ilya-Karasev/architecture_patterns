package hexagonal_architecture.out_adapters;

import hexagonal_architecture.domain.Delivery;
import hexagonal_architecture.domain.DeliveryId;
import hexagonal_architecture.domain.SupplyOrderId;
import hexagonal_architecture.out_ports.DeliveryRepository;
import java.util.*;

public class InMemoryDeliveryRepository implements DeliveryRepository {
    private final Map<DeliveryId, Delivery> db = new HashMap<>();
    @Override
    public void save(Delivery delivery) {
        db.put(delivery.getId(), delivery);
    }
    @Override
    public Optional<Delivery> findById(DeliveryId id) {
        return Optional.ofNullable(db.get(id));
    }
    @Override
    public Optional<Delivery> findBySupplyOrderId(SupplyOrderId supplyOrderId) {
        return db.values().stream()
                .filter(delivery -> delivery.getSupplyOrderId().equals(supplyOrderId))
                .findFirst();
    }
}