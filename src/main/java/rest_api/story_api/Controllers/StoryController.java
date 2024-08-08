package rest_api.story_api.Controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import rest_api.story_api.Entity.User;
import rest_api.story_api.Models.CreateStoryRequest;
import rest_api.story_api.Models.WebResponse;
import rest_api.story_api.Services.StoryService;
import rest_api.story_api.Models.StoryResponse;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Operation(summary = "Membuat cerita baru", description = "Endpoint untuk membuat cerita baru.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cerita berhasil dibuat",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = StoryResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Input tidak valid")
    })
    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<StoryResponse> createStory(User user, @RequestBody CreateStoryRequest request) {
        StoryResponse storyResponse = storyService.createStory(user, request);
        return WebResponse.<StoryResponse>builder().data(storyResponse).build();
    }

    @Operation(summary = "Menghapus cerita", description = "Endpoint untuk menghapus cerita berdasarkan ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cerita berhasil dihapus",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WebResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "Cerita tidak ditemukan")
    })
    @DeleteMapping(
        path ="/{storyId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deleteStory(User user, @PathVariable String storyId) {
        storyService.deleteStory(user, storyId);
        return WebResponse.<String>builder().data("Story deleted").build();
    }

    @Operation(summary = "Mendapatkan cerita teman", description = "Endpoint untuk mendapatkan cerita dari teman pengguna.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cerita berhasil diambil",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = StoryResponse.class)) }),
        @ApiResponse(responseCode = "401", description = "Pengguna tidak terautentikasi")
    })
    @GetMapping(
        path = "/home",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<StoryResponse>> getFriendStories(User user) {
        List<StoryResponse> stories = storyService.getFriendStories(user);
        return WebResponse.<List<StoryResponse>>builder().data(stories).build();
    }
}
