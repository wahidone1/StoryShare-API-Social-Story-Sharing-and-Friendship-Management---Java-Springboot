package rest_api.story_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest_api.story_api.Entity.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findFirstByToken(String Token);
    User findByUsername(String username);
}
