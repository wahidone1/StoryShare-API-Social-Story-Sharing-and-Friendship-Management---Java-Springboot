package rest_api.story_api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import rest_api.story_api.Entity.Friendship;
import rest_api.story_api.Entity.User;
import rest_api.story_api.Models.AddFriendshipRequest;
import rest_api.story_api.Models.FriendshipResponse;
import rest_api.story_api.Repository.FriendshipRepository;
import rest_api.story_api.Repository.UserRepository;

import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public Friendship addFriend(User user, AddFriendshipRequest request) {
        User friend = userRepository.findById(request.getFriend())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend not found"));
        validationService.validate(request);
        Friendship friendship = Friendship.builder()
            .id(UUID.randomUUID().toString())
            .user(user)
            .friend(friend)
            .build();

        return friendshipRepository.save(friendship);
    }

    @Transactional
    public List<FriendshipResponse> getFriends(User user) {
        List<User> friends = friendshipRepository.findFriendsByUsername(user.getUsername());

        return friends.stream()
            .map(friend -> FriendshipResponse.builder()
                .user(user.getUsername())
                .friend(friend.getUsername())
                .build())
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFriend(User user, String friendUsername) {
        User friend = userRepository.findById(friendUsername)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend not found"));

        friendshipRepository.deleteByUserAndFriend(user, friend);
    }

}
