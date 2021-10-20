package com.example.myapplication.sportsImage;

import com.example.myapplication.R;
import com.example.myapplication.beans.Sport;

public class SportsImage {
    public int SportsToImage(Sport sport) {
        int image;
        String name = sport.getName();
        String[] sportsName = new String[12];
        sportsName[0] = "Freeplay";
        sportsName[1] = "Futsal";
        sportsName[2] = "Football";
        sportsName[3] = "Running";
        sportsName[4] = "Squash";
        sportsName[5] = "Badminton";
        sportsName[6] = "Swimming";
        sportsName[7] = "Tennis";
        sportsName[8] = "Hockey";
        sportsName[9] = "Netball";
        sportsName[10] = "Basketball";
        sportsName[11] = "Gym";

        if (name.equals(sportsName[0])) {
            image = R.drawable.outdoor;
        } else if (name.equals(sportsName[1])) {
            image = R.drawable.futsal;
        } else if (name.equals(sportsName[2])) {
            image = R.drawable.soccer;
        } else if (name.equals(sportsName[3])) {
            image = R.drawable.run;
        } else if (name.equals(sportsName[4])) {
            image = R.drawable.squash;
        } else if (name.equals(sportsName[5])) {
            image = R.drawable.badminton;
        } else if (name.equals(sportsName[6])) {
            image = R.drawable.swimming;
        } else if (name.equals(sportsName[7])) {
            image = R.drawable.tennis;
        } else if (name.equals(sportsName[8])) {
            image = R.drawable.hockey;
        } else if (name.equals(sportsName[9])) {
            image = R.drawable.netball;
        } else if (name.equals(sportsName[10])) {
            image = R.drawable.basketball;
        } else if (name.equals(sportsName[11])) {
            image = R.drawable.dumbbell;
        } else {
            image = 0;
        }
        return image;
    }
}
