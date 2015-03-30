package com.arborsoft.librarian.controller;

import com.arborsoft.librarian.exception.UserNotFoundException;
import com.arborsoft.librarian.model.User;
import com.arborsoft.librarian.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired protected LoginService loginService;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        LOG.error(exception.getClass().getCanonicalName() + " : " + exception.getMessage(), exception);
        return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public User login(@ModelAttribute String username, @ModelAttribute String password) throws UserNotFoundException {
        return this.loginService.login(username, password);
    }
}
