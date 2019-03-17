package ru.rus.integrato.controller;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rus.integrato.domain.user.ProfileUser;
import ru.rus.integrato.domain.user.User;
import ru.rus.integrato.filter.AuthFilter;
import ru.rus.integrato.repository.UserRepository;
import ru.rus.integrato.utils.PasswordUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(path = "/users")
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    ServletContext uriInfo;

    @PostMapping(path = "/get")
    public ResponseEntity findUser() {
        User user = userRepository.findUserByUsername((String) request.getAttribute(AuthFilter.USER));
        ProfileUser userResponse = new ProfileUser(user, "");

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(path = "/resetPassword", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity resetPassword(@RequestParam("newPassword") String newPassword) {
        try {
            User user = userRepository.findUserByUsername((String) request.getAttribute(AuthFilter.USER));
            user.setPassword(PasswordUtil.getSaltedHash(newPassword));
            userRepository.save(user);

            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping(path = "/findUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser(@RequestBody User userParam) {
        User user = userRepository.findUserByUsername(userParam.getUsername());

        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/activateUser")
    public ResponseEntity activateAccount() {
        User user = userRepository.findUserByUsername((String) request.getAttribute(AuthFilter.USER));
        user.setActivated(true);

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUserData(@RequestBody ProfileUser profileUser) {
        User user = userRepository.findUserByUsername((String) request.getAttribute(AuthFilter.USER));

        user.setFirstName(profileUser.getFirstName());
        user.setLastName(profileUser.getLastName());
        user.setSecondName(profileUser.getSecondName());

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }


    @PostMapping(path = "/uploadUserPhoto", consumes = FileUploadBase.MULTIPART_FORM_DATA)
    public ResponseEntity uploadPhoto() {

        return ResponseEntity.ok().build();
    }
}
