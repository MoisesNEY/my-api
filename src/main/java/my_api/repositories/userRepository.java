package my_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import my_api.models.User;

public interface userRepository extends JpaRepository<User, Long>{

}
