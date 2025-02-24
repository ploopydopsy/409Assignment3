package edu.ucalgary.oop;
import java.util.Arrays;

public class Customization {
    // class should be public
    private String name;
    private double priceModifier;
    private String series;
    private String[] applicableCategories;

    // parameterized constructor
    public Customization(String name, double priceModifier, String series, String[] applicableCategories) {
        this.name = name;
        this.priceModifier = priceModifier;
        this.series = series;
        this.applicableCategories = Arrays.copyOf(applicableCategories, applicableCategories.length);
    }

    // deep copy constructor
    public Customization(Customization other) {
        this.name = other.name;
        this.priceModifier = other.priceModifier;
        this.series = other.series;
        this.applicableCategories = Arrays.copyOf(other.applicableCategories, other.applicableCategories.length);
    }

    public boolean appliesTo(MenuItem item) {
        for (String category : applicableCategories) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public double getPriceModifier() {
        return priceModifier;
    }

    public String getSeries() {
        return series;
    }

    public String[] getApplicableCategories() {
        return Arrays.copyOf(applicableCategories, applicableCategories.length);
    }
}

