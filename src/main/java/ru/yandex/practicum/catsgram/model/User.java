package ru.yandex.practicum.catsgram.model;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@EqualsAndHashCode(of = { "email" })
@ToString
@Data
public class User {
Long id;
String username;
String email;
String password;
Instant registrationDate;
}
