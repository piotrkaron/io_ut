package io.repository;

import io.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public enum UserRepositoryImpl implements UserRepository {

    INSTANCE;

    private List<User> users = new ArrayList<>();

    @Override
    public Optional<User> findUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public User save(User user) {
        user.setId(UUID.randomUUID().toString());
        users.add(user);
        return user;
    }

    @Override
    public void deleteAll() {
        this.users.clear();
    }
}
