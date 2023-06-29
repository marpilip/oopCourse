package oopcourse.pilipenko.temperature.view;

import oopcourse.pilipenko.temperature.controller.TemperatureController;

import javax.swing.*;
import java.awt.*;

public class TemperatureConverterView {
    private final TemperatureController controller;

    private JFrame frame;
    private JPanel panel;
    private JLabel inputLabel;
    private JTextField inputField;
    private JLabel resultLabel;
    private JTextField resultField;
    private JLabel fromLabel;
    private JComboBox<String> fromScaleComboBox;
    private JLabel toLabel;
    private JComboBox<String> toScaleComboBox;
    private JButton convertButton;

    public TemperatureConverterView(TemperatureController controller) {
        this.controller = controller;
    }

    public void createAndShowGui() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Temperature Converter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMaximumSize(new Dimension(800,600));
            frame.setResizable(false);

            panel = new JPanel();
            panel.setLayout(new GridLayout(4, 2));

            inputLabel = new JLabel("Temperature:");
            inputField = new JTextField(10);

            resultLabel = new JLabel("Result:");
            resultField = new JTextField();
            resultField.setEditable(false);

            fromLabel = new JLabel("From:");
            fromScaleComboBox = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"});

            toLabel = new JLabel("To:");
            toScaleComboBox = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"});

            convertButton = new JButton("Convert");
            convertButton.addActionListener(e -> convertTemperature());

            panel.add(inputLabel);
            panel.add(inputField);
            panel.add(resultLabel);
            panel.add(resultField);
            panel.add(fromLabel);
            panel.add(fromScaleComboBox);
            panel.add(toLabel);
            panel.add(toScaleComboBox);

            frame.add(panel);
            frame.add(convertButton, BorderLayout.SOUTH);

            frame.setLocation(650,300);
            frame.pack();
            frame.setVisible(true);
        });

    }

    private void convertTemperature() {
        try {
            double temperature = Double.parseDouble(inputField.getText());
            String fromScale = (String) fromScaleComboBox.getSelectedItem();
            String toScale = (String) toScaleComboBox.getSelectedItem();

            double result = controller.convertTemperature(temperature, fromScale, toScale);
            resultField.setText(String.format("%.2f", result));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input: Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(ex);
        }
    }
}