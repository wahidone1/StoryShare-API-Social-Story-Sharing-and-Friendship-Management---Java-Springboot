package rest_api.story_api.Services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import rest_api.story_api.Entity.User;
import rest_api.story_api.Models.RegisterUserRequest;
import rest_api.story_api.Models.UpdateUserRequest;
import rest_api.story_api.Models.UserResponse;
import rest_api.story_api.Repository.UserRepository;
import rest_api.story_api.Security.BCrypt;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;


    // public UserService(Validator validator) {
    //     this.validator = validator;
    // }

    @Transactional
    public void register(RegisterUserRequest request){
        validationService.validate(request);

        if(userRepository.existsById(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);

        // Friendship friendship = new Friendship();
        // friendship.setId(UUID.randomUUID().toString());
        // friendship.setUser(request.getUsername());
        // friendship.setFriend(request.getUsername());

        // friendshipRepository.save(friendship);
    }

    public static UserResponse get(User user){
        return UserResponse.builder()
            .username(user.getUsername())
            .name(user.getName())
            .build();
    }

    @Transactional
    public UserResponse update(User user, UpdateUserRequest request){
        validationService.validate(request);

        if(Objects.nonNull(request.getName())){
            user.setName(request.getName());
        }
        if(Objects.nonNull(request.getPassword())){
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(user);

        return UserResponse.builder()
            .name(user.getName())
            .username(user.getUsername())
            .build();
    }

}
