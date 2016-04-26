package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by paul thomas on 20.04.2016.
 */
public class SqlQueriesTest {

    SqlQueries myQuery = new SqlQueries();
    ObservableList<Address> addressList;
    ObservableList<Supplier> supplierList;
    ObservableList<Ingredient> allIngredients;
    ObservableList<Dish> testDishes;
    ObservableList<MenuLine> testMenuLines;
    ObservableList<Menu> testMenus;
    ObservableList<Subscription> testSubscriptions;
    ObservableList<Customer> testCustomers;
    ObservableList<Order> testOrders;
    ObservableList<Employee> testEmployees;
    ObservableList<DishLine> dishlines = FXCollections.observableArrayList();






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
    * */

    @org.junit.Before
    public void setUp() throws Exception {
        addressList = FXCollections.observableArrayList(
                new Address("Dessertgata 10", 7000, "Trondheim"),
                new Address("Kjøttveien 4", 7010, "Trondheim"),
                new Address("Karbogata 28", 7020, "Trondheim"),
                new Address("Sjømatgata 23", 7030, "Trondheim"),
                new Address("Grønnsakveien 1", 7040, "Trondheim"),
                new Address("Snacksveien 8", 7050, "Trondheim"),
                new Address("Kugata 17", 7060, "Trondheim")
        );

        supplierList = FXCollections.observableArrayList(
                new Supplier(90112233, addressList.get(0), "Sweet Pleasures Company"),
                new Supplier(90223344, addressList.get(1), "The Slaughterhouse"),
                new Supplier(40998877, addressList.get(2), "Never Forghetti Ltd."),
                new Supplier(40112233, addressList.get(3), "Crabs 'R' Us"),
                new Supplier(40223344, addressList.get(4), "Fruity Duty"),
                new Supplier(91338292, addressList.get(5), "Crunchy Munchies"),
                new Supplier(97456282, addressList.get(6), "Uncle Jack's Farm")
        );

        allIngredients = FXCollections.observableArrayList(
                new Ingredient("Sugar", "Gram", 10000, 0.02, supplierList.get(0)
                ),
                new Ingredient("Milk", "Liter", 30, 20, supplierList.get(0)
                ),
                new Ingredient("Flour", "Gram", 15000, 0.025, supplierList.get(0)
                ),
                new Ingredient("Salmon", "Number of", 30, 30, supplierList.get(0)
                ),
                new Ingredient("Carrot", "Kilogram", 300, 20, supplierList.get(0)
                ),
                new Ingredient("Chocolate", "Gram", 5000, 0.15, supplierList.get(0)
                ),
                new Ingredient("Trout", "Number of", 25, 40, supplierList.get(0)
                ),
                new Ingredient("Potato", "Kilogram", 60, 25, supplierList.get(0)
                ),
                new Ingredient("Butter", "Kilogram", 7, 50, supplierList.get(0)
                ),
                new Ingredient("Beef", "Kilogram", 20, 50, supplierList.get(0)
                ),
                new Ingredient("Spaghetti", "Kilogram", 50, 10, supplierList.get(0)
                ),
                new Ingredient("Potato chips", "Kilogram", 5, 30, supplierList.get(0)
                ),
                new Ingredient("Lettuce", "Kilogram", 8, 20, supplierList.get(0)
                ),
                new Ingredient("Paprika", "Kilogram", 4, 25, supplierList.get(0)
                ));


        testDishes = FXCollections.observableArrayList(
                new Dish(100, "Carrot cake", allIngredients),
                new Dish(200, "Trout with potato", allIngredients),
                new Dish(175, "Beef with spaghetti", allIngredients),
                new Dish(200, "Salmon with potato", allIngredients),
                new Dish(100, "Weird dish", allIngredients),
                new Dish(75, "Veggie dish", allIngredients)
        );

        MealType mealType = new MealType(1, "testMealType");
        testMenuLines = FXCollections.observableArrayList(
                new MenuLine(testDishes.get(0), 1, 1)
        );

        testMenus = FXCollections.observableArrayList(
                new Menu("Beef and carrot cake", mealType, testMenuLines),
                new Menu("Trout and carrot cake", mealType, testMenuLines),
                new Menu("Weird new Menu", mealType, testMenuLines),
                new Menu("Veggie new Menu", mealType, testMenuLines)
        );

        ObservableList<OrderLine> orderLines = FXCollections.observableArrayList(
                new OrderLine(testDishes.get(0), 1)
        );

        OrderStatus orderStatus = new OrderStatus(1, "Created");

        testOrders = FXCollections.observableArrayList(
                new Order(1, "request1", LocalDateTime.now(), null, 1, orderStatus, orderLines, addressList.get(0)),
                new Order(2, "request2", LocalDateTime.now(), null, 2, orderStatus, orderLines, addressList.get(1)),
                new Order(3, "request3", LocalDateTime.now(), null, 3, orderStatus, orderLines, addressList.get(2)),
                new Order(4, "request4", LocalDateTime.now(), null, 4, orderStatus, orderLines, addressList.get(3)),
                new Order(5, "request5", LocalDateTime.now(), null, 2, orderStatus, orderLines, addressList.get(4))
        );




        testSubscriptions = FXCollections.observableArrayList(
                new Subscription(LocalDate.now(), LocalDate.now(), testOrders),
                new Subscription(LocalDate.now(), LocalDate.now(), testOrders),
                new Subscription(LocalDate.now(), LocalDate.now(), testOrders)
        );

        testCustomers = FXCollections.observableArrayList(
                new Customer(false, "email1", "fName1", "lName1", 12345678,
                        addressList.get(0), null, testSubscriptions.get(0), testOrders),
                new Customer(false, "email2", "fName2", "lName2", 22345678,
                        addressList.get(1), null, testSubscriptions.get(1), testOrders),
                new Customer(true, "email3", "fName3", "lName3", 32345678,
                        addressList.get(2), "Business1", testSubscriptions.get(2), testOrders)
        );
        
        testEmployees = FXCollections.observableArrayList(
        );






        //        Addresses
        for (Address address :
                addressList) {
            assertTrue(myQuery.addAddress(address));
        }
        for (Address address :
                addressList) {
            assertNotNull(myQuery.getAddress(address.getAddressId()));
        }

        for (Supplier supplier :
                supplierList) {
            myQuery.addSupplier(supplier);
        }
        for (Supplier supplier :
                supplierList) {
            supplier.setBusinessName("RiskyBusiness");
            assertTrue(myQuery.updateSupplier(supplier));
        }

        for (Order order :
                testOrders) {
            order.setActualDeliveryDateTime(LocalDateTime.now());
            order.setDeadlineTime(LocalDateTime.now().plusDays(2));
            assertTrue(myQuery.addOrder(null, order, null));
        }

        ObservableList<EmployeePosition> employeePositions = myQuery.getEmployeePositions();
        for (int i = 0; i < 5; i++) {
            testEmployees.add(new Employee("randomUser" + i, "random" + i, "randomlastName" + i,
                    1 + i, "emai.sd" + i, 2123 + i, "password" + i, addressList.get(i), employeePositions.get(2)));
            assertTrue(myQuery.addEmployee(testEmployees.get(i)));
            testEmployees.get(i).setFirstName("TestName" + i);
        }

        //        addSuppliers

        for (Ingredient ingredient :
                allIngredients) {
            assertTrue(myQuery.addIngredient(ingredient));
            ingredient.setIngredientName("IngredientTest");
        }

        for (Dish dish :
                testDishes) {
            assertTrue(myQuery.addDish(dish));
        }

        for (Customer customer :
                testCustomers) {
            customer.setAddress(addressList.get(1));
            assertTrue(myQuery.addCustomer(customer));
        }

        //        Adding Subscriptions
        for (Subscription subscription :
                testSubscriptions) {
            assertTrue(myQuery.addSubscription(subscription, testCustomers.get(0), null));
        }

             /*adding orders on a Customer*/

//        Adds order, updates the order
        for (Order order :
                testOrders) {
            order.setActualDeliveryDateTime(LocalDateTime.now());
            order.setDeadlineTime(LocalDateTime.now().plusDays(2));
            assertTrue(myQuery.addOrder(null, order, null));
        }
















        ObservableList<Supplier> allSuppliers = myQuery.getAllSuppliers();
        ObservableList<Ingredient> allIngredients = myQuery.getAllIngredients(allSuppliers);
        ObservableList<Dish> allDishes = myQuery.getAllDishes(allIngredients);
        ObservableList<Menu> allMenus = myQuery.getAllMenus(allDishes);
        ObservableList<MealType> allMealTypes = myQuery.getAllMealTypes();

        allSuppliers.forEach(Assert::assertNotNull);
        allIngredients.forEach(Assert::assertNotNull);
        allDishes.forEach(Assert::assertNotNull);
        allMenus.forEach(Assert::assertNotNull);
        allMealTypes.forEach(Assert::assertNotNull);
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }



