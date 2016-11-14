package geo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface BookDao extends CrudRepository<Book, Long> {
    List<Book> findByOrderByPublishedAtDesc();
}
