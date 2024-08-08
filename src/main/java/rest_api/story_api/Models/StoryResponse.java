package rest_api.story_api.Models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoryResponse {
    private String id;
    private String user;
    private String story;
    private LocalDateTime createdAt;
}
