package geo;

import org.springframework.data.repository.CrudRepository;

interface BookDao extends CrudRepository<Book, Long> {
}
