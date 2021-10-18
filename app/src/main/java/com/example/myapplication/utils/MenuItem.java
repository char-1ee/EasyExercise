package com.example.myapplication.utils;

public class MenuItem<T> extends Box<T> {
    private boolean isSelected = false;

    public MenuItem(T t) {
        super(t);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void select() {
        isSelected = true;
    }

    public void deselect() {
        isSelected = false;
    }
}
