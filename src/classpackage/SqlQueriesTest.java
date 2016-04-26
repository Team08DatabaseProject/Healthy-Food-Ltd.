/*
package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

*/
/**
 * Created by paul thomas on 20.04.2016.
 *//*

public class SqlQueriesTest {

    public static SqlQueries myQuery = new SqlQueries();


    public static ObservableList<Address> addressList = FXCollections.observableArrayList(
            new Address("Dessertgata 10", 7000, "Trondheim"),
            new Address("Kjøttveien 4", 7010, "Trondheim"),
            new Address("Karbogata 28", 7020, "Trondheim"),
            new Address("Sjømatgata 23", 7030, "Trondheim"),
            new Address("Grønnsakveien 1", 7040, "Trondheim"),
            new Address("Snacksveien 8", 7050, "Trondheim"),
            new Address("Kugata 17", 7060, "Trondheim")
    );

    public static Supplier dessertSupplier = new Supplier(90112233, addressList.get(0), "Sweet Pleasures Company");

    public static Supplier meatSupplier = new Supplier(90223344, addressList.get(1), "The Slaughterhouse");
    public static Supplier carbSupplier = new Supplier(40998877, addressList.get(2), "Never Forghetti Ltd.");
    public static Supplier seafoodSupplier = new Supplier(40112233, addressList.get(3), "Crabs 'R' Us");
    public static Supplier vegetableSupplier = new Supplier(40223344, addressList.get(4), "Fruity Duty");
    public static Supplier snackSupplier = new Supplier(91338292, addressList.get(5), "Crunchy Munchies");
    public static Supplier dairySupplier = new Supplier(97456282, addressList.get(6), "Uncle Jack's Farm");


    public static Ingredient sugar = new Ingredient("Sugar", "Gram", 10000, 0.02, dessertSupplier);
    public static Ingredient milk = new Ingredient("Milk", "Liter", 30, 20, dairySupplier);
    public static Ingredient flour = new Ingredient("Flour", "Gram", 15000, 0.025, dessertSupplier);
    public static Ingredient salmon = new Ingredient("Salmon", "Number of", 30, 30, seafoodSupplier);
    public static Ingredient carrot = new Ingredient("Carrot", "Kilogram", 300, 20, vegetableSupplier);
    public static Ingredient chocolate = new Ingredient("Chocolate", "Gram", 5000, 0.15, dessertSupplier);
    public static Ingredient trout = new Ingredient("Trout", "Number of", 25, 40, seafoodSupplier);
    public static Ingredient potato = new Ingredient("Potato", "Kilogram", 60, 25, carbSupplier);
    public static Ingredient butter = new Ingredient("Butter", "Kilogram", 7, 50, dairySupplier);
    public static Ingredient beef = new Ingredient("Beef", "Kilogram", 20, 50, meatSupplier);
    public static Ingredient spaghetti = new Ingredient("Spaghetti", "Kilogram", 50, 10, carbSupplier);
    public static Ingredient potatoChips = new Ingredient("Potato chips", "Kilogram", 5, 30, snackSupplier);
    public static Ingredient lettuce = new Ingredient("Lettuce", "Kilogram", 8, 20, vegetableSupplier);
    public static Ingredient paprika = new Ingredient("Paprika", "Kilogram", 4, 25, vegetableSupplier);


    public static ObservableList<Supplier> supplierList = FXCollections.observableArrayList(
            dessertSupplier, meatSupplier, carbSupplier, seafoodSupplier, vegetableSupplier, snackSupplier, dairySupplier
    );

    public static ObservableList<Ingredient> allIngredients = FXCollections.observableArrayList(
            sugar, milk, flour, chocolate, carrot, trout, salmon, potato, butter, beef, spaghetti, potatoChips, lettuce, paprika
    );


    ArrayList<String> supplierNames = new ArrayList<>();
    ArrayList<Integer> supplierId = new ArrayList<>();


    public static ObservableList<Dish> testDishes = TestObjects.dishList;
    public static ObservableList<Menu> testMenus = TestObjects.allMenus;
    public static ObservableList<Subscription> testSubscriptions = TestObjects.allSubscriptions;
    public static ObservableList<Customer> testCustomers = TestObjects.allCustomers;
    public static ObservableList<Order> testOrders = TestObjects.orderList;
    public static ObservableList<Employee> employees = FXCollections.observableArrayList();






    */
