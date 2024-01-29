package it.letscode.phoneapi;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GsmArenaItemRepository extends MongoRepository<GsmArenaItem, String> {
}
