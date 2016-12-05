package geo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
public class Book {
    @Id
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Date publishedAt;
    private Integer price;
    private String coverUrl;
    private String link;
    private String description;
    private Boolean read = false;
    private Boolean toRead = false;
    private Boolean owned = false;
    private Boolean interested = false;
    private Integer rank = 4;
    private Date readAt;

    public Book(Map<String, Object> map) {
        id = (Long) map.get("id");
        title = (String) map.get("title");
        author = (String) map.get("author");
        publisher = (String) map.get("publisher");
        publishedAt = (Date) map.get("published_at");
        price = (Integer) map.get("price");
        coverUrl = (String) map.get("cover_url");
        link = (String) map.get("link");
        description = (String) map.get("description");
        read = (Boolean) map.get("read");
        toRead = (Boolean) map.get("to_read");
        owned = (Boolean) map.get("owned");
        interested = (Boolean) map.get("interested");
        rank = (Integer) map.get("rank");
        readAt = (Date) map.get("read_at");
    }
}
