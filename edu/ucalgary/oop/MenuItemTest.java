package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class MenuItemTest {
    private String expectedName = "Pepperoni";
    private double expectedPrice = 12.0;
    private String expectedCategory = "Pizza";
    private MenuItem pizza;

    @Before
    public void setUp() {
        pizza = new MenuItem(expectedName, expectedPrice, expectedCategory);
    }

    @Test
    public void testConstructor() {
        assertEquals("Name should be name given at construction", expectedName, 
            pizza.getName());
        assertEquals("Price should be what was set with constructor", expectedPrice, 
            pizza.getPrice(), 0.0);
        assertEquals("Category should be same as given at constructions", 
            expectedCategory, pizza.getCategory());
    }

    @Test
    public void testDeepCopyConstructor() {
        MenuItem copy = new MenuItem(pizza);

        assertEquals("Copy should have same name", pizza.getName(), copy.getName());
        assertEquals("Copy should have same price", pizza.getPrice(), copy.getPrice(), 
            0.0);
        assertEquals("Copy should have same category", pizza.getCategory(), 
            copy.getCategory());
        assertNotSame("Original and copy should not be the same instance", pizza, copy);
    }

    @Test
    public void testInvalidCategory() {
        String invalidCategory = "Dessert";
        String expectedMessage = 
            "Invalid category. Available categories are: Pizza, Calzone, Drink";

        try {
            MenuItem dessert = new MenuItem("Chocolate Cake", 5.0, invalidCategory);
            fail("Invalid category should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Exception message should list categories", 
                expectedMessage, e.getMessage());
        }
    }

}

