package com.danesisinni;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.SortedMap;
import java.util.TreeMap;

public class ISO3EnumGenerator {

    private static final String WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3";

    public static void main(String[] args) throws IOException {

        SortedMap<String, String> codeToCountryName = getCountryCodes();
        String className = "Country";
        String rawClass = generateRawClass(className, codeToCountryName);

        Path path = Paths.get(className + ".java");
        try(BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(rawClass);
        }

    }

    private static String generateRawClass(String className, SortedMap<String, String> codeToCountryName) {
        StringBuilder rawClass = new StringBuilder();

        rawClass.append("public enum ");
        rawClass.append(className);
        rawClass.append(" { \n\n");

        for (String code : codeToCountryName.keySet()) {
            rawClass.append("\t");
            rawClass.append(code);
            rawClass.append("(\"");
            rawClass.append(codeToCountryName.get(code).toUpperCase());
            rawClass.append("\"),\n");
        }
        rawClass.deleteCharAt(rawClass.length() - 1);
        rawClass.deleteCharAt(rawClass.length() - 1);
        rawClass.append(";\n\n");

        rawClass.append("private final String countryName;\n\n");

        rawClass.append(className);
        rawClass.append(" (String countryName) {\n\tthis.countryName = countryName; \n}\n\n");

        rawClass.append("public String getCountryName() {\n\treturn this.countryName;\n}\n\n");
        rawClass.append("}");

        return rawClass.toString();
    }

    public static SortedMap<String, String> getCountryCodes() throws IOException {
        SortedMap<String, String> codesToCountryName = new TreeMap<>();
        Document document = Jsoup.connect(WIKIPEDIA_URL).get();
        Element table = document.select("tbody").first();

        final Elements rows = document.getElementsByTag("tr");
        for (int i = 1; i < rows.size(); i++) {
            Elements data = rows.get(i).getElementsByTag("td");
            String code = data.get(0).text();
            String country = data.get(1).text();

            codesToCountryName.put(code, country);

            if (code.equals("ZWE")) {
                break;
            }
        }

        return codesToCountryName;
    }
}
