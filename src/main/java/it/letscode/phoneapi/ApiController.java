package it.letscode.phoneapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final GsmArenaItemRepository repository;

    private final ModelDataService modelDataService;

    @GetMapping("/find-data-by-model")
    public ResponseEntity<GsmArenaItem> getItemByModel(@RequestParam String model) {
        return repository.findByModel(model)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/find-image-by-model")
    public ResponseEntity<byte[]> getImageByModel(@RequestParam String model, @RequestParam Optional<String> background, @RequestParam Optional<String> apiKey) {
        byte[] imageData = modelDataService.getImageDataByModel(model, background, apiKey);
        if (imageData != null) {
            MediaType imageType = MediaType.IMAGE_JPEG;
            return ResponseEntity.ok()
                    .contentType(imageType)
                    .body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
