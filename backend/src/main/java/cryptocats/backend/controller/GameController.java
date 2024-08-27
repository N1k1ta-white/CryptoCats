package cryptocats.backend.controller;

import cryptocats.backend.dto.CatDto;
import cryptocats.backend.entity.Egg;
import cryptocats.backend.exception.NoAuthCookieException;
import cryptocats.backend.repository.EggRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.rmi.server.LogStream.log;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final EggRepository eggRepository;

    @GetMapping("/egg/{id}")
    public ResponseEntity<CatDto> openEgg(@PathVariable Long id) {
        Egg egg = eggRepository.findById(id).orElseThrow(() -> new NoAuthCookieException(""));
        return null;
    }
}
