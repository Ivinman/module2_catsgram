package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final Map<Integer, Post> posts = new HashMap<>();
    private final UserService userService;
    private Integer id = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(Integer size, Integer from, String sort) {
        return posts.values().stream().sorted((p0, p1) -> {
            int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
            if (sort.equals("desc")) { //обратный порядок сортировки
                comp = -1 * comp;
            }
            return comp;
        }).skip(from).limit(size).collect(Collectors.toList());
        //return new ArrayList<>(posts.values());
    }

    public Post getById(Integer id) {
        if (posts.get(id) == null) {
            throw new PostNotFoundException("Пост №" + id + " не найден");
        }
        return posts.get(id);
    }

    public Post create(Post post) {
        if (userService.findUserByEmail(post.getAuthor()) == null) {
            throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
        }
        id++;
        post.setId(id);
        posts.put(id, post);
        return post;
    }

    public List<Post> findAllByUserEmail(String email, Integer size, String sort) {
        return posts.values().stream().filter(p -> email.equals(p.getAuthor())).sorted((p0, p1) -> {
            int comp = p0.getCreationDate().compareTo(p1.getCreationDate());
            if (sort.equals("desc")) {
                comp = -1 *  comp;
            }
            return comp;
        }).limit(size).collect(Collectors.toList());
    }
}
