package geo;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookApi {
    public static final String API_URL = "https://apis.daum.net/search/book?apikey=6e092cd32603c577bcab91c10add989cda643f35&output=json&result=20";

    public Book get(String id) {
        return parse(getList(id).get(0), true);
    }

    public List<Book> search(String query) {
        return (List<Book>) getList(query).stream()
                .filter(m -> StringUtils.isNotBlank((String) m.get("isbn13")))
                .map(this::parse).collect(Collectors.toList());
    }

    private List<Map> getList(String query) {
        Map response = new RestTemplate().getForObject(API_URL + "&q=" + query, Map.class);
        return (List<Map>) ((Map) response.get("channel")).get("item");
    }

    private Book parse(Map<String, String> map) {
        return parse(map, false);
    }

    private Book parse(Map<String, String> map, boolean forSave) {
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
        try {
            if (forSave) book.setCoverImage(ImageUtil.getBytes(map.get("cover_l_url")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        book.setLink(map.get("link"));
        book.setDescription(map.get("description"));
        return book;
    }

    private String stripTag(String str) {
        if (str == null) return null;
        return str.replace("&lt;b&gt;", "").replace("&lt;/b&gt;", "");
    }
}
