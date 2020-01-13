package io;

import io.entity.AddressEqualityTest;
import io.entity.ProductEqualityTest;
import io.entity.RentList;
import io.entity.RentListTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;

@RunWith(Categories.class)
@Categories.IncludeCategory(TestEntity.class)
@Categories.SuiteClasses({AddressEqualityTest.class, ProductEqualityTest.class, RentListTest.class})
public class EntityTestSuite {
}
