package oopcourse.pilipenko.temperature_main;

import oopcourse.pilipenko.temperature.controller.TemperatureController;
import oopcourse.pilipenko.temperature.model.TemperatureConverterModel;
import oopcourse.pilipenko.temperature.view.TemperatureConverterView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            TemperatureConverterModel model = new TemperatureConverterModel();
            TemperatureController controller = new TemperatureController(model);
            TemperatureConverterView view = new TemperatureConverterView(controller);

            view.createAndShowGui();
        });
    }
}