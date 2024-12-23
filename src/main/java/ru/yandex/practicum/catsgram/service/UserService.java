package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> findAll() {
        return users.values();
    }

    public User addNewUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        if (users.containsValue(user)) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (users.containsKey(user.getId())) {
            if (users.containsValue(user)) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
            if (user.getUsername() != null) {
                users.get(user.getId()).setUsername(user.getUsername());
            }
            if (user.getEmail() != null) {
                users.get(user.getId()).setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                users.get(user.getId()).setPassword(user.getPassword());
            }
            return users.get(user.getId());
        }
        throw new NotFoundException("Пользователь с id = " + user.getId() + " не найден");
    }

    // вспомогательный метод для генерации идентификатора нового поста
    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    public Optional<User> findUserById(Long id) {
        if (!users.containsKey(id)) {
            return Optional.empty();
        } else {
            return Optional.of(users.get(id));
        }
    }
}

