package oopcourse.pilipenko.temperature.model;

public class CelsiusConversion implements TemperatureConversion {
    @Override
    public double convertToCelsius(double temperature) {
        return temperature;
    }

    @Override
    public double convertFromCelsius(double temperature) {
        return temperature;
    }
}
