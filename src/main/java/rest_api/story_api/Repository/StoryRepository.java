package rest_api.story_api.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rest_api.story_api.Entity.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, String> {

    @Query("SELECT s FROM Story s WHERE s.user.username = :username OR s.user IN (SELECT f.friend FROM Friendship f WHERE f.user.username = :username)")
    List<Story> findFriendStories(@Param("username") String username);

}
