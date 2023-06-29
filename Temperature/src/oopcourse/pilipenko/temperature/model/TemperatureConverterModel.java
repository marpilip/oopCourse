package oopcourse.pilipenko.temperature.model;

public class TemperatureConverterModel {
    public static double convert(double temperature, TemperatureConversion fromScale, TemperatureConversion toScale) {
        if (fromScale.getClass() == toScale.getClass()) {
            return temperature;
        }

        double celsiusTemp = fromScale.convertToCelsius(temperature);

        return toScale.convertFromCelsius(celsiusTemp);
    }
}
