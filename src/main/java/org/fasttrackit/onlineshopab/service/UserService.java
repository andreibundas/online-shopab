package org.fasttrackit.onlineshopab.service;

import org.fasttrackit.onlineshopab.domain.User;
import org.fasttrackit.onlineshopab.persistence.UserRepository;
import org.fasttrackit.onlineshopab.transfer.SaveUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(SaveUserRequest request) {
//        LOGGER.info("Creating user " + request);
        LOGGER.info("Creating user {}", request);
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }
}
