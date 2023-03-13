package oopcourse.pilipenko.csv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Csv {
    String htmlFile = "D:\\Java IT\\table.html";

    public void convertCSVtoHTML(String csvFile) {
        List<List<String>> table = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                List<String> rows = parseCsvLine(line);
                List<String> htmlRow = new ArrayList<>();
                String[] cells;

                for (String cell : rows) {
                    cells = cell.split(",");

                    for (String c : cells) {
                        htmlRow.add(c.trim());
                    }
                }

                table.add(htmlRow);
            }

        } catch (Exception e) {
            System.out.println("Не получилось прочитать файл csv");
            e.printStackTrace();
        }

        writeHtml(table, htmlFile);
    }

    private static List<String> parseCsvLine(String line) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;

        for (char symbol : line.toCharArray()) {
            if (symbol == '\"') {
                inQuotes = !inQuotes;
            } else if (symbol == ',' && !inQuotes) {
                values.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            } else {
                stringBuilder.append(symbol);
            }
        }

        String str = replaceSpecialCharacters(stringBuilder.toString());
        values.add(str);

        return values;
    }

    private static String replaceSpecialCharacters(String line) {
        StringBuilder stringBuilder = new StringBuilder();
        int lineLength = line.length();

        for (int i = 0; i < lineLength; i++) {
            char symbol = line.charAt(i);

            switch (symbol) {
                case '&' -> stringBuilder.append("&amp;");
                case '<' -> stringBuilder.append("&lt;");
                case '>' -> stringBuilder.append("&gt;");
                default -> stringBuilder.append(symbol);
            }
        }

        return stringBuilder.toString();
    }

    public static void writeHtml(List<List<String>> table, String htmlFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile))) {
            writer.write("<table>");
            writer.newLine();

            for (List<String> row : table) {
                writer.write("<tr>");

                for (String cell : row) {
                    writer.write("<td>");
                    writer.write(cell);
                    writer.write("</td>");
                }

                writer.write("</tr>");
                writer.newLine();
            }

            writer.write("</table>");
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
