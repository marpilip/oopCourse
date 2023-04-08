package oopcourse.pilipenko.csv;

import java.io.*;

public class CsvToHtmlConverter {
    private final String csvFile;
    private final String htmlFile;

    public CsvToHtmlConverter(String csvFile, String htmlFile) {
        this.csvFile = csvFile;
        this.htmlFile = htmlFile;
    }

    public void convertCsvToHtml() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile));
             FileWriter fileWriter = new FileWriter(htmlFile)) {
            fileWriter.write("<!DOCTYPE html>\n<html>\n  <head>\n <meta charset=\"utf-8\">\n  <title>New table</title>\n </head>\n <body>\n  <table border=\"3\">");
            String line;

            while ((line = reader.readLine()) != null) {
                fileWriter.write("<tr>\n");
                parseCsvLine(line, fileWriter);
                fileWriter.write("</tr>\n");
            }

            fileWriter.write("  </table>\n </body>\n</html>");
            System.out.println("Произведена успешная конвертация");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }

    }

    private static void parseCsvLine(String line, FileWriter fileWriter) throws IOException {
        int cellStartIndex = 0;
        boolean isQuotes = false;

        for (int i = 0; i <= line.length(); i++) {
            char currentChar;

            if (i < line.length()) {
                currentChar = line.charAt(i);
            } else {
                currentChar = ',';
            }

            if (currentChar == '\"') {
                isQuotes = !isQuotes;
            } else if (currentChar == ',' && !isQuotes) {
                String cell = line.substring(cellStartIndex, i);

                if (cell.startsWith("\"") && cell.endsWith("\"")) {
                    cell = cell.replaceAll("\"{2}", "\"");
                    fileWriter.write("<td>" + replaceSpecialCharacters(cell.substring(1, cell.length() - 1)) + "</td>\n");
                } else {
                    fileWriter.write("<td>" + replaceSpecialCharacters(cell) + "</td>\n");
                }

                cellStartIndex = i + 1;
            }
        }

        fileWriter.write("</td>\n");
    }

    private static String replaceSpecialCharacters(String cell) {
        cell = cell.replace("&", "&amp;");
        cell = cell.replace("<", "&lt;");
        cell = cell.replace(">", "&gt;");
        cell = cell.replace("\"", "&quot;");
        cell = cell.replace("\n", "<br>");

        return cell;
    }
}