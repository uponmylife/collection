package geo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
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
}
