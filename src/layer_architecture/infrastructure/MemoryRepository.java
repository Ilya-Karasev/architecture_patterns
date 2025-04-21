package layer_architecture.infrastructure;

import layer_architecture.domain.Inventory;
import layer_architecture.domain.InventoryRepository;

import java.io.*;
import java.util.Optional;

public class MemoryRepository implements InventoryRepository {
    private final String filePath;
    public MemoryRepository(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public Optional<Inventory> load() {
        File file = new File(filePath);
        if (!file.exists()) {
            return Optional.empty();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Inventory inventory = (Inventory) ois.readObject();
            return Optional.of(inventory);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке инвентаря: " + e.getMessage());
            return Optional.empty();
        }
    }
    @Override
    public void save(Inventory inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(inventory);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении инвентаря: " + e.getMessage());
        }
    }
}
