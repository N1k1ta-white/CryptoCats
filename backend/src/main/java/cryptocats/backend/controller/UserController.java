package cryptocats.backend.controller;

import cryptocats.backend.config.JwtService;
import cryptocats.backend.dto.UserDto;
import cryptocats.backend.entity.User;
import cryptocats.backend.mapper.UserMapper;
import cryptocats.backend.service.UserService;
import cryptocats.backend.util.CookieCreator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final CookieCreator cookieCreator;

    private static final int EXPIRATION = 60 * 60 * 2;

    @PostMapping("/auth")
    public ResponseEntity<UserDto> authUser(@NotNull HttpServletResponse response,
                                            @Valid @RequestBody UserDto userDto) throws NoSuchAlgorithmException, InvalidKeyException {
        User entity = userMapper.DtoToUser(userDto);
        User res = userService.authUser(entity, userDto.getInitData())
                .orElseGet(() -> userService.registerUser(entity));

        UserDto resDto = userMapper.UserToDto(res);

        response.addCookie(cookieCreator.createCookieToken(jwtService.generateToken(res)));

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> test(@NotNull HttpServletResponse response, @NotNull HttpServletRequest req) {
        return new ResponseEntity<String>("GUT", HttpStatus.OK);
    }

}
