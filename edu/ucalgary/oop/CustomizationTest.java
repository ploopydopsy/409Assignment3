package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class CustomizationTest {
    private String[] applicableCategories = {"Pizza", "Calzone"};
    private String expectedName = "Extra";
    private double expectedPriceModifier = 1.5;
    private String expectedSeries = "Cheese";
    private Customization customization;

    @Before
    public void setUp() {
       customization = new Customization(expectedName, 
         expectedPriceModifier, expectedSeries, applicableCategories);
    }

    @Test
    public void testConstructor() {
        assertEquals("Name should be set by constructor", expectedName, 
             customization.getName());
        assertEquals("Price modifier should be set by constructor", expectedPriceModifier, 
            customization.getPriceModifier(), 0.0);
        assertEquals("Series should be set by constructor", expectedSeries, 
            customization.getSeries());
        assertArrayEquals("Applicable categories should be set by constructor", 
            applicableCategories, customization.getApplicableCategories());
    }

    @Test
    public void testAppliesTo() {
        MenuItem pizza = new MenuItem("Pepperoni Pizza", 12.0, "Pizza");
        assertTrue("Customization should apply to pizza", customization.appliesTo(pizza));
    }

    @Test
    public void testCustomizationDoesNotApplyToItem() {
        MenuItem drink = new MenuItem("Cola", 2.0, "Drink");
        assertFalse("Customization should not apply to drink", 
           customization.appliesTo(drink));
    }


    @Test
    public void testDeepCopyConstructor() {
        Customization copy = new Customization(customization);

        assertEquals("Copy name should be the same", customization.getName(), 
            copy.getName());
        assertEquals("Copy price modifier should be the same", 
            customization.getPriceModifier(), copy.getPriceModifier(), 0.0);
        assertEquals("Copy series should be the same", customization.getSeries(), 
            copy.getSeries());
        assertArrayEquals("Copy applicable categories should be the same", 
            customization.getApplicableCategories(), copy.getApplicableCategories());
        assertNotSame("Original and copy should not be the same instance", customization, 
            copy);
    }
}

