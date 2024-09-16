package cryptocats.backend.controller;

import cryptocats.backend.dto.CatDto;
import cryptocats.backend.entity.Content;
import cryptocats.backend.entity.Egg;
import cryptocats.backend.entity.User;
import cryptocats.backend.mapper.CatMapper;
import cryptocats.backend.service.EggService;
import cryptocats.backend.service.UserService;
import cryptocats.backend.util.RandomNumberGenerator;
import cryptocats.backend.util.Searcher;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final EggService eggService;
    private final RandomNumberGenerator randomNumberGenerator;
    private final UserService userService;
    private final CatMapper catMapper;

    @GetMapping("/egg/{id}")
    public ResponseEntity<CatDto> openEgg(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Egg egg = eggService.findById(id);
        String userId = userDetails.getUsername();
        User user = userService.findUserById(userId);

        if (egg.isGift()) {
            userService.updateOpenedTime(user);
        }

        if (!egg.isComputedProbability()) {
            egg.computeProbability();
        }

        Long generatedNumber = randomNumberGenerator.generate();
        List<Content> contents = egg.getContentList();
        Content prize = (Content) Searcher.findSearchable(contents, generatedNumber);

        userService.giveCatToUser(user, prize.getCat());
        CatDto prizeCat =  catMapper.catToDto(prize.getCat());

        return ResponseEntity.ok(prizeCat);
    }
}
