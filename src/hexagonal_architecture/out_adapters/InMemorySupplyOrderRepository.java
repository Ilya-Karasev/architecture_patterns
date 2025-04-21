package hexagonal_architecture.out_adapters;

import hexagonal_architecture.domain.SupplyOrder;
import hexagonal_architecture.domain.SupplyOrderId;
import hexagonal_architecture.out_ports.SupplyOrderRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemorySupplyOrderRepository implements SupplyOrderRepository {
    private final Map<SupplyOrderId, SupplyOrder> db = new HashMap<>();
    @Override
    public void save(SupplyOrder order) {
        db.put(order.getId(), order);
    }
    @Override
    public Optional<SupplyOrder> findById(SupplyOrderId id) {
        return Optional.ofNullable(db.get(id));
    }
    @Override
    public List<Optional<SupplyOrder>> getAll() {
        return db.values().stream()
                .map(Optional::ofNullable)
                .collect(Collectors.toList());
    }
}
