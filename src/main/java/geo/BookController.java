package geo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
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
    public String list(BookFilter filter, BookOrder order,
                       @CookieValue(value = "filter", defaultValue = "unread") BookFilter prevFilter,
                       @CookieValue(value = "order", defaultValue = "rank") BookOrder prevOrder,
                       HttpServletResponse response, Model model) {
        if (filter == null) filter = prevFilter;
        if (order == null) order = prevOrder;
        response.addCookie(new Cookie("filter", filter.name()));
        response.addCookie(new Cookie("order", order.name()));

        List<Book> books = findByFilter(filter, order);
        if (order == BookOrder.random) Collections.shuffle(books);
        model.addAttribute("books", books);
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
    public String submit(Long id, Boolean read, Boolean toRead, Boolean owned, Boolean interested, Integer rank,
                         String readAt) throws Exception {
        Book book = bookDao.findOne(id);
        book.setRead(read != null && read == true);
        book.setToRead(toRead != null && toRead == true);
        book.setOwned(owned != null && owned == true);
        book.setInterested(interested != null && interested == true);
        book.setRank(rank);
        if (StringUtils.isNotBlank(readAt)) book.setReadAt(new SimpleDateFormat("yyyy.M").parse(readAt));
        bookDao.save(book);
        return "redirect:/book";
    }

    @RequestMapping("/history")
    public String history(Model model) {
        Map<String, List<Book>> books = new TreeMap<>(Collections.reverseOrder());
        List<Book> uncheckedBooks = new ArrayList<>();
        jdbcTemplate.queryForList("select * from book where read=true").stream().map(Book::new).forEach(book -> {
            if (book.getReadAt() == null) {
                uncheckedBooks.add(book);
                return;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(book.getReadAt());
            String season = cal.get(Calendar.YEAR) + " " + (1 + cal.get(Calendar.MONTH)/3) + "Q";
            List<Book> list = books.get(season);
            if (list == null) {
                list = new ArrayList<>();
                books.put(season, list);
            }
            list.add(book);
        });

        model.addAttribute("books", books);
        model.addAttribute("uncheckedBooks", uncheckedBooks);

        return "book-history";
    }
}
