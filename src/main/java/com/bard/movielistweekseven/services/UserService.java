package com.bard.movielistweekseven.services;

import com.bard.movielistweekseven.utils.RegexValidation;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private final Map<String, String> userList;


    public UserService() {
        this.userList = new HashMap<>();
    }


    public Optional<String> generateToken(String email) {
        if (!RegexValidation.emailValidation(email)) {
            return Optional.empty();
        }
        Optional<String> optionalToken = Optional.empty();
        Optional<String> user = this.userList.values().stream().filter(e -> e.equals(email)).findFirst();

        if (user.isEmpty()) {
            String token = UUID.randomUUID().toString();
            optionalToken = Optional.of(token);
            this.userList.put(token, email);
        }

        return optionalToken;


    }

    public String getEmail(String token) {
        return this.userList.get(token);
    }

    public boolean checkIfPresent(String token){
        return this.userList.getOrDefault(token, "").isEmpty();
    }


}
