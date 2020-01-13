package io.entity;

import io.TestEntity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
@Category({TestEntity.class})
public class ProductEqualityTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        new Product("Nazwa", BigDecimal.TEN, BigDecimal.ONE, 1),
                        new Product("Inna nazwa", BigDecimal.valueOf(9), BigDecimal.valueOf(99), 1),
                        true
                },
                {
                        new Product("Nazwa", BigDecimal.TEN, BigDecimal.ONE, 1),
                        new Product("Nazwa", BigDecimal.TEN, BigDecimal.ONE, 12),
                        false
                }
        });
    }

    @Parameterized.Parameter
    public Product product1;

    @Parameterized.Parameter(1)
    public Product product2;

    @Parameterized.Parameter(2)
    public boolean expected;

    @Test
    public void equalityTest() {
        boolean equalityCheck = product1.equals(product2);

        assertEquals(
                equalityCheck,
                expected
        );
    }

}
