package hexagonal_architecture.out_ports;

import hexagonal_architecture.domain.Forecast;

public interface ForecastingPort {
    Forecast generateForecast();
}
