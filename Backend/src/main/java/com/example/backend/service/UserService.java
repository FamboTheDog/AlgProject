package com.example.backend.service;

import com.example.backend.db.User;
import com.example.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repo;

    private PasswordEncoder encoder;

    public User register(User user) {
        Optional<User> sameName = repo.findByName(user.getName());
        if (sameName.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        user.setPassword(encoder.encode(user.getPassword()));

        return repo.save(user);
    }

    public User login(User user) {
        Optional<User> toLogin = repo.findByName(user.getName());
        if (toLogin.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (!encoder.matches(user.getPassword(), toLogin.get().getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return toLogin.get();
    }

}
