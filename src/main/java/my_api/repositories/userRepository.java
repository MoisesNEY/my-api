package my_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import my_api.models.User;
import java.util.Optional;

public interface userRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
}
