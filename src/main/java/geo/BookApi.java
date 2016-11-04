package geo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookApi {
    public static final String API_URL = "https://apis.daum.net/search/book?apikey=5ee27d4d783eb83c77c1def985b530a1&output=json";

    public List<Book> search(String query) {
        Map response = new RestTemplate().getForObject(API_URL + "&q=" + query, Map.class);
        return (List<Book>) ((List<Map>) ((Map) response.get("channel")).get("item")).stream()
                .map(this::parse).collect(Collectors.toList());
    }

    private Book parse(Map<String, String> map) {
        Book book = new Book();
        book.setId(Long.parseLong(stripTag(map.get("isbn13"))));
        book.setTitle(stripTag(map.get("title")));
        book.setAuthor(stripTag(map.get("author")));
        book.setPublisher(stripTag(map.get("pub_nm")));
        try {
            book.setPublishedAt(new SimpleDateFormat("yyyyMMdd").parse(map.get("pub_date")));
        } catch (ParseException e) {
        }
        book.setPrice(Integer.parseInt(map.get("sale_price")));
        book.setCoverUrl(map.get("cover_l_url"));
        book.setLink(map.get("link"));
        book.setDescription(map.get("description"));
        return book;
    }

    private String stripTag(String str) {
        if (str == null) return null;
        return str.replace("&lt;b&gt;", "").replace("&lt;/b&gt;", "");
    }
}
