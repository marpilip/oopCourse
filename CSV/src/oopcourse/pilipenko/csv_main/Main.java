package oopcourse.pilipenko.csv_main;

import oopcourse.pilipenko.csv.Csv;

public class Main {
    public static void main(String[] args) {
        String csvFile = "D:\\Java IT\\job.csv";
        Csv csv = new Csv();
        csv.convertCSVtoHTML(csvFile);
    }
}