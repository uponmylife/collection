package geo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookApi bookApi;
    @Autowired
    private BookDao bookDao;

    @RequestMapping("/search")
    public String search(String query, Model model) {
        if (!StringUtils.isEmpty(query)) {
            List<Book> books = bookApi.search(query);
            model.addAttribute("books", books);
        }
        return "book-search";
    }

    @GetMapping
    public String list(Boolean read, Boolean toRead, Boolean owned, Boolean interested, Model model) {
        model.addAttribute("books", bookDao.findByOrderByPublishedAtDesc());
        return "book-list";
    }

    @RequestMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookDao.findOne(id));
        return "book-detail";
    }

    @RequestMapping("/submit")
    public String submit(Long id, Boolean read, Boolean toRead, Boolean owned, Boolean interested, Integer rank) {
        Book book  = bookDao.findOne(id);
        book.setRead(read != null && read == true);
        book.setToRead(toRead != null && toRead == true);
        book.setOwned(owned != null && owned == true);
        book.setInterested(interested != null && interested == true);
        book.setRank(rank);
        bookDao.save(book);
        return "redirect:/book/list";
    }
}
