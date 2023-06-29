package oopcourse.pilipenko.temperature.controller;

import oopcourse.pilipenko.temperature.model.*;

public class TemperatureController {
    private final TemperatureConverterModel model;

    public TemperatureController(TemperatureConverterModel model) {
        this.model = model;
    }

    public double convertTemperature(double temperature, String fromScale, String toScale) {
        TemperatureConversion fromConversion = getConversion(fromScale);
        TemperatureConversion toConversion = getConversion(toScale);

        return model.convert(temperature, fromConversion, toConversion);
    }

    private TemperatureConversion getConversion(String scale) {
        return switch (scale) {
            case "Celsius" -> new CelsiusConversion();
            case "Fahrenheit" -> new FahrenheitConversion();
            case "Kelvin" -> new KelvinConversion();
            default -> throw new IllegalArgumentException("Invalid temperature scale");
        };
    }
}
