package geo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookApi bookApi;

    @RequestMapping("/search")
    public String search(String query, Model model) {
        if (!StringUtils.isEmpty(query)) {
            List<Book> books = bookApi.search(query);
            model.addAttribute("books", books);
        }
        return "book-search";
    }
}
