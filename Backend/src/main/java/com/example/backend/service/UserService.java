package com.example.backend.service;

import com.example.backend.db.User;
import com.example.backend.exceptions.errors.UserCredentialsError;
import com.example.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repo;

    private PasswordEncoder encoder;

    public User register(User user) {
        Optional<User> sameName = repo.findByName(user.getName());
        if (sameName.isPresent()) throw new UserCredentialsError("Username is already taken");

        user.setPassword(encoder.encode(user.getPassword()));

        return repo.save(user);
    }

    public User login(User user) {
        Optional<User> toLogin = repo.findByName(user.getName());
        if (toLogin.isEmpty() || !encoder.matches(user.getPassword(), toLogin.get().getPassword()))
            throw new UserCredentialsError("Wrong username or password");

        return toLogin.get();
    }

    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        repo.findAll().forEach(users::add);
        return users;
    }
}
