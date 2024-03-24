package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap();

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public User createUser(User user) {
        if (users.containsValue(user)) {
            throw new UserAlreadyExistException("Данный пользователь уже добавлен");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || user.getEmail().isEmpty()) {
            throw new InvalidEmailException("Неверный email");
        }
        users.put(user.getEmail(), user);
        return user;
    }

    public User updateOrCreateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || user.getEmail().isEmpty()) {
            throw new InvalidEmailException("Неверный email");
        }
        users.put(user.getEmail(), user);
        return user;
    }

    /*public User findUserByEmail(String author) {
        for (User user : users.values()) {
            if (user.getNickname().equals(author)) {
                return user;
            }
        }
        return null;
    }*/

    /*public User userByEmail(String email) {
        if (users.get(email) == null) {
            throw new UserNotFoundException("Пользователь с почтой " + email + " не найден");
        }
        return users.get(email);
    }*/
    public User findUserByEmail(String email) {
        if (email == null || users.get(email) == null) {
            throw new UserNotFoundException("Пользователь с почтой " + email + " не найден");
            //return null;
        }
        return users.get(email);
    }
}
