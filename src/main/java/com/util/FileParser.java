package com.util;

import com.model.Nominee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yangliu on 18/12/2016.
 */
public class FileParser {

    public static Map<String, Nominee> parseFile(String filePath) {
        Map<String, Nominee> nomineeMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String[] items = currentLine.split("\\|");
                Nominee nominee = new Nominee(items[0], items[1], items[2], items[3]);
                String key = items[0] + "|" + items[3];
                nomineeMap.put(key, nominee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nomineeMap;
    }
}
