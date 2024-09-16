package cryptocats.backend.controller;

import cryptocats.backend.dto.OwnershipDto;
import cryptocats.backend.entity.Ownership;
import cryptocats.backend.mapper.OwnershipMapper;
import cryptocats.backend.service.OwnershipService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/collection")
@AllArgsConstructor
public class OwnershipController {

    private final OwnershipService ownershipService;
    private final OwnershipMapper ownershipMapper;

    @GetMapping
    public ResponseEntity<Page<OwnershipDto>> getCollectionPage(Pageable pageable,
                                                                @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        Page<Ownership> fetchedOwnership = ownershipService.findOwnershipByUserId(userId, pageable);

        return ResponseEntity.ok(fetchedOwnership.map(ownershipMapper::ownershipToDto));
    }

}
