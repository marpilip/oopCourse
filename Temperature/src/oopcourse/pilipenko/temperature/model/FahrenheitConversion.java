package oopcourse.pilipenko.temperature.model;

public class FahrenheitConversion implements TemperatureConversion {
    @Override
    public double convertToCelsius(double temperature) {
        return (temperature - 32) * 5.0 / 9.0;
    }

    @Override
    public double convertFromCelsius(double temperature) {
        return temperature * 9.0 / 5.0 + 32;
    }
}
