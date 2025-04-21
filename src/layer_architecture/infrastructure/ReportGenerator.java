package layer_architecture.infrastructure;

import layer_architecture.domain.Inventory;
import layer_architecture.domain.InventoryItem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public String generateReportText(Inventory inventory) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ОТЧЁТ О ТЕКУЩИХ ЗАПАСАХ ===\n");
        List<InventoryItem> items = inventory.getCurrentStock();
        if (items.isEmpty()) {
            sb.append("Инвентарь пуст.\n");
            return sb.toString();
        }
        for (InventoryItem item : items) {
            sb.append("Продукт: ").append(item.getProduct().getName()).append("\n");
            sb.append("  Количество: ").append(item.getQuantity()).append("\n");
            sb.append("  Температурный режим: ").append(item.getProduct().getTemperatureMode()).append("\n");
            sb.append("  Срок годности до: ").append(item.getExpiryDate().format(DATE_FORMAT)).append("\n");
            sb.append("  Критический уровень: ").append(item.getProduct().getCriticalLevel()).append("\n");
            sb.append("  Минимальный запас: ").append(item.getProduct().getMinimumStock()).append("\n");
            sb.append("  Оптимальный запас: ").append(item.getProduct().getOptimalStock()).append("\n");
            sb.append("  Статус: ")
                    .append(item.isExpired(java.time.LocalDate.now()) ? "ПРОСРОЧЕН" :
                            (item.isCritical() ? "КРИТИЧЕСКИЙ ЗАПАС" : "ОК"))
                    .append("\n");
            sb.append("------------------------------\n");
        }
        return sb.toString();
    }
    public void saveReportToFile(Inventory inventory, String filePath) {
        String report = generateReportText(inventory);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(report);
        } catch (IOException e) {
            System.err.println("Ошибка при записи отчета: " + e.getMessage());
        }
    }
}
