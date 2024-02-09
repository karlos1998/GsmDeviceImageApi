package it.letscode.phoneapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModelDataService {

    private final GsmArenaItemRepository repository;

    private final RemoveBgService removeBgService;

    public Optional<GsmArenaItem> getItemByModel(String model) {
        return repository.findByModelOrModelNameSimilar(model, ".*" + model.replaceAll("\\s+", "").toLowerCase() + ".*");
    }

    public byte[] getImageDataByModel(String model, Optional<String> background, Optional<String> apiKey) {
        return getItemByModel(model).map(item -> {
            if (background.isPresent() && background.get().equals("remove") && apiKey.isPresent()) {
                if (item.getImageDataWithoutBackground() == null) {
                    byte[] imageDataWithoutBg = removeBgService.removeBackground(item.getImageData(), apiKey.get());
                    item.setImageDataWithoutBackground(imageDataWithoutBg);
                    repository.save(item);
                    return imageDataWithoutBg;
                } else {
                    return item.getImageDataWithoutBackground();
                }
            } else {
                return item.getImageData();
            }
        }).orElse(null);
    }
}
