package com.programmers.apitest.users;

import java.util.Optional;

public interface UserRepository {

    void update(User user);

    Optional<User> findById(long id);

    Optional<User> findByEmail(Email email);

}