package geo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
