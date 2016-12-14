package geo;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CollectionApplicationTests {
    @Autowired
	private BookDao bookDao;

//	@Test
	public void contextLoads() throws Exception {
//		bookDao.findAll().forEach(book -> {
//			try {
//				book.setCoverImage(ImageUtil.getBytes(book.getCoverUrl()));
//				bookDao.save(book);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});
	}

}
