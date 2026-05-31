package com.whisperspace.Whisperspace_backend.auth.repository;
import com.whisperspace.Whisperspace_backend.auth.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<user, Long> {
    user findByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCase(String username);
    List<user> findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc(String username);

    void deleteByUsername(String username);
    Optional<user> findByEmail(String email);
    @Query("""
    SELECT u FROM user u
    WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%'))
    ORDER BY 
        CASE WHEN LOWER(u.username) = LOWER(:query) THEN 0 ELSE 1 END,
        u.username ASC
""")
    List<user> searchUsers(@Param("query") String query);
    Optional<user> findById(Long id);
}

