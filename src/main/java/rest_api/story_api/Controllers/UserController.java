package rest_api.story_api.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import rest_api.story_api.Models.RegisterUserRequest;
import rest_api.story_api.Models.WebResponse;
import rest_api.story_api.Services.UserService;
import rest_api.story_api.Entity.User;
import rest_api.story_api.Models.UpdateUserRequest;
import rest_api.story_api.Models.UserResponse;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Registrasi pengguna", description = "Endpoint untuk registrasi pengguna baru.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registrasi berhasil",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = WebResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Input tidak valid")
    })
    @PostMapping(
        path = "/api/users",
        consumes= MediaType.APPLICATION_JSON_VALUE,
        produces= MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterUserRequest request){
        userService.register(request);
        return WebResponse.<String>builder().data("Ok").build();
    }

    @Operation(summary = "Mendapatkan data pengguna saat ini", description = "Endpoint untuk mendapatkan informasi pengguna yang sedang login.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Data pengguna berhasil diambil",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
        @ApiResponse(responseCode = "401", description = "Pengguna tidak terautentikasi")
    })
    @GetMapping(
        path = "/api/users/current",
        produces= MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> get(User user) {
        UserResponse userResponse = UserService.get(user);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

    @Operation(summary = "Memperbarui data pengguna saat ini", description = "Endpoint untuk memperbarui informasi pengguna yang sedang login.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Data pengguna berhasil diperbarui",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Input tidak valid"),
        @ApiResponse(responseCode = "401", description = "Pengguna tidak terautentikasi")
    })
    @PatchMapping(
        path = "/api/users/current",
        consumes= MediaType.APPLICATION_JSON_VALUE,
        produces= MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(User user, @RequestBody UpdateUserRequest request){
        UserResponse userResponse = userService.update(user, request);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }
}
