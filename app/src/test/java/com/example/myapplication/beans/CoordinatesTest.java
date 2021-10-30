package com.example.myapplication.beans;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CoordinatesTest {
    @Test
    public void test(){
        Coordinates start = new Coordinates(1.35152, 103.68053);
        Coordinates des = new Coordinates(1.35041, 103.68473);
        assertTrue(Math.abs(start.getDistance(des) - 0.48) < 0.01);
    }
}
