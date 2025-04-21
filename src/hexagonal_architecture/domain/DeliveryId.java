package hexagonal_architecture.domain;

import java.util.UUID;

public class DeliveryId {
    private final String id;
    public DeliveryId() {
        this.id = UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryId)) return false;
        return id.equals(((DeliveryId) o).id);
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
