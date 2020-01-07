package io.entity;

import java.math.BigDecimal;
import java.util.HashSet;

public class Util {

    public final static String OWNER_ID = "SOME_ID";
    public final static String PRODUCT_NAME = "SOME_ID";
    public final static BigDecimal PRICE = BigDecimal.valueOf(100);
    public final static BigDecimal BAIL = BigDecimal.TEN;
    public final static int PRODUCT_ID = 1234;

    public static Product dummyProduct(String name) {
        return new Product(name, PRICE, BAIL, PRODUCT_ID);
    }

    public static NewRentObject dummyRentObject(Product product) {
        return new NewRentObject(product, 1);
    }

    public static RentList rentListWithThreeProducts(String name1, String name2, String name3) {
        RentList list = new RentList(OWNER_ID);
        HashSet<NewRentObject> set = new HashSet<>();
        NewRentObject a1 = dummyRentObject(dummyProduct(name1));
        NewRentObject a2 = dummyRentObject(dummyProduct(name2));
        NewRentObject a3 = dummyRentObject(dummyProduct(name3));

        set.add(a1);
        set.add(a2);
        set.add(a3);

        list.setProducts(set);

        return list;
    }
}
