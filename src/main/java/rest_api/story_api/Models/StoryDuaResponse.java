package rest_api.story_api.Models;

import java.time.LocalDateTime;

public class StoryDuaResponse {
    private String id;
    private String story;
    private LocalDateTime createdAt;

    // Constructor
    public StoryDuaResponse(String id, String story, LocalDateTime createdAt) {
        this.id = id;
        this.story = story;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
