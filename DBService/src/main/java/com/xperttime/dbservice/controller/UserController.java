package com.xperttime.dbservice.controller;

import com.xperttime.dbservice.model.User;
import com.xperttime.dbservice.repository.UserRepository;
import com.xperttime.dbservice.service.UserService;
import io.micrometer.core.lang.Nullable;
import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path="/",
            consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody Long addNewUser(@RequestBody User user){
        User result = userService.addUser(user);
        return result.getId();
    }

    @PutMapping(path="/{userId}",
            consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user){
        return userService.replaceUser(userId, user).map(c -> ResponseEntity.ok(c))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
     }

    @DeleteMapping(path="/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId){
        return userService.getUser(userId).map(c -> {
            userService.deleteUser(c);
           return ResponseEntity.ok(c);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping(path="/all", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path="/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody Optional<User> getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PatchMapping(path="/{userId}",
            consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> updateUserFields(@PathVariable Long userId, @RequestBody User user){
        return userService.updateUser(userId, user).map(c -> ResponseEntity.ok(c))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /*@GetMapping(path="/{email}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping(path="/{loginHandle}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody Optional<User> getUserByLoginHandle(@PathVariable String loginHandle) {
        return userService.getUserByLoginHandle(loginHandle);
    }*/

}
