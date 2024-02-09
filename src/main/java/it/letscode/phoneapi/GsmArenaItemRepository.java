package it.letscode.phoneapi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GsmArenaItemRepository extends MongoRepository<GsmArenaItem, String> {
    @Query("{ '$or': [ { 'models': ?0 }, { 'modelName': { '$regex': ?1, '$options': 'i' } } ] }")
    List<GsmArenaItem> findByModelOrModelName(String model, String regexPattern);

}
