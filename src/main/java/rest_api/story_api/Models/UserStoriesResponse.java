package rest_api.story_api.Models;

import java.util.List;

public class UserStoriesResponse {
    private String username;
    private List<StoryResponse> stories;

    // Constructor
    public UserStoriesResponse(String username, List<StoryResponse> stories) {
        this.username = username;
        this.stories = stories;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<StoryResponse> getStories() {
        return stories;
    }

    public void setStories(List<StoryResponse> stories) {
        this.stories = stories;
    }
}
