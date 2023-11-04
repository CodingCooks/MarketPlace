package com.pashonokk.marketplace.repository;

import com.pashonokk.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u left join fetch u.role WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);
}