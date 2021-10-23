package com.example.myapplication.beans;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Sport implements Serializable {
    public enum SportType {
        INDOOR("Indoor"), OUTDOOR("Outdoor"), INDOOR_OUTDOOR("Indoor/Outdoor");

        private final String name;

        SportType(String name) {
            this.name = name;
        }

        public static SportType getType(String typeString) {
            for (SportType type : SportType.values()) {
                if (type.toString().equals(typeString)) {
                    return type;
                }
            }
            return null;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }

    private int id;
    private String name;
    private String alternativeName;
    private SportType type;
    private boolean isSelected = false;

//    public Sport(){}

    public Sport(int id, String name, String alternativeName, SportType type) {
        this.id = id;
        this.name = name;
        this.alternativeName = alternativeName;
        this.type = type;
    }

    public void setId(int id) {this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlternativeName(String alternativeName) {
        this.alternativeName = alternativeName;
    }

    public void setType(SportType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public SportType getType() {
        return type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
