package io.entity;

import io.TestControl;
import io.TestEntity;
import io.TestOther;
import io.Util;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.*;

@Category({TestEntity.class, TestControl.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentListTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    @Category({TestOther.class})
    public void givenRentListWithMultipleItems_shouldReturnCorrectSumOfCost() {
        // Given
        RentList lut = Util.rentListWithThreeProducts(1,2,3);
        BigDecimal expected = Util.PRICE.multiply(BigDecimal.valueOf(3));

        // When
        BigDecimal cost = lut.getCost();

        //Then
        assertEquals(expected, cost);
    }

    @Test
    public void givenRentListWithMultipleItems_shouldReturnCorrectSumOfBail() {
        // Given
        RentList lut = Util.rentListWithThreeProducts(1,2,3);
        BigDecimal expected = Util.BAIL.multiply(BigDecimal.valueOf(3));

        // When
        BigDecimal bail = lut.getBail();

        //Then
        assertEquals(expected, bail);
    }

    @Test
    public void givenRentListWithMultipleItems_shouldCorrectlyFindRequestedItem(){
        //Given
        int requestedProductId = 123;
        Product requestedProduct = new Product("ex", BigDecimal.valueOf(123.3), BigDecimal.valueOf(99.9), requestedProductId);
        NewRentObject requestedProductObj = new NewRentObject(requestedProduct, 10);

        int wrongProductId = 1;
        Product prod2 = new Product("ex2", BigDecimal.valueOf(22), BigDecimal.valueOf(22), wrongProductId);
        NewRentObject obj2 = new NewRentObject(requestedProduct, 10);

        RentList list = new RentList("owner");
        HashSet<NewRentObject> set = new HashSet<>();
        set.add(requestedProductObj);
        set.add(obj2);
        list.setProducts(set);

        // When
        Optional<NewRentObject> found = list.find(requestedProduct);

        // Then
        assertTrue(found.isPresent());
        assertEquals(found.get(), requestedProductObj);
        assertEquals(found.get().getProduct(), requestedProduct);
    }

    @Test
    public void givenEmptyList_shouldAddItem(){
        //  Given
        RentList list = new RentList("owner");
        Product prod = Util.dummyProduct(1);
        int quantity = 3;

        // When
        list.createRent(prod, quantity);

        // Then
        assertEquals(1, list.getProducts().size());
        assertEquals(quantity, list.getProducts().iterator().next().getQuantity());
    }

    @Test
    public void givenListWithProduct_whenAddedTheSameProduct_shouldIncreaseQuantity(){
        //  Given
        RentList list = new RentList("owner");
        Product prod = Util.dummyProduct(1);
        list.createRent(prod, 1);

        int addQuanity = 3;

        // When
        list.createRent(prod, addQuanity);

        // Then
        assertEquals(1, list.getProducts().size());
        assertEquals(addQuanity + 1, list.getProducts().iterator().next().getQuantity());
    }
}