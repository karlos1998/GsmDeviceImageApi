package it.letscode.phoneapi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GsmArenaItemRepository extends MongoRepository<GsmArenaItem, String> {
    @Query("{ 'models': ?0 }")
    Optional<GsmArenaItem> findByModel(String model);
}
