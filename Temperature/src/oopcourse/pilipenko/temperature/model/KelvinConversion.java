package oopcourse.pilipenko.temperature.model;

public class KelvinConversion implements TemperatureConversion {
    @Override
    public double convertToCelsius(double temperature) {
        return temperature - 273.15;
    }

    @Override
    public double convertFromCelsius(double temperature) {
        return temperature + 273.15;
    }
}
