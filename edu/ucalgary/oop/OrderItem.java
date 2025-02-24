package edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.List;

public class OrderItem {
    private MenuItem item;
    private List<Customization> customizations;

    public OrderItem(MenuItem item) {
        this.item = new MenuItem(item); // Deep copy of MenuItem
        this.customizations = new ArrayList<>();
    }

    public OrderItem(OrderItem other) {
        this.item = new MenuItem(other.item); // Deep copy
        this.customizations = new ArrayList<>();
        for (Customization c : other.customizations) {
            this.customizations.add(new Customization(c)); // Deep copy of each Customization
        }
    }

    public void addCustomization(Customization customization) throws Exception {
        if (!customization.appliesTo(item)) {
            throw new Exception("Customization does not apply to this item.");
        }
        for (Customization c : customizations) {
            if (c.getSeries().equalsIgnoreCase(customization.getSeries())) {
                throw new Exception("Duplicate customization in the same series is not allowed.");
            }
        }
        customizations.add(new Customization(customization)); // Deep copy added
    }

    public double calculateTotalPrice() {
        double totalPrice = item.getPrice();
        for (Customization c : customizations) {
            totalPrice += c.getPriceModifier();
        }
        return totalPrice;
    }

    public MenuItem getItem() {
        return new MenuItem(item); // Deep copy returned
    }

    public Customization[] getCustomizations() {
        return customizations.toArray(new Customization[0]);
    }

    // fix me
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(item.getName()).append(" - $").append(String.format("%.2f", item.getPrice()));
        for (Customization c : customizations) {
            sb.append("\n  ").append(c.getName()).append(" ").append(c.getSeries())
                    .append(" - $").append(String.format("%.2f", c.getPriceModifier()));
        }
        return sb.toString();
    }
}
