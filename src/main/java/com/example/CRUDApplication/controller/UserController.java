package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.model.User;
import com.example.CRUDApplication.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {

        try {
            List<User> userList = new ArrayList<>();
            userRepo.findAll().forEach(userList::add);

            if (userList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getSpecificUsersById")
    public ResponseEntity<List<User>> getSpecificUsers(@RequestBody List<Long> idList) {

        try {
            List<User> userList = userRepo.findUsersByIdList(idList);

            if (userList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            Optional<User> userData = userRepo.findById(id);

            if (userData.isPresent()) {
                return new ResponseEntity<>(userData.get(), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {

        try {
            User userSaved = userRepo.save(user);

            return new ResponseEntity<>(userSaved, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUserById/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User newUserData) {
        try {
            Optional<User> oldUserData = userRepo.findById(id);

            if (oldUserData.isPresent()) {
                User updatedUser = oldUserData.get();
                updatedUser.setEmail(newUserData.getEmail());
                updatedUser.setUsername(newUserData.getUsername());
                updatedUser.setFirstName(newUserData.getFirstName());
                updatedUser.setLastName(newUserData.getLastName());

                User userSaved = userRepo.save(updatedUser);
                return new ResponseEntity<>(userSaved, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        try {
            userRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUsersByIds")
    public ResponseEntity<HttpStatus> deleteUsersByIds(@RequestBody List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        try {
            for (Long id : idList) {
                userRepo.deleteById(id);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

