package rest_api.story_api.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import rest_api.story_api.Entity.User;
import rest_api.story_api.Models.AddFriendshipRequest;
import rest_api.story_api.Models.FriendshipResponse;
import rest_api.story_api.Models.WebResponse;
import rest_api.story_api.Services.FriendshipService;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @Operation(summary = "Menambah teman", description = "Endpoint untuk menambah teman baru.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Teman berhasil ditambahkan",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WebResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Input tidak valid"),
        @ApiResponse(responseCode = "404", description = "Pengguna tidak ditemukan")
    })
    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> addFriend(User user, @RequestBody AddFriendshipRequest request) {
        friendshipService.addFriend(user, request);
        return WebResponse.<String>builder().data("Friend added").build();
    }

    @Operation(summary = "Mendapatkan daftar teman", description = "Endpoint untuk mendapatkan daftar teman pengguna.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Daftar teman berhasil diambil",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = FriendshipResponse.class)) }),
        @ApiResponse(responseCode = "401", description = "Pengguna tidak terautentikasi")
    })
    @GetMapping(
        path = "/list",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<FriendshipResponse>> listFriends(User user) {
        List<FriendshipResponse> friendResponses = friendshipService.getFriends(user);
        return WebResponse.<List<FriendshipResponse>>builder().data(friendResponses).build();
    }

    @Operation(summary = "Menghapus teman", description = "Endpoint untuk menghapus teman berdasarkan username.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Teman berhasil dihapus",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WebResponse.class)) }),
        @ApiResponse(responseCode = "404", description = "Teman tidak ditemukan")
    })
    @DeleteMapping(
        path = "/{friendUsername}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> deleteFriend(User user, @PathVariable String friendUsername) {
        friendshipService.deleteFriend(user, friendUsername);
        return WebResponse.<String>builder().data("Friend deleted").build();
    }
}
