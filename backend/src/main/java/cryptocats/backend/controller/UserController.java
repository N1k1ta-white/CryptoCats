package cryptocats.backend.controller;

import cryptocats.backend.config.JwtService;
import cryptocats.backend.dto.UserDto;
import cryptocats.backend.entity.User;
import cryptocats.backend.mapper.UserMapper;
import cryptocats.backend.service.UserService;
import cryptocats.backend.util.CookieCreator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final CookieCreator cookieCreator;

    @PostMapping("/auth")
    public ResponseEntity<UserDto> authUser(@NotNull HttpServletResponse response,
                                            @Valid @RequestBody UserDto userDto) {
        User entity = userMapper.dtoToUser(userDto);

        User res = userService.authUser(entity, userDto.getInitData())
                .orElseGet(() -> userService.registerUser(entity));

        UserDto resDto = userMapper.userToDto(res);

        String jwtToken = jwtService.generateToken(res);
        Cookie cookie = cookieCreator.createCookieWithJwtToken(jwtToken);
        response.addCookie(cookie);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    @PostMapping("/referrals")
    public ResponseEntity<Page<UserDto>> getReferrals(Pageable pageable, UserDto owner) {
        Page<User> fetchedReferrals = userService.getAllReferrals(pageable, owner.getId());
        return new ResponseEntity<>(fetchedReferrals.map(userMapper::userToDto), HttpStatus.OK);
    }

}
