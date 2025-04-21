package layer_architecture.domain;

import java.util.Optional;

public interface InventoryRepository {
    Optional<Inventory> load();
    void save(Inventory inventory);
}