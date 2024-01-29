package it.letscode.phoneapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
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
}
