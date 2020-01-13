package io;

import io.entity.RentListTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;

@RunWith(Categories.class)
@Categories.IncludeCategory(TestControl.class)
@Categories.ExcludeCategory(TestOther.class)
@Categories.SuiteClasses({RentListTest.class, RentAppTest.class})
public class ControlTestSuite {
}
