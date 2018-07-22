package com.xperttime.dbservice.service;

import com.xperttime.dbservice.exception.EmailAlreadyExists;
import com.xperttime.dbservice.exception.LoginAlreadyExists;
import com.xperttime.dbservice.model.User;
import com.xperttime.dbservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()) throw new EmailAlreadyExists();
        if (userRepository.findByLoginHandle(user.getLoginHandle()).isPresent()) throw new LoginAlreadyExists();
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long userId, User source){
        return userRepository.findById(userId).map(c -> Optional.of(userRepository.save(copyUser(source, c))))
                .orElse(Optional.ofNullable(null));
    }

    public Optional<User> replaceUser(Long userId, User source){
          return userRepository.findById(userId).map(c -> Optional.of(userRepository.save(copyUser(source, c, true))))
                .orElse(Optional.ofNullable(null));
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByLoginHandle(String loginHandle) {
        return userRepository.findByLoginHandle(loginHandle);
    }
    private User copyUser(User source, User dest){
        return copyUser(source, dest, false);
    }

    private User copyUser(User source, User dest, boolean replace){
        if (userRepository.findByEmail(source.getEmail()).filter(c -> c.getId() != dest.getId()).isPresent()) throw new EmailAlreadyExists();
        if (replace){
            if (userRepository.findByLoginHandle(source.getLoginHandle()).filter(c -> c.getId() != dest.getId()).isPresent()) throw new LoginAlreadyExists();
            dest.setFirstName(source.getFirstName());
            dest.setLastName(source.getLastName());
            dest.setEmail(source.getEmail());
            dest.setLoginHandle(source.getLoginHandle());
        } else {
            if (source.getFirstName() != null) dest.setFirstName(source.getFirstName());
            if (source.getLastName() != null) dest.setLastName(source.getLastName());
            if (source.getEmail() != null) dest.setEmail(source.getEmail());
        }
        return dest;
    }
}
