package rest_api.story_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rest_api.story_api.Entity.Friendship;

import java.util.List;

import rest_api.story_api.Entity.User;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, String> {
    
    @Query("SELECT f.friend FROM Friendship f WHERE f.user.username = :username")
    List<User> findFriendsByUsername(@Param("username") String username);

    void deleteByUserAndFriend(User user, User friend);
}
