package it.letscode.phoneapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final GsmArenaItemRepository repository;

    @GetMapping("/find-data-by-model")
    public ResponseEntity<GsmArenaItem> getItemByModel(@RequestParam String model) {
        return repository.findByModel(model)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
