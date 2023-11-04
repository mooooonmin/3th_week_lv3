package com.level3.admin.repository;

import com.level3.admin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository<엔티티, 엔티티 PK 타입>
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
