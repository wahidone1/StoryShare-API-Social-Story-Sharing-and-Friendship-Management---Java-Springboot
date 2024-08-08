package rest_api.story_api.Services;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import rest_api.story_api.Entity.Story;
import rest_api.story_api.Entity.User;
import rest_api.story_api.Models.CreateStoryRequest;
import rest_api.story_api.Repository.StoryRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import rest_api.story_api.Models.StoryResponse;

@Service
public class StoryService {

    private static final Logger logger = LoggerFactory.getLogger(StoryService.class);


    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public StoryResponse createStory(User user, CreateStoryRequest request) {
        logger.info("ini adalah isi User: {}", user);
        validationService.validate(request);
        Story story = new Story();
        story.setId(UUID.randomUUID().toString());
        story.setStory(request.getStory());
        story.setCreatedAt(LocalDateTime.now());
        story.setUser(user);

        storyRepository.save(story);

        return StoryResponse.builder()
            .id(story.getId())
            .user(story.getUser().getUsername())
            .story(story.getStory())
            .createdAt(story.getCreatedAt())
            .build();
    }

    @Transactional
    public void deleteStory(User user, String storyId) {
        Story story = storyRepository.findById(storyId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));

        // Verifikasi bahwa pengguna yang meminta penghapusan adalah pemilik story
        if (!story.getUser().getUsername().equals(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete story of another user");
        }

        storyRepository.delete(story);
    }

    @Transactional
    public List<StoryResponse> getFriendStories(User user) {
        List<Story> stories = storyRepository.findFriendStories(user.getUsername());
        
        return stories.stream()
            .map(story -> StoryResponse.builder()
            .id(story.getId())
            .user(story.getUser().getUsername())
            .story(story.getStory())
            .createdAt(story.getCreatedAt())
            .build())
            .collect(Collectors.toList());
    }
}
