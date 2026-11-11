package br.edu.ifsudestemg.bgr.model.repository;

import br.edu.ifsudestemg.bgr.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
