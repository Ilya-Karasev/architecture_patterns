package hexagonal_architecture.out_ports;

import hexagonal_architecture.domain.SupplyOrder;

public interface DeliveryNotifier {
    void notifySupplier(SupplyOrder order);
}