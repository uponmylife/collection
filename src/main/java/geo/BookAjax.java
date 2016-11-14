package geo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookAjax {
    @Autowired
    private BookApi bookApi;
    @Autowired
    private BookDao bookDao;

    @PostMapping
    public String post(Long id) {
        Book book = bookApi.search(id.toString()).get(0);
        bookDao.save(book);
        return "ok";
    }

    @DeleteMapping
    @RequestMapping("/{id}")
    public String delete(@PathVariable Long id) {
        bookDao.delete(id);
        return "ok";
    }
}
