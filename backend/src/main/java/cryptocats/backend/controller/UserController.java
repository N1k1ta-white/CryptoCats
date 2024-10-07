package cryptocats.backend.controller;

import cryptocats.backend.config.JwtService;
import cryptocats.backend.dto.PartialUserResponse;
import cryptocats.backend.dto.UserDto;
import cryptocats.backend.entity.User;
import cryptocats.backend.mapper.PartialUserResponseMapper;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final PartialUserResponseMapper userResponseMapper;
    private final PagedResourcesAssembler<PartialUserResponse> pagedResourcesAssemblerUserResponse;

    @PostMapping("/auth")
    public ResponseEntity<UserDto> authUser(@NotNull HttpServletResponse response,
                                            @Valid @RequestBody UserDto userDto) {
        User entity = userMapper.dtoToUser(userDto);

        User res = userService.authorizeUser(entity, userDto.getInitData())
                .orElseGet(() -> userService.registerUser(entity));

        UserDto resDto = userMapper.userToDto(res);

        String jwtToken = jwtService.generateToken(res);
        Cookie cookie = cookieCreator.createCookieWithJwtToken(jwtToken);
        response.addCookie(cookie);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    @GetMapping("/referrals")
    public ResponseEntity<PagedModel<EntityModel<PartialUserResponse>>> getReferrals(@PageableDefault Pageable pageable,
                                                                      @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());

        Page<User> fetchedReferrals = userService.getAllReferrals(pageable, userId);
        return new ResponseEntity<>(pagedResourcesAssemblerUserResponse.toModel(
                fetchedReferrals.map(userResponseMapper::userToPartialUserResponse)), HttpStatus.OK);
    }

}
