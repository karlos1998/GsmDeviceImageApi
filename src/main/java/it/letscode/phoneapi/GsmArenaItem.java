package it.letscode.phoneapi;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class GsmArenaItem {
    @Id
    private String id;

    private String url;
}
