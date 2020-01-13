package io;

import io.entity.Product;
import io.repository.ProductRepositoryImpl;
import io.repository.RentListRepositoryImpl;
import io.repository.UserRepository;
import io.repository.UserRepositoryImpl;

import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Scanner scan = new Scanner(System.in);

    private static RentApp app = new RentApp(
            ProductRepositoryImpl.INSTANCE,
            UserRepositoryImpl.INSTANCE,
            RentListRepositoryImpl.INSTANCE
    );

    public static void main(String[] args) {
        app.populateExampleData();

	    String opt = "";

        greeting();
        while(!login())
            println("_____________________");

        println(menu);
	    while (!opt.trim().equals("q")){
            opt = scan.nextLine();

            switch (opt) {
                case "1": {
                    showCatalogue();
                    break;
                }
                case "2" : {
                    makeReservation();
                    break;
                }
                case "3" :{
                    showRentList();
                    break;
                }
                case "q":{
                    return;
                }
                default :{
                    println("Bledna opcja");
                }
            }

            println(menu);
        }
    }

    private static void showCatalogue(){
        Map<Product, Integer> cat = app.getCatalogue();

        if(cat.isEmpty())
            println("Katalog pusty");
        else
            app.getCatalogue().forEach((product, integer) ->
                print(product.toString() + " |Dostepne " + integer.toString() + "szt.\n"));
    }

    private static void makeReservation(){
        println("Podaj id produktu:");
        String id = scan.nextLine().trim();

        println("Podaj liczbe:");
        String count = scan.nextLine().trim();

        try {
            int idInt = Integer.parseInt(id);
            int countInt = Integer.parseInt(count);
            if(app.reserveProduct(idInt, countInt))
                println("Poprawnie zarezerwowano");
            else
                println("Nie udalo sie zarezerwowac");
        }catch (Exception e){
            println("Bledny format liczb.");
        }
    }

    private static void showRentList(){
        app.showRentList();
    }

    private static boolean login(){
        println("Email: ");
        String email = scan.nextLine();

        println("Haslo: ");
        String password = scan.nextLine();

        return app.login(password,email);
    }

    private static void greeting(){
        println(greeting);
    }

    private static String greeting = ""+
            "Witaj w wypozyczalni sprzetu \"Sprzetex - Emiotr\"\n" +
            "Zaloguj sie:";

    private static String menu = "\n" +
            "Co chcesz zrobic?\n" +
            "1 - wyswietl katalog\n" +
            "2 - dodaj produkt\n" +
            "3 - wyswietl liste wypozyczen\n" +
            "q - wyjscie.\n\n";

    private static void println(String msg){
        System.out.println(msg);
    }

    private static void print(String msg){
        System.out.print(msg);
    }

}
