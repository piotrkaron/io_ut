package io.repository;

import io.entity.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByEmail(String email);

    User save(User user);

    void deleteAll();
}
