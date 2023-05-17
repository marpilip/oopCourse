package oopcourse.pilipenko.temperature;

import javax.swing.*;
import java.awt.*;

public class TemperatureConverterGUI extends JFrame {
    private final JComboBox<TemperatureScale> inputScaleComboBox = new JComboBox<>(TemperatureScale.values());
    private final JComboBox<TemperatureScale> outputScaleComboBox = new JComboBox<>(TemperatureScale.values());
    private final JTextField inputTextField = new JTextField(10);
    private final JTextField outputTextField = new JTextField(10);

    public TemperatureConverterGUI() {
        setTitle("Temperature converter");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        outputTextField.setEditable(false);
        JButton convertButton = new JButton("Convert");

        convertButton.addActionListener(e -> {
            try {
                double input = Double.parseDouble(inputTextField.getText());
                TemperatureScale inputScale = (TemperatureScale) inputScaleComboBox.getSelectedItem();
                TemperatureScale outputScale = (TemperatureScale) outputScaleComboBox.getSelectedItem();
                double output = TemperatureConverter.convert(input, inputScale, outputScale);
                outputTextField.setText(String.format("%.2f", output));
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(TemperatureConverterGUI.this,
                        "Invalid input! Please enter a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        inputPanel.add(new JLabel("Input temperature:"));
        inputPanel.add(inputTextField);
        inputPanel.add(inputScaleComboBox);

        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        outputPanel.add(new JLabel("Output temperature:"));
        outputPanel.add(outputTextField);
        outputPanel.add(outputScaleComboBox);

        JPanel controlPanel = new JPanel();
        controlPanel.add(convertButton);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel);
        mainPanel.add(outputPanel);
        mainPanel.add(controlPanel);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
}
