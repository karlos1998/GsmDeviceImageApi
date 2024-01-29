package it.letscode.phoneapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/find-image-by-model")
    public ResponseEntity<byte[]> getImageByModel(@RequestParam String model) {
        return repository.findByModel(model)
                .map(item -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(item.getImageData()))
                .orElse(ResponseEntity.notFound().build());
    }
}
