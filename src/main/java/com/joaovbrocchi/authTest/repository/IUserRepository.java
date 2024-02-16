package com.joaovbrocchi.authTest.repository;

import com.joaovbrocchi.authTest.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserRepository extends JpaRepository<User, Integer> {
    UserDetails findByEmail(String Email);
}
