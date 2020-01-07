package io.entity;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class RentListTest {
    @Test
    public void getCost() {
        // Given
        RentList lut = Util.rentListWithThreeProducts("p1", "p2", "p3");
        BigDecimal expected = Util.PRICE.multiply(BigDecimal.valueOf(3));

        // When
        BigDecimal cost = lut.getCost();

        //Then
        assertEquals(expected, cost);
    }
}