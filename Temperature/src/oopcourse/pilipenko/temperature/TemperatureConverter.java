package oopcourse.pilipenko.temperature;

public class TemperatureConverter {
    public static double convert(double temperature, TemperatureScale fromScale, TemperatureScale toScale) {
        if (fromScale == toScale) {
            return temperature;
        }

        if (fromScale == TemperatureScale.CELSIUS) {
            return covertFromCelsius(temperature, toScale);
        }

        if (fromScale == TemperatureScale.FAHRENHEIT) {
            return convertFromFahrenheit(temperature, toScale);
        }

        return convertFromKelvin(temperature, toScale);
    }

    private static double convertFromKelvin(double kelvinDegrees, TemperatureScale toScale) {
        if (toScale == TemperatureScale.CELSIUS) {
            return kelvinDegrees - 273.15;
        }

        return (9.0 / 5.0) * (kelvinDegrees - 273.15) + 32.0;
    }

    private static double convertFromFahrenheit(double fahrenheitDegrees, TemperatureScale toScale) {
        if (toScale == TemperatureScale.CELSIUS) {
            return (5.0 / 9.0) * (fahrenheitDegrees - 32.0);
        }

        return (5.0 / 9.0) * (fahrenheitDegrees - 32.0) + 273.15;
    }

    private static double covertFromCelsius(double celsiusDegrees, TemperatureScale toScale) {
        if (toScale == TemperatureScale.FAHRENHEIT) {
            return celsiusDegrees * (9.0 / 5) + 32;
        }

        return celsiusDegrees + 273.15;
    }
}
