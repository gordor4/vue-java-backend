package ru.rus.integrato.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rus.integrato.domain.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
