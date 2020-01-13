package io.entity;

import io.TestEntity;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@Category({TestEntity.class})
@RunWith(Parameterized.class)
public class AddressEqualityTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        new Address("Ciemna", 11, 12345, "Warszawa"),
                        new Address("Ciemna", 11, 12345, "Warszawa"),
                        true
                },
                {
                        new Address("Ciemna", 10, 12345, "Warszawa"),
                        new Address("Zielona", 10, 12345, "Warszawa"),
                        false
                },
                {
                        new Address("Ciemna", 11, 12345, "Warszawa"),
                        new Address("Ciemna", 99, 12345, "Warszawa"),
                        false
                },
                {
                        new Address("Ciemna", 11, 12345, "Warszawa"),
                        new Address("Ciemna", 11, 99999, "Warszawa"),
                        false
                },
                {
                        new Address("Ciemna", 11, 12345, "Warszawa"),
                        new Address("Ciemna", 11, 12345, "Chrząszczyżewoszyce"),
                        false
                },

        });
    }

    @Parameterized.Parameter
    public Address address1;

    @Parameterized.Parameter(1)
    public Address address2;

    @Parameterized.Parameter(2)
    public boolean expected;

    @Test
    public void testEquality(){
        boolean equalityCheck = address1.equals(address2);

        assertEquals(
                equalityCheck,
                expected
        );
    }

}