/*Testing with JUnit
    *
    *
    * addAddress
    * addSupplier
    * addIngredient
    * addDish
    * addIngredientInDish
    * addMenu
    * addDishInMenu
    *
    *
    *
    *
    *
    * *//*


    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @Ignore
    public void address() throws Exception {


//        Addresses
        for (Address address :
                addressList) {
            assertTrue(myQuery.addAddress(address));
        }
        for (Address address :
                addressList) {
            assertNotNull(myQuery.getAddress(address.getAddressId()));
        }

        //        deleteAddress
        for (Address address :
                addressList) {
            assertTrue(myQuery.deleteAddress(address));
        }


    }


    @Ignore
    public void subscription() throws Exception {
        for (Order order :
                testOrders) {
            order.setActualDeliveryDateTime(LocalDateTime.now());
            order.setDeadlineTime(LocalDateTime.now().plusDays(2));
            assertTrue(myQuery.addOrder(null, order, null));
        }
        for (Order order :
                testOrders) {
            assertTrue(myQuery.deleteOrder(order));
        }

    }

//    Testing Employee methods
    @Test
    public void employees() throws Exception {
        ObservableList<EmployeePosition> employeePositions = myQuery.getEmployeePositions();
        for (int i = 0; i < 5; i++) {
            employees.add(new Employee("randomUser" + i, "random" + i, "randomlastName" + i,
                    1 + i, "emai.sd" + i, 2123 + i, "password" + i, addressList.get(i), employeePositions.get(2)));
            assertTrue(myQuery.addEmployee(employees.get(i)));
            employees.get(i).setFirstName("TestName" + i);
            assertTrue(myQuery.updateEmployee(employees.get(i)));
            Employee testEmployee = myQuery.getEmployeeByUsername(employees.get(i).getUsername());
            assertTrue(testEmployee.getEmployeeId() == employees.get(i).getEmployeeId());
            assertTrue(myQuery.deleteEmployee(employees.get(i)));
        }
    }

//    Testing specifically getters from database
    @Test
    public void getters() throws Exception{
        ObservableList<Supplier> allSuppliers = myQuery.getAllSuppliers();
        ObservableList<Ingredient> allIngredients = myQuery.getAllIngredients(allSuppliers);
        ObservableList<Dish> allDishes = myQuery.getAllDishes(allIngredients);
        ObservableList<Menu> allMenus = myQuery.getAllMenus(allDishes);

        allSuppliers.forEach(Assert::assertNotNull);
        allIngredients.forEach(Assert::assertNotNull);
        allDishes.forEach(Assert::assertNotNull);
        allMenus.forEach(Assert::assertNotNull);
    }



    @Ignore
    public void supplier() throws Exception {
//        addSuppliers
        for (Supplier supplier :
                supplierList) {
            myQuery.addSupplier(supplier);
        }
        for (Supplier supplier :
                supplierList) {
            supplier.setBusinessName("RiskyBusiness");
            assertTrue(myQuery.updateSupplier(supplier));
        }
        ObservableList<Supplier> suppliersFromDb = myQuery.getAllSuppliers();

        for (Ingredient ingredient :
                allIngredients) {
            assertTrue(myQuery.addIngredient(ingredient));
            ingredient.setIngredientName("IngredientTest");
        }

        */
/*Checks against the database if the objects exist and the name change was registered*//*

        assertTrue(myQuery.updateIngredient(allIngredients));
        ObservableList<Ingredient> ingredientsFromDb = myQuery.getAllIngredients(suppliersFromDb);
        boolean ingredientsExistsInDb = false;
        for (Ingredient ingredient :
                allIngredients) {
            ingredientsExistsInDb = false;
            boolean equal = false;
            for (Ingredient ingredientInDb :
                    ingredientsFromDb) {
                if (ingredient.getIngredientId() == ingredientInDb.getIngredientId() && ingredient.getIngredientName().equals(ingredientInDb.getIngredientName())) {
                    equal = true;
                }
            }
            if (equal) {
                ingredientsExistsInDb = true;
            }
        }
        assertTrue(ingredientsExistsInDb);

        for (Dish dish :
                testDishes) {
            assertTrue(myQuery.addDish(dish));
        }

        for (Dish dish :
                testDishes) {
            dish.setDishName("TestNameJunit");
            assertTrue(myQuery.updateDish(dish));
        }


//        Adding Subscriptions
        for (Subscription subscription :
                testSubscriptions) {
            assertTrue(myQuery.addSubscription(subscription));
        }

//        adding customers and updating them
        for (Customer customer :
                testCustomers) {
            customer.setAddress(addressList.get(1));
            assertTrue(myQuery.addCustomer(customer));
        }

        for (Customer customer :
                testCustomers) {
            customer.setEmail("JUnit@gmailtesting.org");
            assertTrue(myQuery.updateCustomer(customer));
        }





      */
