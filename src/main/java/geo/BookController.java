package geo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookApi bookApi;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/search")
    public String search(String query, Model model) {
        if (isNotEmpty(query)) {
            List<Book> books = bookApi.search(query);
            model.addAttribute("books", books);
        }
        return "book-search";
    }

    @GetMapping
    public String list(BookFilter filter, BookOrder order, Model model) {
        if (filter == null) filter = BookFilter.unread;
        if (order == null) order = BookOrder.rank;
        model.addAttribute("books", findByFilter(filter, order));
        model.addAttribute("filter", filter);
        model.addAttribute("order", order);
        return "book-list";
    }

    private List<Book> findByFilter(BookFilter filter, BookOrder order) {
        String sql = "select * from book where 1=1" + filter.getSql() + order.getSql();
        return jdbcTemplate.queryForList(sql).stream().map(Book::new).collect(Collectors.toList());
    }

    @RequestMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookDao.findOne(id));
        return "book-detail";
    }

    @RequestMapping("/submit")
    public String submit(Long id, Boolean read, Boolean toRead, Boolean owned, Boolean interested, Integer rank) {
        Book book = bookDao.findOne(id);
        book.setRead(read != null && read == true);
        book.setToRead(toRead != null && toRead == true);
        book.setOwned(owned != null && owned == true);
        book.setInterested(interested != null && interested == true);
        book.setRank(rank);
        bookDao.save(book);
        return "redirect:/book";
    }
}
