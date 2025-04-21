package hexagonal_architecture.out_ports;

import hexagonal_architecture.domain.Delivery;
import hexagonal_architecture.domain.QualityCheckResult;

public interface QualityChecker {
    QualityCheckResult check(Delivery delivery);
}