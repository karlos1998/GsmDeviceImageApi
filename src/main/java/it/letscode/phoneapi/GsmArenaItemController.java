package it.letscode.phoneapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class GsmArenaItemController {

    private final GsmArenaItemRepository repository;
    @GetMapping
    public Page<GsmArenaItem> getAllItems(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GsmArenaItem> getItemById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getItemPhotoById(@PathVariable String id) {
        return repository.findById(id)
                .map(item -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // lub inny odpowiedni typ MIME
                        .body(item.getImageData()))
                .orElse(ResponseEntity.notFound().build());
    }
}
