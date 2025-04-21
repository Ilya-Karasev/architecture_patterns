package hexagonal_architecture.domain;

import java.util.Map;

public class Forecast {
    private final Map<String, Integer> predictedQuantities;
    public Forecast(Map<String, Integer> predictedQuantities) {
        this.predictedQuantities = predictedQuantities;
    }
    public Map<String, Integer> getPredictedQuantities() {
        return predictedQuantities;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Прогноз потребности:\n");
        predictedQuantities.forEach((productId, quantity) -> {
            sb.append("- Продукт ID: ").append(productId).append(", количество: ").append(quantity).append(" шт.\n");
        });
        return sb.toString();
    }
}
