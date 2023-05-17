package oopcourse.pilipenko.temperature;

public enum TemperatureScale {
    CELSIUS("Celsius"),
    FAHRENHEIT("Fahrenheit"),
    KELVIN("Kelvin");

    private final String name;

    TemperatureScale(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
