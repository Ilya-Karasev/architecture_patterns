package hexagonal_architecture.domain;

import java.util.UUID;

public class SupplyOrderId {
    private final String id;
    public SupplyOrderId() {
        this.id = UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplyOrderId)) return false;
        return id.equals(((SupplyOrderId) o).id);
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
