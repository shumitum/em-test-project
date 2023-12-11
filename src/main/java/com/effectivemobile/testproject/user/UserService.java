package com.effectivemobile.testproject.user;

public interface UserService {

    User getUserByEmail(String email);

    User getUserById(Integer userId);
}
