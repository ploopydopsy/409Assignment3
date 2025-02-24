package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class OrderItemTest {
    // MenuItem details
    private String expectedItemName = "Veggie Delight";
    private double expectedPrice = 12.0;
    private String expectedCategory = "Pizza";
    private MenuItem pizza;

    // Customization details
    private String expectedCustomizationName = "Extra Thin";
    private double expectedCustomizationPrice = 0.5;
    private String expectedCustomizationSeries = "Crust";
    private String[] expectedCustomizationCategories = {"Pizza"};
    private Customization expectedCustomization;
    

    @Before
    public void setUp() {
        pizza = new MenuItem(expectedItemName, expectedPrice, expectedCategory);
        expectedCustomization = new Customization(expectedCustomizationName,
            expectedCustomizationPrice, expectedCustomizationSeries,
            expectedCustomizationCategories);
    }
 

    @Test
    public void testConstructor() {
        OrderItem orderItem = new OrderItem(pizza);
        assertEquals("Constructor should store MenuItem", expectedItemName, 
            orderItem.getItem().getName());
    }

    @Test 
    public void testToStringNoCustomization() {
        OrderItem orderItem = new OrderItem(pizza);
        String expectedString = "Veggie Delight - $12.00";
        String orderItemString = orderItem.toString();
        assertEquals("toString without customizations should return just item",
            expectedString, orderItemString);
    }

    @Test
    public void testToStringWithCustomization() throws Exception {
        OrderItem orderItem = new OrderItem(pizza);
        orderItem.addCustomization(expectedCustomization);
        String expectedString = "Veggie Delight - $12.00\n  Extra Thin Crust - $0.50";
        String orderItemString = orderItem.toString();
        assertEquals("toString should return item and customizations",
            expectedString, orderItemString);
    }

    @Test
    public void testAddCustomization() throws Exception {
        OrderItem orderItem = new OrderItem(pizza);
        orderItem.addCustomization(expectedCustomization);
        double expectedPrice = expectedCustomization.getPriceModifier() + 
            pizza.getPrice(); 

        assertEquals("Total price should include customization cost", expectedPrice, 
            orderItem.calculateTotalPrice(), 0.0);
    }

    @Test
    public void testAddMultipleCustomizations() throws Exception {
        String[] extraCustomizationCategories = {"Pizza", "Calzone"};
        Customization secondCustomization = new Customization("Well", 1.5,
            "Doneness", extraCustomizationCategories);
        Customization thirdCustomization = new Customization("Light", 0.5,
            "Cheese", extraCustomizationCategories);

        OrderItem orderItem = new OrderItem(pizza);
        orderItem.addCustomization(expectedCustomization);
        orderItem.addCustomization(secondCustomization);
        orderItem.addCustomization(thirdCustomization);

        double expectedPrice = expectedCustomization.getPriceModifier() + 
            pizza.getPrice() + secondCustomization.getPriceModifier() +
            thirdCustomization.getPriceModifier(); 
        String expectedString = "Veggie Delight - $12.00\n  Extra Thin Crust - $0.50\n  Well Doneness - $1.50\n  Light Cheese - $0.50";

        assertEquals("Total price should include all customization cost", expectedPrice, 
            orderItem.calculateTotalPrice(), 0.0);
        String orderItemString = orderItem.toString();
        assertEquals("toString should return a list of item plus customizations",
            expectedString, orderItemString);
    }


    @Test
    public void testDeepCopyConstructor() throws Exception {
        OrderItem original = new OrderItem(pizza);

        // Add two customizations to the original order
        String[] secondCustomizationCategories = {"Pizza", "Calzone"};
        Customization secondCustomization = new Customization("Well-done", 0.0,
            "Doneness", secondCustomizationCategories);
        original.addCustomization(expectedCustomization);
        original.addCustomization(secondCustomization);

        OrderItem copy = new OrderItem(original);

        assertNotSame("Original and copy should not be the same instance", original, copy);
        assertNotSame("Nested MenuItem object should be deep copied", 
            original.getItem(), copy.getItem());
        assertNotSame("Nested Customization object should be deep copied", 
            original.getCustomizations()[0], copy.getCustomizations()[0]);
        assertNotSame("Nested Customization object should be deep copied", 
            original.getCustomizations()[1], copy.getCustomizations()[1]);
    }

    @Test(expected = Exception.class)
    public void testInvalidCustomization() throws Exception {
        MenuItem calazone = new MenuItem("Veggie Delight", 12.0, "Calzone");
        OrderItem orderItem = new OrderItem(calazone);
        orderItem.addCustomization(expectedCustomization);
    }

    @Test(expected = Exception.class)
    public void testDuplicateCustomizationSeries() throws Exception {
        OrderItem orderItem = new OrderItem(pizza);
        Customization conflictingCustomization = new Customization(
            "Thick", 1.0, expectedCustomizationSeries, expectedCustomizationCategories);

        orderItem.addCustomization(expectedCustomization);
        orderItem.addCustomization(conflictingCustomization);
    }
}

