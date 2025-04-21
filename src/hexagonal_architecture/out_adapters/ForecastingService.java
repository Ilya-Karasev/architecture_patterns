package hexagonal_architecture.out_adapters;

import hexagonal_architecture.domain.Forecast;
import hexagonal_architecture.domain.Product;
import hexagonal_architecture.domain.SupplyOrder;
import hexagonal_architecture.out_ports.ForecastingPort;
import hexagonal_architecture.out_ports.SupplyOrderRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class ForecastingService implements ForecastingPort {
    private final SupplyOrderRepository supplyOrderRepository;
    private final Random random = new Random();
    public ForecastingService(SupplyOrderRepository supplyOrderRepository) {
        this.supplyOrderRepository = supplyOrderRepository;
    }
    @Override
    public Forecast generateForecast() {
        Map<String, Integer> predictedQuantities = new HashMap<>();
        for (Optional<SupplyOrder> orderOpt : supplyOrderRepository.getAll()) {
            orderOpt.ifPresent(order -> {
                for (Product product : order.getProducts()) {
                    predictedQuantities.merge(product.getName(), product.getQuantity(), Integer::sum);
                }
            });
        }
        predictedQuantities.replaceAll((name, quantity) ->
                (int) Math.ceil(quantity * random.nextDouble(0.8, 1.2)));

        return new Forecast(predictedQuantities);
    }
}

