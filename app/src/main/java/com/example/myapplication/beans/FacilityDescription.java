package com.example.myapplication.beans;

import com.example.myapplication.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FacilityDescription {
    private final String description;
    private final Map<String, String> openingHours;
    private final List<String> facilities;
    private final List<String> sports;

    public FacilityDescription(String description) {
        this.description = description;
        Map<String, String> openingHours = new HashMap<>();
        List<String> facilities = new ArrayList<>();
        List<String> sports = new ArrayList<>();

        description = StringUtil.removeEmptyLines(description);
        Scanner scanner = new Scanner(description);
        while (scanner.hasNextLine()) {
            String key = StringUtil
                    .removeSuffixIfExists(scanner.nextLine().trim(), ":");
            if (!scanner.hasNextLine()) {
                scanner.close();
                this.openingHours = new HashMap<>();
                this.facilities = new ArrayList<>();
                this.sports = new ArrayList<>();
                return;
            }
            String value = scanner.nextLine().trim();
            if (key.startsWith("Opening Hours")) {
                openingHours.put(key, value);
            } else if (key.trim().equals("Facilities")) {
                StringTokenizer stringTokenizer =
                        new StringTokenizer(value, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    facilities.add(stringTokenizer.nextToken().trim());
                }
            } else if (key.trim().equals("Sports")) {
                StringTokenizer stringTokenizer =
                        new StringTokenizer(value, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    sports.add(stringTokenizer.nextToken().trim());
                }
            } else {
                continue;
            }
        }
        scanner.close();

        this.openingHours = openingHours;
        this.facilities = facilities;
        this.sports = sports;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getOpeningHours() {
        return openingHours;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public List<String> getSports() {
        return sports;
    }
}
