package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class OrderTest {
    // Order
    private String expectedName = "Dominik Pflug";
    private String phoneNumber = "123-456-7890";
    private String expectedPhone = "1234567890"; // Phone number is normalized
    private Order order;

    // MenuItem
    private String pizzaName = "Margarita";
    private double pizzaPrice = 11.5;
    private String pizzaCategory = "Pizza";
    private MenuItem pizza;

    // Customization
    private String customizationName = "Thin";
    private double customizationPrice = 0.35;
    private String customizationCategory = "Crust";
    private String[] customizationApplies = {"Pizza"};
    private Customization expectedCustomization;

    private OrderItem orderItem;

    // Default setup: Order with one Margarita pizza, thin crust
    @Before 
    public void setUp() throws Exception {
        order = new Order(expectedName, phoneNumber);
        pizza = new MenuItem(pizzaName, pizzaPrice, pizzaCategory);
        orderItem = new OrderItem(pizza);
        expectedCustomization = new Customization(customizationName,
            customizationPrice, customizationCategory, customizationApplies);
        orderItem.addCustomization(expectedCustomization);
        order.addOrderItem(orderItem);
    }
    

    @Test
    public void testConstructor() {
        assertEquals("Constructor should set customer name", expectedName, 
             order.getCustomerName());
        assertEquals("Phone number should be normalized", expectedPhone, 
             order.getPhoneNumber());
    }

    @Test
    public void testPhoneNormalization() {
        String givenPhone = "   1-2a3 4(5__67.890";
        var theOrder = new Order(expectedName, givenPhone);
        assertEquals("Phone number should be normalized", expectedPhone, 
             theOrder.getPhoneNumber());
    }

    @Test
    public void testAddSingleOrderItem() {
        double expectedTotalPrice = pizza.getPrice() + 
               expectedCustomization.getPriceModifier();
        assertEquals("Total price should include item and customizations", 
               expectedTotalPrice, order.calculateTotalPrice(), 0.0);
    }

    @Test
    public void testAddSameItem() {
        // Add two more of the same
        order.addOrderItem(orderItem);
        order.addOrderItem(orderItem);

        double expectedTotalPrice = 3 * (pizza.getPrice() + 
               expectedCustomization.getPriceModifier());

        assertEquals("Total price should include all items and customizations", 
               expectedTotalPrice, order.calculateTotalPrice(), 0.0);
    }

    @Test
    public void testAddMultipleOrderItems() throws Exception {
        // Add a calazone (extra cheese) to the order
        var calazone = new MenuItem("Sausage and Mushroom", 15.0, "Calzone");
        String[] customizationApplicability = {"Pizza", "Calzone"};
        var customizationOne = new Customization("Extra", 0.4, "Cheese", 
            customizationApplicability);
        var orderItemOne = new OrderItem(calazone);
        orderItemOne.addCustomization(customizationOne);
        order.addOrderItem(orderItemOne);

        // Add another pizza (extra cheese, well done, thin crust) to the order
        var pizzaTwo = new MenuItem("Supreme", 16.0, pizzaCategory);
        var customizationTwo = new Customization("Well", 0.0, "Doneness", 
            customizationApplicability);
        var orderItemTwo = new OrderItem(pizzaTwo);
        orderItemTwo.addCustomization(expectedCustomization);
        orderItemTwo.addCustomization(customizationOne);
        orderItemTwo.addCustomization(customizationTwo);
        order.addOrderItem(orderItemTwo);

        // Add a drink to the order
        var drink = new MenuItem("Orange soda", 2.5, "Drink");
        var orderItemThree = new OrderItem(drink);
        order.addOrderItem(orderItemThree);
       
        // Calculate the expected price by looping over each OrderItem
        double expectedTotalPrice = 0.0;
        for (OrderItem item : order.getOrderItems()) {
            expectedTotalPrice += item.calculateTotalPrice();
        }

        assertEquals("Total price should include all items and customizations", 
               expectedTotalPrice, order.calculateTotalPrice(), 0.0);
    }

    @Test
    public void testToStringBasic() {
        var drink = new MenuItem("Bubbly water", 2.0, "Drink");
        var extraItem = new OrderItem(drink);
        var theOrder = new Order(expectedName, phoneNumber);
        theOrder.addOrderItem(extraItem);

        // The expected order number is unknown because tests could be run in any order
        int id = theOrder.getOrderId();
        String expectedString = "Order ID: " + id + "\nCustomer: Dominik Pflug\nPhone: 1234567890\nBubbly water - $2.00\nTotal Price: $2.00";

        assertEquals("toString should return complete order",
            expectedString, theOrder.toString());
    }

    @Test
    public void testToStringComplex() throws Exception {
        // Create a complicated order with a lot of items and customizations
        var theOrder = new Order(expectedName, phoneNumber);

        // Add a calazone (extra cheese) to the order
        var calazone = new MenuItem("Sausage and Mushroom", 15.0, "Calzone");
        String[] customizationApplicability = {"Pizza", "Calzone"};
        var customizationOne = new Customization("Extra", 0.4, "Cheese",
            customizationApplicability);
        var orderItemOne = new OrderItem(calazone);
        orderItemOne.addCustomization(customizationOne);
        theOrder.addOrderItem(orderItemOne);

        // Add a pizza (extra cheese, well done, thin crust) to the order
        var pizzaTwo = new MenuItem("Supreme", 16.0, pizzaCategory);
        var customizationTwo = new Customization("Well", 0.0, "Doneness",
            customizationApplicability);
        var orderItemTwo = new OrderItem(pizzaTwo);
        orderItemTwo.addCustomization(expectedCustomization);
        orderItemTwo.addCustomization(customizationOne);
        orderItemTwo.addCustomization(customizationTwo);
        theOrder.addOrderItem(orderItemTwo);

        // Add three drinks to the order
        var drink = new MenuItem("Orange soda", 2.5, "Drink");
        var orderItemThree = new OrderItem(drink);
        theOrder.addOrderItem(orderItemThree);
        theOrder.addOrderItem(orderItemThree);
        theOrder.addOrderItem(orderItemThree);

        // Add the original pizza, twice 
        theOrder.addOrderItem(orderItem);
        theOrder.addOrderItem(orderItem);
        
        int id = theOrder.getOrderId();
        String expectedString = "Order ID: " + id + "\nCustomer: Dominik Pflug\nPhone: 1234567890\nSausage and Mushroom - $15.00\n  Extra Cheese - $0.40\nSupreme - $16.00\n  Thin Crust - $0.35\n  Extra Cheese - $0.40\n  Well Doneness - $0.00\nOrange soda - $2.50\nOrange soda - $2.50\nOrange soda - $2.50\nMargarita - $11.50\n  Thin Crust - $0.35\nMargarita - $11.50\n  Thin Crust - $0.35\nTotal Price: $63.35";

        assertEquals("toString should return complete order",
            expectedString, theOrder.toString());
    }


    @Test
    public void testDeepCopyConstructor() throws Exception {
        // Add another order item
        var drink = new MenuItem("Bubbly water", 2.0, "Drink");
        var extraItem = new OrderItem(drink);
        order.addOrderItem(extraItem);

        Order copy = new Order(order);

        // Check that order was deep copied
        assertEquals("Copied customer name should be the same", 
            order.getCustomerName(), copy.getCustomerName());
        assertEquals("Copied phone number should be the same", 
            order.getPhoneNumber(), copy.getPhoneNumber());
        assertEquals("Copied total price should be the same", 
            order.calculateTotalPrice(), copy.calculateTotalPrice(), 0.0);
        assertNotSame("Original and copy should not be the same instance", order, copy);

        // Each orderItem should be deep copied too
        OrderItem[] originalItems = order.getOrderItems();
        OrderItem[] copyItems = copy.getOrderItems();
        assertNotSame("Nested OrderItem object should be deep copied", 
            originalItems[0], copyItems[0]);
        assertNotSame("Nested OrderItem object should be deep copied", 
            originalItems[1], copyItems[1]);
    }
}

