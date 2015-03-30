package com.arborsoft.librarian.service;

import com.arborsoft.librarian.exception.UserNotFoundException;
import com.arborsoft.librarian.model.User;
import com.arborsoft.librarian.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

    @Autowired private UserRepository userRepository;

    public User login(String username, String password) throws UserNotFoundException {
        try {
            return this.userRepository.authenticate(username, password);
        } catch (Exception exception) {
            throw new UserNotFoundException(exception.getMessage(), exception);
        }
    }
}