    @Ignore
    public void address2() throws Exception {
        //        deleteAddress
        for (Address address :
                addressList) {
            assertTrue(myQuery.deleteAddress(address));
        }


    }


    @Ignore
    public void subscription() throws Exception {


    }

    //    Testing Employee methods
/*    @Ignore
    public void employees() throws Exception {
        ObservableList<EmployeePosition> employeePositions = myQuery.getEmployeePositions();
        for (int i = 0; i < 5; i++) {
            assertTrue(myQuery.updateEmployee(testEmployees.get(i)));
            Employee testEmployee = myQuery.getEmployeeByUsername(testEmployees.get(i).getUsername());
            assertTrue(testEmployee.getEmployeeId() == testEmployees.get(i).getEmployeeId());
            assertTrue(myQuery.deleteEmployee(testEmployees.get(i)));
        }
    }*/

    //    Testing specifically getters from database
    @Ignore
    public void getters() throws Exception {


    }


    @Test
    public void supplier() throws Exception {


        ObservableList<Supplier> suppliersFromDb = myQuery.getAllSuppliers();

        /*Checks against the database if the objects exist and the name change was registered*/
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
            dish.setDishName("TestNameJunit");
            assertTrue(myQuery.updateDish(dish));
        }


/*//        Adding Subscriptions
        for (Subscription subscription :
                testSubscriptions) {
            assertTrue(myQuery.addSubscription(subscription, testCustomers.get(0), null));
        }*/

//        adding customers and updating them


        for (Customer customer :
                testCustomers) {
            customer.setEmail("JUnit@gmailtesting.org");
            assertTrue(myQuery.updateCustomer(customer));
        }









        for (Order order :
                testOrders) {
            order.setActualDeliveryDateTime(LocalDateTime.now());
            order.setDeadlineTime(LocalDateTime.now().plusDays(5));
            order.setPrice(34.5);
            assertTrue(myQuery.updateOrder(order));
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

//        Add order with customer
        for (Order order :
                testOrders) {
            assertTrue(myQuery.addOrder(null, order, testCustomers.get(0)));
        }



//        Dishlines

        for (Ingredient ingredient :
                allIngredients) {
            dishlines.add(new DishLine(ingredient, 42));
        }

        for (Dish dish :
                testDishes) {
            assertTrue(myQuery.addIngredientInDish(dish, dishlines));
        }

//        Adding MealTypes
        for (MealType mealType :
                TestObjects.mealTypes) {
            assertTrue(myQuery.addMealType(mealType));
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

    }

    @After
    public void after() {

        for (Order order :
                testOrders) {
            assertTrue(myQuery.deleteOrder(order));
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

}
