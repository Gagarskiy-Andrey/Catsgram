package ru.yandex.practicum.catsgram.model;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode(of = { "id" })
@ToString
@Data
public class Image {
Long id;
long postId;
String originalFileName;
String filePath;
}
