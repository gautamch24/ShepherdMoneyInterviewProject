package com.shepherdmoney.interviewproject.repository;

import com.shepherdmoney.interviewproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for User entity
 */
@Repository("UserRepo")
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int userId); 
    void deleteById(int userId);
}

