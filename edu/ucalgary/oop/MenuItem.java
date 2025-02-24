package edu.ucalgary.oop;

import java.util.Arrays;

public class MenuItem {
    public static final String[] AVAILABLE_CATEGORIES = {"Pizza", "Calzone", "Drink"};

    private final String name;
    private final double price;
    private String category;

    public MenuItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        setCategory(category); // Validate category
    }

    public MenuItem(MenuItem other) {
        this(other.name, other.price, other.category);
    }

    public String getName() {
        return name; // Corrected
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category; // Corrected
    }

    private void setCategory(String category) {
        boolean validCategory = false;
        for (String valid : AVAILABLE_CATEGORIES) {
            if (valid.equalsIgnoreCase(category)) {
                validCategory = true;
                break;
            }
        }
        if (!validCategory) {
            throw new IllegalArgumentException("Invalid category. Available categories are: Pizza, Calzone, Drink");
        }
        this.category = category;
    }

    public static String[] getAvailableCategories() {
        return Arrays.copyOf(AVAILABLE_CATEGORIES, AVAILABLE_CATEGORIES.length);
    }
}