package rest_api.story_api.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rest_api.story_api.Entity.User;
import rest_api.story_api.Models.LoginUserRequest;
import rest_api.story_api.Models.TokenResponse;
import rest_api.story_api.Models.WebResponse;
import rest_api.story_api.Services.AuthService;

@RestController
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @Operation(summary = "Login pengguna", description = "Endpoint untuk login pengguna dan mendapatkan token JWT.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login berhasil",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class)) }),
        @ApiResponse(responseCode = "401", description = "Login gagal - kredensial salah")
    })
    @PostMapping(
        path = "/api/auth/login",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request){
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }

    @Operation(summary = "Logout pengguna", description = "Endpoint untuk logout pengguna.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logout berhasil"),
    })
    @DeleteMapping(
    path = "/api/auth/logout",
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logout(User user){
        authService.logout(user);
        return WebResponse.<String>builder().data("Ok").build();
    }
}
