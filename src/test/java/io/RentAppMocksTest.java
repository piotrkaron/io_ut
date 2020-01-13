package io;

import io.entity.Address;
import io.entity.Product;
import io.entity.User;
import io.repository.ProductRepository;
import io.repository.RentListRepository;
import io.repository.UserRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Category({TestOther.class})
public class RentAppMocksTest {

    @Tested
    RentApp app;

    @Injectable
    ProductRepository productRepo;

    @Injectable
    UserRepository userRepo;

    @Injectable
    RentListRepository rentListRepo;

    @Test
    public void givenNonExistentCredentials_shouldReturnFalse() {
        //Given
        String email = "email";
        new Expectations() {
            {
                userRepo.findUserByEmail(anyString);
                result = Optional.empty();
            }
        };

        //When
        boolean login = app.login("any", email);

        //Then
        assertFalse(login);

        new Verifications() {
            {
                userRepo.findUserByEmail(email);
                times = 1;
            }
        };
    }

    @Test
    public void givenWrongCredentials_shouldReturnFalse() {
        //Given
        Address addr = new Address("str", 1, 123, "Town");
        String pass = "paswd";
        String email = "email";
        User user = new User(email, pass, addr, "123");

        new Expectations() {
            {
                userRepo.findUserByEmail(anyString);
                result = user;
            }
        };

        //When
        boolean login = app.login("any", email);

        //Then
        assertFalse(login);

        new Verifications() {
            {
                userRepo.findUserByEmail(email);
                times = 1;
            }
        };
    }

    @Test
    public void givenCorrectCredentials_shouldReturnTrue() {
        //Given
        Address addr = new Address("str", 1, 123, "Town");
        String pass = "paswd";
        String email = "email";
        User user = new User(email, encode(pass), addr, null);

        new Expectations() {
            {
                userRepo.findUserByEmail(email);
                result = user;
            }
        };

        //When
        boolean login = app.login(pass, email);

        //Then
        assertTrue(login);

        new Verifications() {
            {
                userRepo.findUserByEmail(email);
                times = 1;
            }
        };
    }

    @Test
    public void givenReservationRequest_shouldReserve() {
        //Given
        int prodId;
        Product prod = new Product("ProdName", BigDecimal.TEN, BigDecimal.TEN, 123);
        prodId = prod.getId();

        Address addr = new Address("str", 1, 123, "Town");
        String pass = "paswd";
        String email = "email";
        User user = new User(email, encode(pass), addr, null);

        new Expectations() {
            {
                userRepo.findUserByEmail(anyString);
                result = user;
                productRepo.findById(prodId);
                result = Map.entry(prod, 100);
            }
        };

        app.login(pass, email);

        //When
        boolean reserve = app.reserveProduct(prodId, 10);

        //Then
        assertTrue(reserve);

        new Verifications() {
            {
                productRepo.findById(prodId);
                times = 1;
            }
        };

    }

    @Test
    public void givenReservationRequest_shouldNotReserveDueToNotEnoughQuantity() {
        //Given
        int productQuantity = 1;
        int requestedQuantity = 5;

        int prodId;
        Product prod = new Product("ProdName", BigDecimal.TEN, BigDecimal.TEN, 123);
        prodId = prod.getId();

        Address addr = new Address("str", 1, 123, "Town");
        String pass = "paswd";
        String email = "email";
        User user = new User(email, encode(pass), addr, null);
        User saved = userRepo.save(user);

        new Expectations() {
            {
                userRepo.findUserByEmail(anyString);
                result = user;
                productRepo.findById(prodId);
                result = Map.entry(prod, 1);
            }
        };

        app.login(pass, email);

        //When
        boolean reserve = app.reserveProduct(prodId, requestedQuantity);

        //Then
        assertFalse(reserve);

        new Verifications() {
            {
                productRepo.findById(prodId);
                times = 1;
            }
        };

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