/*adding orders on a Customer*//*


//        Adds order, updates the order, and deletes them
        for (Order order :
                testOrders) {
            order.setActualDeliveryDateTime(LocalDateTime.now());
            order.setDeadlineTime(LocalDateTime.now().plusDays(2));
            assertTrue(myQuery.addOrder(null, order, null));
        }

        for (Order order :
                testOrders) {
            order.setActualDeliveryDateTime(LocalDateTime.now());
            order.setDeadlineTime(LocalDateTime.now().plusDays(5));
            order.setPrice(34.5);
            assertTrue(myQuery.updateOrder(order, null));
        }


        for (Order order :
                testOrders) {
            assertTrue(myQuery.deleteOrder(order));
        }

//        Adds order with subscription and deletes them
        for (Order order :
                testOrders) {
            assertTrue(myQuery.addOrder(testSubscriptions.get(1), order, null));
        }
        for (Order order :
                testOrders) {
            assertTrue(myQuery.deleteOrder(order));
        }

//        Add order with customer and deletes them
        for (Order order :
                testOrders) {
            assertTrue(myQuery.addOrder(null, order, testCustomers.get(0)));
        }

        for (Order order :
                testOrders) {
            assertTrue(myQuery.deleteOrder(order));
        }


//        Dishlines
        ObservableList<DishLine> dishlines = FXCollections.observableArrayList();
        for (Ingredient ingredient :
                allIngredients) {
            dishlines.add(new DishLine(ingredient, 42));
        }

        for (Dish dish :
                testDishes) {
            assertTrue(myQuery.addIngredientInDish(dish, dishlines));
        }

//        Adding menus
        for (Menu menu :
                testMenus) {
            assertTrue(myQuery.addMenu(menu));
        }


//        Adding menulines in a menu

        ObservableList<MenuLine> testMenuLines = FXCollections.observableArrayList();
        for (Dish dish :
                testDishes) {
            testMenuLines.add(new MenuLine(dish, 42, 1));
        }

        for (Menu menu :
                testMenus) {
            assertTrue(myQuery.addDishesInMenu(menu, testMenuLines));
        }

//        Deleting MenuLines in menus
        for (Menu menu :
                testMenus) {
            assertTrue(myQuery.deleteDishesInMenu(menu, testMenuLines));
        }


//        Deleting a menu
        for (Menu menu :
                testMenus) {
            assertTrue(myQuery.deleteMenu(menu));
        }

//        Deleting a Dish
        for (Dish dish :
                testDishes) {
            assertTrue(myQuery.deleteDish(dish));
        }


//        Deleting Ingredients in Dish
        for (Dish dish :
                testDishes) {
            assertTrue(myQuery.deleteIngredientsInDish(dish, dishlines));
        }


//        Deleting Ingredient And checking if the elements
        for (Ingredient ingredient :
                allIngredients) {
            assertTrue(myQuery.addIngredient(ingredient));
            assertTrue(myQuery.deleteIngredient(ingredient));
        }
    }


*/
/*

Template
//

//        Testing against the db to ensure the suppliers were added
        boolean suppliersExists = true;
        ObservableList<Supplier> suppliersInDb = myQuery.getAllSuppliers();
        for (Supplier supplier :
                supplierList) {
            if (!suppliersInDb.contains(supplier)){
                suppliersExists = false;
            }
        }
        assertTrue(suppliersExists);



//      Adding ingredient
        for (Ingredient ingredient :
                allIngredients) {
            assertTrue(myQuery.addIngredient(ingredient));
        }



        //        Testing against the db to ensure the Ingredients were added
        boolean ingredientsExists = true;
        ObservableList<Ingredient> ingredientsInDb = myQuery.getAllIngredients(suppliersInDb);
        for (Ingredient ingredient :
                ingredientsInDb) {
            if (!ingredientsInDb.contains(ingredient)){
                suppliersExists = false;
            }
        }
        assertTrue(ingredientsExists);

//        Deleting Ingredients
        for (Ingredient ingredient :
                allIngredients) {
            assertTrue(myQuery.deleteIngredient(ingredient));
        }

//        Deleting Suppliers
        for (Supplier supplier :
                supplierList) {
            assertTrue(myQuery.deleteSupplier(supplier));
        }

       *//*



}
*/
