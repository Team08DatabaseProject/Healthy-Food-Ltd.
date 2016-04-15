package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.FXCollections.*;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import div.TranslationMethod;

/**
 * Created by axelkvistad on 4/15/16.
 * This is a class for retrieving Observablelists of test objects for the different class types
 * When testing you need test objects, use the ones made in this class instead of making your own.
 * This saves time and makes the code more readable.
 */
public class TestObjects {

    private static TranslationMethod tm = new TranslationMethod();

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

    public static ObservableList<Supplier> supplierList = FXCollections.observableArrayList(
            dessertSupplier, meatSupplier, carbSupplier, seafoodSupplier, vegetableSupplier, snackSupplier, dairySupplier
    );

    public static Ingredient sugar = new Ingredient("Sugar", "Gram", 10000, 0.02, dessertSupplier);
    public static Ingredient milk = new Ingredient("Milk", "Liter", 30, 20, dairySupplier);
    public static Ingredient flour = new Ingredient("Flour", "Gram", 15000, 0.025, dessertSupplier);
    public static Ingredient chocolate = new Ingredient("Chocolate", "Gram", 5000, 0.15, dessertSupplier);
    public static Ingredient carrot = new Ingredient("Carrot", "Kilogram", 300, 20, vegetableSupplier);
    public static Ingredient trout = new Ingredient("Trout", "Number of", 25, 40, seafoodSupplier);
    public static Ingredient salmon = new Ingredient("Salmon", "Number of", 30, 30, seafoodSupplier);
    public static Ingredient potato = new Ingredient("Potato", "Kilogram", 60, 25, carbSupplier);
    public static Ingredient butter = new Ingredient("Butter", "Kilogram", 7, 50, dairySupplier);
    public static Ingredient beef = new Ingredient("Beef", "Kilogram", 20, 50, meatSupplier);
    public static Ingredient spaghetti = new Ingredient("Spaghetti", "Kilogram", 50, 10, carbSupplier);
    public static Ingredient potatoChips = new Ingredient("Potato chips", "Kilogram", 5, 30, snackSupplier);
    public static Ingredient lettuce = new Ingredient("Lettuce", "Kilogram", 8, 20, vegetableSupplier);
    public static Ingredient paprika = new Ingredient("Paprika", "Kilogram", 4, 25, vegetableSupplier);

    public static DishLine sugarDL = new DishLine(sugar, 30);
    public static DishLine milkDL = new DishLine(milk, 30);
    public static DishLine flourDL = new DishLine(flour, 30);
    public static DishLine chocolateDL = new DishLine(chocolate, 30);
    public static DishLine carrotDL = new DishLine(carrot, 30);
    public static DishLine troutDL = new DishLine(trout, 30);
    public static DishLine salmonDL = new DishLine(salmon, 30);
    public static DishLine potatoDL = new DishLine(potato, 30);
    public static DishLine butterDL = new DishLine(butter, 30);
    public static DishLine beefDL = new DishLine(beef, 30);
    public static DishLine spaghettiDL = new DishLine(spaghetti, 30);
    public static DishLine potatoChipsDL = new DishLine(potatoChips, 30);
    public static DishLine lettuceDL = new DishLine(lettuce, 30);
    public static DishLine paprikaDL = new DishLine(paprika, 30);



    public static ObservableList<Ingredient> allIngredients = FXCollections.observableArrayList(
            sugar, milk, flour, chocolate, carrot, trout, salmon, potato, butter, beef, spaghetti, potatoChips, lettuce, paprika
    );

    public static ObservableList<DishLine> allIngredientsDL = FXCollections.observableArrayList(
            sugarDL, milkDL, flourDL, chocolateDL, carrotDL, troutDL, salmonDL, potatoDL, butterDL, beefDL, spaghettiDL, potatoChipsDL, lettuceDL, paprikaDL
    );

    public static ObservableList<DishLine> ingredientListDL1 = FXCollections.observableArrayList(
            sugarDL, milkDL, flourDL, chocolateDL, carrotDL
    );

    public static ObservableList<DishLine> ingredientListDL2 = FXCollections.observableArrayList(
            troutDL, potatoDL, butterDL, lettuceDL
    );

    public static ObservableList<DishLine> ingredientListDL3 = FXCollections.observableArrayList(
            beefDL, spaghettiDL, lettuceDL, paprikaDL
    );

    public static ObservableList<DishLine> ingredientListDL4 = FXCollections.observableArrayList(
            salmonDL, potatoDL, butterDL, paprikaDL
    );

    public static ObservableList<DishLine> ingredientListDL5 = FXCollections.observableArrayList(
            potatoChipsDL, lettuceDL, paprikaDL, butterDL, sugarDL, chocolateDL
    );

    public static ObservableList<DishLine> ingredientListDL6 = FXCollections.observableArrayList(
            lettuceDL, paprikaDL, potatoDL, carrotDL
    );

    public static Dish carrotDish = new Dish(100, "Carrot cake", ingredientListDL1);
    public static Dish troutDish = new Dish(200, "Trout with potato", ingredientListDL2);
    public static Dish beefDish = new Dish(175, "Beef with spaghetti", ingredientListDL3);
    public static Dish salmonDish = new Dish(200, "Salmon with potato", ingredientListDL4);
    public static Dish weirdDish = new Dish(100, "Weird dish", ingredientListDL5);
    public static Dish veggieDish = new Dish(75, "Veggie dish", ingredientListDL6);

