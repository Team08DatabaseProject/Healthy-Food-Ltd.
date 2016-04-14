package div;

import classpackage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by paul thomas on 01.04.2016.
 */
class PaulsOwnMainForTesting {
    public static void main(String[] args) {

        SqlQueries myQuery = new SqlQueries();

        ObservableList<String> myList = FXCollections.observableArrayList(
                "sdf", "sdfg"
        );
/*
        Address newAddress = new Address("johannesgate 4", 2034);
        myQuery.addAddress(newAddress);*/
      //  Customer firstCustomer = new Customer(false, "customerEmail", "john", "langaas", 92929292, newAddress, "");
        //myQuery.addCustomer(firstCustomer);
/*
        Supplier newSupplier = new Supplier(32, null);
        Ingredient newIngredient = new Ingredient("Name", "kg", 2.5, 32, newSupplier);
        int sdf = newIngredient.getSupplier().getSupplierId();
        System.out.print(sdf);*/


        /*
        Mehtod getAllCustomers works!
        myQuery.getAllCustomers().forEach(customer -> System.out.println(customer));
*/
        /*Method getAllSuppliers works*/
        /*Method getAllIngredients works*/
        /*Method getDishLinesByDish works*/
        /*ObservableList<Supplier> allSuppliers = myQuery.getAllSuppliers();
        allSuppliers.forEach(supplier -> System.out.println(supplier));
        ObservableList<Ingredient> allIngredients = myQuery.getAllIngredients(allSuppliers);

        allIngredients.forEach(ingredient -> System.out.println(ingredient));

        Dish myDish = new Dish(1, 13, "myDish");
        ObservableList<DishLine> myDishlines = myQuery.getDishLinesByDish(myDish, allIngredients);
        myDishlines.forEach(dishLine -> System.out.println("Anything:\t" + dishLine));*/


// myQuery.addIngredient();


    }
}

