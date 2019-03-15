package ru.rus.integrato.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rus.integrato.domain.TokenResponse;
import ru.rus.integrato.domain.user.EmailReset;
import ru.rus.integrato.domain.user.User;
import ru.rus.integrato.repository.UserRepository;
import ru.rus.integrato.service.EmailService;
import ru.rus.integrato.utils.KeyGenerator;
import ru.rus.integrato.utils.PasswordUtil;
import ru.rus.integrato.utils.TokenUtils;

import javax.servlet.ServletContext;
import java.util.Date;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeyGenerator keyGenerator;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ServletContext uriInfo;

    @PostMapping(path = "auth", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public TokenResponse authenticateUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password) {
        try {
            String id = authenticate(username, password);
            String token = TokenUtils.issueToken(username, id, keyGenerator, uriInfo.getContextPath());
            return new TokenResponse(token);
        } catch (Exception e) {
            return null;
        }
    }

    private String authenticate(String login, String password) throws Exception {
        User user = userRepository.findUserByUsername(login);

        if (user == null || !PasswordUtil.check(password, user.getPassword()))
            throw new SecurityException("Invalid user/password");

        return String.valueOf(user.getId());
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody User user) {
        try {
            String userPassword = user.getPassword();
            user.setPassword(PasswordUtil.getSaltedHash(userPassword));
            user.setCreationDate(new Date());
            user.setActivated(false);
            user.setBanned(false);

            userRepository.save(user);
            emailService.sendEmailActivation(user.getEmail(), user.getUsername());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping(path = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendResetEmail(@RequestBody EmailReset email) {
        User user = userRepository.findUserByEmail(email.getEmail());
        if (user != null) {
            String token = TokenUtils.issueToken(user.getUsername(), String.valueOf(user.getId()), keyGenerator, uriInfo.getContextPath());
            emailService.sendResetPassword(user.getEmail(), user.getUsername(), token);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }

}
