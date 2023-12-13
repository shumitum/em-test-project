package com.effectivemobile.testproject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(String.format("Пользователя с почтой %s не существует", email)));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Пользователя с ID=%d не существует", userId)));
    }

    @Override
    @Transactional(readOnly = true)
    public void checkUserExistence(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException(String.format("Пользователя с ID=%d не существует", userId));
        }
    }
}