    public static MenuLine carrotML = new MenuLine(carrotDish, 1, 0.9);
    public static MenuLine troutML = new MenuLine(troutDish, 2, 0.7);
    public static MenuLine beefML = new MenuLine(beefDish, 1, 0.9);
    public static MenuLine salmonML = new MenuLine(salmonDish, 1, 0.8);
    public static MenuLine weirdML = new MenuLine(weirdDish, 4, 0.9);
    public static MenuLine veggieML = new MenuLine(veggieDish, 3, 0.9);

    public static ObservableList<MenuLine> allDishes = FXCollections.observableArrayList(
            carrotML, troutML, beefML, salmonML, weirdML
    );

    public static ObservableList<MenuLine> dishList1 = FXCollections.observableArrayList(
            beefML, carrotML
    );

    public static ObservableList<MenuLine> dishList2 = FXCollections.observableArrayList(
            troutML, carrotML
    );

    public static ObservableList<MenuLine> dishList3 = FXCollections.observableArrayList(
            beefML, salmonML, weirdML
    );

    public static ObservableList<MenuLine> dishList4 = FXCollections.observableArrayList(
            veggieML
    );

    public static Menu menu1 = new Menu("Beef and carrot cake", "Non-vegetarian", dishList1);
    public static Menu menu2 = new Menu("Trout and carrot cake", "Non-vegetarian", dishList2);
    public static Menu menu3 = new Menu("Weird menu", "Non-vegeterian", dishList3);
    public static Menu menu4 = new Menu("Veggie menu", "Vegetarian", dishList4);

    public static ObservableList<Menu> allMenus = FXCollections.observableArrayList(
            menu1, menu2, menu3, menu4
    );




    public static Customer customer1 = new Customer(false, "email1", "fName1", "lName1", 12345678, addressList.get(0), null);
    public static Customer customer2 = new Customer(false, "email2", "fName2", "lName2", 22345678, addressList.get(1), null);
    public static Customer customer3 = new Customer(true, "email3", "fName3", "lName3", 32345678, addressList.get(2), "Business1");
    public static Customer customer4 = new Customer(false, "email4", "fName4", "lName4", 42345678, addressList.get(3), null);
    public static Customer customer5 = new Customer(true, "email5", "fName5", "lName5", 52345678, addressList.get(4), "Business2");
    public static Customer customer6 = new Customer(false, "email6", "fName6", "lName6", 62345678, addressList.get(5), null);
    public static Customer customer7 = new Customer(true, "email7", "fName7", "lName7", 72345678, addressList.get(6), "Business3");

    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList(
            customer1, customer2, customer3, customer4, customer5, customer6, customer7
    );



    public static LocalDate deadline1 = LocalDate.of(2016, 5, 1);
    public static LocalDate deadline2 = LocalDate.of(2016, 9, 2);
    public static LocalDate deadline3 = LocalDate.of(2016, 6, 3);
    public static LocalDate deadline4 = LocalDate.of(2016, 12, 4);
    public static LocalDate deadline5 = LocalDate.of(2016, 3, 5);
    public static LocalDate deadline6 = LocalDate.of(2016, 5, 6);
    public static LocalDate deadline7 = LocalDate.of(2016, 6, 7);

    public static LocalDate subDeadline1 = LocalDate.of(2016, 6, 1);
    public static LocalDate subDeadline2 = LocalDate.of(2016, 7, 1);
    public static LocalDate subDeadline3 = LocalDate.of(2016, 8, 1);
    public static LocalDate subDeadline4 = LocalDate.of(2016, 9, 1);
    public static LocalDate subStart1 = LocalDate.of(2016, 5, 15);
    public static LocalDate subEnd1 = LocalDate.of(2016, 9, 1);


    public static ObservableList<OrderLine> orderLineList1 = tm.getOrderListFromMenuList(dishList1);
    private static double price1 = tm.getOrderPriceFromMenuList(dishList1);
    public static ObservableList<OrderLine> orderLineList2 = tm.getOrderListFromMenuList(dishList2);
    private static double price2 = tm.getOrderPriceFromMenuList(dishList2);
    public static ObservableList<OrderLine> orderLineList3 = tm.getOrderListFromMenuList(dishList3);
    private static double price3 = tm.getOrderPriceFromMenuList(dishList3);
    public static ObservableList<OrderLine> orderLineList4 = tm.getOrderListFromMenuList(dishList4);
    private static double price4 = tm.getOrderPriceFromMenuList(dishList4);

    public static Order order1 = new Order("request1", deadline1, price1, "Created", orderLineList1);
    public static Order order2 = new Order("request2", deadline2, price2, "Created", orderLineList2);
    public static Order order3 = new Order("request3", deadline3, price3, "Created", orderLineList3);
    public static Order order4 = new Order("request4", deadline4, price4, "Created", orderLineList4);
    public static Order order5 = new Order("request5", deadline5, price2, "Created", orderLineList2);

    public static Order subOrder1 = new Order("subrequest1", subDeadline1, price1, "Created", orderLineList1);
    public static Order subOrder2 = new Order("subrequest2", subDeadline2, price1, "Created", orderLineList1);
    public static Order subOrder3 = new Order("subrequest3", subDeadline3, price1, "Created", orderLineList1);
    public static Order subOrder4 = new Order("subrequest4", subDeadline4, price1, "Created", orderLineList1);


    public static ObservableList<Order> allOrders = FXCollections.observableArrayList(
            order1, order2, order3, order4, order5,
            subOrder1, subOrder2, subOrder3, subOrder4
    );

    public static ObservableList<Order> subOrderList1 = FXCollections.observableArrayList(
            subOrder1, subOrder2, subOrder3, subOrder4
    );


    public static Subscription subscription1 = new Subscription(subStart1, subEnd1, subOrderList1);


}
