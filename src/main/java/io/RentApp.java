package io;

import io.entity.Address;
import io.entity.Product;
import io.entity.RentList;
import io.entity.User;
import io.repository.ProductRepository;
import io.repository.RentListRepository;
import io.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class RentApp {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private RentListRepository rentListRepository;
    private User currentUser;
    private RentList currentRentList = null;

    public RentApp(
            ProductRepository productRepository,
            UserRepository userRepository,
            RentListRepository rentListRepository) {

        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.rentListRepository = rentListRepository;
    }

    public boolean login(String password, String email) {
        Optional<User> opt = userRepository.findUserByEmail(email);
        if (opt.isEmpty()) {
            showError("Bledne dane");
            return false;
        }

        User user = opt.get();

        if (!isPasswordCorrect(password, user)) {
            showError("Bledne dane");
            return false;
        }

        setupSession(user);
        redirectToMainPage();
        return true;
    }

    public Map<Product, Integer> getCatalogue() {
        return productRepository.findAllAvailableProducts();
    }

    public boolean reserveProduct(int productId, int quantity) {
        if (currentUser == null) {
            showError("Nie zalogowano.");
            return false;
        }

        Optional<Map.Entry<Product, Integer>> prodOpt = productRepository.findById(productId);
        if (prodOpt.isEmpty() || prodOpt.get().getValue() < quantity) {
            showError("Brak produktu lub zly kod (id)");
            return false;
        }

        if (currentRentList == null) {
            currentRentList = new RentList(currentUser.getId());
            currentRentList = rentListRepository.save(currentRentList);
        }

        currentRentList.createRent(prodOpt.get().getKey(), quantity);
        rentListRepository.save(currentRentList);
        return true;
    }

    public void showRentList() {
        if (currentRentList == null) {
            showError("Brak listy");
        } else {
            System.out.println(currentRentList.toString());
        }
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

    private boolean isPasswordCorrect(String password, User user) {
        return encode(password).trim().toLowerCase()
                .equals(user.getPassword().trim().toLowerCase());
    }

    private void setupSession(User user) {
        currentUser = user;
    }

    private void redirectToMainPage() {
        System.out.println("Powrot na strone glowna");
    }

    private void showError(String error) {
        System.out.println(error);
    }

    public void populateExampleData() {
        Address address = new Address("Ciemna", 4, 42253, "Elo");

        User usr1 = new User("test", encode("test"), address, "id");
        userRepository.save(usr1);

        Product p1 = new Product("Latarka", BigDecimal.TEN, BigDecimal.TEN, 0);
        productRepository.save(p1, 123);

        Product p2 = new Product("Mlot", BigDecimal.valueOf(25.5), BigDecimal.TEN, 0);
        productRepository.save(p2, 1);

        Product p3 = new Product("Lina", BigDecimal.valueOf(25.5), BigDecimal.TEN, 0);
        productRepository.save(p3, 3);
    }
}
