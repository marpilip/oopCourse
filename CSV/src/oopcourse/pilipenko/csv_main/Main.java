package oopcourse.pilipenko.csv_main;

import oopcourse.pilipenko.csv.CsvToHtmlConverter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String csvFile = "D:\\Java IT\\4.csv";
        String htmlFile = "D:\\Java IT\\table.html";
        CsvToHtmlConverter csvToHtmlConverter = new CsvToHtmlConverter(csvFile, htmlFile);

        try {
            csvToHtmlConverter.convertCsvToHtml();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}