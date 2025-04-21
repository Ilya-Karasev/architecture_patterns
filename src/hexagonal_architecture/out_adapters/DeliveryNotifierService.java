package hexagonal_architecture.out_adapters;

import hexagonal_architecture.domain.SupplyOrder;
import hexagonal_architecture.out_ports.DeliveryNotifier;

public class DeliveryNotifierService implements DeliveryNotifier {
    @Override
    public void notifySupplier(SupplyOrder order) {
        System.out.println("Заказ отправлен поставщику: " + order.getId().getId() +
                " | Кол-во товаров: " + order.getProducts().size());
    }
}
