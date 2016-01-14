package com.luxoft.bankapp.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class BankFeedService {
    public static void loadFeed(String file) throws FileNotFoundException {
        Map<String, String> propertiesMap = new TreeMap<>();
        File fileToLoad = new File(file);
        Scanner sc = new Scanner(fileToLoad);
        while (sc.hasNextLine()) {
            String regexForLine = ";";
            String line = sc.nextLine();
            String splittedLine[] = line.split(regexForLine);
            String regexForProperty = "=";
            for (String property : splittedLine) {
                String[] currentProperty = property.split(regexForProperty);
                propertiesMap.put(currentProperty[0], currentProperty[1]);
            }
        }
        BankCommander.currentBank.parseFeed(propertiesMap);
        sc.close();
    }
}
