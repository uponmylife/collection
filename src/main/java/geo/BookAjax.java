package geo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/book")
public class BookAjax {
    @Autowired
    private BookApi bookApi;
    @Autowired
    private BookDao bookDao;

    @PostMapping
    public String post(Long id) {
        Book book = bookApi.get(id.toString());
        bookDao.save(book);
        return "ok";
    }

    @DeleteMapping
    @RequestMapping("/{id}")
    public String delete(@PathVariable Long id) {
        bookDao.delete(id);
        return "ok";
    }

    @GetMapping
    @RequestMapping("/{id}/cover")
    public byte[] getCoverImage(@PathVariable Long id) {
        return bookDao.findOne(id).getCoverImage();
    }
//
//    @GetMapping
//    @RequestMapping("/download")
//    public String downloadAll() {
//        bookDao.findAll().forEach(book -> {
//            try {
//                book.setCoverImage(ImageUtil.getBytes(book.getCoverUrl()));
//                bookDao.save(book);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        return "ok";
//    }
}
