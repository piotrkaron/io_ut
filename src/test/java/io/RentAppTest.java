package io;

import io.entity.Address;
import io.entity.Product;
import io.entity.User;
import io.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@Category({TestControl.class})
public class RentAppTest {

    private UserRepository userRepo = UserRepositoryImpl.INSTANCE;
    private ProductRepository productRepo = ProductRepositoryImpl.INSTANCE;
    private RentListRepository rentListRepo = RentListRepositoryImpl.INSTANCE;

    private RentApp app = new RentApp(productRepo, userRepo, rentListRepo);

    @Before
    public void setUp(){
        userRepo.deleteAll();
        productRepo.deleteAll();
        rentListRepo.deleteAll();
    }

    @Test
    public void givenNonExistentCredentials_shouldReturnFalse(){
        //When
        boolean login = app.login("any", "any");

        //Then
        assertFalse(login);
    }

    @Test
    public void givenWrongCredentials_shouldReturnFalse(){
        //Given
        Address addr = new Address("str", 1, 123, "Town");
        String pass = "paswd";
        String email = "email";
        User user = new User(email, pass, addr, null);
        User saved = userRepo.save(user);

        //When
        boolean login = app.login("any", email);

        //Then
        assertFalse(login);
    }

    @Test
    public void givenCorrectCredentials_shouldReturnTrue(){
        //Given
        Address addr = new Address("str", 1, 123, "Town");
        String pass = "paswd";
        String email = "email";
        User user = new User(email, encode(pass), addr, null);
        User saved = userRepo.save(user);

        //When
        boolean login = app.login(pass, email);

        //Then
        assertTrue(login);
    }

    @Test
    public void givenReservationRequest_shouldReserve(){
        //Given
        int prodId;
        Product prod = new Product("ProdName", BigDecimal.TEN, BigDecimal.TEN, 123);
        prod = productRepo.save(prod, 100);
        prodId = prod.getId();

        Address addr = new Address("str", 1, 123, "Town");
        String pass = "paswd";
        String email = "email";
        User user = new User(email, encode(pass), addr, null);
        User saved = userRepo.save(user);

        app.login(pass, email);

        //When
        boolean reserve = app.reserveProduct(prodId, 10);

        //Then
        assertTrue(reserve);

    }

    @Test
    public void givenReservationRequest_shouldNotReserveDueToNotEnoughQuantity(){
        //Given
        int productQuantity = 1;
        int requestedQuantity = 5;

        int prodId;
        Product prod = new Product("ProdName", BigDecimal.TEN, BigDecimal.TEN, 123);
        prod = productRepo.save(prod, productQuantity);
        prodId = prod.getId();

        Address addr = new Address("str", 1, 123, "Town");
        String pass = "paswd";
        String email = "email";
        User user = new User(email, encode(pass), addr, null);
        User saved = userRepo.save(user);

        app.login(pass, email);

        //When
        boolean reserve = app.reserveProduct(prodId, requestedQuantity);

        //Then
        assertFalse(reserve);

    }

    @Test
    public void givenReservationRequest_shouldNotReserveDueToUserNotLogged() {
        //Given
        int productQuantity = 1;
        int requestedQuantity = 5;

        int prodId;
        Product prod = new Product("ProdName", BigDecimal.TEN, BigDecimal.TEN, 123);
        prod = productRepo.save(prod, productQuantity);
        prodId = prod.getId();

        //When
        boolean reserve = app.reserveProduct(prodId, requestedQuantity);

        //Then
        assertFalse(reserve);

    }

    private String encode(String password) {
        char[] chars = password.toCharArray();
        char[] newPwd = new char[chars.length];
        int i = 0;
        for (char ch : chars) {
            newPwd[i++] = (char) (ch + 4);
        }

        return String.valueOf(newPwd);
    }
}